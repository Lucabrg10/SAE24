package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entity.Commessa;
import model.entity.Reparto;
import model.service.CommessaService;
import model.service.ManagerService;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class AndamentoCommesseController {
	@FXML
	private TableView<Commessa> tableViewAndamento;
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
	private TableColumn<Commessa, String> padreCol;
	
	private ObservableList<Commessa> commesse;


	public void initialize() {

		ManagerService service = new ManagerService("");
		commesse = FXCollections.observableArrayList(service.getAllCommesse());

		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		descrizioneCol.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
		repartoCol.setCellValueFactory(new PropertyValueFactory<>("reparto"));
		padreCol.setCellValueFactory(new PropertyValueFactory<>("commessaPadre"));
		durataCol.setCellValueFactory(new PropertyValueFactory<>("tempoStimato"));
		
		tableViewAndamento.setItems(commesse);
	}

	
	@FXML
	public void showAndamentoCommessa(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Commessa selectedItem = tableViewAndamento.getSelectionModel().getSelectedItem();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/AndamentoCommessa.fxml"));
				Scene scene = new Scene(loader.load());

				AndamentoCommessaController controller = loader.getController();
				controller.show(selectedItem);
				Stage stage = new Stage();
				stage.setTitle("Andamento commessa");
				stage.initModality(Modality.APPLICATION_MODAL); 
				stage.setScene(scene);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
