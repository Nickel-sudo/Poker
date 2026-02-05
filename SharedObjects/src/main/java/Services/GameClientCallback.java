package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameClientCallback extends Remote{
    void sendNotification(String msg) throws RemoteException;
    int clientSelectAction() throws RemoteException; 
    int clientRaisedAction(int raiseMin) throws RemoteException;
    int clientBetAction(int minBet) throws RemoteException;
}
