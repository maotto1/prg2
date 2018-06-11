package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fieldState.Response;
import model.Shot;

public interface RemoteInterface extends Remote{
	
	public Response treatShot(Shot shot) throws RemoteException;

}
