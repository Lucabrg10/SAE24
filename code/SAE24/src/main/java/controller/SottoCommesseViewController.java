package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import model.entity.Commessa;
import model.entity.Reparto;
import model.service.ManagerService;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class SottoCommesseViewController {

	@FXML
	private Label commessaPrincipaleLabel;

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

	private ObservableList<Commessa> sottoCommesse;

	public void show(Commessa padre) {
		ManagerService service = new ManagerService("");
		commessaPrincipaleLabel.setText(padre.getNome());
		
		ObservableList<Commessa> sottoCommesse= FXCollections.observableArrayList(service.getListOfSubCommesse(padre));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		descrizioneCol.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
		repartoCol.setCellValueFactory(new PropertyValueFactory<>("reparto"));
		padreIdCol.setCellValueFactory(new PropertyValueFactory<>("commessaPadre"));
		durataCol.setCellValueFactory(new PropertyValueFactory<>("tempoStimato"));
	/*	eliminaCol.setCellFactory(param -> new TableCell<Commessa, Void>() {
			private final Button eliminaColButton = new Button("Elimina");

			{
				eliminaColButton.setOnAction(event -> {
					Commessa commessa = getTableView().getItems().get(getIndex());
					// Rimuovi dalla lista visibile
					CommessaService service = new CommessaService("");
					if (service.deleteCommessa(commessa.getId())) {
						sottoCommesse.remove(commessa);
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
		tableViewCommesse.setItems(sottoCommesse);
	}

}
