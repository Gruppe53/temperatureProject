package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import server.RTClientInterface;
import server.TStoredData;

public class TClientRMI implements RTClientInterface {
	private RTClientInterface rtc;
	
	public TClientRMI(String host, int port, String robj) throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry(host, port);
		this.rtc = (RTClientInterface) reg.lookup(robj);
	}
	
	public String getAverageTemperature() throws RemoteException {
		return rtc.getAverageTemperature();
	}
	
	public List<TStoredData> getStoredData() throws RemoteException {
		return rtc.getStoredData();
	}
}