package Client;
import java.util.InputMismatchException;
import java.util.Scanner;

import Client.Exceptions.InvalidNumber;

public class Start {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to Pokerino. The rules are aligned to classic Texas Hold'em");
		
		init();
		SCANNER.close();
	}

	private static void init() {
		
		Setup setup = new Setup();
		
		while(true) {
			
			System.out.println("Enter the proper number \n 1. Create New Game \n 2. Join Existing Game \n 3.Quit");
			
			int choice = SCANNER.nextInt();
			
			try {
				if (choice > 3) {
					throw new InvalidNumber();
				} else if (choice == 3) {
					break;
				} else {
					setup.inferAction(choice);
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter only numbers");
			} catch (InvalidNumber e) {
				System.out.println("Please enter a proper number.");
			} 
		}
	}
}
