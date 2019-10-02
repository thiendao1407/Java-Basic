import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Start {
	static ArrayList<Book> listBooks = new ArrayList<Book>();
	static ArrayList<BorrowedBooks> listBorrowedBooks = new ArrayList<BorrowedBooks>();
	static Scanner scanner = new Scanner(System.in);
	static String userName = null;
	static final String url = "jdbc:mysql://localhost:3306/hello";
	static final String username = "root";
	static final String password = "1234";
	static boolean isOkStart = false;

	public static void main(String[] args) {

		while (true) {
			System.out.println("----------------\n1. Admin");
			System.out.println("2. Reader");
			System.out.println("0. Exit");
			System.out.print("Your choice: ");
			int yourChoice = scanner.nextInt();
			scanner.nextLine();

			switch (yourChoice) {
			case 0:
				System.out.println("Goodbye!");
				scanner.close();
				System.exit(0);
				break;

			case 1:
				Start.loginAsAdmin();
				if (isOkStart) {
					Start.isOkStart = false;
					Admin.main(new String[] {});
				}
				break;

			case 2:
				Start.loginAsReader();
				if (isOkStart) {
					Start.isOkStart = false;
					Reader.main(new String[] {});
				}
				break;
			}
		}

	}// end main

	public static void loginAsAdmin() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected database successfully...");

			// Execute a query
			System.out.println();
			stmt = conn.createStatement();

			// Login as admin
			System.out.print("\nPlease enter your username: ");
			Start.userName = Start.scanner.nextLine();
			System.out.print("Please enter your password: ");
			String pass = Start.scanner.nextLine();
			String sql = "SELECT * FROM login WHERE userName = '" + Start.userName + "' and password = '" + pass
					+ "' and permissions = 'admin';";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Start.isOkStart = true;
			} else {
				System.out.println("\nIncorrect username and/or password! Please try again\n");
			}
			rs.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public static void loginAsReader() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected database successfully...");

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
				Start.isOkStart = true;
			} else {
				System.out.println("Incorrect username and/or password! Please try again\n");
			}
			rs.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public static void retrieveListBooksData() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected database successfully...");

			// Execute a query
			System.out.println("Retrieving data from database...");
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
				Start.listBooks.add(book);
			}
			System.out.println("Retrieved data from database successfully...");
			rs.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	public static void retrieveListBorrowedBooksData() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected database successfully...");

			// Execute a query
			System.out.println("Retrieving data from database...");
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
				Start.listBorrowedBooks.add(borrowedBooks);
			}
			System.out.println("Retrieved data from database successfully...");
			rs.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

}// end Start
