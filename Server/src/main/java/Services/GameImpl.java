package Services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import Helper.CallbackManager;
import Models.Player;

public class GameImpl implements Game {

	private String gameId;
	private String gameName;
	private int maxPlayers;
	private int jetons;
	private int smallBlind;
	private int bigBlind;
	private Player hostPlayer;
	private boolean running;
	private String host;
	private int port;
	
	List<Player> players;
	CallbackManager callbackManager;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public GameImpl() {
		this.players = new ArrayList<Player>(); 
		this.callbackManager = new CallbackManager();
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setJetons(int jetons) {
		this.jetons = jetons;
	}

	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}

	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}
	
	public void setHostPlayer(Player player) {
		this.hostPlayer = player;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String getId() throws RemoteException {
		return this.gameId;
	}

	@Override
	public String getName() throws RemoteException {
		return this.gameName;
	}

	@Override
	public int getJetons() throws RemoteException {
		return this.jetons;
	}

	@Override
	public int getSmallBlind() throws RemoteException {
		return this.smallBlind;
	}

	@Override
	public int getBigBlind() throws RemoteException {
		return this.bigBlind;
	}

	@Override
	public int getMaxPlayers() throws RemoteException {
		return this.maxPlayers;
	}
	
	@Override
	public Player getHostPlayer() throws RemoteException {
		return this.hostPlayer;
	}

	@Override
	public List<Player> getPlayers() throws RemoteException {
		return null;
	}
	
	@Override
	public int getRemainingSpots() throws RemoteException {
		
		this.lock.readLock().lock();
		
		int remainingPlayers;
		
		try {
			remainingPlayers = this.maxPlayers - this.players.size();
		} finally {
			this.lock.readLock().unlock();
		}
		
		return remainingPlayers;
	}

	@Override
	public String getHost() throws RemoteException {
		return this.host;
	}

	@Override
	public int getPort() throws RemoteException {
		return this.port;
	}
	
	public CallbackManager getCallbackManager() {
		return this.callbackManager;
	}
	
	private boolean idTaken(String id) {
		return this.players.stream().anyMatch(player -> player.getPlayerId().equals(id));
	}

	public void addPlayer(Player player, GameClientCallbackImpl gameClientCallback) {
		
		this.lock.writeLock().lock();
		
		try {
			String playerId;
        
			do {
				playerId = UUID.randomUUID().toString();
			} while(idTaken(playerId));
		
			player.setPlayerId(playerId);
		
			if (this.players.size() < maxPlayers - 1) {
				this.players.add(player);
				this.callbackManager.addCallback(playerId, gameClientCallback);
			}
		} finally {
			this.lock.writeLock().unlock();
		}
	}
}
