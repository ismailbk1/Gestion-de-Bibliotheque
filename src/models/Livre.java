package models;

public class Livre {
   int id;
   String titre;
   String auteur;
   String isbn;
   int quantite;
   
   public Livre() {
	   super();
   }

public Livre(int id, String nom, String auteur, String isbn,int qnt) {
	//super();
	this.id = id;
	this.titre = nom;
	this.auteur = auteur;
	this.isbn = isbn;
	this.quantite=qnt;
}

public int getQuantite() {
	return quantite;
}

public void setQuantite(int quantite) {
	this.quantite = quantite;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getNom() {
	return titre;
}

public void setNom(String nom) {
	this.titre = nom;
}

public String getAuteur() {
	return auteur;
}

public void setAuteur(String auteur) {
	this.auteur = auteur;
}

public String getIsbn() {
	return isbn;
}

public void setIsbn(String isbn) {
	this.isbn = isbn;
}

public String getTitre() {
	return titre;
}

public void setTitre(String titre) {
	this.titre = titre;
}
   
   
   
   
}
