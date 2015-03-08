package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import client.GUI.ClientGUI;

public class TClient {
	private TSensor sensor;
	private TClientRMI rmi;
	protected Thread thread = null;

	// Default values
	private final String LOCATION_DESCRIPTION	= "Test room 1"; // This should be determined by CLI
	private final String SERVER_HOST			= "localhost"; // Server address
	private final int SERVER_PORT				= 17056; // Which port should we connect through
	private final int RMI_PORT					= (SERVER_PORT + 1);

	public TClient() {
		// Create GUI
		//ClientGUI gui = new ClientGUI();
		//gui.createAndShowGUI(LOCATION_DESCRIPTION);
		
		// For now, GUI isn't necessary (isn't completed anyway...)
		
		// Create sensor
		sensor = new TSensor();
		System.out.println("Sensor initialized with temperature: " + sensor.getTemperatureAsDouble(2));
		
		// Syncing thread...
		synchronized(this) {
			this.thread = Thread.currentThread();
		}
		
		// Create stream
		new Thread(
			new TClientSCon(sensor, LOCATION_DESCRIPTION, SERVER_HOST, SERVER_PORT)
		).start();
		
		// Create RMI
		try {
			rmi = new TClientRMI(SERVER_HOST, RMI_PORT, LOCATION_DESCRIPTION);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		// Read input from human
		// Create an input scanner
		Scanner scanner = new Scanner(System.in);
		
		// Keep track of option chosen
		int option = 0;
		
		// Cosmetic so that the human knows what he/she can do
		System.out.println("Options:");
		System.out.println("1. Get average (RMI)");
		System.out.println("2. Stop client");
		System.out.println("3. This message...");
		System.out.print("Choose option: ");
		
		// Keep scanner active until we choose option 2 which stops the client
		while(true) {
			// What we should do depending on input (use cases and NOT if-statements)
			switch(option = scanner.nextInt()) {
				case 1:
					try {
						System.out.println(rmi.getAverageTemperature());
					} catch (RemoteException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					
					break;
				case 2:
					// TODO (optional...)
					// Stop the client
					
					// Test
					System.out.println("Chose: " + option);
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