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
	
	/**
	 * Class-constructor that takes two parameters - a client socket and a room location/description.
	 * <p>
	 * Note that "RunningTClient" can't (shouldn't) be manipulated with. It is supposed to run as a single
	 * thread (Runnable), and keep doing so until the server has received <MAX_UPDATES> temperatures from client.
	 * <p>
	 * RTClient will terminate any resources if incoming client closes the connection or "END"-message has
	 * been sent to client (indicating <MAX_UPDATES> has been met).
	 * <p>
	 * <MAX_UPDATES> should be removed if this program ever leaves test phase...
	 * 
	 * @param client	the client socket
	 * @param location	the location or description of the client
	 */
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
			
			/**
			 *  TODO Input should be read and stored. Create a list with each stored temperature.
			 *  Every time we've added something to this list calculate an average and print 
			 *  this average in the server console.
			 *  
			 *  1.	Read input
			 *  2.	Save input as TStoredData-object in List<TStoredData (use "new ArrayList<TStoredData>")
			 *  2b.	Calculate average temperature.
			 *  2c. Print calculated average.
			 *  2d. Remember to add 1 to "updates".
			 */
			
			// Test...
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