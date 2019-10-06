import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Start start = new Start();
		ArrayList<Book> listBooks = start.retrieveListBooksData();
		ArrayList<BorrowedBooks> listBorrowedBooks = start.retrieveListBorrowedBooksData();
		Reader reader = new Reader();

		boolean isOk = false;

		while (isOk == false) {
			System.out.println("----------------\n1. Search books");
			System.out.println("2. Borrow books");
			System.out.println("3. Return books");
			System.out.println("0. Back");
			System.out.print("Your choice: ");
			int yourChoice = reader.scanner.nextInt();
			reader.scanner.nextLine();

			switch (yourChoice) {
			case 0:
				isOk = true;
				break;

			case 1:
				reader.searchBooks(listBooks, listBorrowedBooks);
				break;

			case 2:
				reader.borrowBooks(listBooks, listBorrowedBooks);
				break;

			case 3:
				reader.returnBooks(listBooks, listBorrowedBooks);
				break;
			}
		}

		Start.main(new String[] {});

	}

	public int availableBooks(String bookId, ArrayList<Book> listBooks, ArrayList<BorrowedBooks> listBorrowedBooks) {
		int total = 0;
		int borrowed = 0;
		for (Book book : listBooks) {
			if (bookId.equals(book.getBookId())) {
				total += book.getNumberOfBooks();
			}
		}

		for (BorrowedBooks borrowedBooks : listBorrowedBooks) {
			if (bookId.equals(borrowedBooks.getBookId())) {
				borrowed += borrowedBooks.getNumberOfBorrowedBooks();
			}
		}

		return total - borrowed;

	}

	public void searchBooks(ArrayList<Book> listBooks, ArrayList<BorrowedBooks> listBorrowedBooks) {
		int yourChoice = -1;
		while (yourChoice != 0) {
			System.out.println("Please choose an option: ");
			System.out.println("1. Search by id");
			System.out.println("2. Search by name");
			System.out.println("3. Search by author");
			System.out.println("0. Back");
			System.out.print("Your choice: ");
			yourChoice = scanner.nextInt();
			scanner.nextLine();

			switch (yourChoice) {
			case 0:
				break;

			case 1:
				System.out.print("ID of book (No full ID required): ");
				String REGEX = ".*" + scanner.nextLine() + ".*";
				Pattern pattern = Pattern.compile(REGEX);
				for (Book book : listBooks) {
					Matcher matcher = pattern.matcher(book.getBookId());
					if (matcher.matches())
						System.out.println(book);
				}
				break;

			case 2:
				System.out.print("Name of book (No full name required): ");
				REGEX = ".*" + scanner.nextLine() + ".*";
				pattern = Pattern.compile(REGEX);
				for (Book book : listBooks) {
					Matcher matcher = pattern.matcher(book.getBookName());
					if (matcher.matches())
						System.out.println(book);
				}
				break;

			case 3:
				System.out.print("Author of book (No full name required): ");
				REGEX = ".*" + scanner.nextLine() + ".*";
				pattern = Pattern.compile(REGEX);
				for (Book book : listBooks) {
					Matcher matcher = pattern.matcher(book.getAuthor());
					if (matcher.matches())
						System.out.println(book);
				}
				break;
			}
		}

		return;
	}

	public void borrowBooks(ArrayList<Book> listBooks, ArrayList<BorrowedBooks> listBorrowedBooks) {
		Reader reader = new Reader();
		Start start = new Start();
		int yourChoice;

		while (true) {

			// Print the list of books
			System.out.println("\nThe list of book: ");
			for (Book book : listBooks) {
				System.out.println("[" + book.getBookId() + ", " + book.getBookName() + ", " + book.getAuthor() + ", "
						+ book.getPublishYear() + ", remaining: "
						+ reader.availableBooks(book.getBookId(), listBooks, listBorrowedBooks) + "]");
			}

			System.out.print("\nID of book you want to borrow: ");
			String bookId = scanner.nextLine();
			boolean isOk = false;

			for (Book book : listBooks) {
				if (bookId.equals(book.getBookId())) {

					if (reader.availableBooks(bookId, listBooks, listBorrowedBooks) == 0) {
						System.out.println("\nAll books have been borrowed...");
						return;
					}

					System.out.print("\nHow many books do you want to borrow?" + " " + "Or enter \"0\" to go back...");
					yourChoice = scanner.nextInt();
					scanner.nextLine();

					if (yourChoice == 0) {
						return;

					} else if (yourChoice > reader.availableBooks(bookId, listBooks, listBorrowedBooks)
							|| yourChoice < 0) {
						System.out.println("\nThe number is not valid!...");
						isOk = true;
						break;

					} else {
						for (BorrowedBooks borrowedBooks : listBorrowedBooks) {
							if (start.getCurrentUserName().equals(borrowedBooks.getUserName())
									&& bookId.equals(borrowedBooks.getBookId())) {
								borrowedBooks.setNumberOfBorrowedBooks(yourChoice);
								reader.updateBorrowedRecord(borrowedBooks.getNumberOfBorrowedBooks(),
										borrowedBooks.getBookId(), borrowedBooks.getUserName());
								isOk = true;
								System.out.println();
								break;
							}
						}

						if (isOk == false) {
							BorrowedBooks borrowedBooks = new BorrowedBooks(bookId, book.getBookName(),
									start.getCurrentUserName(), yourChoice);
							listBorrowedBooks.add(borrowedBooks);
							reader.insertBorrowedRecord(borrowedBooks.getBookId(), borrowedBooks.getBookName(),
									borrowedBooks.getUserName(), borrowedBooks.getNumberOfBorrowedBooks());
							System.out.println();
							isOk = true;
						}

						break;
					}

				} // end if

			} // end for loop

			if (isOk == false) {
				System.out.println("\nThe ID does not exist...\n");
			}

			do {
				System.out.println("\nDo you want to borrow another book?");
				System.out.println("1. Yes");
				System.out.print("0. No -> Your choice: ");
				yourChoice = scanner.nextInt();
				scanner.nextLine();
			} while (yourChoice != 0 && yourChoice != 1);

			if (yourChoice == 0) {
				return;
			}

		} // end while loop
	}// end method

	public void returnBooks(ArrayList<Book> listBooks, ArrayList<BorrowedBooks> listBorrowedBooks) {

		Reader reader = new Reader();
		Start start = new Start();
		int yourChoice;

		while (true) {

			// Check whether you borrowed any book and print the list
			int icount = 0;
			System.out.println("\nThe list of books you have borrowed...");
			for (BorrowedBooks borrowedBooks : listBorrowedBooks) {
				if (start.getCurrentUserName().equals(borrowedBooks.getUserName())) {
					System.out.println(borrowedBooks);
					icount++;
				}
			}

			if (icount == 0) {
				System.out.println("\nYou have not borrowed any book...");
				System.out.println();
				return;
			}

			// Return a book
			System.out.print("\nID of book you want to return: ");
			String bookId = scanner.nextLine();
			boolean isOk = false;

			for (BorrowedBooks borrowedBooks : listBorrowedBooks) {
				if (bookId.equals(borrowedBooks.getBookId())
						&& start.getCurrentUserName().equals(borrowedBooks.getUserName())) {

					isOk = true;
					System.out.print("How many books you want to return?" + " " + "Or enter \"0\" to go back...");
					yourChoice = scanner.nextInt();

					if (yourChoice == 0) {
						System.out.println();
						return;

					} else if (yourChoice > borrowedBooks.getNumberOfBorrowedBooks() || yourChoice < 0) {
						System.out.println("\nThe number is not valid!...");
						break;

					} else if (yourChoice == borrowedBooks.getNumberOfBorrowedBooks()) {
						listBorrowedBooks.remove(borrowedBooks);
						reader.deleteBorrowedRecord(borrowedBooks.getBookId(), borrowedBooks.getUserName());
						break;

					} else if (yourChoice < borrowedBooks.getNumberOfBorrowedBooks()) {
						borrowedBooks.setNumberOfBorrowedBooks(-yourChoice);
						reader.updateBorrowedRecord(borrowedBooks.getNumberOfBorrowedBooks(), borrowedBooks.getBookId(),
								borrowedBooks.getUserName());
						break;
					}
					do {
						System.out.println("\nDo you want to return another book?");
						System.out.println("1. Yes");
						System.out.print("0. No -> Your choice: ");
						yourChoice = scanner.nextInt();
						scanner.nextLine();
					} while (yourChoice != 0 && yourChoice != 1);
					if (yourChoice == 0) {
						return;
					}
				} // end if
			} // end for loop

			if (isOk == false) {
				System.out.println("\nThe ID does not exist in the list of books you have borrowed...");
			}

			do {
				System.out.println("\nDo you want to return another book?");
				System.out.println("1. Yes");
				System.out.print("0. No -> Your choice: ");
				yourChoice = scanner.nextInt();
				scanner.nextLine();
			} while (yourChoice != 0 && yourChoice != 1);

			if (yourChoice == 0) {
				return;
			}
		} // end while

	}

	public void insertBorrowedRecord(String bookId, String bookName, String userName, int numberOfBorrowedBooks) {
		// JDBC driver name and database URL
		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
//			System.out.println("Connected database successfully...");
			// Execute a query
//			System.out.println("Inserting record into the table...");
			stmt = conn.createStatement();

			String sql = "INSERT INTO borrowedbooks VALUES ('" + bookId + "', '" + bookName + "', '" + userName + "', '"
					+ numberOfBorrowedBooks + "')";
			stmt.executeUpdate(sql);
			System.out.println("Inserted record into the table successfully...");

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

	}

	public void deleteBorrowedRecord(String bookId, String userName) {
		// JDBC driver name and database URL
		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
//			System.out.println("Connected database successfully...");
			// Execute a query
//			System.out.println("Deleting record in the table...");
			stmt = conn.createStatement();

			String sql = "DELETE FROM borrowedbooks WHERE bookId = '" + bookId + "' and userName = '" + userName + "'";
			stmt.executeUpdate(sql);
			System.out.println("Deleted record in the table successfully...");

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

	}

	public void updateBorrowedRecord(int numberOfBorrowedBooks, String bookId, String userName) {
		// JDBC driver name and database URL
		Connection conn = null;
		Statement stmt = null;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
//			System.out.println("Connected database successfully...");
			// Execute a query
//			System.out.println("Updating record into the table...");
			stmt = conn.createStatement();

			String sql = "UPDATE borrowedbooks " + " SET numberOfBorrowedBooks = " + numberOfBorrowedBooks
					+ " WHERE bookId = '" + bookId + "' and userName = '" + userName + "'";
			stmt.executeUpdate(sql);
			System.out.println("Updated record into the table successfully...");

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

	}

}
