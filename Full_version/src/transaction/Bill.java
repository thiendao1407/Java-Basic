package transaction;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Bill {
	private int bill_id;
	private String account_id;
	private int transaction_id;
	private LocalDate bill_date;
	private LocalDate transaction_date;
	private int returned_books;
	private double rental_fee;
	private double amount;
	private BillStatus bill_status;

	public Bill(int bill_id, String account_id, int transaction_id, LocalDate bill_date, LocalDate transaction_date,
			int returned_books, double rental_fee, BillStatus bill_status) {
		this.bill_id = bill_id;
		this.account_id = account_id;
		this.transaction_id = transaction_id;
		this.bill_date = bill_date;
		this.transaction_date = transaction_date;
		this.returned_books = returned_books;
		this.rental_fee = rental_fee;
		this.amount = rental_fee * returned_books * transaction_date.until(bill_date, ChronoUnit.DAYS);
		this.bill_status = bill_status;

	}

	public String toString() {
		return "Bill ID: " + bill_id + ", Account ID: " + account_id + ", Transaction ID: " + transaction_id
				+ ", Bill date: " + bill_date + ", Transaction date: " + transaction_date
				+ ", Number of books returned: " + returned_books + ", Rental fee: " + rental_fee + ", Amount: "
				+ amount + ", Bill status: " + bill_status;
	}

	public enum BillStatus {
		UNPAID, PAID
	}

	public int getBill_id() {
		return bill_id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public LocalDate getBill_date() {
		return bill_date;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public LocalDate getTransaction_date() {
		return transaction_date;
	}

	public double getRental_fee() {
		return rental_fee;
	}

	public int getReturned_books() {
		return returned_books;
	}

	public double getAmount() {
		return amount;
	}

	public BillStatus getBill_status() {
		return bill_status;
	}

}
