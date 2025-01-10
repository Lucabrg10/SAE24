package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.layout.VBox;
import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Task;
import model.entity.TaskDipendente;
import model.service.TaskDipendenteService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	private  ObservableList<TaskDipendente> tasksDipendenti ;
	
	public void initialize() {
	TaskDipendenteService service = new TaskDipendenteService("");
	tasksDipendenti=FXCollections.observableArrayList(service.retrieveListOfTasksDipendente());
		
	System.out.println(tasksDipendenti);
	
	instanza.setCellValueFactory(cellData -> {
	    TaskDipendente taskDipendente = cellData.getValue();
	    if (taskDipendente.getTask() != null && taskDipendente.getTask().getCommessaInstance() != null) {
	        return new SimpleStringProperty(String.valueOf(taskDipendente.getTask().getCommessaInstance().getInstance()));
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
	
	tableViewTasksDipendente.setItems(tasksDipendenti);
	}

}
