package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TClientSCon implements Runnable {
	private Socket client;
	private BufferedReader input;
	private DataOutputStream output;
	private TSensor sensor;
	private String location;
	private String serverHost;
	private int serverPort;

	// Default values
	private final TimeUnit UPDATE_UNIT			= TimeUnit.MILLISECONDS; // What time unit should we use
	private final int UPDATE_INTERVAL			= 5000; // With which interval should we update the temperature
	private final int START_TIME				= 5000; // Delay the client from sending data to the server (cosmetic reasons only)
	
	public TClientSCon(TSensor sensor, String location, String serverHost, int serverPort) {
		this.sensor = sensor;
		this.location = location;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}

	@Override
	public void run() {
		try {
			// Create client and I/O objects
			this.client = new Socket(this.serverHost, this.serverPort);
			this.output = new DataOutputStream(client.getOutputStream());
			this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
	
			// If client and I/O objects are created...
			if (this.client != null && this.output != null && this.input != null) {
				// Send the room location/description
				try {
					this.output.writeBytes(this.location + "\r");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				// Create a scheduled executor service so we can keep sending
				// new data to the server with a specific interval
				ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
	
				// TODO Find a way to close executor if socket is closed
				// Define executor service
				exe.scheduleAtFixedRate(new Runnable() {
						// What should be done every update interval
						@Override
						public void run() {	
							try {
								// Set new temperature
								sensor.newTemperature();
								
								// Output new temperature to server as bytes
								output.writeBytes(sensor.getTemperatureAsString(2) + "\r");
							} catch (IOException e) {
								System.out.println(e.getMessage());
								e.printStackTrace();
							} catch (Exception e) {
								System.out.println(e.getMessage());
								e.printStackTrace();
							}
						}
					},					// Runnable block-end
				this.START_TIME,		// Start immediately
				this.UPDATE_INTERVAL,	// The update interval (by default 5000)
				this.UPDATE_UNIT);		// Defines the UPDATE_INTERVAL unit (by default milliseconds)
	
				// Print response
				while (true) {
					String res = null;
					res = input.readLine();
					
					if (res.indexOf("END") != -1)
						break;
				}
			}
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
				this.input.close();
				this.output.close();
				this.client.close();
	
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