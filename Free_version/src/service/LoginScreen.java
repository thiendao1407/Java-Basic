package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import entity.User;
import exception.FailedLoginException;
import repository.UserRepository;

public class LoginScreen {

	public static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		LoginScreen loginScreen = new LoginScreen();
		loginScreen.showMenu();
	}

	public void showMenu() {
		int yourChoice = -1;
		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Login" + "\n0. Exit" + "\nYour choice: ");
			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());
				switch (yourChoice) {
				case 0:
					System.out.println("Goodbye!");
					break;

				case 1:
					System.out.println("Please enter your username: ");
					String user_name = bufferedReader.readLine();
					System.out.println("Please enter your password: ");
					String password = bufferedReader.readLine();
					loginSystem(user_name, password);
					break;

				default:
					System.out.println("\nPlease choose the correct options.");
				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void loginSystem(String user_name, String password) {
		User user = getUserByNameAndPassword(user_name, password);
		if (user == null) {
			return;
		}

		if (user.isAdmin()) {
			AdminService adminService = new AdminService();
			adminService.showMenu();
		} else {
			ReaderService readerService = new ReaderService();
			readerService.showMenu(user_name);
		}

		return;
	}

	public User getUserByNameAndPassword(String user_name, String password) {

		User user = null;

		try {
			user = new UserRepository().getUserByName(user_name);

			if (!user.getPassword().equals(password)) {
				throw new FailedLoginException(user_name, password);
			}
		} catch (SQLException | FailedLoginException e) {
			e.printStackTrace();
			return null;
		}

		return user;

	}

}
