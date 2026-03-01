package App;

import java.io.IOException;
import java.net.InetAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

import Client.Setup;
import Exceptions.InvalidNumberException;
import Helper.HostSetup;

public class Main {

private static final Scanner SCANNER = new Scanner(System.in);
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		System.out.println("Welcome to Pokerino. The rules are aligned to classic Texas Hold'em");
		
		init();
		SCANNER.close();
	}

	private static void init() throws InterruptedException, IOException{
		
		Setup setup = new Setup();
		
		while(true) {
			
			System.out.println("Enter the proper number \n 1. Create New Game \n 2. Join Existing Game \n 3.Quit");
			
			int choice = SCANNER.nextInt();
			
			try {
				if (choice > 3) {
					throw new InvalidNumberException();
				} else if (choice == 3) {
					break;
				} else {
					
					if (choice == 1) {
						HostSetup.setupHost(InetAddress.getLocalHost().getHostAddress(), 9999);
						setup.createGame();
					} else {
						setup.joinGame();
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter only numbers");
			} catch (InvalidNumberException e) {
				System.out.println("Please enter a proper number.");
			} 
		}
	}	
}
