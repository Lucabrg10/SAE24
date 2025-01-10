package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Dipendente;
import model.entity.TaskDipendente;
import model.service.TaskDipendenteService;
import model.service.TaskService;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;

public class TasksDipendenteController {
	@FXML
	private VBox mainContainer;

	@FXML
	private GridPane gridContainer;
	private Dipendente dipendente;

	TaskDipendenteService service = new TaskDipendenteService("");
	List<TaskDipendente> taskDipendente;

	public void show() {
		taskDipendente = service.findTasksDipendente(dipendente);

		System.out.println(taskDipendente);
		
		if (taskDipendente != null && !taskDipendente.isEmpty()) {
			for (TaskDipendente taskD : taskDipendente) {
				GridPane newGrid = new GridPane();

				Label task = new Label("task " + (taskD.getTask().getCommessa().getNome()));
				task.setId("taskLabel" + taskD.getId());
				newGrid.add(task, 0, 0);

				// Bottone Start
				Button start = new Button("Start");
				start.setId("start" + taskD.getId());
				start.setOnAction(e -> startTask(convertToLong(taskD.getId())));
				newGrid.add(start, 0, 1);

				// Bottone Stop
				Button stop = new Button("Stop");
				stop.setId("stop" + taskD.getId());

				stop.setOnAction(e -> stopTask(convertToLong(taskD.getId())));
				newGrid.add(stop, 1, 1);

				// Bottone Sospendi
				Button sospendi = new Button("Sospendi");
				sospendi.setId("sospendi" + taskD.getId());
				sospendi.setOnAction(e -> {
					try {
						sospendiTask(convertToLong(taskD.getId()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				newGrid.add(sospendi, 2, 1);

				mainContainer.getChildren().add(newGrid);
				newGrid.getStyleClass().add("taskgrid");
				newGrid.setId("" + taskD.getId());
			}

		} else {
			System.out.println("Nessun task trovato per la matricola: " + dipendente.getMatricola());
		}
	}

	public void startTask(Long taskIndex) {
		Button button = (Button) mainContainer.lookup("#start" + taskIndex);
		if (button != null) {
			button.setDisable(true);
		} else {
			System.out.println("Button not found with id: " + taskIndex);
		}
		GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);
		if (gridPane != null) {
			Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);
			if (taskLabel != null) {
				String taskText = taskLabel.getText();
				System.out.println("inizia: " + taskText);

				service.iniziaAttività(taskIndex);

			}
		}
	}

	@FXML
	public void sospendiTask(Long taskIndex) throws IOException {
		try {
			GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);

			if (gridPane != null) {
				Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);

				if (taskLabel != null) {
					String taskText = taskLabel.getText();
					System.out.println("sospendi: " + taskText);

					// Carica il file FXML
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/dipendente/MotivazioneSospensione.fxml"));
					Parent root = loader.load();

					// Ottieni il controller e passa il parametro
					MotivazioneSospensioneController controller = loader.getController();
					controller.setTaskText(taskText);

					// Cambia scena
					Stage stage = (Stage) mainContainer.getScene().getWindow();
					stage.setScene(new Scene(root));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void stopTask(Long taskIndex) {

		GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);

		if (gridPane != null) {

			Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);

			if (taskLabel != null) {
				String taskText = taskLabel.getText();
				System.out.println("stoppa: " + taskText);

				service.stopAttività(taskIndex);

			}
		}
	}

	public void terminaTurno(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/dipendente/TerminaTurno.fxml"));
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

		stage.setScene(new Scene(root));
	}

	private Long convertToLong(Object obj) {
		if (obj instanceof Integer) {
			return ((Integer) obj).longValue(); // Converti Integer a Long
		} else if (obj instanceof Long) {
			return (Long) obj; // Restituisci direttamente il Long
		} else {
			throw new IllegalArgumentException("Expected Integer or Long, but got: " + obj.getClass());
		}
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

}
