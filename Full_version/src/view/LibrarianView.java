package view;

import static view.Main.bufferedReader;
//import static controller.AccessControl.con;

import java.io.IOException;

import account.Account;
import controller.AccountControl;
import controller.BookControl;
import controller.TransactionControl;
import exception.FailedLoginException;

public class LibrarianView {

	public void showMenu(Account account) throws FailedLoginException {

		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Manage book" + "\n2. Manage account"
					+ "\n3. Manage transaction" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					showBookManagementMenu();
					break;

				case 2:
					showAccountManagementMenu(account);
					break;
				case 3:
					showTransactionManagementMenu();
					break;

				default:
					System.out.println("\nPlease choose the correct options.");

				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void showBookManagementMenu() {

		BookControl bookControl = new BookControl();
		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Add book" + "\n2. Edit book" + "\n3. Delete book"
					+ "\n4. Search book" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					bookControl.addBook();
					break;

				case 2:
					bookControl.editBook();
					break;

				case 3:
					bookControl.deleteBook();
					break;

				case 4:
					showSearchBookMenu(bookControl);
					break;

				default:
					System.out.println("\nPlease choose the correct options.");

				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void showSearchBookMenu(BookControl bookControl) {
		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Search book by title" + "\n2. Search book by author"
					+ "\n3. Search book by subject" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					bookControl.searchBookByTitle();
					break;

				case 2:
					bookControl.searchBookByAuthor();
					break;
				case 3:
					bookControl.searchBookBySubject();
					break;

				default:
					System.out.println("\nPlease choose the correct options.");
				}

			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void showAccountManagementMenu(Account account) throws FailedLoginException {
		AccountControl accountControl = new AccountControl();
		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Change your information" + "\n2. Change accounts status"
					+ "\n3. Search account" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					accountControl.changeYourInformation(account);
					break;

				case 2:
					accountControl.changeAccountStatus(account);
					break;
				case 3:
					showSearchAccountMenu(accountControl);
					break;

				default:
					System.out.println("\nPlease choose the correct options.");
				}

			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void showSearchAccountMenu(AccountControl accountControl) {
		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Search account by name" + "\n2. Search account by status"
					+ "\n3. Search account by type" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					accountControl.searchAccountByName();
					break;

				case 2:
					accountControl.searchAccountByStatus();
					break;
				case 3:
					accountControl.searchAccountByType();
					break;

				default:
					System.out.println("\nPlease choose the correct options.");
				}

			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void showTransactionManagementMenu() {
		TransactionControl transactionControl = new TransactionControl();
		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println(
					"----------------" + "\n1. Issue book" + "\n2. Return book" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					transactionControl.createTransaction();
					break;

				case 2:
					transactionControl.updateTransactionAndCreateBill();
					break;

				default:
					System.out.println("\nPlease choose the correct options.");
				}

			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
