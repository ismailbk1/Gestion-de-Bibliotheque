package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.lecteur;

public class LecteurController implements Initializable {

	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	
	@FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private TableColumn<lecteur, String> lec_cin;

    @FXML
    private TableColumn<lecteur, Integer> lec_id;

    @FXML
    private TableColumn<lecteur, String> lec_nom;

    @FXML
    private TableColumn<lecteur, String> lec_prenom;

    @FXML
    private TableView<lecteur> table;

    @FXML
    private TextField txt_cin;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField txt_search;
 public ObservableList<lecteur> donnees=FXCollections.observableArrayList();
    @FXML
    void addlecteur() {
         String nom=txt_nom.getText();
         String prenom=txt_prenom.getText();
         String cin=txt_cin.getText();
         String sql="INSERT INTO Lecteur (cin,nom,prenom) VALUES (?,?,?)";
         String sqlCheck = "SELECT * FROM Lecteur WHERE cin = ?";
         
         if(nom.equals("")  ||prenom.equals("") ||cin.equals("")  ) {
        	 Alert alert=new Alert(AlertType.WARNING, "verifier que tous champs note empty !",ButtonType.OK);
             alert.showAndWait();
         }else {
        	 try {
      			st=cnx.prepareStatement(sqlCheck);
      			st.setString(1, cin);
      			ResultSet result = st.executeQuery();
      			
      			if (result.next()) {
      				Alert alert=new Alert(AlertType.WARNING, "Le lecteur existe déjà !",ButtonType.OK);
      				alert.showAndWait();
      				return;
      			}}catch (Exception e) {
					// TODO: handle 
e.printStackTrace();
				}
             try {
     			st=cnx.prepareStatement(sql);
     			st.setString(1,cin);
     			st.setString(2,nom);
     			st.setString(3,prenom);
     			st.execute();
     			// Add Abonnement
     			Date datedebut = new Date();
     			Calendar calendar = Calendar.getInstance();
     			calendar.setTime(datedebut);
     			calendar.add(Calendar.MONTH, 3);
     			Date datefin = calendar.getTime();
     			java.sql.Date sqlDateFin = new java.sql.Date(datefin.getTime());
     			//st.setDate(3, sqlDateFin);
     			java.sql.Date sqlDatedebut = new java.sql.Date(datedebut.getTime());
     			//st.setDate(3, sqlDateFin);
     			String sqlAbonnement = "INSERT INTO abonnement (cin, datedebut, datefin) VALUES (?,?,?)";
     			st = cnx.prepareStatement(sqlAbonnement);
     			st.setString(1, cin);
     			st.setDate(2, sqlDatedebut);
     			st.setDate(3, sqlDateFin);
     			st.execute();
     		
     			txt_cin.setText(""); 
     			txt_nom.setText(""); 
     			txt_prenom.setText(""); 
             	Alert alert=new Alert(AlertType.CONFIRMATION, "Lecteur ajouter avec succée !",ButtonType.OK);
                 alert.showAndWait();
     			showlecteur();
     		} catch (SQLException e) {
     			e.printStackTrace();
     		}
              
         }

    }

    @FXML
    void search() {
    	table.getItems().clear();

        String sql="select id, cin ,nom,prenom from lecteur where cin = '"+txt_search.getText()+"'";
        int m=0;
        try {
			st=cnx.prepareStatement(sql);
			result=st.executeQuery();
			if(result.next()) {
				m=1;
		   
					donnees.add(new lecteur(result.getInt("id"),result.getString("cin"),result.getString("nom"),result.getString("prenom")));
					//System.out.println(result.getInt("id"));
		   	//System.out.println(donnees);
		    	lec_id.setCellValueFactory(new PropertyValueFactory<lecteur, Integer>("id"));
		    	lec_cin.setCellValueFactory(new PropertyValueFactory<lecteur, String>("cin"));
		    	lec_nom.setCellValueFactory(new PropertyValueFactory<lecteur, String>("nom"));
		    	lec_prenom.setCellValueFactory(new PropertyValueFactory<lecteur, String>("prenom"));
		    	table.setItems(donnees);

				txt_cin.setText(result.getString("cin"));
				txt_nom.setText(result.getString("nom"));
				txt_prenom.setText(result.getString("prenom"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        if(m==0) {
        	
        	Alert alert=new Alert(AlertType.ERROR, "Acun lecteur trouve avec Cin = "+txt_search.getText()+"",ButtonType.OK);
                alert.showAndWait();
     			txt_search.setText("");

                showlecteur();
        	System.out.println("il ya error dans l Alert");
        
        }
        
    }

    @FXML
    void supprimerlecteur() {
         String sql="delete from lecteur where cin='"+txt_search.getText()+"'";
         try {
			st=cnx.prepareStatement(sql);
			st.executeUpdate();
			txt_cin.setText(""); 
 			txt_nom.setText(""); 
 			txt_prenom.setText(""); 
 			txt_search.setText("");
 			Alert alert=new Alert(AlertType.CONFIRMATION, "Lecteur supprime avec succeé",ButtonType.OK);
            alert.showAndWait();
            showlecteur();
		} catch (SQLException e) {
			e.printStackTrace();
		}
         
    }

    
    public void showlecteur() {
    	table.getItems().clear();
    	String reqt="select * from lecteur";
    	try {
			st=cnx.prepareStatement(reqt);
			result=st.executeQuery();
			while(result.next()) {
				donnees.add(new lecteur(result.getInt("id"),result.getString("cin"),result.getString("nom"),result.getString("prenom")));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	lec_id.setCellValueFactory(new PropertyValueFactory<lecteur, Integer>("id"));
    	lec_cin.setCellValueFactory(new PropertyValueFactory<lecteur, String>("cin"));
    	lec_nom.setCellValueFactory(new PropertyValueFactory<lecteur, String>("nom"));
    	lec_prenom.setCellValueFactory(new PropertyValueFactory<lecteur, String>("prenom"));
    	table.setItems(donnees);
    	
    }
    
    @FXML
    void selectlecteur() {
    	lecteur lect=table.getSelectionModel().getSelectedItem();
    	String sql="select * from lecteur where id =?";
    	try {
			st=cnx.prepareStatement(sql);
			st.setInt(1, lect.getId());
			result=st.executeQuery();
			
			if(result.next()) {
				txt_cin.setText(result.getString("cin"));
				txt_nom.setText(result.getString("nom"));
				txt_prenom.setText(result.getString("prenom"));
				txt_search.setText(result.getString("cin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	

    }
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cnx=ConnexionMysql.ConectionDb();
		
	   showlecteur();
	}

}
