package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import application.ConnexionMysql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Emprunt;
import models.lecteur;

public class EmpruntController  implements Initializable {
	

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	@FXML
    private TextField cin;

    @FXML
    private DatePicker date;

    @FXML
    private TableColumn<Emprunt , String> empcin;

    @FXML
    private TableColumn<Emprunt, Date> empdate;

    @FXML
    private TableColumn<Emprunt, Integer> empid;

    @FXML
    private TableColumn<Emprunt, String> empisbn;

    @FXML
    private TextField isbn;

    @FXML
    private TableView<Emprunt> table;
	 public ObservableList<Emprunt> donnees=FXCollections.observableArrayList();

    @FXML
    void addEmprunt( ) {
    	
    	// Check if the reader has already borrowed the book
    	try {
    	    st = cnx.prepareStatement("SELECT * FROM emprunt WHERE cin = ? and isbn = ?");
    	    st.setString(1, cin.getText());
    	    st.setString(2, isbn.getText());
    	    result = st.executeQuery();
    	    if (result.next()) {
    	        // Show error alert if the reader has already borrowed the book
    	        Alert alert = new Alert(AlertType.ERROR);
    	        alert.setTitle("Error");
    	        alert.setHeaderText("Emprunt exists");
    	        alert.setContentText("The reader has already borrowed this book.");
    	        alert.showAndWait();
    	        return;
    	    }
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	   // Check if the CIN exists in the "user" table
        try {
            st = cnx.prepareStatement("SELECT * FROM Lecteur WHERE cin = ?");
            st.setString(1, cin.getText());
            result = st.executeQuery();
            if (!result.next()) {
                // Show error alert if the CIN doesn't exist
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid CIN");
                alert.setContentText("The CIN you entered doesn't exist in the database.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check if the ISBN exists and its quantity is greater than 0 in the "livre" table
        try {
            st = cnx.prepareStatement("SELECT * FROM livre WHERE isbn = ? and quantite > 0");
            st.setString(1, isbn.getText());
            result = st.executeQuery();
            if (!result.next()) {
                // Show error alert if the ISBN doesn't exist or its quantity is 0
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid ISBN");
                alert.setContentText("The ISBN you entered doesn't exist or its quantity is 0.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     // Check if the reader's subscription is still valid
        try {
            st = cnx.prepareStatement("SELECT * FROM abonnement WHERE cin = ? and datefin >= ?");
            st.setString(1, cin.getText());
            st.setDate(2, java.sql.Date.valueOf(date.getValue()));
            result = st.executeQuery();
            if (!result.next()) {
                // Show error alert if the subscription is expired
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Subscription expired");
                alert.setContentText("The reader's subscription has expired.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the emprunt to the database
        try {
            st = cnx.prepareStatement("INSERT INTO emprunt (cin, date, isbn) VALUES (?,?,?)");
            st.setString(1, cin.getText());
            st.setDate(2, java.sql.Date.valueOf(date.getValue()));
            st.setString(3, isbn.getText());
            st.executeUpdate();
            st = cnx.prepareStatement("UPDATE livre SET quantite = quantite - 1 WHERE isbn = ?");
            st.setString(1, isbn.getText());
            st.executeUpdate();
            // Show success alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Emprunt added");
            alert.setContentText("The emprunt has been successfully added to the database.");
            alert.showAndWait();

            // Clear the input fields
            cin.clear();
            date.setValue(null);
            isbn.clear();
            showEmprunt();
    }   catch (SQLException e) {
        e.printStackTrace();
    }
    }
    

    @FXML
    void deleteemprunt( ) {
    	try {
            st = cnx.prepareStatement("DELETE FROM emprunt WHERE cin = ? and isbn = ?");
            st.setString(1, cin.getText());
            //st.setDate(2, java.sql.Date.valueOf(date.getValue()));
            st.setString(2, isbn.getText());
            st.executeUpdate();

            // Show success alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Emprunt deleted");
            alert.setContentText("The emprunt has been successfully deleted from the database.");
            alert.showAndWait();

          
            // Increment the quantity of the book
            st = cnx.prepareStatement("UPDATE livre SET quantite = quantite + 1 WHERE isbn = ?");
            st.setString(1, isbn.getText());
            st.executeUpdate();
            // Clear the input fields
            cin.clear();
            date.setValue(null);
            isbn.clear();
            
            showEmprunt();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void showEmprunt() {
    	table.getItems().clear();
        try {
            st = cnx.prepareStatement("SELECT * FROM emprunt");
            result = st.executeQuery();


            ObservableList<Emprunt> list = FXCollections.observableArrayList();

            while (result.next()) {
               
                donnees.add(new Emprunt(
                        result.getInt("id"),
                        result.getString("cin"),
                        result.getString("isbn"),
                        result.getDate("date")
                        
                    ));
            }

            empid.setCellValueFactory(new PropertyValueFactory<Emprunt,Integer>("id"));
            empcin.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("cin"));
            empdate.setCellValueFactory(new PropertyValueFactory<Emprunt,Date>("date"));
            empisbn.setCellValueFactory(new PropertyValueFactory<Emprunt,String>("isbn"));

	    	table.setItems(donnees);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void selectemprunt() {

    	
    	Emprunt emp=table.getSelectionModel().getSelectedItem();
    	String sql="select * from Emprunt where id =?";
    	try {
			st=cnx.prepareStatement(sql);
			st.setInt(1, emp.getId());
			result=st.executeQuery();
			
			if(result.next()) {
				isbn.setText(result.getString("isbn"));
				cin.setText(result.getString("cin"));
			//	empdate.setUserData(java.sql.Date.valueOf(date.getValue()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	

    }

	public void initialize(URL arg0, ResourceBundle arg1) {
	      System.out.println("jawa bahi ");

		cnx=ConnexionMysql.ConectionDb();
showEmprunt();
	}


}
