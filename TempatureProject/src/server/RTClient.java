package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class RTClient implements Runnable {
	private Socket client = null;
	private String location;
	private BufferedReader input;
	private DataOutputStream output;
	private int updates;
	private ArrayList<TStoredData> tData = new ArrayList<TStoredData>();
	private double average;
	
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
			
			// We create cbuf because save the input in, as char so that we can convert it to string, 
			// and convert the sting to double
			char[] cbuf = new char[5];
			
			while(true){
				// -1 is the end character we get from the input
				if((input.read(cbuf)) != -1){
					// save the input into cbuf
					input.read(cbuf);
					// add the input in the arraylist tData
					tData.add(new TStoredData(
							Double.parseDouble(
									String.valueOf(cbuf))));
					
					// Calculate the average of all the temperatures in tData
					for(int i = 0; i < tData.size(); i++) {
						double sum = 0;
						sum += tData.get(i).getTemperature();
						
						average = sum/tData.size();
					}
					
					// Print out the average
					System.out.println(average);
					
					updates++;
				}
				
				// If update hits 100 or the client is closed we end the while loop
				if(updates == 100 || client.isClosed())
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