package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashSet;

import connectionpool.DataSource;
import controller.MySqlInputValidityControl;
import exception.ValueNotFoundException;
import transaction.Bill;
import transaction.Bill.BillStatus;
import transaction.Transaction;

public class TransactionRepository implements MySqlInputValidityControl {

	@Override
	public void checkSqlInputValidity(String... val) {
		for (String a : val) {
			if (a.matches("(.*)[=;'\"](.*)")) {
				throw new IllegalArgumentException("The values could not contain = ' \" and ;");
			}
		}
	}

	public void createTransaction(String account_id, String book_id, int number) throws SQLException {
		checkSqlInputValidity(account_id, book_id);

		final String sql = "INSERT INTO transaction (account_id, book_id, transaction_date, issued_books, unreturned_books) "
				+ "VALUES (?, ?, ?, ?, ?)";

		Connection con = null;
		try {
			con = DataSource.getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				int index = 1;
				ps.setString(index++, account_id);
				ps.setString(index++, book_id);
				ps.setString(index++, LocalDate.now().toString());
				ps.setInt(index++, number);
				ps.setInt(index++, number);

				ps.executeUpdate();
				System.out.println("\nTransaction inserted successfully.");
			}
		} finally {
			DataSource.returnConnection(con);
		}

	}

	public void updateTransactionAndCreateBill(int yourTransaction_id, int number) throws SQLException {
		final String sql_update_transaction = "UPDATE transaction SET unreturned_books = unreturned_books - ? WHERE transaction_id = ?";

		final String sql_get_bill_instance = "SELECT account_id, transaction_id, transaction_date, b.rental_fee "
				+ "FROM book AS b RIGHT JOIN transaction AS t ON b.book_id = t.book_id where transaction_id = ?";

		final String sql_insert_bill = "INSERT INTO bill (account_id, transaction_id, bill_date, "
				+ "transaction_date, returned_books, rental_fee, amount, bill_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		Bill bill = null;

		Connection con = null;
		try {
			con = DataSource.getConnection();
			try (PreparedStatement ps1 = con.prepareStatement(sql_update_transaction);
					PreparedStatement ps2 = con.prepareStatement(sql_get_bill_instance);
					PreparedStatement ps3 = con.prepareStatement(sql_insert_bill)) {
				// Update transaction
				ps1.setInt(1, number);
				ps1.setInt(2, yourTransaction_id);

				ps1.executeUpdate();
				System.out.println("\nTransaction updated sucessfully.");

				// Create bill
				ps2.setInt(1, yourTransaction_id);
				try (ResultSet rs = ps2.executeQuery()) {
					if (rs.next()) {
						bill = mapResultSetToBill(rs, number);
					}
				}
				int index = 1;
				ps3.setString(index++, bill.getAccount_id());
				ps3.setInt(index++, bill.getTransaction_id());
				ps3.setString(index++, bill.getBill_date().toString());
				ps3.setString(index++, bill.getTransaction_date().toString());
				ps3.setInt(index++, bill.getReturned_books());
				ps3.setDouble(index++, bill.getRental_fee());
				ps3.setDouble(index++, bill.getAmount());
				ps3.setString(index++, bill.getBill_status().toString());

				ps3.executeUpdate();
				System.out.println("\nBill inserted successfully.");
			}
		} finally {
			DataSource.returnConnection(con);
		}
	}

	public LinkedHashSet<Transaction> getSetOfOpenTransactions(String account_id) throws SQLException {
		checkSqlInputValidity(account_id);

		final String sql = "SELECT * FROM transaction WHERE account_id = ? AND unreturned_books > 0";
		LinkedHashSet<Transaction> setOfTransactions = new LinkedHashSet<Transaction>();

		Connection con = null;
		try {
			con = DataSource.getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, account_id);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Transaction transaction = mapResultSetToTransaction(rs);
						setOfTransactions.add(transaction);
					}
				}
			}
		} finally {
			DataSource.returnConnection(con);
		}

		return setOfTransactions;
	}

	public LinkedHashSet<Bill> getUnpaidBill(String account_id) throws SQLException {
		checkSqlInputValidity(account_id);

		final String sql = "SELECT * FROM bill WHERE account_id = ? AND bill_status = 'UNPAID'";
		LinkedHashSet<Bill> setOfBills = new LinkedHashSet<Bill>();

		Connection con = null;
		try {
			con = DataSource.getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, account_id);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Bill bill = mapResultSetToBill(rs);
						setOfBills.add(bill);
					}
				}
			}
		} finally {
			DataSource.returnConnection(con);
		}
		return setOfBills;
	}

	public void payBills(String account_id) throws SQLException {
		checkSqlInputValidity(account_id);

		final String sql = "UPDATE bill SET bill_status = 'PAID' WHERE account_id = ? AND bill_status = 'UNPAID'";

		Connection con = null;
		try {
			con = DataSource.getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, account_id);
				ps.executeUpdate();
				System.out.println("\nBill paid successfully.");
			}
		} finally {
			DataSource.returnConnection(con);
		}
	}

	public int getNumberOfRemainingBook(String book_id) throws ValueNotFoundException, SQLException {
		checkSqlInputValidity(book_id);

		final String sql = "SELECT b.book_id, number_of_books - IFNULL(SUM(unreturned_books), 0) AS remaining_books"
				+ " FROM book AS b LEFT JOIN transaction AS t ON b.book_id = t.book_id "
				+ "WHERE b.book_id = ? GROUP BY b.book_id";

		Connection con = null;
		try {
			con = DataSource.getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, book_id);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.first()) {
						return rs.getInt("remaining_books");
					} else
						throw new ValueNotFoundException("This ID: " + book_id + "does not exists");
				}
			}
		} finally {
			DataSource.returnConnection(con);
		}
	}

	private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
		int transaction_id = rs.getInt("transaction_id");
		String account_id = rs.getString("account_id");
		String book_id = rs.getString("book_id");
		String transaction_date = rs.getString("transaction_date");
		int issued_books = rs.getInt("issued_books");
		int unreturned_books = rs.getInt("unreturned_books");

		Transaction transaction = new Transaction(transaction_id, account_id, book_id, transaction_date, issued_books,
				unreturned_books);

		return transaction;
	}

	private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
		int bill_id = rs.getInt("bill_id");
		String account_id = rs.getString("account_id");
		int transaction_id = rs.getInt("transaction_id");
		LocalDate bill_date = LocalDate.parse(rs.getString("bill_date"));
		LocalDate transaction_date = LocalDate.parse(rs.getString("transaction_date"));
		int returned_books = rs.getInt("returned_books");
		double rental_fee = rs.getDouble("rental_fee");
		BillStatus bill_status = BillStatus.UNPAID;

		Bill bill = new Bill(bill_id, account_id, transaction_id, bill_date, transaction_date, returned_books,
				rental_fee, bill_status);

		return bill;
	}

	private Bill mapResultSetToBill(ResultSet rs, int number) throws SQLException {

		int bill_id = 0; // symbolic value
		String account_id = rs.getString("account_id");
		int transaction_id = rs.getInt("transaction_id");
		LocalDate bill_date = LocalDate.now();
		LocalDate transaction_date = LocalDate.parse(rs.getString("transaction_date"));
		int returned_books = number;
		double rental_fee = rs.getDouble("rental_fee");
		BillStatus bill_status = BillStatus.UNPAID;

		Bill bill = new Bill(bill_id, account_id, transaction_id, bill_date, transaction_date, returned_books,
				rental_fee, bill_status);

		return bill;
	}
}
