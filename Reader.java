import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

	public static void main(String[] args) {
		Start.retrieveListBooksData();
		Start.retrieveListBorrowedBooksData();

		while (true) {
			System.out.println("----------------\n1. Search books");
			System.out.println("2. Borrow books");
			System.out.println("3. Return books");
			System.out.println("0. Back");
			System.out.print("Your choice: ");
			int yourChoice = Start.scanner.nextInt();
			Start.scanner.nextLine();

			switch (yourChoice) {
			case 0:
				Start.listBooks.clear();
				Start.listBorrowedBooks.clear();
				Start.main(new String[] {});
				break;

			case 1:
				Reader.searchBooks();
				break;

			case 2:
				Reader.borrowBooks();
				break;

			case 3:
				Reader.returnBooks();
				break;

			}
		}

	}

	public static int availableBooks(String bookId) {
		int total = 0;
		int borrowed = 0;
		for (Book book : Start.listBooks) {
			if (bookId.equals(book.getBookId())) {
				total += book.getNumberOfBooks();
			}
		}

		for (BorrowedBooks borrowedBooks : Start.listBorrowedBooks) {
			if (bookId.equals(borrowedBooks.getBookId())) {
				borrowed += borrowedBooks.getNumberOfBorrowedBooks();
			}
		}

		return total - borrowed;

	}

	public static void searchBooks() {
		while (true) {
			System.out.println("Please choose an option: ");
			System.out.println("1. Search by id");
			System.out.println("2. Search by name");
			System.out.println("3. Search by author");
			System.out.println("0. Back");
			System.out.print("Your choice: ");
			int yourChoice = Start.scanner.nextInt();
			Start.scanner.nextLine();

			switch (yourChoice) {
			case 0:
				Reader.main(new String[] {});
				break;

			case 1:
				System.out.print("ID of book (No full ID required): ");
				String REGEX = ".*" + Start.scanner.nextLine() + ".*";
				Pattern pattern = Pattern.compile(REGEX);
				for (Book book : Start.listBooks) {
					Matcher matcher = pattern.matcher(book.getBookId());
					if (matcher.matches())
						System.out.println(book);
				}
				break;

			case 2:
				System.out.print("Name of book (No full name required): ");
				REGEX = ".*" + Start.scanner.nextLine() + ".*";
				pattern = Pattern.compile(REGEX);
				for (Book book : Start.listBooks) {
					Matcher matcher = pattern.matcher(book.getBookName());
					if (matcher.matches())
						System.out.println(book);
				}
				break;

			case 3:
				System.out.print("Author of book (No full name required): ");
				REGEX = ".*" + Start.scanner.nextLine() + ".*";
				pattern = Pattern.compile(REGEX);
				for (Book book : Start.listBooks) {
					Matcher matcher = pattern.matcher(book.getAuthor());
					if (matcher.matches())
						System.out.println(book);
				}
				break;
			}
		}
	}

	public static void borrowBooks() {
		label1: while (true) {
			// Print the list of books
			System.out.println("\nThe list of book: ");
			for (Book book : Start.listBooks) {
				System.out.println("[" + book.getBookId() + ", " + book.getBookName() + ", " + book.getAuthor() + ", "
						+ book.getPublishYear() + ", remaining: " + Reader.availableBooks(book.getBookId()) + "]");
			}

			System.out.print("\nID of book you want to borrow: ");
			String bookId = Start.scanner.nextLine();

			for (Book book : Start.listBooks) {
				if (bookId.equals(book.getBookId())) {

					if (Reader.availableBooks(bookId) == 0) {
						System.out.println("\nAll books have been borrowed...");
						return;
					}

					System.out.print("\nHow many books do you want to borrow?" + " " + "Or enter \"0\" to go back...");
					int yourChoice = Start.scanner.nextInt();
					Start.scanner.nextLine();

					if (yourChoice == 0) {
						System.out.println();
						return;
					} else if (yourChoice > Reader.availableBooks(bookId) || yourChoice < 0) {
						System.out.println("\nThe number is not valid!...");
					} else {
						boolean isOk = false;
						for (BorrowedBooks borrowedBooks : Start.listBorrowedBooks) {
							if (Start.userName.equals(borrowedBooks.getUserName())
									&& bookId.equals(borrowedBooks.getBookId())) {
								borrowedBooks.setNumberOfBorrowedBooks(yourChoice);
								Reader.updateBorrowedRecord(borrowedBooks.getNumberOfBorrowedBooks(),
										borrowedBooks.getBookId(), borrowedBooks.getUserName());
								isOk = true;
								System.out.println();
								break;
							}
						}
						if (!isOk) {
							BorrowedBooks borrowedBooks = new BorrowedBooks(bookId, book.getBookName(), Start.userName,
									yourChoice);
							Start.listBorrowedBooks.add(borrowedBooks);
							Reader.insertBorrowedRecord(borrowedBooks.getBookId(), borrowedBooks.getBookName(),
									borrowedBooks.getUserName(), borrowedBooks.getNumberOfBorrowedBooks());
							System.out.println();
						}

					}
					do {
						System.out.println("\nDo you want to borrow another book?");
						System.out.println("1. Yes");
						System.out.print("0. No -> Your choice: ");
						yourChoice = Start.scanner.nextInt();
						Start.scanner.nextLine();
					} while (yourChoice != 0 && yourChoice != 1);
					if (yourChoice == 0) {
						return;
					} else
						continue label1;
				} // end if
			} // end for loop

			System.out.println("\nThe ID does not exist...");
			System.out.println();
			return;

		}
	}// end method

	public static void returnBooks() {
		label1: while (true) {
			// Check whether you borrowed any book and print the list
			boolean isOk = false;
			System.out.println("\nThe list of books you borrowed...");
			for (BorrowedBooks borrowedBooks : Start.listBorrowedBooks) {
				if (Start.userName.equals(borrowedBooks.getUserName())) {
					System.out.println(borrowedBooks);
					isOk = true;
				}
			}

			if (!isOk) {
				System.out.println("\nYou have not borrowed any book...");
				System.out.println();
				return;
			}

			// Return a book
			System.out.print("\nID of book you want to return: ");
			String bookId = Start.scanner.nextLine();

			for (BorrowedBooks borrowedBooks : Start.listBorrowedBooks) {
				if (bookId.equals(borrowedBooks.getBookId()) && Start.userName.equals(borrowedBooks.getUserName())) {

					System.out.print("How many books you want to return?" + " " + "Or enter \"0\" to go back...");
					int yourChoice = Start.scanner.nextInt();

					if (yourChoice == 0) {
						System.out.println();
						return;
					} else if (yourChoice > borrowedBooks.getNumberOfBorrowedBooks() || yourChoice < 0) {
						System.out.println("\nThe number is not valid!...");
					} else if (yourChoice == borrowedBooks.getNumberOfBorrowedBooks()) {
						Start.listBorrowedBooks.remove(borrowedBooks);
						Reader.deleteBorrowedRecord(borrowedBooks.getBookId(), borrowedBooks.getUserName());
					} else if (yourChoice < borrowedBooks.getNumberOfBorrowedBooks()) {
						borrowedBooks.setNumberOfBorrowedBooks(-yourChoice);
						Reader.updateBorrowedRecord(borrowedBooks.getNumberOfBorrowedBooks(), borrowedBooks.getBookId(),
								borrowedBooks.getUserName());
					}
					do {
						System.out.println("\nDo you want to return another book?");
						System.out.println("1. Yes");
						System.out.print("0. No -> Your choice: ");
						yourChoice = Start.scanner.nextInt();
						Start.scanner.nextLine();
					} while (yourChoice != 0 && yourChoice != 1);
					if (yourChoice == 0) {
						return;
					} else
						continue label1;
				} // end if
			} // end for loop

			System.out.println("\nThe ID does not exist in the list of books you have borrowed...");
			System.out.println();
			return; // end method
		}

	}

	public static void insertBorrowedRecord(String bookId, String bookName, String userName,
			int numberOfBorrowedBooks) {
		// JDBC driver name and database URL
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");
			// Execute a query
			System.out.println("Inserting record into the table...");
			stmt = conn.createStatement();

			sql = "INSERT INTO borrowedbooks VALUES ('" + bookId + "', '" + bookName + "', '" + userName + "', '"
					+ numberOfBorrowedBooks + "')";
			stmt.executeUpdate(sql);
			System.out.println("Inserted record into the table successfully...");
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public static void deleteBorrowedRecord(String bookId, String userName) {
		// JDBC driver name and database URL
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");
			// Execute a query
			System.out.println("Deleting record in the table...");
			stmt = conn.createStatement();

			sql = "DELETE FROM borrowedbooks WHERE bookId = '" + bookId + "' and userName = '" + userName + "'";
			stmt.executeUpdate(sql);
			System.out.println("Deleted record in the table successfully...");
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public static void updateBorrowedRecord(int numberOfBorrowedBooks, String bookId, String userName) {
		// JDBC driver name and database URL
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");
			// Execute a query
			System.out.println("Updating record into the table...");
			stmt = conn.createStatement();

			sql = "UPDATE borrowedbooks " + " SET numberOfBorrowedBooks = " + numberOfBorrowedBooks
					+ " WHERE bookId = '" + bookId + "' and userName = '" + userName + "'";
			stmt.executeUpdate(sql);
			System.out.println("Updated record into the table successfully...");
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

}
