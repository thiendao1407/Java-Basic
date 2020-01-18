package view;

import static view.Main.bufferedReader;

import java.io.IOException;

import account.Account;
import controller.AccountControl;
import controller.BookControl;
import controller.TransactionControl;
import exception.FailedLoginException;

public class MemberView {

	public void showMenu(Account account) throws FailedLoginException {
		BookControl bookControl = new BookControl();
		TransactionControl transactionControl = new TransactionControl();
		AccountControl accountControl = new AccountControl();

		int yourChoice = -1;

		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Search books" + "\n2. Pay bill"
					+ "\n3. Change your information" + "\n0. Back" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					break;

				case 1:
					showSearchBookMenu(bookControl);
					break;

				case 2:
					transactionControl.payBills(account.getAccount_id());
					break;
				case 3:
					accountControl.changeYourInformation(account);
					break;

				default:
					throw new IllegalArgumentException("Please choose the correct options.");
				}
			} catch (IllegalArgumentException | IOException e) {
				e.printStackTrace();
			}
		}

		return;
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
					throw new IllegalArgumentException("Please choose the correct options.");
				}

			} catch (IOException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

	}
}
