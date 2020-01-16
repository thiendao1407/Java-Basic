package controller;

import java.util.LinkedHashSet;

import account.Account;

public interface SearchAccount {
	public LinkedHashSet<Account> searchAccountByName();

	public LinkedHashSet<Account> searchAccountByStatus();

	public LinkedHashSet<Account> searchAccountByType();
}
