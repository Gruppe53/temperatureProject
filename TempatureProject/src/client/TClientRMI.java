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
	private String robj = "clientList";
	
	public TClientRMI(String host, int port) throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry(host, port);
		this.rtc = (RTClientInterface) reg.lookup(robj);
	}

	@Override
	public double getAverageTemperature(String description) throws RemoteException {
		return rtc.getAverageTemperature(description);
	}

	@Override
	public boolean subscribeTClient(String description) throws RemoteException {
		return rtc.subscribeTClient(description);
	}

	@Override
	public boolean publishTemperature(String description, double temperature) throws RemoteException {
		return rtc.publishTemperature(description, temperature);
	}
}