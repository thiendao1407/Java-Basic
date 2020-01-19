package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

import book.Book;
import book.BookFactory;
import connectionpool.DataSource;
import controller.MySqlInputValidityControl;
import exception.ValueNotFoundException;

public class BookRepository implements MySqlInputValidityControl {

	@Override
	public void checkSqlInputValidity(String... val) {
		for (String a : val) {
			if (a.matches("(.*)[=;'\"](.*)")) {
				throw new IllegalArgumentException("The values could not contain = ' \" and ;");
			}
		}
	}

	public Book getBook(String book_id) throws SQLException, ValueNotFoundException {
		checkSqlInputValidity(book_id);

		final String sql = "SELECT * FROM book " + "WHERE book_id = ?";

		Book book = null;

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					book = mapResultSetToBook(rs);
				} else
					throw new ValueNotFoundException("This ID does not exist");
			}
		}

		return book;
	}

	public boolean existBookId(String book_id) throws SQLException {
		checkSqlInputValidity(book_id);

		boolean existBookId = false;

		final String sql = "SELECT book_id FROM book " + "WHERE book_id = ?";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					existBookId = true;
				} else {
					// do nothing
				}
			}
		}

		return existBookId;
	}

	public void addBook(Book book) throws SQLException {
		final String sql = "INSERT INTO book (book_id, title, author, publish_date, number_of_books, book_status, subject, rental_fee) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			int index = 1;
			ps.setString(index++, book.getBook_id());
			ps.setString(index++, book.getTitle());
			ps.setString(index++, book.getAuthor());
			ps.setString(index++, book.getPublish_date() == null ? null : book.getPublish_date().toString());
			ps.setInt(index++, book.getNumber_of_books());
			ps.setString(index++, book.getBook_status().toString());
			ps.setString(index++, book.getSubject().toString());
			ps.setDouble(index++, book.getRentalFee());

			ps.executeUpdate();
			System.out.println("\nBook inserted successfully.");
		}

	}

	public void editBook(Book book, String oldBook_id) throws SQLException {

		checkSqlInputValidity(oldBook_id);

		final String sql = "UPDATE book SET book_id = ?, title = ?, author = ?, publish_date = ?, "
				+ "number_of_books = ?, book_status = ?, subject = ?, rental_fee  = ? WHERE (book_id = ?)";

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			int index = 1;
			ps.setString(index++, book.getBook_id());
			ps.setString(index++, book.getTitle());
			ps.setString(index++, book.getAuthor());
			ps.setString(index++, book.getPublish_date() == null ? null : book.getPublish_date().toString());
			ps.setInt(index++, book.getNumber_of_books());
			ps.setString(index++, book.getBook_status().toString());
			ps.setString(index++, book.getSubject().toString());
			ps.setDouble(index++, book.getRentalFee());
			ps.setString(index++, oldBook_id);

			ps.executeUpdate();
			System.out.println("\nBook updated successfully.");
		}
	}

	public void deleteBook(String book_id) throws SQLException {
		checkSqlInputValidity(book_id);

		final String sql = "DELETE FROM book" + " WHERE book_id = '" + book_id + "'";

		try (Connection con = DataSource.getConnection(); Statement stmt = con.createStatement()) {

			stmt.executeUpdate(sql);
			System.out.println("\nBook deleted successfully.");
		}
	}

	public LinkedHashSet<Book> searchBook(String column, String value, boolean isFixedValue) throws SQLException {
		checkSqlInputValidity(column, value);

		final String sql = isFixedValue == true ? "SELECT * FROM book WHERE " + column + " = ?"
				: "SELECT * FROM book WHERE LOWER(`" + column + "`) LIKE ?";
		LinkedHashSet<Book> setOfBooks = new LinkedHashSet<Book>();

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, value);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Book book = mapResultSetToBook(rs);
					setOfBooks.add(book);
				}
			}
		}

		return setOfBooks;
	}

	private Book mapResultSetToBook(ResultSet rs) throws SQLException {
		String book_id = rs.getString("book_id");
		String title = rs.getString("title");
		String author = rs.getString("author");
		String publish_date = rs.getString("publish_date");
		int number_of_books = rs.getInt("number_of_books");
		String book_status = rs.getString("book_status");
		String subject = rs.getString("subject");

		Book book = BookFactory.retrieveBook(subject);
		book.setBook_id(book_id);
		book.setTitle(title);
		book.setAuthor(author);
		book.setPublish_date(publish_date);
		book.setNumber_of_books(number_of_books);
		book.setBook_status(book_status);

		return book;
	}

	public String getBook_status(String book_id) throws SQLException, ValueNotFoundException {
		checkSqlInputValidity(book_id);

		final String sql = "SELECT book_id, book_status FROM book " + "WHERE book_id = ?";

		String book_status = null;

		try (Connection con = DataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, book_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					book_status = rs.getString("book_status");
				} else {
					throw new ValueNotFoundException("This ID does not exist");
				}
			}
		}

		return book_status;
	}

}
