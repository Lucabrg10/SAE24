package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ManagerService;
import model.Reparto;

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
	private ComboBox<Reparto> repartoComboBox;
	@FXML
	private Label errorLabel;

	
	
	private Reparto repartoSelezionato;
	private ArrayList<Object> valori = new ArrayList<>();

	public void SwitchToGestione() throws IOException {
		System.out.println("sono entrato in modalità gestione personale");
		Parent root2 = FXMLLoader.load(getClass().getResource("/manager/GestionePersonale.fxml"));

		contentPane3.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane3.getChildren().add(root2); // Aggiungi il nuovo contenuto
	}

	@FXML
	public void initialize() {

		// Aggiungi opzioni al ComboBox
		repartoComboBox.getItems().addAll(FXCollections.observableArrayList(Reparto.values()));

		// Aggiungi azione al ComboBox
		repartoComboBox.setOnAction(event -> {
			repartoSelezionato = repartoComboBox.getSelectionModel().getSelectedItem();
			System.out.println("Reparto selezionato: " + repartoSelezionato);
		});

		// Aggiungi azione al pulsante
		inserisciButton.setOnAction(event -> {

			String nome = nomeTextField.getText();
			String cognome = cognomeTextField.getText();
			String dataNascita = dataNascitaTextField.getText();
			String matricola = matricolaTextField.getText();

			if (nome.isEmpty() || cognome.isEmpty() || dataNascita.isEmpty() || matricola.isEmpty()
					|| repartoSelezionato == null) {
				System.out.println("Errore: tutti i campi devono essere compilati.");
				return;
			}

			ManagerService service = new ManagerService();
			String error = service.addDipendente(nome, cognome, matricola, repartoSelezionato);
			if(error==null) {
				errorLabel.setText("Il dipendente "+nome+" "+cognome+" è stato aggiunto correttamente!");
			}else {
				errorLabel.setText(error);

			}
			
		});
	}

}
