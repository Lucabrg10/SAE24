package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class GestionePersonaleController {
	
	
	 @FXML
	    private AnchorPane contentPane2;   // Pannello centrale per i contenuti

	public void SwitchToInserimento() throws IOException {
		 System.out.println("sono entrato in modalità inserimento personale");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root1 = FXMLLoader.load(getClass().getResource("/InserimentoPersonale.fxml"));

	        contentPane2.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane2.getChildren().add(root1);  // Aggiungi il nuovo contenuto
	}
	
	public void SwitchToGestione() throws IOException {
		 System.out.println("sono entrato in modalità gestione personale");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root2 = FXMLLoader.load(getClass().getResource("/GestionePersonale.fxml"));

	        contentPane2.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane2.getChildren().add(root2);  // Aggiungi il nuovo contenuto
	}
}
