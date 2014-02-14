package hello;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		String resultat = client.envoyer("lister");
		
		System.out.println(resultat);
	}

}
