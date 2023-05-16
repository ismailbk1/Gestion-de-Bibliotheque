	package application;

import java.sql.*;

public class ConnexionMysql {
 
	
	public Connection cn=null;

	 public static  Connection ConectionDb() {
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn;
			try {
				cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblio","root","");
				System.out.println("conex success");
				 return cn;
			} catch (SQLException e) {
				System.out.println("connexion echoueé ");
				e.printStackTrace();
				return null;
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("connexion echoueé ");
			e.printStackTrace();
			return null;
		}
		 
	 }
}
