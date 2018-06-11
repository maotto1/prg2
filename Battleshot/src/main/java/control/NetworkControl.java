package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import fieldState.Response;
import model.Shot;
import network.RemoteInterface;
import network.RemoteObject;

public class NetworkControl {
	
	public static int myPort = 6061, port= 6062;
	public static String host = "localhost";
	private final GameControl game;
	private RemoteObject myRemoteObject;
	private RemoteInterface adversaryRemoteObject;
	public int registryPort;
	
	
	public NetworkControl(GameControl game) {
		this.game = game;
		myRemoteObject = new RemoteObject(game);
	}
	
	public void bindExportObject() {
		registryPort = myPort+2;
		
		// eigenes RemoteObject online stellen
		try {
			//Registry registry = LocateRegistry.getRegistry(registryPort);
			LocateRegistry.createRegistry(registryPort);  	// ursprünglich: Registry.REGISTRY_PORT
		} catch (RemoteException e1) {
			System.out.println("Registry nicht erzeugt.");
			e1.printStackTrace();
		}
		
		RemoteInterface stub;
		Registry reg;
		try {
			stub = (RemoteInterface) UnicastRemoteObject.exportObject(myRemoteObject, registryPort);  // alternativ Port 0 
			System.out.println("exportObject nicht schiefgegangen.");
			reg = LocateRegistry.getRegistry(registryPort);
			System.out.println("lokale Registry gefunden.");
			reg.rebind("RemoteInterface", stub);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		System.out.println("Phase 1 abgeschlossen.");
		//System.setProperty("java.rmi.server.hostname","192.168.0.6");
		//RemoteServer.setLog(System.out);
	}
	
	public void lookup() {
		// das vom Gegner bekommen 
	    try {
	    	int port2 = port+2;
	        Registry registry = LocateRegistry.getRegistry(host,port2);
	        System.out.println("Client: "+myPort+ " schaut in Registry von "+ port2 + "\nzugehöriger Server hat Registry angelegt auf "+ registryPort);
	        adversaryRemoteObject = (RemoteInterface) registry.lookup("RemoteInterface");
	        
	        
	    } catch (Exception e) {
	        System.err.println(Thread.currentThread().getName() + ":  Client exception: " + e.toString());
	        e.printStackTrace();
	    }
	}
	
	
	public Response fire(Shot shot) {
		try {
			Response response = adversaryRemoteObject.treatShot(shot);
			System.out.println("response: " + response);
			return response;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void runServer() {
		try (
				ServerSocket serverSocket = new ServerSocket(myPort);
				Socket clientSocket = serverSocket.accept();
				PrintWriter serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		) {
			String toSend, serverInput = null;
			System.out.println("Ich bin " + myPort + " und fungiere als Server.");

			while ((serverInput = serverIn.readLine()) != null) {
				System.out.println("echo: (" + myPort + ")\t " + serverInput);
			}
			// System.out.println("echo: " + serverInput);

		} catch (UnknownHostException e) {
			System.err.println(Thread.currentThread().getName() + ":  Don't know about host " + host);
			System.exit(1);
		} catch (IOException e) {
			System.err.println(Thread.currentThread().getName() + ":  Couldn't get I/O for the connection to " + host + ":"+ port);
		}
	}

	public boolean runClient() {
		boolean succes = false;
		try (

				Socket echoSocket = new Socket(host, port);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

		) {
			String toSend, serverInput = null;
			System.out.println("Ich bin Client: " + myPort);
			toSend = myPort + " möchte Kontakt zu " + port;
			out.println(toSend);
			succes = true;

		} catch (UnknownHostException e) {
			System.err.println(Thread.currentThread().getName() + ":  Don't know about host " + host);
			//System.exit(1);
		} catch (IOException e) {
			System.err.println(Thread.currentThread().getName() + ":  Client "+ myPort + " couldn't get I/O for the connection to " + host + port);
		}
		return succes;
	}
}
