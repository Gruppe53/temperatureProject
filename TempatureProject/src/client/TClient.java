package client;

import java.util.Scanner;

import client.GUI.ClientGUI;

public class TClient {
	private TSensor sensor;
	protected Thread thread = null;

	// Default values
	private final String LOCATION_DESCRIPTION	= "Test room 1"; // This should be determined by CLI

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
			new TClientSCon(sensor, LOCATION_DESCRIPTION)
		).start();
		
		// TODO
		// Create RMI (maybe it needs to be done in its own thread, note how stream has been implemented)
		
		// Read input from human
		int option = 0;
		
		// Create an input scanner
		Scanner scanner = new Scanner(System.in);
		
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
					// TODO (important to implement)
					// 1. Call getAverage on RMI object
					// 2. Print the average
					
					// Test
					System.out.println("Chose: " + option);
					
					// Prepare next line to be read
					scanner.nextLine();
					break;
				case 2:
					// TODO (optional...)
					// Stop the client
					
					// Test
					System.out.println("Chose: " + option);
					
					// For now, make sure next line is prepared
					scanner.nextLine();
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
					
					// Prepare next line to be read
					scanner.nextLine();
					break;
			}
			
			// If we choose option 2 break while loop and end client...
			if(option == 2)
				break;
			
			// Cosmetic...
			System.out.print("Choose option: ");
		}
		
		System.out.println("Exiting...");
		
		scanner.close();
	}

	// Start the client...
	public static void main(String[] args) {
		new TClient();
	}
}