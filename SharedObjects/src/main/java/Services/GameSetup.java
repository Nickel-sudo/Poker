package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import Models.Player;

public interface GameSetup extends Remote{
    void joinGame(String gameId, Player player) throws RemoteException;
    Game createGame(Map<String, String> gameSettings, Player player)
            throws RemoteException;
    void abortStart(String gameId) throws RemoteException;
    Game getSelectedGame(String gameId) throws RemoteException;
}