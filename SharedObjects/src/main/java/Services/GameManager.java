package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManager extends Remote{
    Game getGame(String id) throws RemoteException;
    void removeAndTerminate(String gameId, String msg, String terminatedOrAborted) throws RemoteException;
}
