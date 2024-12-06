package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;

public class InserimentoDipendentiController {

	
		@FXML
		private AnchorPane contentPane3;
		@FXML
	    private TextField nomeTextField;
	    @FXML
	    private TextField cognomeTextField;
	    @FXML
	    private TextField dataNascitaTextField;
	    @FXML
	    private TextField matricolaTextField;
	    @FXML
	    private Button inserisciButton;
	    @FXML
	    private ComboBox<String> repartoComboBox;
	    
	    private String repartoSelezionato;
	    private ArrayList<String> valori = new ArrayList<>();

	
	public void SwitchToGestione() throws IOException {
		 System.out.println("sono entrato in modalità gestione personale");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root2 = FXMLLoader.load(getClass().getResource("/GestionePersonale.fxml"));

	        contentPane3.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane3.getChildren().add(root2);  // Aggiungi il nuovo contenuto
	}
	
    @FXML
    public void initialize() {
    	
        // Aggiungi opzioni al ComboBox
        repartoComboBox.getItems().addAll("manager", "ferramenta", "ingegneria");

        // Aggiungi azione al ComboBox
        repartoComboBox.setOnAction(event -> {
            repartoSelezionato = repartoComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Reparto selezionato: " + repartoSelezionato);
        });

        // Aggiungi azione al pulsante
        inserisciButton.setOnAction(event -> {
            // Pulisci l'ArrayList per evitare duplicati
            valori.clear();

            // Leggi i valori dai TextField
            String nome = nomeTextField.getText();
            String cognome = cognomeTextField.getText();
            String dataNascita = dataNascitaTextField.getText();
            String matricola = matricolaTextField.getText();

            // Controlla che tutti i campi siano compilati
            if (nome.isEmpty() || cognome.isEmpty() || dataNascita.isEmpty() || matricola.isEmpty() || repartoSelezionato == null) {
                System.out.println("Errore: tutti i campi devono essere compilati.");
                return;
            }

            // Aggiungi i valori all'ArrayList
            valori.add(nome);
            valori.add(cognome);
            valori.add(dataNascita);
            valori.add(matricola);
            valori.add(repartoSelezionato);

            // Stampa i valori
            System.out.println("Valori inseriti:");
            for (String valore : valori) {
                System.out.println(valore);
            }
        });
    }

}
