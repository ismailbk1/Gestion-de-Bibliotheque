package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
public class SignInController implements Initializable {
	public Connection cnx;
	int x=0;
	public PreparedStatement st;
	public ResultSet result;
	   @FXML
	    private Button btnForget;

	    @FXML
	    private Button btn_Go;

	    @FXML
	    private TextField txt_Password;

	    @FXML
	    private TextField txt_userName;
	   
	    @FXML
	    private AnchorPane AnchorPane;
	    private Parent fxml;
	    

	    @FXML
	    void Click_Go() throws IOException, InterruptedException, SQLException 
	    {
	    	
	    	String nom = txt_userName.getText();
	    	String pass=txt_Password.getText();
	    	String sql="select* from Admin";
	    	try {
				st=cnx.prepareStatement(sql);
				result=st.executeQuery();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	    	
	    	while(result.next())
	    	{
	    		
	    		
	    		if( nom.equals(result.getString("UserName")) && pass.equals(result.getString("Password")) ) 
		    	{  x=1;
		    		  Stage stage = (Stage) btn_Go.getScene().getWindow();
		    		    stage.close();
		    	
		    		Stage home=new Stage();
		    	 fxml=FXMLLoader.load(getClass().getResource("/Interfaces/Home.fxml"));   
		    		 Scene scene =new Scene(fxml);
		    		
		    		 home.setScene(scene);
		    		
		    		
		     home.show();
		    	}
		    		
	    		
	    	}
	    	
			if(x==0)
	    	{
	    		
	    		//System.out.println("Non de l'utilisateur ou mot de passe est incorrect");
    			Alert alert=new Alert(AlertType.ERROR,"Non de l'utilisateur ou mot de passe est incorrect",ButtonType.OK);
    	alert.showAndWait();
	    	}
	    	
	    	
	    	
	    	
	    }

	    @FXML
	    void btnForget() {

	    }
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
			cnx=ConnexionMysql.ConectionDb();

	
		
	}
}
