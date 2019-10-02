import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {

	public static void main(String[] args) {
		Start.retrieveListBooksData();

		while (true) {
			System.out.println("----------------\n1. Add books");
			System.out.println("2. Edit books");
			System.out.println("3. Remove books");
			System.out.println("0. Back");
			System.out.print("Your choice: ");
			int yourChoice = Start.scanner.nextInt();
			Start.scanner.nextLine();

			switch (yourChoice) {
			case 0:
				Start.listBooks.clear();
				Start.main(new String[] {});
				break;

			case 1:
				Admin.addBooks();
				break;

			case 2:
				Admin.editBooks();
				break;

			case 3:
				Admin.removeBooks();
				break;

			}
		}
	}

	public static void editBooks() {
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("\nConnecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");
			label1: while (true) {
				System.out.print("ID of the book: ");
				String bookId = Start.scanner.next();

				for (Book bookEx : Start.listBooks) {

					if (bookId.equals(bookEx.getBookId())) {

						System.out.println("----------------\n1. ID of the book");
						System.out.println("2. Name of the book");
						System.out.println("3. The author");
						System.out.println("4. The publish year");
						System.out.println("5. The number of books");
						System.out.println("0. Back");
						System.out.print("Which field do you want to edit: ");
						int yourChoice = Start.scanner.nextInt();
						Start.scanner.nextLine();

						switch (yourChoice) {
						case 0:
							conn.close();
							Admin.main(new String[] {});
							break;

						case 1:
							System.out.print("\nThe new ID: ");
							bookId = Start.scanner.nextLine();
							boolean isOk = true;
							for (Book bookEx1 : Start.listBooks) {
								if (bookId.equals(bookEx.getBookId()) || bookId.equals(bookEx1.getBookId())) {
									System.out.println(
											"\nThe ID has already existed or the updated is the same as the old, please try again");
									isOk = false;
									break;
								}
							}

							if (isOk == true) {
								if (bookId.isBlank()) {
									System.out.println("\nThe bookId field can not be blank, please try again\n");
								} else {
									// Execute a query
									System.out.println("\nUpdating record into the table...");
									stmt = conn.createStatement();

									sql = "UPDATE  books " + "SET bookId = '" + bookId.toString() + "' WHERE id = '"
											+ bookEx.getBookId() + "'";
									stmt.executeUpdate(sql);
									System.out.println("Updated records into the table...");
									bookEx.setBookId(bookId);
								}
							}
							break;

						case 2:
							System.out.print("\nThe updated bookName: ");
							String bookName = Start.scanner.nextLine();
							isOk = true;
							if (bookName.equals(bookEx.getBookName())) {
								System.out.println("\nThe updated is the same as the old");
								isOk = false;
							}

							if (isOk == true) {
								if (bookName.isBlank()) {
									System.out.println("\nThe bookName field can not be blank, please try again");
								} else {
									// Execute a query
									System.out.println("\nUpdating record into the table...");
									stmt = conn.createStatement();

									sql = "UPDATE  books " + "SET bookName = '" + bookName.toString()
											+ "' WHERE bookId = '" + bookEx.getBookId() + "'";
									stmt.executeUpdate(sql);
									System.out.println("Updated records into the table...");
									bookEx.setBookName(bookName);
								}
							}
							break;

						case 3:
							System.out.print("\nThe updated author: ");
							String author = Start.scanner.nextLine();
							isOk = true;
							if (author.equals(bookEx.getBookName())) {
								System.out.println("\nThe updated is the same as the old");
								isOk = false;
							}

							if (isOk == true) {
								if (author.isBlank()) {
									System.out.println("\nThe author field can not be blank, please try again\n");
								} else {
									// Execute a query
									System.out.println("\nUpdating record into the table...");
									stmt = conn.createStatement();

									sql = "UPDATE  books " + "SET author = '" + author.toString() + "' WHERE bookId = '"
											+ bookEx.getBookId() + "'";
									stmt.executeUpdate(sql);
									System.out.println("Updated records into the table...");
									bookEx.setBookName(author);
								}
							}
							break;

						case 4:
							System.out.print("\nThe updated publishYear: ");
							Integer publishYear = Start.scanner.nextInt();
							Start.scanner.nextLine();
							isOk = true;
							if (publishYear.equals(bookEx.getPublishYear())) {
								System.out.println("\nThe updated is the same as the old");
								isOk = false;
							}

							if (isOk == true) {
								// Execute a query
								System.out.println("\nUpdating record into the table...");
								stmt = conn.createStatement();

								sql = "UPDATE  books " + "SET publishYear = " + publishYear.toString()
										+ " WHERE bookId = " + bookEx.getBookId() + "";
								stmt.executeUpdate(sql);
								System.out.println("Updated records into the table...");
								bookEx.setPublishYear(publishYear);
							}
							break;

						case 5:
							System.out.print("\nThe updated number of books: ");
							Integer numberOfBooks = Start.scanner.nextInt();
							Start.scanner.nextLine();
							isOk = true;
							if (numberOfBooks.equals(bookEx.getNumberOfBooks())) {
								System.out.println("\nThe updated is the same as the old");
								isOk = false;
							}

							if (isOk == true) {
								// Execute a query
								System.out.println("\nUpdating record into the table...");
								stmt = conn.createStatement();

								sql = "UPDATE  books " + "SET numberOfBooks = " + numberOfBooks.toString()
										+ " WHERE bookId = " + bookEx.getBookId() + "";
								stmt.executeUpdate(sql);
								System.out.println("Updated records into the table...");
								bookEx.setNumberOfBooks(numberOfBooks);
							}
							break;

						}// end switch
						do {
							System.out.println("\nDo you want to edit another book?");
							System.out.println("1. Yes");
							System.out.print("0. No -> Your choice: ");
							yourChoice = Start.scanner.nextInt();
							Start.scanner.nextLine();
						} while (yourChoice != 0 && yourChoice != 1);
						if (yourChoice == 0) {
							break label1;
						} else
							continue label1;
					} // end if
				} // end for loop

				System.out.println("\nThe ID does not exist");
				System.out.println();
				break label1;

			} // end while loop
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public static void removeBooks() {
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("\nConnecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");

			label1: while (true) {
				System.out.print("\nID of the book: ");
				String bookId = Start.scanner.nextLine();

				for (Book book : Start.listBooks) {
					if (bookId.equals(book.getBookId())) {
						Start.listBooks.remove(book);
						// Execute a query
						System.out.println("Deleting record in the table...");
						stmt = conn.createStatement();

						sql = "DELETE FROM books " + "WHERE bookId = " + book.getBookId() + "";
						stmt.executeUpdate(sql);
						System.out.println("Deleted record in the table successfully...");
						break;
					}
					int yourChoice;
					do {
						System.out.println("\nDo you want to remove another book?");
						System.out.println("1. Yes");
						System.out.print("0. No -> Your choice: ");
						yourChoice = Start.scanner.nextInt();
						Start.scanner.nextLine();
					} while (yourChoice != 0 && yourChoice != 1);
					if (yourChoice == 0) {
						break label1;
					} else
						continue label1;
				}

				System.out.println("The ID does not exist");
				System.out.println();
				break label1;

			}
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}

	}

	public static void addBooks() {
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			System.out.println("\nConnecting to a selected database...");
			conn = DriverManager.getConnection(Start.url, Start.username, Start.password);
			System.out.println("Connected database successfully...");

			label1: while (true) {
				System.out.print("\nID of book: ");
				String bookId = Start.scanner.nextLine();

				for (Book bookEx : Start.listBooks) {
					if (bookId.equals(bookEx.getBookId())) {
						System.out.println("\nThe ID has already existed, please try again");
						break label1;
					}
				}

				// add a book
				Book book = new Book();
				book.eterBook(bookId);

				if (book.getBookId().isBlank() || book.getAuthor().isBlank() || book.getBookName().isBlank()) {
					System.out.println("\nThe fields can not be blank, please try again");
					continue label1;
				} else {
					Start.listBooks.add(book);
					// Execute a query
					System.out.println("\nInserting record into the table...");
					stmt = conn.createStatement();

					sql = "INSERT INTO books " + "VALUES ('" + book.getBookId() + "', '" + book.getBookName() + "',"
							+ " '" + book.getAuthor() + "', " + book.getPublishYear() + ", " + book.getNumberOfBooks()
							+ ")";
					stmt.executeUpdate(sql);
					System.out.println("Inserted record into the table...");
				}

				int yourChoice;
				do {
					System.out.println("\nDo you want to borrow another book?");
					System.out.println("1. Yes");
					System.out.print("0. No -> Your choice: ");
					yourChoice = Start.scanner.nextInt();
					Start.scanner.nextLine();
				} while (yourChoice != 0 && yourChoice != 1);

				if (yourChoice == 1) {
					continue label1;
				} else {
					break label1;
				}

			} // end while loop : label1

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
