package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TClient {
	private TSensor sensor;
	private Socket client;
	private DataInputStream input;
	private DataOutputStream output;
	
	// Default values
	private final String SERVER_HOST = "localhost";
	private final int SERVER_PORT = 15051;
	private final int UPDATE_INTERVAL = 5000;
	private final TimeUnit UPDATE_UNIT = TimeUnit.MILLISECONDS;
	
	public TClient() {
		// TODO TemperatureClient.main
		/**
		 * 1. Create CLI-object (Apache Commons CLI, JAR already included in project)
		 * 		- Command Line Input should hold information for initial RNG-values
		 * 		- Use default values (specified in TemperatureSensor-class) if no command arguments are given
		 * 		- Following command lines should be implemented:
		 * 			* -inittemp <double>	(initial temperature)
		 * 			* -initfactor <double>	(initial factor)
		 * 			* -upinterval <double>	(update interval)
		 * 2. Create sensor with default values or input data from CLI
		 * 3. Create client-socket and necessary I/O objects
		 * 4. Wait for the client to connect to a server with socket
		 * 5. Start timer by adding sensor-task and UPDATE_INTERVAL to timer-schedule
		 * 		- UPDATE_INTERVAL should be used if no interval was given through before mentioned CLI 
		 * 6. Each return value from sensor should be passed to the server find solution
		 * 		- This will be done for as long as the client isn't closed
		 * 
		 * Extra:
		 * Try to create a GUI 
		 */
		
		// Create sensor
		sensor = new TSensor();
		
		System.out.println("Sensor initialized with temperature: " + sensor.getTemperatureAsDouble(2));
		
		try {
			// Create client and I/O objects
			client = new Socket(this.SERVER_HOST, this.SERVER_PORT);
			output = new DataOutputStream(client.getOutputStream());
			input = new DataInputStream(client.getInputStream());
			
			// If client and I/O objects are created...
			if(client != null && output != null && input != null) {
				try {
					output.write(sensor.getTemperatureAsByte());
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
				
				// Create a scheduled executor service so we can keep sending new data to the server with a specific interval
				ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
				
				// Define executor service
				exe.scheduleAtFixedRate(new Runnable() {
						// What should be done every update interval
						@Override
						public void run() {
							// Set new temperature
							sensor.newTemperature();
							
							System.out.println("Changed the temperature: " + sensor.getTemperatureAsDouble(2));
							
							// Output new temperature to server as bytes
							try {
								output.write(sensor.getTemperatureAsByte());
								System.out.println("New temperature sent to server.");
							} catch (IOException e) {
								System.out.println(e.getMessage());
							}
						}
					},					// Runnable block-end
				0,						// Start immediately
				this.UPDATE_INTERVAL,	// The update interval (by default 5000)
				this.UPDATE_UNIT); // Defines the UPDATE_INTERVAL unit
			}
		} catch (UnknownHostException e) {
			System.out.println("Host not recognized.");
		} catch (IOException e) {
			System.out.println("IO Exception.");
		}
	}
	
	
	// Start the client...
	public static void main(String[] args) {
		new TClient();
	}
}