package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entity.Reparto;
import model.service.ManagerService;

import java.io.IOException;

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

	public void SwitchToGestione() throws IOException {
		Parent root2 = FXMLLoader.load(getClass().getResource("/manager/GestionePersonale.fxml"));
		contentPane3.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane3.getChildren().add(root2); // Aggiungi il nuovo contenuto
	}

	@FXML
	public void initialize() {
		repartoComboBox.getItems().addAll(FXCollections.observableArrayList(Reparto.values()));
		repartoComboBox.setOnAction(event -> {
			repartoSelezionato = repartoComboBox.getSelectionModel().getSelectedItem();
		});

		inserisciButton.setOnAction(event -> {

			String nome = nomeTextField.getText();
			String cognome = cognomeTextField.getText();
			String dataNascita = dataNascitaTextField.getText();
			String matricola = matricolaTextField.getText();

			if (nome.isEmpty() || cognome.isEmpty() || dataNascita.isEmpty() || matricola.isEmpty()
					|| repartoSelezionato == null) {
				return;
			}

			ManagerService service = new ManagerService("");
			String error = service.addDipendente(nome, cognome, matricola, repartoSelezionato);
			if (error == null) {
				errorLabel.setText("Il dipendente " + nome + " " + cognome + " è stato aggiunto correttamente!");
			} else {
				errorLabel.setText(error);
			}
		});
	}

}
