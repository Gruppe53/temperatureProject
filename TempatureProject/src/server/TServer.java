package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TServer implements Runnable {
	protected Thread thread = null;
	protected ServerSocket server;
	
	// Default values
	private final int SERVER_PORT = 17056;
	
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
				client = server.accept();
				
				System.out.println("Client [" + client.getLocalSocketAddress() + "] connected");
			} catch (IOException e) {
				System.out.println("-----------------ERROR-----------------");
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("-----------------ERROR-----------------");
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				// When client is connected add as new thread
				new Thread(
					// TODO Find a way to replace "Unknown" with room location/description - should be received from client
					new RTClient(client, "Unknown")
				).start();
			}
		}
	}
	
	private void startServer() {
		try {
			if((this.server = new ServerSocket(this.SERVER_PORT)) != null)
				System.out.println("Waiting for clients...\n");
		} catch(IOException e) {
			System.out.println("-----------------ERROR-----------------");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("-----------------ERROR-----------------");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Start the server
	public static void main(String[] args) {
		TServer server = new TServer();
		new Thread(server).start();
	}
}