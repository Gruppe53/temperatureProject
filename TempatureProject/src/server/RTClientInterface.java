package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RTClientInterface extends Remote {
	/**
	 * Calls remote object and returns the current average temperature.
	 * 
	 * @return current average temperature.
	 * @throws RemoteException is thrown if a remote error occurs.
	 */
	public String getAverageTemperature() throws RemoteException;
	
	/**
	 * Calls remote object and returns the current list with all recorded data.
	 * 
	 * @return current data list.
	 * @throws RemoteException is thrown if a remote error occurs.
	 */
	public List<TStoredData> getStoredData() throws RemoteException;
	
	/**
	 * Allows a client to subscribe to the server's client list.
	 * 
	 * @param description Description/location of the sensor (client).
	 * @return true if client is successfully subscribed.
	 * @throws RemoteException is thrown if a remote error occurs.
	 */
	public boolean subscribeTClient(String description) throws RemoteException;
	
	/**
	 * * Allows a client to publish current temperature to server.
	 * 
	 * @param temperature Current temperature.
	 * @return true if temperature was received and successfully saved.
	 * @throws RemoteException is thrown if a remote error occurs.
	 */
	public boolean publishTemperature(double temperature) throws RemoteException;
}
