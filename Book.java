import java.util.Scanner;

public class Book {
	private int numberOfBooks, publishYear;
	private String bookId, bookName, author;
	Scanner scanner = new Scanner(System.in);

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getNumberOfBooks() {
		return numberOfBooks;
	}

	public void setNumberOfBooks(int numberOfBooks) {
		this.numberOfBooks = numberOfBooks;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Book(String bookId, String bookName, String author, int publishYear, int numberOfBooks) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.author = author;
		this.publishYear = publishYear;
		this.numberOfBooks = numberOfBooks;
	}

	public Book() {

	}

	public void eterBook() {
		System.out.print("ID of book: ");
		bookId = scanner.nextLine();
		System.out.print("Name of book: ");
		bookName = scanner.nextLine();
		System.out.print("The author: ");
		author = scanner.nextLine();
		System.out.print("The publish year: ");
		publishYear = scanner.nextInt();
		System.out.print("The number of books: ");
		numberOfBooks = scanner.nextInt();
	}

	public void eterBook(String bookId) {
		System.out.print("Name of book: ");
		bookName = scanner.nextLine();
		this.bookId = bookId;
		System.out.print("The author: ");
		author = scanner.nextLine();
		System.out.print("The publish year: ");
		publishYear = scanner.nextInt();
		System.out.print("The number of books: ");
		numberOfBooks = scanner.nextInt();
	}

	public String toString() {
		return "ID: " + this.bookId + ", Book name: " + this.bookName + ", Author: " + this.author + ", Publish year: "
				+ this.publishYear + ", Number of books: " + this.numberOfBooks;

	}
}
