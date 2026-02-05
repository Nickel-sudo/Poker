package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Models.Player;

public interface Game extends Remote{
    String getId() throws RemoteException; 
    String getName() throws RemoteException;
    int getJetons() throws RemoteException;
    int getSmallBlind() throws RemoteException;
    int getBigBlind() throws RemoteException;
    int getMaxPlayers() throws RemoteException;
    List<Player> getPlayers() throws RemoteException;
    int getRemainingSpots() throws RemoteException;
    Player getHostPlayer() throws RemoteException;
    String getHost() throws RemoteException;
    int getPort() throws RemoteException;
    boolean isRunning() throws RemoteException;
}
