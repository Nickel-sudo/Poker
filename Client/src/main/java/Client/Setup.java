package Client;

import java.io.IOException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Enums.GameSetupSteps;
import Models.Player;
import Network.GameDiscoveryBroadcaster;
import Network.GameDiscoveryListener;
import Network.GameInfo;
import Network.RegistryManager;
import Services.Game;
import Services.GameSetup;

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
			else {
				System.out.println("Enter Refresh to update the list of available games.");
				this.joinGame();
			}
				
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
			
			String input = this.inputThread.inputValidation(step.expectsNumeric(), "Blocking", 0);
			
			if (input.equals("-1"))
				return;
			
			settings.put(step.toString(), input);
		}
		
		Player hostPlayer = new Player(this.inputThread.inputValidation(false, "Blocking", 0), true);
		
		this.inputThread.stopInputThread();
		//TODO: push settings to Remote
		//TODO: create game
		//TODO: advertise game 
		System.out.println("Game created");
	}

	private void joinGame() throws IOException, InterruptedException  {
		
		Game selectedGame = null;
		this.inputThread.startInputThread();
		GameDiscoveryListener listener = new GameDiscoveryListener();
		listener.start();
				
		while (selectedGame == null) {
			
			List<GameInfo> games = listener.getGameInfos();
			List<GameInfo> copyOfGames = List.copyOf(games); //prevent simultaneous access as gameInfos in listener could be modified
			
			if (copyOfGames.isEmpty()) {
				System.out.println("No Games are currently available.");
				try {
					Thread.sleep(500);
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
				System.out.println("Do you want to refresh game list? (Yes/No)");
				
				
				String input = this.inputThread.inputValidation(false, "Blocking", 0);
					
				if (!input.equals("No") && !input.equals("Yes")) {
					System.out.println("Enter either Yes or No. Or Exit to abort.");
				}
					
				if (input.equals("No") || input.equals("-1")) {
					this.inputThread.stopInputThread();
					return;
				}
				
				continue;
			}
			
			int availableGames = copyOfGames.size();
			
			for (int i = 1; i <= availableGames; i++) {
				System.out.println(i + ": " + copyOfGames.get(i - 1).getName());
			}
			
			System.out.println("Game Number: ");
			String input = this.inputThread.inputValidation(true, "Blocking", availableGames);
			
			if (input.equals("Refresh"))
				continue;
			
			int gameChoice = Integer.valueOf(input);
			
			GameInfo selectedGameTmp = copyOfGames.get(gameChoice - 1);
			
			//TODO:fetch game 
			Registry registry = RegistryManager.getRegistry(selectedGameTmp.getHost(), gameChoice, false);
			
			if (registry == null) {
				System.out.println("Connection to Host failed. Please try again.");
				continue;
			}
			
			GameSetup service;
			int attempts = 0;
			
			while (true) {
				try {
					service = (GameSetup) registry.lookup("GameSetup"); 
					Player player = new Player(this.inputThread.inputValidation(false, "Blocking", 0), false);
					//add Player to game
					break;
				} catch(Exception e) {
					if (attempts < 2) {
						System.out.println("Service not bound. Attempting to bind...");
						RegistryManager.registerService(registry, null, "GameSetup"); //TODO: Service implementation of GameSetup
						attempts++;
					} else {
						System.out.println("Failed to connect to host.");
						break;
					}
				}
			}
		}
	}
}
