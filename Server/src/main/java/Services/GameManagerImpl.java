package Services;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import Models.Player;

public class GameManagerImpl implements GameManager {
	
	private final ConcurrentHashMap<String, GameImpl> activeGames = new ConcurrentHashMap<>();

	@Override
	public Game getGame(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAndTerminate(String gameId, String msg, String terminatedOrAborted) throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	public ConcurrentHashMap<String, GameImpl> getActiveGames() {
		return this.activeGames;
	}

	public void addGame(GameImpl game, Player player, GameClientCallback callback) {
		// TODO Auto-generated method stub
		
	}
}
