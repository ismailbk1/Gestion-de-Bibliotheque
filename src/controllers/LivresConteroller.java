package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Livre;
import models.lecteur;



public class LivresConteroller implements Initializable {
	
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	
	
	
	
	
	 public ObservableList<Livre> donnees=FXCollections.observableArrayList();

	 @FXML
	    private TextField auteur;

	    @FXML
	    private TextField isbn;

	    @FXML
	    private TextField quantite;
	    @FXML
	    private TextField nom_livre;

	    @FXML
	    private TextField search_livres;

	    @FXML
	    private TableView<Livre> table;

	    @FXML
	    private TableColumn<Livre, String> liv_auteur;

	    @FXML
	    private TableColumn<Livre, Integer> liv_id;

	    @FXML
	    private TableColumn<Livre, String> liv_isbn;

	    @FXML
	    private TableColumn<Livre, String> liv_titre;
	    @FXML
	    private TableColumn<Livre, Integer> liv_qt;

	    @FXML
	    void addlivre() {
	    	 String titer=nom_livre.getText();
	         String Auteur=auteur.getText();
	         String Isbn=isbn.getText();
	         int qnt = Integer.parseInt(quantite.getText());
	         String sql="INSERT INTO livre (titre,auteur,isbn,quantite) VALUES (?,?,?,?)";
	         
	         if(titer.equals("")  ||Auteur.equals("") ||Isbn.equals("")  ) {
	        	 Alert alert=new Alert(AlertType.WARNING, "verifier que tous champs note empty !",ButtonType.OK);
	             alert.showAndWait();
	         }else {
	             try {
	                 // Check if the book with the same ISBN already exists in the database
	                 ResultSet rs = cnx.createStatement().executeQuery("SELECT * FROM livre WHERE isbn='" + Isbn + "'");
	                 if (rs.next()) {
	                    Alert alert=new Alert(AlertType.WARNING, "Le livre existe déjà dans la base de données !",ButtonType.OK);
	                    alert.showAndWait();
	                 } else {
	                     st=cnx.prepareStatement(sql);
	                     st.setString(1,titer);
	                     st.setString(2,Auteur);
	                     st.setString(3,Isbn);
	                     st.setInt(4, qnt);
	                     st.execute();
	             
	                     nom_livre.setText(""); 
	                     auteur.setText(""); 
	                     isbn.setText(""); 
	                     quantite.clear();
	                     Alert alert=new Alert(AlertType.CONFIRMATION, "livre ajouter avec succée !",ButtonType.OK);
	                     alert.showAndWait();
	                     showlivre();
	                 }
	             } catch (SQLException e) {
	                 e.printStackTrace();
	             }
	         }
	    }




	    @FXML
	    void deletelivre() {
	    	  String sql="delete from Livre where Isbn='"+search_livres.getText()+"'";
	          try {
	 			st=cnx.prepareStatement(sql);
	 			st.executeUpdate();
	 			isbn.setText(""); 
	 			nom_livre.setText(""); 
	 			auteur.setText(""); 
	 			search_livres.setText("");
	  			Alert alert=new Alert(AlertType.CONFIRMATION, "Lecteur supprime avec succeé",ButtonType.OK);
	             alert.showAndWait();
	             showlivre();
	 		} catch (SQLException e) {
	 			e.printStackTrace();
	 		}
	          
	    }

	    @FXML
	    void search() {
	    	table.getItems().clear();

	    	String sql="select id, titre ,auteur,isbn,quantite from livre where titre like '%"+search_livres.getText()+"%' OR auteur like '%"+search_livres.getText()+"%'";
	        int m=0;
	        try {
				st=cnx.prepareStatement(sql);
				result=st.executeQuery();
				if(result.next()) {
					m=1;
			   
					donnees.add(new Livre(result.getInt("id"),result.getString("titre"),result.getString("auteur"),result.getString("isbn"),result.getInt("quantite")));
					liv_id.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));
			        liv_titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
			    	liv_auteur.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
			    //	liv_titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
			    	liv_isbn.setCellValueFactory(new PropertyValueFactory<Livre, String>("isbn"));
			    	liv_qt.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("quantite"));
			    	table.setItems(donnees);
			    	

					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        if(m==0) {
	        	
	        	Alert alert=new Alert(AlertType.ERROR, "Acun Livre leur auteur ou bien leur Titrecommence par : "+search_livres.getText()+"",ButtonType.OK);
	                alert.showAndWait();
	                search_livres.setText("");

	                showlivre();	        
	        }
	        
	    	
	    }
	    public void showlivre() {
	    	table.getItems().clear();
	    	String reqt="select * from livre";
	    	try {
				st=cnx.prepareStatement(reqt);
				result=st.executeQuery();
				while(result.next()) {
					donnees.add(new Livre(result.getInt("id"),
							result.getString("titre"),
							result.getString("auteur"),
							result.getString("isbn"),
							result.getInt("quantite")));
					System.out.println(result.getString("titre"));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	    	
	    	liv_id.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));
	        liv_titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
	    	liv_auteur.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
	    //	liv_titre.setCellValueFactory(new PropertyValueFactory<Livre, String>("auteur"));
	    	liv_isbn.setCellValueFactory(new PropertyValueFactory<Livre, String>("isbn"));
	    	liv_qt.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("quantite"));
	    	table.setItems(donnees);
	    	
	    	
	    }
	    @FXML
	    void selectlivre() {
	    	Livre liv=table.getSelectionModel().getSelectedItem();
	    	String sql="select * from livre where id =?";
	    	try {
				st=cnx.prepareStatement(sql);
				st.setInt(1, liv.getId());
				result=st.executeQuery();
				
				if(result.next()) {
					nom_livre.setText(result.getString("titre"));
					isbn.setText(result.getString("isbn"));
					auteur.setText(result.getString("auteur"));
					quantite.setText(result.getString("quantite"));
					search_livres.setText(result.getString("titre"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	

	    }

	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=ConnexionMysql.ConectionDb();
		
		showlivre();
		
	}



}
