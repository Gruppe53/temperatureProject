package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class TClientSCon {
	private TSensor sensor;
	private Socket client;
	private BufferedReader input;
	private DataOutputStream output;

	// Default values
	private final String SERVER_HOST			= "localhost";
	private final int SERVER_PORT				= 17056; // Which port should we connect through



public TClientSCon() {

	try {
		// Create client and I/O objects
		client = new Socket(this.SERVER_HOST, this.SERVER_PORT);
		output = new DataOutputStream(client.getOutputStream());
		input = new BufferedReader(new InputStreamReader(client.getInputStream()));

		
	} catch (UnknownHostException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	} catch (Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	} finally {
		try {
			// Close resources
			input.close();
			output.close();
			client.close();

			System.out.println("Connection closed.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
}