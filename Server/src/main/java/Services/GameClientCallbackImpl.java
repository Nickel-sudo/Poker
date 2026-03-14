package Services;

import java.rmi.RemoteException;

public class GameClientCallbackImpl implements GameClientCallback {

	@Override
	public void sendNotification(String msg) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int clientSelectAction() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clientRaisedAction(int raiseMin) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clientBetAction(int minBet) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

}
