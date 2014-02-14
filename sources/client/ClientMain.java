package client;

import java.util.Scanner;



class ClientMain 
{
	public static void main(String[] args)
	{
		Client client = new Client();

		Scanner sc = new Scanner(System.in);
		boolean cntn = true;

		while(cntn) {
			System.out.println("Que souhaitez vous faire ?\n");

			System.out.println("\t- Lister");
			System.out.println("\t- Ajouter");
			System.out.println("\t- Affecter");
			System.out.println("\t- Terminer");
			System.out.println("\t- Supprimer\n");

			String demande = sc.nextLine();
			if(!demande.equals("Lister") && !demande.equals("Ajouter") && !demande.equals("Affecter") && !demande.equals("Terminer") && !demande.equals("Supprimer"))
				System.out.println("Mauvaise demande...");
			else {
				if(demande.equals("Lister")) {
					System.out.println("Souhaitez vous lister :\n");

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

				}
				
				String resultat = client.envoyer(demande);
				System.out.print(resultat);
			}

			System.out.println("\nSouhaitez vous continuer ?");
			System.out.println("\t- 1 : Oui");
			System.out.println("\t- 2 : Non\n");

			if(! sc.nextLine().equals("1")){
				cntn = false;
				client.fermer();
			}
		}

		System.out.println("Bonne journée !\n");
	}

}
