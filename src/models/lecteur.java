package models;

public class lecteur {
 int id;
 String cin;
 String nom;
 String prenom;
 public lecteur() {
		super();
	}
public lecteur(int id, String cin, String nom, String prenom) {
	super();
	this.id = id;
	this.cin = cin;
	this.nom = nom;
	this.prenom = prenom;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCin() {
	return cin;
}
public void setCin(String cin) {
	this.cin = cin;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public String getPrenom() {
	return prenom;
}
public void setPrenom(String prenom) {
	this.prenom = prenom;
}

 
 
}
