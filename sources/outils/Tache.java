package outils;



public class Tache implements Comparable<Tache>
{
	private String libelle;
	private String auteur;
	private String statut;
	private String affecteA;



	public Tache(String libelle, String auteur)
	{
		this.libelle 	= libelle;
		this.auteur 	= auteur;
		statut 			= "Libre";
		affecteA	 	= null;
	}



	public String libelle()
	{
		return libelle;
	}

	public String auteur()
	{
		return auteur;
	}

	public String statut()
	{
		return statut;
	}

	public String affecteA()
	{
		return affecteA;
	}



	public void affecter(String nom)
	{
		statut 		= "Affectée";
		affecteA 	= nom;
	}

	public void finir()
	{
		statut 		= "Réalisée";
		affecteA 	= null;
	}



	public int compareTo(Tache t)
	{
		return libelle.compareTo(t.libelle());
	}



	public String toString()
	{
		String res;

		res = "Tâche : " + libelle + "\n";
		res += "\tAuteur : " + auteur + "\n";
		if(statut.equals("Affectée"))
			res += "\tAffectée à : " + affecteA;
		else if(statut.equals("Réalisée"))
			res += "\tTâche déjà réalisée.";
		else
			res += "\tTâche libre.";

		return res;
	}
}