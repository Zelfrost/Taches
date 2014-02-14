package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
	private ServerSocket serveurSocket = null;
	
	public Serveur() {
		try {
			serveurSocket = new ServerSocket(8599);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void miseEnService() {
		Socket unClient = null;
		
		while (true ) {
			try {
				unClient = serveurSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		
			realiseService(unClient);
		}
	}
	
	private void realiseService(Socket unClient) {
		PrintWriter envoi = null;
		BufferedReader reception = null;
		try {
	
			envoi = new PrintWriter(unClient.getOutputStream(), true);
			
			reception = new BufferedReader(
                    new InputStreamReader(unClient.getInputStream()));
	
			String message = reception.readLine();
			envoi.println("bonjour " + message);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
