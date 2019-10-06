import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Start start = new Start();
		ArrayList<Book> listBooks = start.retrieveListBooksData();
		Admin admin = new Admin();

		boolean isOk = false;

		while (isOk == false) {
			System.out.println("----------------\n1. Add books");
			System.out.println("2. Edit books");
			System.out.println("3. Remove books");
			System.out.println("0. Back");
			System.out.print("Your choice: ");

			int yourChoice = admin.scanner.nextInt();
			admin.scanner.nextLine();

			switch (yourChoice) {
			case 0:
				isOk = true;
				break;

			case 1:
				admin.addBooks(listBooks);
				break;

			case 2:
				admin.editBooks(listBooks);
				break;

			case 3:
				admin.removeBooks(listBooks);
				break;
			}
		} // end while

		Start.main(new String[] {});

	}

	public void editBooks(ArrayList<Book> listBooks) {
		Connection conn = null;
		Statement stmt = null;
		int yourChoice;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("\nConnecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");

			while (true) {

				// Print the list of books
				System.out.println("\nThe list of book: ");
				for (Book book : listBooks) {
					System.out.println(book);
				}

				System.out.print("ID of the book: ");
				String bookId = scanner.next();
				boolean isOk = false;

				for (Book book : listBooks) {

					if (bookId.equals(book.getBookId())) {

						isOk = true;

						System.out.println("----------------\n1. ID of the book");
						System.out.println("2. Name of the book");
						System.out.println("3. The author");
						System.out.println("4. The publish year");
						System.out.println("5. The number of books");
						System.out.println("0. Back");
						System.out.print("Which field do you want to edit: ");
						yourChoice = scanner.nextInt();
						scanner.nextLine();

						switch (yourChoice) {
						case 0:
							break;

						case 1:
							System.out.print("\nThe new ID: ");
							bookId = scanner.nextLine();

							boolean isOk2 = true;
							for (Book book2 : listBooks) {
								if (bookId.equals(book.getBookId()) || bookId.equals(book2.getBookId())) {
									System.out.println(
											"\nThe ID has already existed or the updated is the same as the old, please try again");
									isOk2 = false;
									break;
								}
							}

							if (isOk2 == true) {

								if (bookId.isBlank()) {
									System.out.println("\nThe bookId field can not be blank, please try again\n");

								} else {
									// Execute a query
//									System.out.println("\nUpdating record into the table...");
									stmt = conn.createStatement();

									String sql = "UPDATE  books " + "SET bookId = '" + bookId + "' WHERE bookId = '"
											+ book.getBookId() + "'";
									stmt.executeUpdate(sql);
									System.out.println("Updated records into the table...");
									book.setBookId(bookId);
								}

							}

							break;

						case 2:
							System.out.print("\nThe updated bookName: ");
							String bookName = scanner.nextLine();

							if (bookName.equals(book.getBookName())) {
								System.out.println("\nThe updated is the same as the old");
								break;
							}

							if (bookName.isBlank()) {
								System.out.println("\nThe bookName field can not be blank, please try again");

							} else {
								// Execute a query
								System.out.println("\nUpdating record into the table...");
								stmt = conn.createStatement();

								String sql = "UPDATE  books " + "SET bookName = '" + bookName.toString()
										+ "' WHERE bookId = '" + book.getBookId() + "'";
								stmt.executeUpdate(sql);
								System.out.println("Updated records into the table...");
								book.setBookName(bookName);
							}

							break;

						case 3:
							System.out.print("\nThe updated author: ");
							String author = scanner.nextLine();

							if (author.equals(book.getBookName())) {
								System.out.println("\nThe updated is the same as the old");
								break;
							}

							if (author.isBlank()) {
								System.out.println("\nThe author field can not be blank, please try again\n");

							} else {
								// Execute a query
								System.out.println("\nUpdating record into the table...");
								stmt = conn.createStatement();

								String sql = "UPDATE  books " + "SET author = '" + author.toString()
										+ "' WHERE bookId = '" + book.getBookId() + "'";
								stmt.executeUpdate(sql);
								System.out.println("Updated records into the table...");
								book.setBookName(author);
							}

							break;

						case 4:
							System.out.print("\nThe updated publishYear: ");
							Integer publishYear = scanner.nextInt();
							scanner.nextLine();

							if (publishYear.equals(book.getPublishYear())) {
								System.out.println("\nThe updated is the same as the old");
								break;
							}

							// Execute a query
							System.out.println("\nUpdating record into the table...");
							stmt = conn.createStatement();

							String sql = "UPDATE  books " + "SET publishYear = " + publishYear.toString()
									+ " WHERE bookId = '" + book.getBookId() + "'";
							stmt.executeUpdate(sql);
							System.out.println("Updated records into the table...");
							book.setPublishYear(publishYear);

							break;

						case 5:
							System.out.print("\nThe updated number of books: ");
							Integer numberOfBooks = scanner.nextInt();
							scanner.nextLine();

							if (numberOfBooks.equals(book.getNumberOfBooks())) {
								System.out.println("\nThe updated is the same as the old");
								break;
							}

							// Execute a query
							System.out.println("\nUpdating record into the table...");
							stmt = conn.createStatement();

							sql = "UPDATE  books " + "SET numberOfBooks = " + numberOfBooks.toString()
									+ " WHERE bookId = '" + book.getBookId() + "'";
							stmt.executeUpdate(sql);
							System.out.println("Updated records into the table...");
							book.setNumberOfBooks(numberOfBooks);

							break;
						}// end switch

						break;
					} // end if

				} // end for loop

				if (isOk == false) {
					System.out.println("The ID does not exist\n");
				}

				do {
					System.out.println("\nDo you want to edit another book?");
					System.out.println("1. Yes");
					System.out.print("0. No -> Your choice: ");
					yourChoice = scanner.nextInt();
					scanner.nextLine();
				} while (yourChoice != 0 && yourChoice != 1);

				if (yourChoice == 0) {
					break;
				}

			} // end while loop

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

	public void removeBooks(ArrayList<Book> listBooks) {
		Connection conn = null;
		Statement stmt = null;
		int yourChoice;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
//			System.out.println("\nConnecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
//			System.out.println("Connected database successfully...");

			while (true) {

				// Print the list of books
				System.out.println("\nThe list of book: ");
				for (Book book : listBooks) {
					System.out.println(book);
				}

				System.out.print("\nID of the book you want to remove: ");
				String bookId = scanner.nextLine();

				boolean isOk = false;

				for (Book book : listBooks) {
					if (bookId.equals(book.getBookId())) {

						isOk = true;
						listBooks.remove(book);

						// Execute a query
//						System.out.println("Deleting record in the table...");
						stmt = conn.createStatement();

						String sql = "DELETE FROM books " + "WHERE bookId = " + book.getBookId() + "";
						stmt.executeUpdate(sql);
						System.out.println("Deleted record in the table successfully...");
						break;
					}
				}

				if (isOk == false) {
					System.out.println("The ID does not exist\n");
				}

				do {
					System.out.println("\nDo you want to remove another book?");
					System.out.println("1. Yes");
					System.out.print("0. No -> Your choice: ");
					yourChoice = scanner.nextInt();
					scanner.nextLine();
				} while (yourChoice != 0 && yourChoice != 1);

				if (yourChoice == 0) {
					break;
				}

			} // end while

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

	public void addBooks(ArrayList<Book> listBooks) {
		Connection conn = null;
		Statement stmt = null;
		int yourChoice;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
//			System.out.println("\nConnecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
//			System.out.println("Connected database successfully...");

			while (true) {

				// Print the list of books
				System.out.println("\nThe list of book: ");
				for (Book book : listBooks) {
					System.out.println(book);
				}

				System.out.print("\nID of book you want to add: ");
				String bookId = scanner.nextLine();
				boolean isOk = false;

				for (Book book : listBooks) {
					if (bookId.equals(book.getBookId())) {
						System.out.println("\nThe ID has already existed, please try again");
						isOk = true;
						break;
					}
				}

				// add a book
				if (isOk != true) {

					Book book = new Book();
					book.eterBook(bookId);

					if (book.getBookId().isBlank() || book.getAuthor().isBlank() || book.getBookName().isBlank()) {
						System.out.println("\nThe fields can not be blank, please try again");

					} else {

						listBooks.add(book);

						// Execute a query
						System.out.println("\nInserting record into the table...");
						stmt = conn.createStatement();

						String sql = "INSERT INTO books " + "VALUES ('" + book.getBookId() + "', '" + book.getBookName()
								+ "'," + " '" + book.getAuthor() + "', " + book.getPublishYear() + ", "
								+ book.getNumberOfBooks() + ")";
						stmt.executeUpdate(sql);
						System.out.println("Inserted record into the table...");
					}
				}

				do {
					System.out.println("\nDo you want to add another book?");
					System.out.println("1. Yes");
					System.out.print("0. No -> Your choice: ");
					yourChoice = scanner.nextInt();
					scanner.nextLine();
				} while (yourChoice != 0 && yourChoice != 1);

				if (yourChoice == 0) {
					return;
				}

			} // end while loop

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
