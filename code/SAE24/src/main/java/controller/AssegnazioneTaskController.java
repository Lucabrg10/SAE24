package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.layout.VBox;
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
	
	static final String STATO_IN_LAVORAZIONE = "IN_LAVORAZIONE";
	static final String STATO_COMPLETATA = "COMPLETATA";
	static final String STATO_ASSEGNATA = "ASSEGNATA";
	
	public void initialize() {
	    initializeServiceAndTasks();
	    initializeCellValueFactories();
	    initializeRowStyling();
	    tableViewTasksDipendente.setItems(tasksDipendenti);
	}

	private void initializeServiceAndTasks() {
	    TaskDipendenteService service = new TaskDipendenteService("");
	    tasksDipendenti = FXCollections.observableArrayList(service.getListOfTasksDipendente());
	}

	private void initializeCellValueFactories() {
	    instanza.setCellValueFactory(cellData -> createPropertyForInstanza(cellData.getValue()));
	    commessaPrincipale.setCellValueFactory(cellData -> createPropertyForCommessa(cellData.getValue()));
	    nomeTask.setCellValueFactory(new PropertyValueFactory<>("task"));
	    dipendente.setCellValueFactory(new PropertyValueFactory<>("dipendente"));
	    statoTask.setCellValueFactory(new PropertyValueFactory<>("status"));
	}

	private SimpleStringProperty createPropertyForInstanza(TaskDipendente taskDipendente) {
	    if (taskDipendente.getTask() != null && taskDipendente.getTask().getCommessaInstance() != null) {
	        return new SimpleStringProperty(
	                String.valueOf(taskDipendente.getTask().getCommessaInstance().getInstance()));
	    } else {
	        return new SimpleStringProperty("N/A");
	    }
	}

	private SimpleStringProperty createPropertyForCommessa(TaskDipendente taskDipendente) {
	    if (taskDipendente.getTask() != null && taskDipendente.getTask().getCommessaInstance() != null) {
	        return new SimpleStringProperty(taskDipendente.getTask().getCommessaInstance().getCommessa().getNome());
	    } else {
	        return new SimpleStringProperty("N/A");
	    }
	}

	private void initializeRowStyling() {
	    tableViewTasksDipendente.setRowFactory(tv -> {
	        TableRow<TaskDipendente> row = new TableRow<>();
	        row.itemProperty().addListener((observable, oldItem, newItem) -> styleRowBasedOnStatus(row, newItem));
	        return row;
	    });
	}

	private void styleRowBasedOnStatus(TableRow<TaskDipendente> row, TaskDipendente newItem) {
	    if (newItem != null) {
	        switch (newItem.getStatus()) {
	            case STATO_COMPLETATA:
	                row.setStyle("-fx-background-color: lightgreen;");
	                break;
	            case STATO_IN_LAVORAZIONE:
	                row.setStyle("-fx-background-color: gold;");
	                break;
	            default:
	                row.setStyle("-fx-background-color: lightcoral;");
	                break;
	        }
	    }
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
					if (newItem.getStatus().equals(STATO_COMPLETATA)) {
						row.setStyle("-fx-background-color: lightgreen;");
					} else if (newItem.getStatus().equals(STATO_IN_LAVORAZIONE)) {
						row.setStyle("-fx-background-color: gold;");
					} else if (newItem.getStatus().equals(STATO_ASSEGNATA)){
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
		show(STATO_COMPLETATA);
	}

	@FXML
	public void showAssegnate(ActionEvent event) throws IOException {
		show(STATO_ASSEGNATA);
	}

	@FXML
	public void showInLavorazione(ActionEvent event) throws IOException {
		show(STATO_IN_LAVORAZIONE);
	}

}
