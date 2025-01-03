package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import model.Commessa;
import model.Dipendente;
import model.ManagerService;
import model.Reparto;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
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
	private TableColumn<Commessa, Long> padreIdCol;

	private  ObservableList<Commessa> commesse ;

	
	public void initialize() {

		ManagerService service = new ManagerService();
		commesse = FXCollections.observableArrayList(service.getAllCommesse());
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		descrizioneCol.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
		repartoCol.setCellValueFactory(new PropertyValueFactory<>("reparto"));
	//	padreIdCol.setCellValueFactory(new PropertyValueFactory<>("commessaPadre"));
		
		tableViewCommesse.setItems(commesse);
	}
	@FXML
	public void SwitchToInserimento(ActionEvent event) throws IOException {

		System.out.println("sono entrato in modalità generazione commessa");

		// Carica il file FXML di inserimentoTask.fxml
		Parent root1 = FXMLLoader.load(getClass().getResource("/manager/GenerazioneCommesse.fxml"));

		contentPane2.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane2.getChildren().add(root1); // Aggiungi il nuovo contenuto
	}

}
