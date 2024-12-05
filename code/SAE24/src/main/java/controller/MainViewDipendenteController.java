package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

public class MainViewDipendenteController {
	@FXML
	private AnchorPane contentPane;
	@FXML
	private Label titleLabel;

	
	public void initialize() throws IOException {
		visualizzaTask(null);
	
	}
	// Event Listener on Button.onAction
	@FXML
	public void visualizzaTask(ActionEvent event) throws IOException 
	{
	        System.out.println("visualizzaTask");
	        Parent root2 = FXMLLoader.load(getClass().getResource("/TasksDipendente.fxml"));
	        contentPane.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane.getChildren().add(root2);  // Aggiungi il nuovo contenuto
           

            String css = this.getClass().getResource("/application.css").toExternalForm();
            root2.getStylesheets().add(css);//
	        
	}
	public void visualizzaProfilo(ActionEvent event) throws IOException 
	{
	        System.out.println("visualizzaTask");
	        Parent root1 = FXMLLoader.load(getClass().getResource("/profilo.fxml"));
	        contentPane.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane.getChildren().add(root1);  // Aggiungi il nuovo contenuto
           

            String css = this.getClass().getResource("/application.css").toExternalForm();
            root1.getStylesheets().add(css);//
	        
	}
}
