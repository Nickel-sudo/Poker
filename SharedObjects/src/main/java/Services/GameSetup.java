package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import Models.Player;

public interface GameSetup extends Remote{
    void joinGame(String gameId, Player player, GameClientCallback callback) throws RemoteException;
    Game createGame(Map<String, String> gameSettings, Player player, GameClientCallback callback)
            throws RemoteException;
    void abortStart(String gameId) throws RemoteException;
    Game getSelectedGame(String gameId) throws RemoteException;
}