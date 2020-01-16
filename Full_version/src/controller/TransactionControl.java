package controller;

import static view.Main.bufferedReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import account.Account;
import exception.InvalidValueException;
import exception.ValueNotFoundException;
import repository.AccountRepository;
import repository.BookRepository;
import repository.TransactionRepository;
import transaction.Bill;
import transaction.Transaction;

public class TransactionControl {

	AccountRepository accountRepository = new AccountRepository();
	BookRepository bookRepository = new BookRepository();
	TransactionRepository transactionRepository = new TransactionRepository();

	public void createTransaction() {
		try {
			System.out.println("Account ID: ");
			String account_id = bufferedReader.readLine();
			Account account = accountRepository.getAccount(account_id);
			if (account.isLibrarian())
				throw new InvalidValueException("Librarian account is not authorized to borrow books");
			if (!account.isActive())
				throw new InvalidValueException("This account is not authorized to borrow books");

			System.out.println("Book ID: ");
			String book_id = bufferedReader.readLine();
			if (bookRepository.getBook_status(book_id).equalsIgnoreCase("RESERVED")) {
				throw new InvalidValueException("This book is reserved");
			}

			int remainingBook = transactionRepository.getNumberOfRemainingBook(book_id);
			if (remainingBook == 0) {
				System.out.println("This book is unavailable");
				return;
			} else {
				System.out.println("Number of remaining books: " + remainingBook);
			}
			System.out.println("Number of books you want to issue: ");
			int number = Integer.parseInt(bufferedReader.readLine());
			if (number > remainingBook || number <= 0) {
				throw new InvalidValueException();
			}

			transactionRepository.createTransaction(account_id, book_id, number);

		} catch (IOException | ValueNotFoundException | InvalidValueException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateTransactionAndCreateBill() {
		try {
			System.out.println("Account ID: ");
			String account_id = bufferedReader.readLine();
			LinkedHashSet<Transaction> setOfTransactions = transactionRepository.getSetOfOpenTransactions(account_id);
			if (setOfTransactions.isEmpty()) {
				System.out.println("This ID does not borrow any books");
				return;
			}

			showTransactions(setOfTransactions);

			System.out.println("ID of the transaction you want to update: ");
			int yourTransaction_id = Integer.parseInt(bufferedReader.readLine());

			Transaction yourTransaction = null;
			for (Transaction transaction : setOfTransactions) {
				if (transaction.getTransaction_id() == yourTransaction_id) {
					yourTransaction = transaction;
					break;
				}
			}

			if (yourTransaction == null) {
				throw new InvalidValueException();
			}

			System.out.println("Number of books you want to return: ");
			int number = Integer.parseInt(bufferedReader.readLine());

			if (number > yourTransaction.getUnreturned_books() || number <= 0) {
				throw new InvalidValueException();
			}

			transactionRepository.updateTransactionAndCreateBill(yourTransaction_id, number);

		} catch (IOException | InvalidValueException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void payBills(String account_id) {
		try {
			LinkedHashSet<Bill> resultSet = transactionRepository.getUnpaidBill(account_id);
			if (resultSet.isEmpty()) {
				System.out.println("You already paid all bills");
				return;
			}

			showBills(resultSet);

			System.out.println("\nPay your bills?" + "\n1. Yes" + "\n2. No");
			int yourChoice = Integer.parseInt(bufferedReader.readLine());

			switch (yourChoice) {
			case 1:
				transactionRepository.payBills(account_id);
				break;
			default:
				break;
			}

		} catch (NumberFormatException | IOException | SQLException | InvalidValueException e) {
			e.printStackTrace();
		}

	}

	public void showBills(LinkedHashSet<Bill> linkedHashSet) {
		if (linkedHashSet.isEmpty())
			return;
		System.out.println("------");
		for (Bill bill : linkedHashSet) {
			System.out.println(bill);
		}
		System.out.println("------");

	}

	public void showTransactions(LinkedHashSet<Transaction> linkedHashSet) {
		if (linkedHashSet.isEmpty())
			return;
		System.out.println("------");
		for (Transaction transaction : linkedHashSet) {
			System.out.println(transaction);
		}
		System.out.println("------");
	}

}
