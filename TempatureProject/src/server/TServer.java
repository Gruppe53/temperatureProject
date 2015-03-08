package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TServer implements Runnable {
	protected Thread thread;
	protected ServerSocket server;

	// Default values
	private final int SERVER_PORT	= 17056;
	private final int RMI_PORT 		= (SERVER_PORT + 1);
	
	/**
	 * Empty class constructor.
	 */
	public TServer() {
	}

	@Override
	public void run() {
		// Sync thread
		synchronized (this) {
			this.thread = Thread.currentThread();
		}

		// Start server
		if (this.startServer()) {
			while (true) {
				// Wait for client to connect
				Socket client = null;

				try {
					client = this.server.accept();

					System.out.println("Client [" + client.getLocalSocketAddress() + "] connected");
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally {
					// When client is connected add as new thread
					try {
						RTClient rtc;
						
						// Start a running temperature client
						new Thread(
							rtc = new RTClient(client, "Unknown")
						).start();
						
						// Create registry and bind object
						Registry reg = LocateRegistry.createRegistry(RMI_PORT);
						reg.bind(rtc.getLocation(), rtc);
						System.out.println("Bound " + rtc.getLocation() + " to list");
					} catch (RemoteException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					} catch (AlreadyBoundException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		} else
			System.out.println("Couldn't start server.");
	}

	private boolean startServer() {
		try {
			if ((this.server = new ServerSocket(this.SERVER_PORT)) != null)
				System.out.println("Waiting for clients...\n");
			
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	// Start the server
	public static void main(String[] args) {
		TServer server = new TServer();
		new Thread(server).start();
	}
}