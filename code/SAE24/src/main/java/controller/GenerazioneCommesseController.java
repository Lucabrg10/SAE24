package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import model.entity.Commessa;
import model.entity.Reparto;
import model.service.CommessaService;
import model.service.ManagerService;

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
	String durata;
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

		TextFormatter<Integer> numberFilter = new TextFormatter<>(new javafx.util.converter.IntegerStringConverter(), 0,
				c -> {
					if (c.getControlNewText().matches("\\d*")) {
						return c;
					} else {
						return null;
					}
				});

		durataTF.setTextFormatter(numberFilter);

		rigeneraCommesse();
		
		comboReparto.getItems().addAll(FXCollections.observableArrayList(Reparto.values()));
		
		
		comboReparto.setOnAction(event -> {
			repartoSelezionato = comboReparto.getSelectionModel().getSelectedItem();
		});

		aggiungiButton.setOnAction(event -> {
			nome = nomeCommessaTF.getText();
			descrizione = descrizioneTA.getText();
			durata = durataTF.getText();

			if (nome.isEmpty() || descrizione.isEmpty() || repartoSelezionato == null) {
				labelErr.setText("Errore: tutti i campi devono essere compilati.");
				return;
			}
			service.addCommessa(nome, descrizione,durata, repartoSelezionato, padre);
			labelErr.setText("Commessa aggiunta");
			rigeneraCommesse();
		});
	}
	
	public void rigeneraCommesse() {
		ManagerService serviceManager = new ManagerService("");
		commesse = FXCollections.observableArrayList(serviceManager.getAllCommesse());
		comboPadre.getItems().addAll(commesse);
		comboPadre.setOnAction(event -> {
			padre = comboPadre.getSelectionModel().getSelectedItem();
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

		Parent root = FXMLLoader.load(getClass().getResource("/manager/VisualizzaCommesse.fxml"));
		contentPaneGenerazioneCommesse.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPaneGenerazioneCommesse.getChildren().add(root); // Aggiungi il nuovo contenuto

	}
}
