package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TServer implements Runnable {
	protected Thread thread;

	// Default values
	private final int RMI_PORT = 17056;
	
	/**
	 * Empty class constructor.
	 */
	public TServer() {
	}

	@Override
	public void run() {
		// Sync thread
		synchronized(this) {
			this.thread = Thread.currentThread();
		}
		
		try {
			// Start a client list
			TClientList rtc = new TClientList();
			
			Registry reg = LocateRegistry.createRegistry(RMI_PORT);
			reg.bind("clientList", rtc);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	// Start the server
	public static void main(String[] args) {
		TServer server = new TServer();
		new Thread(server).start();
	}
}