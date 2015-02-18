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
			} finally {
				// When client is connected add as new thread
				new Thread(
					new RTClient(client, "Unknown")
				).start();
			}
		}
	}
	
	private void startServer() {
		try {
			this.server = new ServerSocket(this.SERVER_PORT);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Start the server
	public static void main(String[] args) {
		TServer server = new TServer();
		new Thread(server).start();
	}
}