package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TServer implements Runnable {
	protected Thread thread = null;
	protected ServerSocket server;
	
	// Default values
	private final int SERVER_PORT = 15051;
	
	public TServer() {
		// TODO TemperatureServer.main
		/*
		 * 1. Create server with specified port
		 * 		- Port should be given through the command line
		 * 		- If no port is given through command line, use default value, SERVER_PORT
		 * 2. Create necessary I/O-objects (inputstream, printstream, clientsocket)
		 * 3. Wait for client to connect
		 * 4. Handle client
		 * 		4-1. Accept client and its inputs (inputstream should only contain bytes)
		 * 		4-2. Return client's inputs (printstream)
		 * 			- Do this until client disconnects or server program is closed 
		 * 5. Each input should be processed
		 * 		5-1. Save bytes to file (CSV or just as a byte-object)
		 * 		5-2. Calculate mean of data (convert from byte to double)
		 * 		5-3. Print mean to screen (TUI/GUI)
		 * 
		 * Extra:
		 * Try to create a GUI
		 */
	}

	@Override
	public void run() {
		// Sync thread
		synchronized(this) {
			this.thread = Thread.currentThread();
		}
		
		// Start server
		startServer();
		
		while(true) {
			// Wait for client to connect
			Socket client = null;
			
			try {
				System.out.println("Waiting for client...");
				
				client = server.accept();
				
				System.out.println("Client [" + client.getLocalAddress() + "] connected");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			// When client is connected add as new thread
			new Thread(
				new RTClient(client)
			).start();
		}
	}
	
	private void startServer() {
		try {
			this.server = new ServerSocket(this.SERVER_PORT);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		TServer server = new TServer();
		new Thread(server).start();
	}
}