package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

import account.Account;
import account.Account.AccountStatus;
import account.Person;
import connectionpool.DataSource;
import controller.MySqlInputValidityControl;

public class AccountRepository implements MySqlInputValidityControl {

	@Override
	public void checkSqlInputValidity(String... val) {
		for (String a : val) {
			if (a.matches("(.*)[=;'\"](.*)")) {
				throw new IllegalArgumentException("The values could not contain = ' \" and ;");
			}
		}
	}

	public boolean existAccountID(String account_id) throws SQLException {
		checkSqlInputValidity(account_id);
		boolean existAccountID = false;

		final String sql = "SELECT account_id FROM account " + "WHERE account_id = ?";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, account_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					existAccountID = true;
				} else {
					// do nothing
				}
			}
		}

		return existAccountID;
	}

	public void insertAccount(Account account) throws SQLException {
		System.out.println(account);

		final String sql = "INSERT INTO account VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, account.getAccount_id());
			ps.setString(2, account.getPassword());
			ps.setString(3, account.getAccountStatus().toString());
			ps.setString(4, account.getAccount_type().toString());
			ps.setString(5, account.getPerson().getName());
			ps.setString(6, account.getPerson().getAddress());
			ps.setString(7, account.getPerson().getEmail());
			ps.setString(8, account.getPerson().getPhone());

			ps.executeUpdate();
			System.out.println("\nAccount inserted successfully.");
		}

	}

	public Account getAccount(String account_id) throws SQLException {
		checkSqlInputValidity(account_id);

		Account account = null;

		final String sql = "SELECT * FROM account WHERE account_id = '" + account_id + "'";

		try (Connection con = DataSource.getConnection(); Statement stmt = con.createStatement()) {

			try (ResultSet rs = stmt.executeQuery(sql)) {
				if (rs.first()) {
					account = mapResultSetToAccount(rs);
				} else {
					throw new IllegalArgumentException("Account ID: '" + account_id + "' does not exist");
				}
			}
		}

		return account;
	}

	private Account mapResultSetToAccount(ResultSet rs) throws SQLException {

		String account_id = rs.getString("account_id");
		String password = rs.getString("password");
		String account_status = rs.getString("account_status");
		String account_type = rs.getString("account_type");
		String name = rs.getString("name");
		String address = rs.getString("address");
		String email = rs.getString("email");
		String phone = rs.getString("phone");
		Person person = new Person(name, address, email, phone);
		Account account = new Account(account_id, password, account_status, account_type, person);
		return account;
	}

	public void updateAccount(Account account) throws SQLException {
		final String sql = "UPDATE account SET password = ?, account_status = ?, account_type = ?, name = ?, "
				+ "address = ?, email = ?, phone  = ? WHERE (account_id = ?)";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, account.getPassword());
			ps.setString(2, account.getAccountStatus().toString());
			ps.setString(3, account.getAccount_type().toString());
			ps.setString(4, account.getPerson().getName());
			ps.setString(5, account.getPerson().getAddress());
			ps.setString(6, account.getPerson().getEmail());
			ps.setString(7, account.getPerson().getPhone());
			ps.setString(8, account.getAccount_id());

			ps.executeUpdate();
			System.out.println("\nAccount updated successfully.");
		}
	}

	public void updateAccount(String account_id, AccountStatus accountStatus) throws SQLException {
		checkSqlInputValidity(account_id);

		final String sql = "UPDATE account SET account_status = ? WHERE (account_id = ?)";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, accountStatus.toString());
			ps.setString(2, account_id);

			ps.executeUpdate();
			System.out.println("\nAccount status updated successfully.");
		}
	}

	public LinkedHashSet<Account> searchAccount(String column, String value, boolean isFixedValue) throws SQLException {
		checkSqlInputValidity(column, value);

		final String sql = isFixedValue == true ? "SELECT * FROM account WHERE " + column + " = ?"
				: "SELECT * FROM account WHERE " + column + " LIKE ?";

		LinkedHashSet<Account> setOfAccounts = new LinkedHashSet<Account>();

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, value);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Account account = mapResultSetToAccount(rs);
					setOfAccounts.add(account);
				}
			}
		}

		return setOfAccounts;
	}

}
