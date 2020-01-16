package transaction;

import java.time.LocalDate;

public class Transaction {
	private int transaction_id;
	private String book_id;
	private String account_id;
	private LocalDate transaction_date;
	private int issued_books;
	private int unreturned_books;

	public Transaction(int transaction_id, String account_id, String book_id, String transaction_date, int issued_books,
			int unreturned_books) {
		this.transaction_id = transaction_id;
		this.account_id = account_id;
		this.book_id = book_id;
		this.issued_books = issued_books;
		this.unreturned_books = issued_books;
		this.transaction_date = LocalDate.parse(transaction_date);
	}

	public String toString() {
		return "Transaction ID: " + transaction_id + ", Book ID: " + book_id + ", Account ID: " + account_id
				+ ", Transaction date: " + transaction_date + ", Issued books: " + issued_books + ", Unreturned books: "
				+ unreturned_books;
	}

	public boolean isOpen() {
		return unreturned_books != 0;
	}

	public boolean isClosed() {
		return unreturned_books == 0;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public String getBook_id() {
		return book_id;
	}

	public LocalDate getTransaction_date() {
		return transaction_date;
	}

	public int getIssued_books() {
		return issued_books;
	}

	public int getUnreturned_books() {
		return unreturned_books;
	}

}
