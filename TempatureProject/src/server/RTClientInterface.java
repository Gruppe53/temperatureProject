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
}
