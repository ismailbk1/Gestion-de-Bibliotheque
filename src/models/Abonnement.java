package models;

import java.util.Date;

public class Abonnement {

	int id;
	String cin;
	Date datedebut;
	Date datefin;
	public Abonnement(int id,String cin, Date datedebut, Date datefin) {
		super();
		this.id = id;
		this.cin=cin;
		this.datedebut = datedebut;
		this.datefin = datefin;
	}
	
	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDatedebut() {
		return datedebut;
	}
	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}
	public Date getDatefin() {
		return datefin;
	}
	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}
	
	
}
