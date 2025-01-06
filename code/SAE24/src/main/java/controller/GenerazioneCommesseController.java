package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Commessa;
import model.CommessaService;
import model.ManagerService;
import model.Reparto;

public class GenerazioneCommesseController {

	@FXML
	private AnchorPane contentPaneGenerazioneCommesse;
	@FXML
	private Label labelErr;

	@FXML
	private TextField nomeCommessaTF;
	String nome;
	@FXML
	private TextField durataTF;
	@FXML
	private TextArea descrizioneTA;
	String descrizione;

	@FXML
	private ComboBox<Reparto> comboReparto;
	@FXML
	private ComboBox<Commessa> comboPadre;
	@FXML
	private Button aggiungiButton;
	private CommessaService service = new CommessaService("");
	private Reparto repartoSelezionato;
	private Commessa padre;
	private ObservableList<Commessa> commesse;

	@FXML
	public void initialize() {
		
		 TextFormatter<Integer> numberFilter = new TextFormatter<>(new javafx.util.converter.IntegerStringConverter(), 0, c -> {
	            if (c.getControlNewText().matches("\\d*")) {  
	                return c;
	            } else {
	                return null; 
	            }
	        });

	        // Imposta il TextFormatter nel TextField
	        durataTF.setTextFormatter(numberFilter);
		
		ManagerService serviceManager = new ManagerService("");
		commesse = FXCollections.observableArrayList(serviceManager.getAllCommesse());
		comboReparto.getItems().addAll(FXCollections.observableArrayList(Reparto.values()));
		comboPadre.getItems().addAll(commesse);

		comboPadre.setOnAction(event -> {
			padre = comboPadre.getSelectionModel().getSelectedItem();
		});
		comboReparto.setOnAction(event -> {
			repartoSelezionato = comboReparto.getSelectionModel().getSelectedItem();
		});

		aggiungiButton.setOnAction(event -> {
			nome = nomeCommessaTF.getText();
			descrizione = descrizioneTA.getText();

			if (nome.isEmpty() || descrizione.isEmpty() || repartoSelezionato == null) {
				labelErr.setText("Errore: tutti i campi devono essere compilati.");
				return;
			}
			service.addCommessa(nome, descrizione, "0", repartoSelezionato, padre);
			commesse = FXCollections.observableArrayList(serviceManager.getAllCommesse());
			labelErr.setText("Commessa aggiunta");
		});
	}

	public String getNome() {
		return this.nome;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	@FXML
	public void switchToGenerazioneCommesse(ActionEvent event) throws IOException {
		System.out.println("sono entrato in modalità visualizza commesse");

		// Carica il file FXML di inserimentoTask.fxml
		Parent root = FXMLLoader.load(getClass().getResource("/manager/VisualizzaCommesse.fxml"));

		contentPaneGenerazioneCommesse.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPaneGenerazioneCommesse.getChildren().add(root); // Aggiungi il nuovo contenuto

	}
}
