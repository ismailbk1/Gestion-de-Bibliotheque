package models;

import java.util.Date;

public class Emprunt {
	 int id;
	   String cin;
	   String isbn;
	   Date date;
	public Emprunt(int id, String cin, String isbn, Date date) {
		super();
		this.id = id;
		this.cin = cin;
		this.isbn = isbn;
		this.date = date;
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
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	   
	   
}
