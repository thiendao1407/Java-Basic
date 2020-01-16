package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.AccountControl;

public class Main {

	public static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	AccountControl accountControl = new AccountControl();

	public static void main(String[] args) {
		Main main = new Main();
		main.showMenu();
	}

	public void showMenu() {

		int yourChoice = -1;
		while (yourChoice != 0) {
			System.out.println("----------------" + "\n1. Log in" + "\n2. Sign up" + "\n0. Exit" + "\nYour choice: ");

			try {
				yourChoice = Integer.parseInt(bufferedReader.readLine());

				switch (yourChoice) {
				case 0:
					System.out.println("Goodbye!");
					break;

				case 1:
					accountControl.login();
					break;

				case 2:
					accountControl.signup();
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