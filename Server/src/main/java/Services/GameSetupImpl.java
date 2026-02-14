package Services;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.UUID;

import Models.Player;

public class GameSetupImpl implements GameSetup {

	GameManagerImpl gameManager;
	
	public GameSetupImpl() {
		this.gameManager = new GameManagerImpl();
	}
	
	@Override
	public Game createGame(Map<String, String> gameSettings, Player player, GameClientCallback callback)
			throws RemoteException {
		
        GameImpl game = new GameImpl();
		
        String gameId;
        
		do {
			gameId = UUID.randomUUID().toString();
		} while(this.gameManager.getActiveGames().containsKey(gameId));
		
		game.setGameId(gameId);
		game.setGameName(gameSettings.get("LOBBY_NAME"));
		game.setMaxPlayers(Integer.valueOf(gameSettings.get("NUM_PLAYERS")));
		game.setJetons(Integer.valueOf(gameSettings.get("NUM_JETONS")));
		game.setSmallBlind(Integer.valueOf(gameSettings.get("SMALL_BLIND")));
		game.setBigBlind(Integer.valueOf(gameSettings.get("BIG_BLIND")));
		game.setHost(gameSettings.get("HOST"));
		game.setPort(Integer.valueOf(gameSettings.get("PORT")));
		game.setRunning(true);
		game.setHostPlayer(player);
		
		this.gameManager.addGame(game, player, callback);
		
		return game;
	}
	
	@Override
	public void joinGame(String gameId, Player player, GameClientCallback callback) throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Game getSelectedGame(String gameId) throws RemoteException {
		return this.gameManager.getGame(gameId);
	}

	@Override
	public void abortStart(String gameId) throws RemoteException {
		// TODO Auto-generated method stub
	}
	
	
}
