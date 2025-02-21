package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.service.ManagerService;
import java.io.IOException;

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

	private ObservableList<Dipendente> dipendenti;

	public void initialize() {

		ManagerService service = new ManagerService("");
		dipendenti = FXCollections.observableArrayList(service.getAllDipendenti());

		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		matricolaCol.setCellValueFactory(new PropertyValueFactory<>("matricola"));
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeCol.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		repartoCol.setCellValueFactory(new PropertyValueFactory<>("reparto"));
		/*
		 * deleteColumn.setCellFactory(param -> new TableCell<Dipendente, Void>() {
		 * private final Button deleteButton = new Button("Elimina"); {
		 * deleteButton.setOnAction(event -> { Dipendente dipendente =
		 * getTableView().getItems().get(getIndex()); dipendenti.remove(dipendente);
		 * ManagerService service = new ManagerService("");
		 * service.deleteDipendente(dipendente.getId()); }); }
		 * 
		 * @Override public void updateItem(Void item, boolean empty) {
		 * super.updateItem(item, empty); if (empty) { setGraphic(null); } else {
		 * setGraphic(deleteButton); } } });
		 */

		tableView.setItems(dipendenti);
		tableView.setEditable(true);
	}

	@FXML
	public void SwitchToInserimento(ActionEvent event) throws IOException {
		Parent root1 = FXMLLoader.load(getClass().getResource("/manager/InserimentoDipendenti.fxml"));
		contentPane2.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane2.getChildren().add(root1); // Aggiungi il nuovo contenuto
	}

	@FXML
	public void showPerformance(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2) {
			Dipendente selectedItem = tableView.getSelectionModel().getSelectedItem();

			if (selectedItem != null && selectedItem.getReparto()!=Reparto.MANAGER) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/PerformanceDipendenti.fxml"));
					Scene scene = new Scene(loader.load());
					PerformanceDipendentiController controller = loader.getController();
					controller.show(selectedItem);
					Stage stage = new Stage();
					stage.setTitle("Performance dipendenti");
					stage.initModality(Modality.APPLICATION_MODAL); // Imposta come pop-up modale
					stage.setScene(scene);
					stage.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
