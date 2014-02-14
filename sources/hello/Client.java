package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket clientSocket = null;
	PrintWriter envoi = null;
	BufferedReader reception = null;

	public Client() {
		try {
			clientSocket = new Socket("localhost",8599);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	    try {
			envoi = new PrintWriter(clientSocket.getOutputStream(), true);
			reception = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public String envoyer(String message) {
		    envoi.println(message);
		    
		    try {
				return reception.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		    return null;
	}

}
