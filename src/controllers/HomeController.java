package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController implements Initializable {
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	private Parent fxml;
	
	@FXML
    private AnchorPane parent;
	  @FXML
	    void Acueil() {

		    
	       
	    	try {
			fxml=FXMLLoader.load(getClass().getResource("/interfaces/Acueil.fxml"));
			parent.getChildren().removeAll();
			parent.getChildren().setAll(fxml);
			
		    
		    
			} catch (IOException e) {
				e.printStackTrace();
			}
    	
    	
    }

    @FXML
    void empruntes() {

	    
        
    	try {
    		fxml=FXMLLoader.load(getClass().getResource("/interfaces/Emprunt.fxml"));
			parent.getChildren().removeAll();
			parent.getChildren().setAll(fxml);
		} catch (IOException e) {
			System.out.println("here the error exist");
			e.printStackTrace();
		}
    }

    @FXML
    void lecteur(MouseEvent event) {

	    
        
    	try {
		fxml=FXMLLoader.load(getClass().getResource("/interfaces/Lecteurs.fxml"));
		parent.getChildren().removeAll();
		parent.getChildren().setAll(fxml);
	    
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void livres(MouseEvent event) {

	    
        
    	try {
		fxml=FXMLLoader.load(getClass().getResource("/interfaces/livres.fxml"));
		parent.getChildren().removeAll();
		parent.getChildren().setAll(fxml);
	    
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConectionDb();
    	try {
		fxml=FXMLLoader.load(getClass().getResource("/interfaces/Acueil.fxml"));
		parent.getChildren().removeAll();
		parent.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
