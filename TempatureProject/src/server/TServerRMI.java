package server;

import java.rmi.registry.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import com.sun.javafx.tk.Toolkit.Task;


public class TServerRMI {
	
	public TServerRMI() {
		super();
	}
	
	public <T> T executeTask(Task<T> t) {
		return t.execute();
	}
	
	public static void main(String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "Server";
			TServer server = new TServer();
			TServer stub = (TServer) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}
}
