package client;

import java.util.Scanner;



class ClientMain 
{
	/**
	 *	Cette méthode permet simplement de récupérer l'ordre principal que l'utilisateur veut exécuter :
	 *	- Lister pour avoir la liste des tâches, Ajouter pour en ajouter une, Supprimer pour en supprimer une, etc ...
	 * 	Elle crée une instance de Client, qui va demander à l'utilisateur son nom.
	 * 	C'est la classe Client qui s'occupe de demander les données secondaire, l'id de la tâche dans le cas d'une suppression par exemple
	 */
	public static void main(String[] args)
	{
		Client client = new Client();

		Scanner sc = new Scanner(System.in);
		boolean cntn = true;

		while(cntn) {
			System.out.println("\nQue souhaitez vous faire ?\n");

			System.out.println("\t- 1 : Lister");
			System.out.println("\t- 2 : Ajouter");
			System.out.println("\t- 3 : Affecter");
			System.out.println("\t- 4 : Terminer");
			System.out.println("\t- 5 : Supprimer");
			System.out.println("\t- 6 : Quitter\n");

			String demande = sc.nextLine();
			if(!demande.equals("1") && !demande.equals("2") && !demande.equals("3") && !demande.equals("4") && !demande.equals("5") && !demande.equals("6"))
				System.out.println("Mauvaise demande...");
			else {
				if(demande.equals("1")) {
					System.out.println("\nSouhaitez vous lister :\n");

					System.out.println("\t- 1 : Toutes les tâches");
					System.out.println("\t- 2 : Toutes vos tâches");
					System.out.println("\t- 3 : Toutes les tâches non affectée");
					System.out.println("\t- 4 : Toutes les tâches affectée");
					System.out.println("\t- 5 : Toutes les tâches terminée");

					demande = sc.nextLine();
					if(demande.equals("1"))
						demande = "Lister";
					else if(demande.equals("2"))
						demande = "ListerUtilisateur";
					else if(demande.equals("3"))
						demande = "ListerNA";
					else if(demande.equals("4"))
						demande = "ListerA";
					else
						demande = "ListerT";
				} else if(demande.equals("2"))
					demande = "Ajouter";
				else if(demande.equals("3"))
					demande = "Affecter";
				else if(demande.equals("4"))
					demande = "Terminer";
				else if(demande.equals("5"))
					demande = "Supprimer";
				else {
					cntn = false;
					client.fermer();
				}

				if(cntn) {
					String resultat = client.envoyer(demande);
					System.out.print(resultat);
				}
			}
		}

		System.out.println("Bonne journée !\n");
	}

}
