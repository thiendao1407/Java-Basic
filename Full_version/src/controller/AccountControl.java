package controller;

import static view.Main.bufferedReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import account.Account;
import account.Account.AccountStatus;
import account.Person;
import exception.FailedLoginException;
import exception.ValueAlreadyExistsException;
import exception.ValueNotFoundException;
import repository.AccountRepository;
import view.LibrarianView;
import view.MemberView;

public class AccountControl implements SearchAccount {

	AccountRepository accountRepository = new AccountRepository();

	// log in
	public void login() {

		try {
			System.out.println("Please enter your ID: ");
			String account_id = bufferedReader.readLine();
			Account account = accountRepository.getAccount(account_id);

			System.out.println("Please enter your password: ");
			String password = bufferedReader.readLine();

			if (!account.getPassword().equals(password)) {
				throw new FailedLoginException("Password is incorrect");
			}

			if (account.isLocked()) {
				throw new FailedLoginException("Your account is temporarily locked.");
			}

			if (account.isRequested()) {
				throw new FailedLoginException("Your account is awaiting verification.");
			}

			System.out.println(account);

			if (account.isLibrarian())
				new LibrarianView().showMenu(account);
			else if (account.isMember())
				new MemberView().showMenu(account);

		} catch (FailedLoginException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	// sign up
	public void signup() {
		try {
			System.out.println("Please enter your account ID: ");
			String account_id = bufferedReader.readLine();
			if (accountRepository.existAccountID(account_id)) {
				throw new ValueAlreadyExistsException("This ID alrready exists.");
			}

			Account account = new Account();
			setAccountInformation(account, account_id);

			accountRepository.insertAccount(account);
			System.out.println("Request sent successfully, your account is awaiting verification.");

		} catch (IOException | ValueAlreadyExistsException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void changeYourInformation(Account account) throws FailedLoginException {
		try {
			setAccountInformation(account);
			accountRepository.updateAccount(account);
			throw new FailedLoginException("Your account is awaiting verification.");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void changeAccountStatus(Account yourAccount) throws FailedLoginException {
		System.out.println("Please enter account ID: ");

		try {
			String account_id = bufferedReader.readLine();
			if (!accountRepository.existAccountID(account_id))
				throw new ValueNotFoundException("This ID" + account_id + " does not exist");

			AccountStatus accountStatus = setAccountStatus();
			accountRepository.updateAccount(account_id, accountStatus);

			if (yourAccount.isLocked()) {
				throw new FailedLoginException("Your account is temporarily locked");
			}

			if (yourAccount.isRequested()) {
				throw new FailedLoginException("Your account is awaiting verification.");
			}
		} catch (IOException | ValueNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public LinkedHashSet<Account> searchAccountByName() {
		System.out.println("Please enter account name (No full name required): ");
		try {
			String regex = "%" + bufferedReader.readLine().toLowerCase() + "%";
			LinkedHashSet<Account> resultSet = accountRepository.searchAccount("name", regex, false);
			// Print the result
			showAccounts(resultSet);

			return resultSet;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedHashSet<Account> searchAccountByStatus() {
		System.out.println("\nPlease choose account status\n1. ACTIVE\n2. BLACKLISTED\n3. LOCKED\n4. REQUESTED");
		String status;
		try {
			int yourChoice = Integer.parseInt(bufferedReader.readLine());
			switch (yourChoice) {

			case 1:
				status = "ACTIVE";
				break;
			case 2:
				status = "BLACKLISTED";
				break;
			case 3:
				status = "LOCKED";
				break;
			case 4:
				status = "REQUESTED";
				break;
			default:
				throw new IllegalArgumentException("\nPlease choose the correct options.");
			}
			LinkedHashSet<Account> resultSet = accountRepository.searchAccount("account_status", status, true);
			// Print the result

			showAccounts(resultSet);

			return resultSet;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedHashSet<Account> searchAccountByType() {
		System.out.println("\nPlease choose account type\n1. LIBRARIAN\n2. MEMBER");
		String type;
		try {
			int yourChoice = Integer.parseInt(bufferedReader.readLine());
			switch (yourChoice) {
			case 1:
				type = "LIBRARIAN";
				break;
			case 2:
				type = "MEMBER";
				break;
			default:
				throw new IllegalArgumentException("\nPlease choose the correct options.");
			}
			LinkedHashSet<Account> resultSet = accountRepository.searchAccount("account_type", type, true);
			// Print the result

			showAccounts(resultSet);

			return resultSet;
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private AccountStatus setAccountStatus() throws NumberFormatException, IOException {
		System.out.println("----------------" + "\nAccount Status" + "\n1. Active" + "\n2. Black listed" + "\n3. Locked"
				+ "\nYour choice: ");

		int yourChoice = Integer.parseInt(bufferedReader.readLine());
		switch (yourChoice) {
		case 1:
			return AccountStatus.ACTIVE;
		case 2:
			return AccountStatus.BLACKLISTED;
		case 3:
			return AccountStatus.LOCKED;
		default:
			throw new IllegalArgumentException("\nPlease choose the correct options.");
		}
	}

	private void setAccountInformation(Account account) throws IOException {
		System.out.println("Please enter account name: ");
		account.getPerson().setName(bufferedReader.readLine());
		System.out.println("Please enter account password: ");
		account.setPassword(bufferedReader.readLine());

		account.setAccountStatus("REQUESTED");

		System.out.println("Please enter account address: ");
		account.getPerson().setAddress(bufferedReader.readLine());
		System.out.println("Please enter account email: ");
		account.getPerson().setEmail(bufferedReader.readLine());
		System.out.println("Please enter account phone: ");
		account.getPerson().setPhone(bufferedReader.readLine());
	}

	private void setAccountInformation(Account account, String account_id) throws IOException {
		account.setAccount_id(account_id);
		System.out.println("Please enter account password: ");
		account.setPassword(bufferedReader.readLine());
		account.setAccountStatus("REQUESTED");

		Person person = new Person();
		System.out.println("Please enter account name: ");
		person.setName(bufferedReader.readLine());
		System.out.println("Please enter account address: ");
		person.setAddress(bufferedReader.readLine());
		System.out.println("Please enter account email: ");
		person.setEmail(bufferedReader.readLine());
		System.out.println("Please enter account phone: ");
		person.setPhone(bufferedReader.readLine());

		account.setPerson(person);
	}

	private void showAccounts(LinkedHashSet<Account> linkedHashSet) {
		if (linkedHashSet.isEmpty())
			return;
		System.out.println("------");
		for (Account account : linkedHashSet) {
			System.out.println(account);
		}
		System.out.println("------");
	}

}
