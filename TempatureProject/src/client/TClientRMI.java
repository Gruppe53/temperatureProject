package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import client.TClient;

public class TClientRMI {
	public static void main (String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			String name = "Client";
			Registry registry = LocateRegistry.getRegistry(args[0]);
			TClient client = (TClient) registry.lookup(name);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}