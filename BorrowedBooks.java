
public class BorrowedBooks {

	private String bookId, bookName, userName;
	private int numberOfBorrowedBooks;

	public BorrowedBooks() {

	}

	public BorrowedBooks(String bookId, String bookName, String userName, int numberOfBorrowedBooks) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.userName = userName;
		this.numberOfBorrowedBooks = numberOfBorrowedBooks;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getNumberOfBorrowedBooks() {
		return numberOfBorrowedBooks;
	}

	public void setNumberOfBorrowedBooks(int numberOfBorrowedBooks) {
		this.numberOfBorrowedBooks += numberOfBorrowedBooks;
	}

	public String toString() {
		return "ID: " + this.bookId + ", Book name: " + this.bookName + ", Username: " + this.userName
				+ ", Number of borrowed books: " + this.numberOfBorrowedBooks;

	}

}
