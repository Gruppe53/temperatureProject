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
	 * Class-constructor that takes two parameters - a client socket and a room
	 * location/description.
	 * <p>
	 * Note that "RunningTClient" can't (shouldn't) be manipulated with. It is
	 * supposed to run as a single thread (Runnable), and keep doing so until
	 * the server has received <MAX_UPDATES> temperatures from client.
	 * <p>
	 * RTClient will terminate any resources if incoming client closes the
	 * connection or "END"-message has been sent to client (indicating
	 * <MAX_UPDATES> has been met).
	 * <p>
	 * <MAX_UPDATES> should be removed if this program ever leaves test phase...
	 * 
	 * @param client
	 *            the client socket
	 * @param location
	 *            the location or description of the client
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

			if (input != null && output != null) {
				// First line received should be the room location/description
				String location;
				if((location = input.readLine()) != null)
					this.location = location;

				while (true) {
					String temperatureStr = null;

					// If something has been read
					if ((temperatureStr = input.readLine()) != null) {
						// Check if input can be parsed as a double
						try {
							double temperature = Double.parseDouble(temperatureStr);
							
							// Add temperature to tData for later average calculation
							tData.add(new TStoredData(temperature));
							
							// Calculate the new average
							calculateAverage();
							
							// For testing purposes we keep track of a counter which
							// will automatically end the test if updates reach a maximum
							updates++;
						} catch(NumberFormatException e) {
							System.out.println("Received something else than a double [error: " + e.getMessage() + "].");
						}
						
						// Print the new average and room location/description
						System.out.println(average + " [" + this.location + "]");
					}

					// If update hits 100 break while loop
					if (updates == MAX_UPDATES) {
						output.writeBytes("END");
						break;
					} else
						output.writeBytes(temperatureStr);

					// If the client is closed break while loop
					if (client.isClosed())
						break;
				}
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				client.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void calculateAverage() {
		// Calculate average temperature stored in tData
		for (int i = 0; i < tData.size(); i++) {
			double sum = 0;

			sum += tData.get(i).getTemperature();

			average = sum / tData.size();
		}
	}
}