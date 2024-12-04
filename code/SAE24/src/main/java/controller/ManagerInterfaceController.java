package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ManagerInterfaceController {

	
	
	 @FXML
	    private AnchorPane contentPane;   // Pannello centrale per i contenuti

	 
	    // Metodo chiamato al clic del pulsante Inserimento Task
	    public void inserimentoTask(ActionEvent e) throws IOException {
	        System.out.println("inserimento task");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root1 = FXMLLoader.load(getClass().getResource("/InserimentoTask.fxml"));

	        contentPane.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane.getChildren().add(root1);  // Aggiungi il nuovo contenuto
	    }
	    
	    // Metodo chiamato al clic del pulsante "Visualizza Task"
	    public void visualizzaTask(ActionEvent e) throws IOException {
	        System.out.println("visualizzaTask");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root2 = FXMLLoader.load(getClass().getResource("/VisualizzaTask.fxml"));

	        contentPane.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane.getChildren().add(root2);  // Aggiungi il nuovo contenuto
	    }
	    
	    // Metodo chiamato al clic del pulsante "Gestione personale"
	    public void gestionePersonale(ActionEvent e) throws IOException {
	        System.out.println("gestionePersonale");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root3 = FXMLLoader.load(getClass().getResource("/GestionePersonale.fxml"));

	        contentPane.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane.getChildren().add(root3);  // Aggiungi il nuovo contenuto
	    }

	}
    

