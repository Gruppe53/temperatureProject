package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RTClient implements Runnable {
	private Socket client = null;
	private String location;
	private BufferedReader input;
	private DataOutputStream output;
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
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new DataOutputStream(client.getOutputStream());
			
			// Read input and return with answer
			while(true) {
				String s;
				
				if((s = input.readLine()) != null) {
					System.out.println("Received temperature: " + s + " [" + this.location + "]");
					
					s += "[" + this.location + "]";
					
					// TODO Proper conversion to bytes before responding
					output.write(s.getBytes());
					
					updates++;
				}
				
				// TODO Proper conversion to bytes before responding
				// Stop loop if MAX_UPDATES is matched
				if(updates == MAX_UPDATES) {
					output.write("END".getBytes());
					break;
				}
				
				// End and close (with finally-block) all resources if client disconnects
				if(client.isClosed())
					break;
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				input.close();
				output.close();
				client.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}