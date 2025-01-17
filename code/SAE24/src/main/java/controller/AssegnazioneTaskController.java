package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.layout.VBox;
import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Task;
import model.entity.TaskDipendente;
import model.service.TaskDipendenteService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;

public class AssegnazioneTaskController {
	@FXML
	private VBox vBox;
	@FXML
	private TableView<TaskDipendente> tableViewTasksDipendente;
	@FXML
	private TableColumn<TaskDipendente, String> instanza;
	@FXML
	private TableColumn<TaskDipendente, String> commessaPrincipale;
	@FXML
	private TableColumn<TaskDipendente, String> nomeTask;
	@FXML
	private TableColumn<TaskDipendente, String> dipendente;
	@FXML
	private TableColumn<TaskDipendente, String> statoTask;

	private ObservableList<TaskDipendente> tasksDipendenti;

	public void initialize() {
		TaskDipendenteService service = new TaskDipendenteService("");
		tasksDipendenti = FXCollections.observableArrayList(service.getListOfTasksDipendente());

		instanza.setCellValueFactory(cellData -> {
			TaskDipendente taskDipendente = cellData.getValue();
			if (taskDipendente.getTask() != null && taskDipendente.getTask().getCommessaInstance() != null) {
				return new SimpleStringProperty(
						String.valueOf(taskDipendente.getTask().getCommessaInstance().getInstance()));
			} else {
				return new SimpleStringProperty("N/A");
			}
		});

		commessaPrincipale.setCellValueFactory(cellData -> {
			TaskDipendente taskDipendente = cellData.getValue();
			if (taskDipendente.getTask() != null && taskDipendente.getTask().getCommessaInstance() != null) {
				return new SimpleStringProperty(taskDipendente.getTask().getCommessaInstance().getCommessa().getNome());
			} else {
				return new SimpleStringProperty("N/A");
			}
		});

		nomeTask.setCellValueFactory(new PropertyValueFactory<>("task"));
		dipendente.setCellValueFactory(new PropertyValueFactory<>("dipendente"));
		statoTask.setCellValueFactory(new PropertyValueFactory<>("status"));

		tableViewTasksDipendente.setRowFactory(tv -> {
			TableRow<TaskDipendente> row = new TableRow<>();
			row.itemProperty().addListener((ObservableValue<? extends TaskDipendente> observable,
					TaskDipendente oldItem, TaskDipendente newItem) -> {
				if (newItem != null) {
					// Se la task è completata, coloriamo la riga di verde, altrimenti di rosso
					if (newItem.getStatus().equals("COMPLETATA")) {
						row.setStyle("-fx-background-color: lightgreen;");
					} else if (newItem.getStatus().equals("IN_LAVORAZIONE")) {
						row.setStyle("-fx-background-color: gold;");
					} else {
						row.setStyle("-fx-background-color: lightcoral;");
					}
				}
			});
			return row;
		});
		tableViewTasksDipendente.setItems(tasksDipendenti);
	
	}

	public void show(String filter) {
		TaskDipendenteService service = new TaskDipendenteService("");
		tasksDipendenti = FXCollections.observableArrayList(service.getListOfTasksDipendente());

		tableViewTasksDipendente.setRowFactory(tv -> {
			TableRow<TaskDipendente> row = new TableRow<>();
			row.itemProperty().addListener((ObservableValue<? extends TaskDipendente> observable,
					TaskDipendente oldItem, TaskDipendente newItem) -> {
				if (newItem != null) {
					// Se la task è completata, coloriamo la riga di verde, altrimenti di rosso
					if (newItem.getStatus().equals("COMPLETATA")) {
						row.setStyle("-fx-background-color: lightgreen;");
					} else if (newItem.getStatus().equals("IN_LAVORAZIONE")) {
						row.setStyle("-fx-background-color: gold;");
					} else if (newItem.getStatus().equals("ASSEGNATA")){
						row.setStyle("-fx-background-color: lightcoral;");
					}else {
						row.setStyle("-fx-background-color: white;");
					}
				}else {
					row.setStyle("-fx-background-color: white;");
				}
			});
			return row;
		});
		if (filter != null) {
			tableViewTasksDipendente.setItems(FXCollections.observableArrayList(tasksDipendenti.stream()
					.filter(task -> filter.equals(task.getStatus())).collect(Collectors.toList())));
		}else {
			tableViewTasksDipendente.setItems(tasksDipendenti);
		}
	}
	
	@FXML
	public void refresh(ActionEvent event) throws IOException {
		show(null);
	}

	@FXML
	public void showCompletate(ActionEvent event) throws IOException {
		show("COMPLETATA");
	}

	@FXML
	public void showAssegnate(ActionEvent event) throws IOException {
		show("ASSEGNATA");
	}

	@FXML
	public void showInLavorazione(ActionEvent event) throws IOException {
		show("IN_LAVORAZIONE");
	}

}
