package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AcueilController implements Initializable {

	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

    @FXML
    private TextField nbemprunt;

    @FXML
    private TextField nblecteurs;

    @FXML
    private TextField nblivres;

	
	public void showdetails() {
		System.out.println("jawwwwwww bahi");

		 try {
			 st = cnx.prepareStatement("SELECT COUNT(*) FROM livre");
		      ResultSet rs = st.executeQuery();
		      if (rs.next()) {
		    	 
		         nblivres.setText(rs.getString(1));
		      }

		      rs = cnx.createStatement().executeQuery("SELECT COUNT(*) FROM lecteur");
		      if (rs.next()) {
		         nblecteurs.setText(rs.getString(1));
		      }

		      rs = cnx.createStatement().executeQuery("SELECT COUNT(*) FROM emprunt");
		      if (rs.next()) {
		         nbemprunt.setText(rs.getString(1));
		      }
		   } catch (SQLException e) {
		      e.printStackTrace();
		   }
	}
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	//	System.out.println("conex success");
		System.out.println("jawwwwwww bahi");

		cnx=ConnexionMysql.ConectionDb();
		

		showdetails();
		
	     

	}

	

}
