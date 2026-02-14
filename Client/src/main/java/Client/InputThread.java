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

public class InputThread {
	
	Thread inputThread;
	
	private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private volatile boolean running = false;
	
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
		
		if (mode.equals("Blocking"))
			input = this.queue.take().trim();
		else {
			input = this.queue.poll().trim();
			if (input == null)
				return null;
		}
		
		return input.trim();
	}
	
	public void stopInputThread() {
		running = false;
		this.inputThread.interrupt();
	}
	
	public String inputValidation(boolean numericOnly, String mode, int maxValue) throws InterruptedException{
		
		String input = this.next(mode);
		
		if (input == null)
			return null;
		
		boolean recurring = false;
		
		while (true) {
			
			if (input.equals("Exit")) {
				this.stopInputThread();
				return "-1";
			} else if (input.equals("Refresh")) {
				break;
			}
			
			if (recurring) {
				System.out.println("Please try again.");
				input = this.next(mode);
			} else if (input.trim().equals("")) {
				System.out.println("Input cannot be empty.");
				recurring = true;
				continue;
			} else if (numericOnly) {
				try {
					int tmp = Integer.valueOf(input);
					
					if (tmp < 0)
						throw new NegativeNumberException();
					
					if (tmp > maxValue)
						throw new InvalidNumberException();
					
				} catch(NumberFormatException e) {
					System.out.println("Only numbers allowed.");
					recurring = true;
					continue;
				} catch(NegativeNumberException e) {
					System.out.println("Negative Numbers are not allowed.");
					recurring = true;
					continue;
				} catch(InvalidNumberException e) {
					System.out.println("Please enter a valid number.");
					recurring = true;
					continue;
				}
			}
			
			break;
			
		}
		
		return input;
	}
}

