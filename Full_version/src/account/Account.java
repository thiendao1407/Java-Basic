package account;

import static view.Main.bufferedReader;

import java.io.IOException;

public class Account {
	private String account_id;
	private String password;
	private AccountStatus account_status;
	private AccountType account_type; // does not have set method
	private Person person;

	// Account status
	public enum AccountStatus {
		ACTIVE, BLACKLISTED, LOCKED, REQUESTED
	}

	// Account type
	public enum AccountType {
		LIBRARIAN, MEMBER
	}

	public Account(String account_id, String password, String account_status, String account_type, Person person) {
		setAccount_id(account_id);
		setPassword(password);
		setAccountStatus(account_status);
		if (account_type.equalsIgnoreCase("Librarian")) {
			this.account_type = AccountType.LIBRARIAN;
		} else
			this.account_type = AccountType.MEMBER;
		setPerson(person);
	}

	public Account() throws NumberFormatException, IOException {
		System.out.println("\nPlease choose your account type" + "\n1. Librarian" + "\n2. Member" + "\nYour choice: ");

		int yourChoice = Integer.parseInt(bufferedReader.readLine());
		switch (yourChoice) {
		case 1:
			this.account_type = AccountType.LIBRARIAN;
			break;
		case 2:
			this.account_type = AccountType.MEMBER;
			break;
		default:
			throw new IllegalArgumentException("\nPlease choose the correct options.");
		}
	}

	public String toString() {
		return "Account ID: " + account_id + ", Account status: " + account_status + ", Account type: " + account_type
				+ ", " + person.toString();
	}

	public boolean isActive() {
		return this.account_status == AccountStatus.ACTIVE;
	}

	public boolean isBlackListed() {
		return this.account_status == AccountStatus.BLACKLISTED;
	}

	public boolean isLocked() {
		return this.account_status == AccountStatus.LOCKED;
	}

	public boolean isRequested() {
		return this.account_status == AccountStatus.REQUESTED;
	}

	public boolean isLibrarian() {
		return this.account_type == AccountType.LIBRARIAN;
	}

	public boolean isMember() {
		return this.account_type == AccountType.MEMBER;
	}

	// Get-Set
	public AccountStatus getAccountStatus() {
		return account_status;
	}

	public void setAccountStatus(String status) {
		if (status.equalsIgnoreCase("ACTIVE")) {
			this.account_status = AccountStatus.ACTIVE;
		} else if (status.equalsIgnoreCase("BLACKLISTED")) {
			this.account_status = AccountStatus.BLACKLISTED;
		} else if (status.equalsIgnoreCase("LOCKED")) {
			this.account_status = AccountStatus.LOCKED;
		} else if (status.equalsIgnoreCase("REQUESTED")) {
			this.account_status = AccountStatus.REQUESTED;
		} else
			throw new IllegalArgumentException("Invalid type of account status: " + status);
	}

	//
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	//
	public AccountType getAccount_type() {
		return account_type;
	}

	//
	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String id) {
		if (id.isBlank())
			throw new IllegalArgumentException("ID could not be blank");
		this.account_id = id;
	}

	//
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.isBlank())
			throw new IllegalArgumentException("Password could not be blank");
		this.password = password;
	}
}
