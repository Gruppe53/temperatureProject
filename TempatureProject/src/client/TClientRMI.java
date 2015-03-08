package client;

import java.rmi.*;


public interface client extends Remote {
	<T> T executeTask(Task<T> t) throws RemoteException
}
public class TClientRMI {

}
