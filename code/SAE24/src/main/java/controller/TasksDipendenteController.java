package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Dipendente;
import model.entity.TaskDipendente;
import model.service.TaskDipendenteService;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class TasksDipendenteController {
	@FXML
	private VBox mainContainer;

	@FXML
	private GridPane gridContainer;
	private Dipendente dipendente;

	TaskDipendenteService service = new TaskDipendenteService("");
	ObservableList<TaskDipendente> taskDipendente;

	public void show() {
		long idInLav = -1;
		mainContainer.getChildren().clear();

		taskDipendente = FXCollections.observableArrayList(service.findTasksDipendente(dipendente));
		if (taskDipendente != null && !taskDipendente.isEmpty()) {
			for (TaskDipendente taskD : taskDipendente) {
				GridPane newGrid = new GridPane();

				newGrid.getStyleClass().add("taskgrid");
				newGrid.setHgap(1000);
				newGrid.setVgap(10);
				newGrid.setPadding(new Insets(10));
				if (taskD.getStatus().equals("IN_LAVORAZIONE")) {
					idInLav = taskD.getId();

				}

				Label task = new Label("task " + (taskD.getTask().getCommessa().getNome()));
				task.getStyleClass().add("taskLabel");
				task.setId("taskLabel" + taskD.getId());
				newGrid.add(task, 0, 0);

				// Bottone Start
				Button start = new Button("Start");
				start.setId("start" + taskD.getId());
				start.setOnAction(e -> startTask(taskD));
				start.getStyleClass().add("start-button");
				newGrid.add(start, 0, 1);

				// Bottone Stop
				Button stop = new Button("Stop");
				stop.setId("stop" + taskD.getId());

				stop.setOnAction(e -> stopTask(taskD));
				stop.setDisable(true);
				newGrid.add(stop, 1, 1);
				stop.getStyleClass().add("stop-button");

				// Bottone Sospendi
				Button sospendi = new Button("Sospendi");
				sospendi.setId("sospendi" + taskD.getId());
				sospendi.setOnAction(e -> {
					try {
						sospendiTask(taskD);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				sospendi.setDisable(true);
				newGrid.add(sospendi, 2, 1);
				sospendi.getStyleClass().add("sospendi-button");

				mainContainer.getChildren().add(newGrid);
				newGrid.getStyleClass().add("taskgrid");
				newGrid.setId("" + taskD.getId());
			}

		}

		if (idInLav != -1) {
			disabilitaStart(idInLav);
		}
	}

	private void disabilitaStart(Long id) {
		for (TaskDipendente taskD : taskDipendente) {
			if (taskD.getId().equals(id)) {
				Button button = (Button) mainContainer.lookup("#stop" + taskD.getId());
				button.setDisable(false);

			}
			Button button = (Button) mainContainer.lookup("#start" + taskD.getId());
			button.setDisable(true);
		}

	}

	public void startTask(TaskDipendente task) {
		long taskIndex = task.getId();
		Button button = (Button) mainContainer.lookup("#start" + taskIndex);
		if (button != null) {
			button.setDisable(true);
		}

		Button buttonstop = (Button) mainContainer.lookup("#stop" + taskIndex);
		// Button buttonsospendi = (Button) mainContainer.lookup("#sospendi" +
		// taskIndex);
		if (buttonstop != null) {
			for (TaskDipendente taskD : taskDipendente) {
				Button button1 = (Button) mainContainer.lookup("#start" + taskD.getId());
				button1.setDisable(true);
			}
			buttonstop.setDisable(false);
			// buttonsospendi.setDisable(false);
		}
		GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);
		if (gridPane != null) {
			Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);
			if (taskLabel != null) {
				service.iniziaAttività(taskIndex);
			}
		}
	}

	@FXML
	public void refresh(ActionEvent event) throws IOException {
		show();
	}

	@FXML
	public void sospendiTask(TaskDipendente task) throws IOException {
		long taskIndex = task.getId();
		try {
			GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);

			if (gridPane != null) {
				Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);

				if (taskLabel != null) {
					String taskText = taskLabel.getText();
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/dipendente/MotivazioneSospensione.fxml"));
					Parent root = loader.load();
					MotivazioneSospensioneController controller = loader.getController();
					controller.setTaskText(taskText);
					Stage stage = (Stage) mainContainer.getScene().getWindow();
					stage.setScene(new Scene(root));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void stopTask(TaskDipendente task) {
		long taskIndex = task.getId();
		GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);
		if (gridPane != null) {
			Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);
			if (taskLabel != null) {
				service.stopAttività(taskIndex);
				show();

			}
		}
	}

	public void terminaTurno(ActionEvent event) throws IOException {

		Parent root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dipendente/TerminaTurno.fxml"));
		root = loader.load();
		TerminaTurnoController controller = loader.getController();
		controller.setDipendente(dipendente);
		controller.show();
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

		stage.setScene(new Scene(root));

	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

}
