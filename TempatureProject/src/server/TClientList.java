package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TClientList extends UnicastRemoteObject implements RTClientInterface {
	private static final long serialVersionUID = 1L;
	
	private List<TStoredData> tData = new ArrayList<TStoredData>();
	
	/**
	 * Running Temperature Client - will hold the stored temperatures published by the clients.
	 * 
	 * @throws RemoteException
	 */
	public TClientList() throws RemoteException {
		super();
	}
	
	@Override
	public double getAverageTemperature(String description) throws RemoteException {
		for(int i = 0; i < tData.size(); i++)						// Iterate through client list
			if(tData.get(i).getDescription().equals(description))	// Compare parameter description with client description
				return calculateAverage(i);							// If equal return a calculated average of the client's temperature
		
		return -273; 												// If no match is found return -273 (this should be handled by the client as an error) 
	}
	
	@Override
	public boolean subscribeTClient(String description) throws RemoteException {
		for(TStoredData t : tData)						// Iterate through client list
			if(t.getDescription().equals(description))	// Compare parameter description with client description
				return false;							// If description already exist return false (we won't allow two matching descriptions)
		
		if(tData.add(new TStoredData(description)))		// If the new client is added to the list successfully...
			return true;								// ...return true
		
		return false;									// Return false otherwise
	}

	@Override
	public boolean publishTemperature(String description, double temperature) throws RemoteException {
		for(int i = 0; i < tData.size(); i++)						// Iterate through client list
			if(tData.get(i).getDescription().equals(description))	// Compare parameter description with client description
				if(tData.get(i).addTemperature(temperature)) {		// If equal and temperature is successfully added return true
					System.out.println("[" + description + "]\t" + temperature + " recorded.");
					return true;
				}
				
		return false;												// Return false otherwise
	}
	
	/**
	 * Will calculate the average temperature - takes a single parameter
	 * to find the desired client's temperature list.
	 * 
	 * @param i the client's index number
	 * @return average temperature for specified client
	 */
	private double calculateAverage(int i) {
		double sum = tData.get(i).getTemperatureSum();			// Get the sum of a client's stored temperatures
		
		return sum / this.tData.get(i).getTemperature().size();	// Return the sum divided by the amount of recorded temperatures (equals the average) 
	}
}