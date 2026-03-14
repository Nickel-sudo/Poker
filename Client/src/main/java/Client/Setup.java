package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
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

	public void createGame() throws InterruptedException, IOException, NotBoundException {
		
		this.inputThread.startInputThread();
		
		Map<String, String> settings = new HashMap<>();
		
		for (GameSetupSteps step : GameSetupSteps.values()) {
			
			step.getPrompt();
			
			String input = this.inputThread.inputValidation(step.expectsNumeric(), 0);
			
			if (input.equals("-1"))
				return;
			
			settings.put(step.toString(), input);
		}
		
		Player hostPlayer = new Player(this.inputThread.inputValidation(false, 0), true);
		
		Registry hostRegistry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress(), 9999);
		GameSetup setupService = (GameSetup) hostRegistry.lookup("GameSetup");
		Game newGame = setupService.createGame(settings, hostPlayer);
		
		System.out.println("Game created. Waiting for others to join...");
		
		while(newGame.getRemainingSpots() > 0) {
			
			String input = this.inputThread.inputDuringLobbyInit();
			
			if (input.equals("-1")) {
				setupService.abortStart(newGame.getId());
				clear(null);
				return;
			}
		}
		
		clear(null);
		System.out.println("All Players have joined. Game ready to start.");
		
		//init game start
	}

	public void joinGame() throws IOException, InterruptedException  {
		
		System.out.println("Enter Refresh to update the list of available games.");
		
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
				
				String input = this.inputThread.inputValidation(false, 0);
					
				if (input.equals("No") || input.equals("-1")) {
					clear(listener);
					return;
				}
				
				continue;
			}
			
			int availableGames = copyOfGames.size();
			
			for (int i = 1; i <= availableGames; i++) {
				System.out.println(i + ": " + copyOfGames.get(i - 1).getName());
			}
			
			System.out.println("Game Number: ");
			String input = this.inputThread.inputValidation(true, availableGames);
			
			if (input.equals("Refresh")) //cache fetched games
				continue;
			
			int gameChoice = Integer.valueOf(input);
			
			GameInfo selectedGameTmp = copyOfGames.get(gameChoice - 1);
			
			//TODO:fetch game 
			Registry registry = RegistryManager.getRegistry(selectedGameTmp.getHost(), gameChoice, false);
			
			if (registry == null) {
				System.out.println("Connection to Host failed. Please try again.");
				continue;
			}
			
			int attempts = 0;
			
			while (true) {
				try {
					GameSetup service = (GameSetup) registry.lookup("GameSetup"); 
					Player player = new Player(this.inputThread.inputValidation(false, 0), false);
					//add Player to game
					break;
				} catch(Exception e) {
					if (attempts < 2) {
						System.out.println("Service not bound. Attempting to bind...");
						RegistryManager.registerService(registry, null, "GameSetup"); //TODO: Service implementation of GameSetup
						attempts++;
					} else {
						System.out.println("Failed to connect to host.");
						clear(listener);
						break;
					}
				}
			}
			
			clear(listener);
			//wait for Lobby
		}
	}
	
	void clear(GameDiscoveryListener listener) {
		
		if (listener != null)
			listener.stopListening();
		
		this.inputThread.stopInputThread();
		this.inputThread = null;
	}
}
