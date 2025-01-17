package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.service.CommessaInstanceService;
import model.service.CommessaService;
import model.service.ManagerService;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class VisualizzaCommesseController {
	@FXML
	private AnchorPane contentPane2;
	@FXML
	private VBox mainContainer;
	@FXML
	private TableView<Commessa> tableViewCommesse;
	@FXML
	private TableColumn<Commessa, String> idCol;
	@FXML
	private TableColumn<Commessa, String> nomeCol;
	@FXML
	private TableColumn<Commessa, String> descrizioneCol;
	@FXML
	private TableColumn<Commessa, Reparto> repartoCol;
	@FXML
	private TableColumn<Commessa, String> durataCol;
	@FXML
	private TableColumn<Commessa, Void> eliminaCol;
	@FXML
	private TableColumn<Commessa, String> padreIdCol;
	private ObservableList<Commessa> commesse;

	public void initialize() {

		ManagerService service = new ManagerService("");
		commesse = FXCollections.observableArrayList(service.getAllCommessePrincipali());

		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		descrizioneCol.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
		repartoCol.setCellValueFactory(new PropertyValueFactory<>("reparto"));
		durataCol.setCellValueFactory(new PropertyValueFactory<>("tempoStimato"));
	/*	eliminaCol.setCellFactory(param -> new TableCell<Commessa, Void>() {
			private final Button eliminaColButton = new Button("Elimina");

			{
				eliminaColButton.setOnAction(event -> {
					Commessa commessa = getTableView().getItems().get(getIndex());
					// Rimuovi dalla lista visibile
					CommessaService service = new CommessaService("");
					if (service.deleteCommessa(commessa.getId())) {
						commesse.remove(commessa);
					}

				});
			}

			@Override
			public void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(eliminaColButton);
				}
			}
		});*/

		tableViewCommesse.setItems(commesse);
	}

	@FXML
	public void assegnaCommessa(ActionEvent event) {
		Commessa selectedItem = tableViewCommesse.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			// devi implemntare Instance come static per contare quante instanze
			CommessaService service = new CommessaService("");
			CommessaInstanceService serviceInstance = new CommessaInstanceService("");
			CommessaInstance cm = serviceInstance.creaNewCommessaInstance(selectedItem);

			serviceInstance.salvaCommessaInstance(cm);
			service.assegnaTasksSistema(selectedItem, cm);

		}
	}

	private void showSottoCommesse(Commessa selectedItem) {
		if (selectedItem != null) {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/SottoCommesseView.fxml"));
				Scene scene = new Scene(loader.load());

				SottoCommesseViewController controller = loader.getController();
				controller.show(selectedItem);
				Stage stage = new Stage();
				stage.setTitle("Sotto-Commesse");
				stage.initModality(Modality.APPLICATION_MODAL); // Imposta come pop-up modale
				stage.setScene(scene);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void vediSottoCommesse(ActionEvent event) {
		Commessa selectedItem = tableViewCommesse.getSelectionModel().getSelectedItem();
		showSottoCommesse(selectedItem);
	}

	@FXML
	public void handleRowDoubleClick(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2) {
			Commessa selectedItem = tableViewCommesse.getSelectionModel().getSelectedItem();
			showSottoCommesse(selectedItem);
		}
	}

	@FXML
	public void modificaCommessa(ActionEvent event) {
		Commessa selectedItem = tableViewCommesse.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			System.out.println("Modifica: " + selectedItem);
		}
	}

	@FXML
	public void switchToInserimento(ActionEvent event) throws IOException {

		System.out.println("sono entrato in modalità generazione commessa");

		// Carica il file FXML di inserimentoTask.fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/GenerazioneCommesse.fxml"));
		Parent root1 = loader.load();
		contentPane2.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane2.getChildren().add(root1); // Aggiungi il nuovo contenuto
	}

}
