package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class RTClient implements Runnable {
	protected Socket client = null;
	protected String location;
	private DataInputStream input;
	private PrintStream printer;
	private int updates;
	
	// Default values
	private final int MAX_UPDATES = 100;
	
	public RTClient(Socket client) {
		this.client = client;
		this.updates = 0;
	}

	@Override
	public void run() {
		try {
			// Create IO objects
			input = new DataInputStream(client.getInputStream());
			printer = new PrintStream(client.getOutputStream());
			
			// Read input and return with answer.
			while(true) {
				// Testing... nothing is working :<
				String d;
				if((d = input.readUTF()) != null) {
					System.out.println(d);
					printer.print(d.toCharArray());
					
					updates++;
				}
				
				if(updates == MAX_UPDATES) {
					printer.print("END".toCharArray());
					break;
				}
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}