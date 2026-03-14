package Helper;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import Services.GameClientCallback;
import Services.GameClientCallbackImpl;

public class CallbackManager {

	private ConcurrentHashMap<String, GameClientCallback> callbacks;
	
	public CallbackManager() {
		this.callbacks = new ConcurrentHashMap<String, GameClientCallback>();
	}
	
	public ConcurrentHashMap<String, GameClientCallback> getCallbacks() {
		return this.callbacks;
	}

	public void addCallback(String playerId, GameClientCallbackImpl gameClientCallback) {
		this.callbacks.put(playerId, gameClientCallback);
	}

	public void notifyAllPlayersExceptHost(String hostId, String msg) {
		
		for (String key : this.callbacks.keySet()) {
			
			if (key == hostId)
				continue;
			
			try {
				GameClientCallback callback = this.callbacks.get(key);
				callback.sendNotification(msg);
			} catch(RemoteException r) {
				System.out.println("Failed to notify player due to " + r.getMessage());
			}
		}
	}

	public void notifyAllPlayers(String msg) {
		
		for (String key: this.callbacks.keySet()) {
			try {
				this.callbacks.get(key).sendNotification(msg);
			} catch(RemoteException r) {
				System.out.println("Failed to notify player due to " + r.getMessage());
			}
		}
	}
}
