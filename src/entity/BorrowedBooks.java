package entity;

public class BorrowedBooks {
	private String book_id;
	private String user_name;
	private int number_of_borrowed_books;

	public BorrowedBooks() {
		//
	}

	public BorrowedBooks(String book_id, String user_name, int number_of_borrowed_books) {
		this.book_id = book_id;
		this.user_name = user_name;
		this.number_of_borrowed_books = number_of_borrowed_books;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public int getNumber_of_borrowed_books() {
		return number_of_borrowed_books;
	}

	public void setNumber_of_borrowed_books(int number_of_borrowed_books) {
		this.number_of_borrowed_books = number_of_borrowed_books;
	}

	public String toString() {
		return "ID: " + this.book_id + ", Username: " + this.user_name + ", Number of borrowed books: "
				+ this.number_of_borrowed_books;
	}

}
