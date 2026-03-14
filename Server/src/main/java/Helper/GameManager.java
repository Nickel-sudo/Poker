package Helper;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import Models.Player;
import Services.Game;
import Services.GameClientCallback;
import Services.GameImpl;

public class GameManager{
	
	//a host could have multiple game sessions with different friends, pausing of games will be enabled
	
	private static final ConcurrentHashMap<String, GameImpl> activeGames = new ConcurrentHashMap<>();

	public static Game getGame(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void removeAndTerminate(String gameId, boolean aborted, String msg) throws RemoteException {
		
		GameImpl currentGame = activeGames.remove(gameId);
		Player hostPlayer = currentGame.getHostPlayer();
		hostPlayer.getBroadcast().stopBroadcasting();
		
		if (aborted) {
			currentGame.getCallbackManager().notifyAllPlayersExceptHost(hostPlayer.getPlayerId(), msg);
		} else {
			currentGame.getCallbackManager().notifyAllPlayers(msg);
		}

		currentGame = null;
	}
	
	public static ConcurrentHashMap<String, GameImpl> getActiveGames() {
		return activeGames;
	}

	public static void addGame(GameImpl game, Player player) throws RemoteException {
		activeGames.put(game.getId(), game);
	}
}
