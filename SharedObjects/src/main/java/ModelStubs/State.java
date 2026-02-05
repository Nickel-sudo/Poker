package ModelStubs;

import java.io.Serializable;
import java.rmi.RemoteException;

import Models.Player;

public interface State extends Serializable {
	
	// Callback Manager not defined yet
    //int initAction(Player player, CallbackManager callbackManager, int minBetCallRaise) throws RemoteException;
    String printAction() throws RemoteException;
}
