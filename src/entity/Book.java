package entity;

import static service.LoginScreen.bufferedReader;

import java.io.IOException;

import exception.InvalidFormatException;

public class Book {
	private int number_of_books;
	private int publish_year;
	private String book_id;
	private String book_name;
	private String author;

	public Book() {

	}

	public Book(String book_id, String book_name, String author, int publish_year, int number_of_books) {
		this.book_id = book_id;
		this.book_name = book_name;
		this.author = author;
		this.publish_year = publish_year;
		this.number_of_books = number_of_books;
	}

	public int getNumber_of_books() {
		return number_of_books;
	}

	public void setNumber_of_books(int number_of_books) {
		this.number_of_books = number_of_books;
	}

	public int getPublish_year() {
		return publish_year;
	}

	public void setPublish_year(int publish_year) {
		this.publish_year = publish_year;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String toString() {
		return "ID: " + this.book_id + ", Name: " + this.book_name + ", Author: " + this.author + ", Publish year: "
				+ this.publish_year + ", Number of books: " + this.number_of_books;

	}

	public Book fillInTheInformation(String book_id) throws IOException {

		this.book_id = book_id;

		System.out.print("Name of book: ");
		book_name = bufferedReader.readLine();

		System.out.print("Author: ");
		author = bufferedReader.readLine();

		System.out.print("Publish year: ");
		publish_year = Integer.parseInt(bufferedReader.readLine());

		System.out.print("Number of books: ");
		number_of_books = Integer.parseInt(bufferedReader.readLine());

		return this;

	}

	public Book fillInTheInformation() throws IOException {

		System.out.print("ID of book: ");
		book_id = bufferedReader.readLine();

		System.out.print("Name of book: ");
		book_name = bufferedReader.readLine();

		System.out.print("Author: ");
		author = bufferedReader.readLine();

		System.out.print("Publish year: ");
		publish_year = Integer.parseInt(bufferedReader.readLine());

		System.out.print("Number of books: ");
		number_of_books = Integer.parseInt(bufferedReader.readLine());

		return this;

	}

	public void checkTheValidityOfBook() throws InvalidFormatException {

		if (this.getBook_id().isBlank() || this.getAuthor().isBlank() || this.getBook_name().isBlank()
				|| this.getNumber_of_books() < 0) {
			throw new InvalidFormatException();
		}
	}

}
