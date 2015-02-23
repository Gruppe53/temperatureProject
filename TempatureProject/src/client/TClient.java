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

public class TClient {
	private TSensor sensor;
	private Socket client;
	private BufferedReader input;
	private DataOutputStream output;
	
	// Default values
	private final String SERVER_HOST = "localhost";
	private final int SERVER_PORT = 17056;
	private final int UPDATE_INTERVAL = 5000;
	private final TimeUnit UPDATE_UNIT = TimeUnit.MILLISECONDS;
	
	public TClient() {	
		// Create sensor
		sensor = new TSensor();
		
		System.out.println("Sensor initialized with temperature: " + sensor.getTemperatureAsDouble(2));
		
		try {
			// Create client and I/O objects
			client = new Socket(this.SERVER_HOST, this.SERVER_PORT);
			output = new DataOutputStream(client.getOutputStream());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			// If client and I/O objects are created...
			if(client != null && output != null && input != null) {
				// Create a scheduled executor service so we can keep sending new data to the server with a specific interval
				ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
				
				// TODO Find a way to close executor if socket is closed.........
				// Define executor service
				exe.scheduleAtFixedRate(new Runnable() {
						// What should be done every update interval
						@Override
						public void run() {
							try {
								// Set new temperature
								sensor.newTemperature();
								System.out.println("Changed the temperature: " + sensor.getTemperatureAsDouble(2));
								
								// Output new temperature to server as bytes
								output.write(sensor.getTemperatureAsByte());
								System.out.println("New temperature sent to server.");
							} catch (IOException e) {
								System.out.println("-----------------ERROR-----------------");
								System.out.println(e.getMessage());
								e.printStackTrace();
							} catch(Exception e) {
								System.out.println("-----------------ERROR-----------------");
								System.out.println(e.getMessage());
								e.printStackTrace();
							}
						}
					},					// Runnable block-end
				0,						// Start immediately
				this.UPDATE_INTERVAL,	// The update interval (by default 5000)
				this.UPDATE_UNIT);		// Defines the UPDATE_INTERVAL unit
				
				// Print response
				String res;
				
				while(true) {
					res = input.readLine();
					
					// If "END" is recieved, stop printing AND stop executor
					// Not working
					if(res != null) {
						System.out.println("Server: " + res);
						
						if(res.indexOf("END") != -1) {
							exe.shutdown();
							break;
						}
					}
				}
				
				// If we end up here exe should be shut down, if not already...
				exe.shutdownNow();
			}
		} catch (UnknownHostException e) {
			System.out.println("-----------------ERROR-----------------");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("-----------------ERROR-----------------");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("-----------------ERROR-----------------");
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
				System.out.println("-----------------ERROR-----------------");
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("-----------------ERROR-----------------");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	// Start the client...
	public static void main(String[] args) {
		new TClient();
	}
}