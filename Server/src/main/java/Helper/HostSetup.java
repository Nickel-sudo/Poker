package Helper;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Services.GameSetupImpl;

public class HostSetup {

	public static void setupHost(String host, int port) {
		
		Registry registry = null;
		
		try {
			registry = LocateRegistry.getRegistry(host, port);
			registry.list();
			System.out.println("Registry successfully created.");
		} catch(RemoteException r) {
			System.out.println("No Registry found. Attempting to create Registry...");
			try {
				registry = LocateRegistry.createRegistry(port);
				System.out.println("Registry successfully created.");
			} catch(RemoteException r2) {
				System.out.println("Error creating Registry");
				return;
			}
		}
	
		while (true) {
			try {
				registry.bind("GameSetup", new GameSetupImpl()); 
				//registry.bind("GameClientCallback", new GameClientCallbackImpl());
				//registry.bind("GameManager", new GameManagerImpl());
				//registry.bind("Game", new GameImpl());
				break;
			} catch(AlreadyBoundException a) {
				System.out.println("Service already bound. Continuing...");
				continue;
			} catch(Exception e) {
				System.out.println("Error registering Service.");
				break;
			}
		}
	}
}


