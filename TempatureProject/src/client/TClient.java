package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TClient {
	private TSensor sensor;
	private TClientRMI rmi;
	private ScheduledExecutorService exe;

	// Default values
	private final String LOCATION_DESCRIPTION = "Test room 3";		// Sensor position (e.g. "living room", "kitchen"....)
	private final String SERVER_HOST = "localhost";					// Server address
	private final int RMI_PORT = 17056;								// Server port
	
	private final int UPDATE_INTERVAL = 5000;						// Update interval
	private final int START_TIME = 5000;							// Starting delay
	private final TimeUnit UPDATE_UNIT = TimeUnit.MILLISECONDS;		// Time unit for update/delay

	public TClient() {
		// Create sensor
		sensor = new TSensor();
		System.out.println("Sensor initialized with temperature: " + sensor.getTemperatureAsDouble(2));
		
		// Bind client and subscribe current client to server
		try {
			// Lookup
			rmi = new TClientRMI(SERVER_HOST, RMI_PORT);
			
			// Subscribe
			rmi.subscribeTClient(LOCATION_DESCRIPTION);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		// Start sending data to server
		exe = Executors.newSingleThreadScheduledExecutor();
		exe.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					// Set new temperature
					sensor.newTemperature();
				
					// Output new temperature to server as bytes
					rmi.publishTemperature(LOCATION_DESCRIPTION, sensor.getTemperatureAsDouble(2));
				} catch (RemoteException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		},						// Runnable block-end
		this.START_TIME,		// Start immediately
		this.UPDATE_INTERVAL,	// The update interval (by default 5000)
		this.UPDATE_UNIT);		// Defines the UPDATE_INTERVAL unit (by default milliseconds)
		
		
		// Read input from human - using scanner
		Scanner scanner = new Scanner(System.in);
		
		// Keep track of option chosen
		int option = 0;
		
		// Cosmetic so that the human knows what he/she can do
		System.out.println("Options:");
		System.out.println("1. Get average");
		System.out.println("2. Stop client");
		System.out.println("3. This message...");
		System.out.print("Choose option: ");
		
		// Keep scanner active until we choose option 2 which stops the client
		while(true) {
			// What we should do depending on input (use cases and NOT if-statements)
			switch(option = scanner.nextInt()) {
				case 1:
					// Print current average
					try {
						System.out.println(rmi.getAverageTemperature(LOCATION_DESCRIPTION));
					} catch (RemoteException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					
					break;
				case 2:
					// Shut down executor
					exe.shutdownNow();
					break;
				case 3:
					// Print options menu
					System.out.println();
					System.out.println("Options:");
					System.out.println("1. Get average (RMI)");
					System.out.println("2. Stop client");
					System.out.println("3. This message...");
					break;
				default:
					// If chosen input isn't acknowledged ask human to try again
					System.out.println();
					System.out.println("Wrong input, please try again.");
					break;
			}
			
			// If we choose option 2 break while loop and end client...
			if(option == 2)
				break;
			
			// Cosmetic...
			System.out.print("Choose option: ");
			
			// Prepare next line to be read
			scanner.nextLine();
		}
		
		// Prompt user that we're closing down
		System.out.println("Exiting...");
		
		// Closing resources
		scanner.close();
	}

	// Start the client...
	public static void main(String[] args) {
		new TClient();
	}
}