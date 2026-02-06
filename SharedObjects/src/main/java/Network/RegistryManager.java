package Network;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryManager {

	public static Registry getRegistry(String host, int port, boolean isHost){
		
		try {
			Registry registry = LocateRegistry.getRegistry(host, port);
			System.out.println("Registry successfully created.");
			return registry;
		} catch(RemoteException e) {
			if (isHost) {
				try {
					Registry registry = LocateRegistry.createRegistry(port);
					System.out.println("Registry successfully created.");
					return registry;
				} catch(RemoteException f) {
					System.out.println("Error creating registry.");
				}
			} else {
				System.out.println("Error connecting to host.");
			}
		}
		
		return null;
	}
	
	public static void registerService(Registry registry, Remote remote, String name) {
		
		try {
			registry.bind(name, remote);
			System.out.println("Service successfully bound.");
		} catch(AlreadyBoundException e) {
			System.out.println("Service already bound.");
		} catch(RemoteException e) {
			System.out.println("Error connecting to host.");
		}
	}
}
