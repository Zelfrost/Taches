package serveur;



import outils.Tache;

import java.util.Collections;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;



class Serveur
{
	private ServerSocket socket;
	private ArrayList<Tache> taches;



	public Serveur()
	{
		taches = new ArrayList<Tache>();

		try {
			socket = new ServerSocket(9921);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}


	
	public void miseEnService()
	{
		Socket unClient = null;
		
		while (true) {
			try {
				unClient = socket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		
			realiseService(unClient);
		}
	}
	
	/**
	 *	Cette méthode récupère la demande et renvoi vers les fonctions de traitement secondaires,
	 *	qui récupèreront elles-mêmes les données dont elles ont besoin.
	 */
	private void realiseService(Socket unClient) {
		PrintWriter envoi = null;
		BufferedReader reception = null;

		try {
	
			envoi = new PrintWriter(unClient.getOutputStream(), true);
			
			reception = new BufferedReader(
                    new InputStreamReader(unClient.getInputStream()));
			
			String demande = "";

			while(! demande.equals("STOP")) {
				demande = reception.readLine();
				if(demande.equals("Lister")) {
					envoi.println( lister() );
				} else if( demande.equals("ListerUtilisateur")) {
					envoi.println( listerUtilisateur(reception) );
				} else if( demande.equals("ListerNA")) {
					envoi.println( listerNA() );
				} else if( demande.equals("ListerA")) {
					envoi.println( listerA() );
				} else if( demande.equals("ListerT")) {
					envoi.println( listerT() );
				} else if( demande.equals("Ajouter") ) {
					envoi.println( 	
						((ajouter(reception))
						?"La tâche s'est bien ajoutée.\nSTOP"
						:"Erreur lors de l'ajout.\nSTOP")
					);
				} else if( demande.equals("Affecter") ) {
					envoi.println( 	
						((affecter(reception))
						?"La tâche a bien été affectée.\nSTOP"
						:"Erreur lors de l'affectation.\nSTOP")
					);
				} else if( demande.equals("Terminer")) {
					envoi.println( 	
						((terminer(reception))
						?"La tâche a bien été terminée.\nSTOP"
						:"Erreur pour mettre fin à la tâche.\nSTOP")
					);
				} else if( demande.equals("Supprimer")) {
					envoi.println( 	
						((supprimer(reception))
						?"La tâche a bien été supprimée.\nSTOP"
						:"Erreur lors de la suppression de la tâche.\nSTOP")
					);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 *	Pas de données secondaires, liste les tâches
	 */
	private String lister()
	{
		String res = "";
		for(int i=0; i<taches.size(); i++) {
			res += i + ": \n";
			res += taches.get(i).toString() + "\n";
			res += "\n====================\n\n";
		}
		if(res.length() > 0)
			res = "\n" + res.substring(0, res.length() - 22);
		else
			res = "Aucune tâche pour le moment.\n";

		res += "STOP";

		return res;
	}

	/**
	 * Récupère le nom de l'utilisateur, affiche les tâches qui lui sont affectées
	 */
	private String listerUtilisateur(BufferedReader reception)
	{
		String res = "";
		String utilisateur;
		try {
			utilisateur = reception.readLine();
		} catch( Exception e ) {
			return "Aucune tâche pour le moment.\n";
		}

		for(int i=0; i<taches.size(); i++) {
			if(taches.get(i).affecteeA() != null && taches.get(i).affecteeA().equals(utilisateur)) {
				res += i + ": \n";
				res += taches.get(i).toString() + "\n";
				res += "\n====================\n\n";
			}
		}
		if(res.length() > 0)
			res = "\n" + res.substring(0, res.length() - 22);
		else
			res = "Aucune tâche à votre nom pour le moment.\n";

		res += "STOP";

		return res;
	}

	/**
	 * Pas de données secondaires, liste les tâches non affectées
	 */
	private String listerNA()
	{
		String res = "";

		for(int i=0; i<taches.size(); i++) {
			if(taches.get(i).affecteeA() != null) {
				res += i + ": \n";
				res += taches.get(i).toString() + "\n";
				res += "\n====================\n\n";
			}
		}
		if(res.length() > 0)
			res = "\n" + res.substring(0, res.length() - 22);
		else
			res = "Aucune tâche libre pour le moment.\n";

		res += "STOP";

		return res;
	}

	/**
	 * Pas de données secondaires, liste les tâches affectées
	 */
	private String listerA()
	{
		String res = "";

		for(int i=0; i<taches.size(); i++) {
			if(taches.get(i).affecteeA() == null) {
				res += i + ": \n";
				res += taches.get(i).toString() + "\n";
				res += "\n====================\n\n";
			}
		}
		if(res.length() > 0)
			res = "\n" + res.substring(0, res.length() - 22);
		else
			res = "Aucune tâche affectée pour le moment.\n";

		res += "STOP";

		return res;
	}

	/**
	 * Pas de données secondaires, liste les tâches terminées
	 */
	private String listerT()
	{
		String res = "";

		for(int i=0; i<taches.size(); i++) {
			if(taches.get(i).statut().equals("Réalisée")) {
				res += i + ": \n";
				res += taches.get(i).toString() + "\n";
				res += "\n====================\n\n";
			}
		}
		if(res.length() > 0)
			res = "\n" + res.substring(0, res.length() - 22);
		else
			res = "Aucune tâche terminée pour le moment.\n";

		res += "STOP";

		return res;
	}

	/**
	 * Récupère le nom de l'utilisateur et un libellé de tâche. Crée la tâche associée
	 */
	private boolean ajouter(BufferedReader reception)
	{
		String libelle, auteur;

		try {
			auteur 	= reception.readLine();
			libelle = reception.readLine();
		} catch( Exception e ) {
			return false;
		}
		
		Tache t = new Tache(libelle, auteur);

		try {
			taches.add(t);
			Collections.sort(taches);
			return true;
		} catch( Exception e ) {
			return false;
		}
	}

	/**
	 * Récupère un id de tâche et un nom d'utilisateur. Affecte la tâche à l'utilisateur
	 */
	private boolean affecter(BufferedReader reception)
	{
		int id;
		String affecteeA;

		try {
			id 			= Integer.parseInt(reception.readLine());
			affecteeA 	= reception.readLine();
		} catch( Exception e ) {
			return false;
		}

		try {
			taches.get(id).affecter(affecteeA);
			return true;
		} catch( Exception e ) {
			return false;
		}
	}

	/**
	 * Récupère un id de tâche. Met fin à la tâche
	 */
	private boolean terminer(BufferedReader reception)
	{
		int id;

		try {
			id = Integer.parseInt(reception.readLine());
		} catch( Exception e ) {
			return false;
		}

		try {
			taches.get(id).finir();
			return true;
		} catch( Exception e ) {
			return false;
		}
	}

	/**
	 * Récupère un id de tâche. Supprime la tâche
	 */
	private boolean supprimer(BufferedReader reception)
	{
		int id;

		try {
			id = Integer.parseInt(reception.readLine());
		} catch( Exception e ) {
			return false;
		}

		try {
			taches.remove(id);
			return true;
		} catch( Exception e ) {
			return false;
		}
	}
}