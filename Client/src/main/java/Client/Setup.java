package Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Enums.GameSetupSteps;
import Models.Player;
import Services.Game;

public class Setup {
	
	InputThread inputThread;
	
	public Setup() {
		this.inputThread = new InputThread();
	}
	
	public static void main(String[] args) {
		
		Setup setup = new Setup();
		setup.inferAction(1);
	}

	public void inferAction(int choice) {
		
		System.out.println("Enter Exit to quit game setup");
		
		try {
			if (choice == 1)
				this.createGame();
			else 
				this.joinGame();
		} catch(InterruptedException e){
			e.printStackTrace(); //TODO:define proper print statement
		} catch (IOException e) {
			e.printStackTrace(); //TODO:define proper print statement
		}
	} 

	private void createGame() throws InterruptedException, IOException {
		
		this.inputThread.startInputThread();
		
		Map<String, String> settings = new HashMap<>();
		
		for (GameSetupSteps step : GameSetupSteps.values()) {
			
			step.getPrompt();
			
			String input = this.inputThread.inputValidation(step.expectsNumeric(), 0);
			
			if (input == "-1")
				return;
			
			settings.put(step.toString(), input);
		}
		
		Player hostPlayer = new Player(this.inputThread.inputValidation(false, 0), true);
		
		this.inputThread.stopInputThread();
		//TODO: push settings to Remote
		//TODO: create game
		//TODO: advertise game 
		System.out.println("Game created");
	}

	private void joinGame() throws IOException, InterruptedException  {
		
		Game selectedGame = null;
		this.inputThread.startInputThread();
		
		while (selectedGame == null) {
			
			//TODO: fetch available games
			//TODO: check if games exist
			
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				continue;
			}
			
			//TODO:print available games
			
			System.out.println("Game Number: ");
			String gameChoice = this.inputThread.inputValidation(true, 0); //0 serves as placeholder and is replaced by number of available games
			
			//TODO:fetch game 
			
			Player player = new Player(this.inputThread.inputValidation(false, 0), false);
			
			//add Player to game
		}
	}
}
