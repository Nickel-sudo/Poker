package Client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import Exceptions.InvalidNumberException;
import Exceptions.NegativeNumberException;
import Services.Game;

public class InputThread {
	
	Thread inputThread;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	Game game;
	
	private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	public volatile boolean running = false;

	public boolean lobbyFull;
	
	public void startInputThread() throws IOException {
		
		Terminal terminal = TerminalBuilder.builder().system(true).build();
		LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
		
		running = true;
		
		this.inputThread = new Thread(() -> {
			while(running) {
				try {
					String line = reader.readLine();
					this.queue.offer(line);
				} catch(UserInterruptException e) {
					break;
				} catch(Exception e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		});
		
		this.inputThread.setDaemon(true);
		this.inputThread.start();
	}
	
	public String next(String mode) throws InterruptedException {
		
		String input;
		
		if (mode.equals("Blocking")) {
			input = this.queue.take().trim();
			switch(input.trim()) {
				case "Exit": return "-1";
				case "": return "Empty";
				default: return input;
			}
		}
		else {
			input = this.queue.poll();
			if (input == null)
				return null;
			return input.trim().equals("Exit") ? "-1" : input.trim();
		}
	}
	
	public void stopInputThread(boolean gameInitiated) {
		
		if (gameInitiated)
			lobbyFull = false;
			
		running = false;
		this.inputThread.interrupt();
	}
	
	public String inputValidation(boolean numericExpected, int maxValue) throws InterruptedException {
		
		while(true) {
			String input = this.next("Blocking");
			
			if (input.equals("-1") || input.equals("Refresh"))
				return input;
			
			if (input.equals("Empty")) {
				System.out.println("Empty Values are not allowed. Please try again.");
				continue;
			}
			
			if (numericExpected) {
				 String intInput = numericInputDuringSetup(input, maxValue);
				 if (intInput.trim().equals("Error"))
					 continue;
				 return intInput;
			}
			
			return input;
		}
	}
	
	public String numericInputDuringSetup(String input, int maxValue) throws NumberFormatException, InterruptedException {
		
		while(true) {
			
			try{
				int inputToInt = Integer.valueOf(input);
				
				if (inputToInt <= 0)
					throw new NegativeNumberException();
				
				if (maxValue != 0 && inputToInt > maxValue)
					throw new InvalidNumberException();
				
				return String.valueOf(inputToInt);
				
			} catch(NumberFormatException nfe) {
				System.out.println("Only numbers are allowed. Please try again.");
				return "Error";
			} catch(NegativeNumberException nne) {
				System.out.println("No negative numbers are allowed. Please try again.");
				return "Error";
			} catch(InvalidNumberException ine) {
				System.out.println("Input larger than Maximum. Please try again.");
				return "Error";
			}
		}
	}
	
	public String inputDuringLobbyInit() throws InterruptedException {
		
		while(true) {
			String input = this.next("nonBlocking");
			
			if (input.equals("-1"))
				return input;
		}
	}
	
	
//	public String inputValidation(boolean numericOnly, String mode, int maxValue, boolean gameInitiated) throws InterruptedException{
//		
//		String input = this.next(mode);
//		
//		if (input == null)
//			return null;
//		
//		boolean recurring = false;
//		
//		while (true) {
//			
//			if (input.equals("Exit")) {
//				this.stopInputThread(gameInitiated);
//				return "-1";
//			} 
//			
//			if (!gameInitiated) {
//				
//				if (input.equals("Refresh")) {
//					break;
//				}
//				
//				if (recurring) {
//					System.out.println("Please try again.");
//					input = this.next(mode);
//				} else if (input.trim().equals("")) {
//					System.out.println("Input cannot be empty.");
//					recurring = true;
//					continue;
//				} else if (numericOnly) {
//					try {
//						int tmp = Integer.valueOf(input);
//						
//						if (tmp < 0)
//							throw new NegativeNumberException();
//						
//						if (tmp > maxValue)
//							throw new InvalidNumberException();
//						
//					} catch(NumberFormatException e) {
//						System.out.println("Only numbers allowed.");
//						recurring = true;
//						continue;
//					} catch(NegativeNumberException e) {
//						System.out.println("Negative Numbers are not allowed.");
//						recurring = true;
//						continue;
//					} catch(InvalidNumberException e) {
//						System.out.println("Please enter a valid number.");
//						recurring = true;
//						continue;
//					}
//				}
//			}
//			
//			break;
//		}
//		
//		return input;
//	}
}