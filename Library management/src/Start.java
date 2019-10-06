import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Start {

	private static Scanner scanner = new Scanner(System.in);
	private static String userName = null;
	static final String url = "jdbc:mysql://localhost:3306/hello";
	static final String username = "root";
	static final String password = "1234";

	public static void main(String[] args) {
		Start start = new Start();
		int yourChoice = -1;
		boolean isOk = false;

		while (isOk == false) {
			System.out.println("----------------\n1. Admin");
			System.out.println("2. Reader");
			System.out.println("0. Exit");
			System.out.print("Your choice: ");
			yourChoice = scanner.nextInt();
			scanner.nextLine();

			switch (yourChoice) {
			case 0:
				System.out.println("Goodbye!");
				isOk = true;
				break;

			case 1:
				isOk = start.loginAsAdmin();
				break;

			case 2:
				isOk = start.loginAsReader();
				break;
			}
		}

		if (yourChoice == 1) {
			Admin.main(new String[] {});
		}

		if (yourChoice == 2) {
			Reader.main(new String[] {});
		}

		Start.scanner.close();

	}// end main

	public String getCurrentUserName() {
		return Start.userName;
	}

	public boolean loginAsAdmin() {
		Connection conn = null;
		Statement stmt = null;
		boolean isOk = false;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
//			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
//			System.out.println("Connected database successfully...");

			// Execute a query
			System.out.println();
			stmt = conn.createStatement();

			// Login as admin
			System.out.print("Please enter your username: ");
			Start.userName = Start.scanner.nextLine();
			System.out.print("Please enter your password: ");
			String pass = Start.scanner.nextLine();
			String sql = "SELECT * FROM login WHERE userName = '" + Start.userName + "' and password = '" + pass
					+ "' and permissions = 'admin';";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				isOk = true;
			} else {
				System.out.println("\nIncorrect username and/or password! Please try again\n");
			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return isOk;

	}

	public boolean loginAsReader() {
		Connection conn = null;
		Statement stmt = null;
		boolean isOk = false;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
//			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
//			System.out.println("Connected database successfully...");

			// Execute a query
			System.out.println();
			stmt = conn.createStatement();

			// Login as reader
			System.out.print("Please enter your username: ");
			Start.userName = Start.scanner.nextLine();
			System.out.print("Please enter your password: ");
			String pass = Start.scanner.nextLine();
			String sql = "SELECT * FROM login WHERE userName = '" + Start.userName + "' and password = '" + pass
					+ "' and permissions = 'reader';";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				isOk = true;
			} else {
				System.out.println("\nIncorrect username and/or password! Please try again\n");
			}
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return isOk;

	}

	public ArrayList<Book> retrieveListBooksData() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Book> listBooks = new ArrayList<Book>();
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
//			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
//			System.out.println("Connected database successfully...");

			// Execute a query
//			System.out.println("Retrieving data from database...");
			stmt = conn.createStatement();

			// Book
			String sql = "SELECT bookId, bookName, author, publishYear, numberOfBooks FROM books";
			ResultSet rs = stmt.executeQuery(sql);
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name

				String bookId = rs.getString("bookId");
				String bookName = rs.getString("bookName");
				String author = rs.getString("author");
				int publishYear = rs.getInt("publishYear");
				int numberOfBooks = rs.getInt("numberOfBooks");
				Book book = new Book(bookId, bookName, author, publishYear, numberOfBooks);
				listBooks.add(book);
			}
//			System.out.println("Retrieved data from database successfully...");
			rs.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		return listBooks;
	}

	public ArrayList<BorrowedBooks> retrieveListBorrowedBooksData() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<BorrowedBooks> listBorrowedBooks = new ArrayList<BorrowedBooks>();

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
//			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
//			System.out.println("Connected database successfully...");

			// Execute a query
//			System.out.println("Retrieving data from database...");
			stmt = conn.createStatement();
			// BorrowedBooks
			String sql = "SELECT bookId, bookName, userName, numberOfBorrowedBooks FROM borrowedbooks";
			ResultSet rs = stmt.executeQuery(sql);
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name

				String bookId = rs.getString("bookId");
				String bookName = rs.getString("bookName");
				Start.userName = rs.getString("userName");
				int numberOfBorrowedBooks = rs.getInt("numberOfBorrowedBooks");
				BorrowedBooks borrowedBooks = new BorrowedBooks(bookId, bookName, Start.userName,
						numberOfBorrowedBooks);
				listBorrowedBooks.add(borrowedBooks);
			}
//			System.out.println("Retrieved data from database successfully...");
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources

			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return listBorrowedBooks;
	}

}// end Start
