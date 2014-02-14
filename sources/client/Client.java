package client;



import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;



class Client
{
	String nom;

	Socket clientSocket = null;
	PrintWriter envoi = null;
	BufferedReader reception = null;



	public Client()
	{
		nom = demanderNom();

		try {
			clientSocket = new Socket("localhost", 9921);
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

	private String demanderNom()
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Quel est vôtre nom ?");
		return sc.nextLine();
	}
	
	public String envoyer(String message)
	{
	    envoi.println(message);

		Scanner sc = new Scanner(System.in);
		if(message.equals("Ajouter")) {

			envoi.println(nom);
			System.out.println(nom);

			System.out.println("Quel est le nom de la tache ?");
			envoi.println( sc.nextLine() );

		} else if(message.equals("Affecter")) {

			System.out.println("Quel est l'id de la tâche ?");
			envoi.println( sc.nextLine() );

			System.out.println("A qui souhaitez vous l'affecter ?");
			envoi.println( sc.nextLine() );

		} else if(message.equals("Terminer") || message.equals("Supprimer")) {

			System.out.println("Quel est l'id de la tâche ?");
			envoi.println( sc.nextLine() );

		}
		String res = "";
		String line;

	    try {
	    	while(! (line = reception.readLine()).equals("STOP") )
				res += line + "\n";
			
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	    return null;
	}

	public void fermer()
	{
		envoi.println("STOP");
	}
}
