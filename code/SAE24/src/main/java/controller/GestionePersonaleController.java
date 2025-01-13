package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.service.ManagerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;

public class GestionePersonaleController {
	@FXML
	private AnchorPane contentPane2;
	@FXML
	private VBox mainContainer;
	@FXML
	private TableView<Dipendente> tableView; // Tabella che mostra i dipendenti

	@FXML
	private TableColumn<Dipendente, String> matricolaCol; // Colonna per la matricola

	@FXML
	private TableColumn<Dipendente, String> nomeCol; // Colonna per il nome

	@FXML
	private TableColumn<Dipendente, String> cognomeCol; // Colonna per il cognome

	@FXML
	private TableColumn<Dipendente, String> dataCol; // Colonna per il ruolo
	@FXML
	private TableColumn<Dipendente, String> idCol; // Colonna per il ruolo
	@FXML
	private TableColumn<Dipendente, Reparto> repartoCol; // Colonna per il ruolo
	@FXML
	private TableColumn<Dipendente, Void> deleteColumn;

	private  ObservableList<Dipendente> dipendenti ;


	public void initialize() {

		ManagerService service = new ManagerService("");
		dipendenti = FXCollections.observableArrayList(service.getAllDipendenti());
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		matricolaCol.setCellValueFactory(new PropertyValueFactory<>("matricola"));
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		repartoCol.setCellValueFactory(new PropertyValueFactory<>("reparto"));
		deleteColumn.setCellFactory(param -> new TableCell<Dipendente, Void>() {
		        private final Button deleteButton = new Button("Elimina");

		        {
		            deleteButton.setOnAction(event -> {
		                Dipendente dipendente = getTableView().getItems().get(getIndex());
		                dipendenti.remove(dipendente); // Rimuovi dalla lista visibile
		                ManagerService service = new ManagerService("");
		                service.deleteDipendente(dipendente.getId()); // Elimina dal database
		            });
		        }

		        @Override
		        public void updateItem(Void item, boolean empty) {
		            super.updateItem(item, empty);
		            if (empty) {
		                setGraphic(null);
		            } else {
		                setGraphic(deleteButton);
		            }
		        }
		    });
		
		
		tableView.setItems(dipendenti);
		tableView.setEditable(true);
	}


	@FXML
	public void SwitchToInserimento(ActionEvent event) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("/manager/InserimentoDipendenti.fxml"));
		contentPane2.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane2.getChildren().add(root1); // Aggiungi il nuovo contenuto
	}

}
