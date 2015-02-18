package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class RTClient implements Runnable {
	private Socket client = null;
	private String location;
	private DataInputStream input;
	private PrintStream printer;
	private int updates;
	
	// Default values
	private final int MAX_UPDATES = 100;
	
	public RTClient(Socket client, String location) {
		this.client = client;
		this.location = location;
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
					System.out.println("Received temperature: " + d + "[" + this.location + "]");
					
					String res = null;
					
					res += "[" + this.location + "]";
					res += d;
					
					printer.print(res.toCharArray());
					
					updates++;
				}
				
				// Stop loop if MAX_UPDATES is matched
				if(updates == MAX_UPDATES) {
					printer.print("END".toCharArray());
					break;
				}
				
				// End and close (with finally-block) all resources
				if(client.isClosed())
					break;
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				input.close();
				printer.close();
				client.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}