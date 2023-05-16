package models;

public class Admin {
int id;
String UserName;String pasPassword;


public Admin(int id, String userName, String pasPassword) {
	super();
	this.id = id;
	UserName = userName;
	this.pasPassword = pasPassword;
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getUserName() {
	return UserName;
}


public void setUserName(String userName) {
	UserName = userName;
}


public String getPasPassword() {
	return pasPassword;
}


public void setPasPassword(String pasPassword) {
	this.pasPassword = pasPassword;
}


}
