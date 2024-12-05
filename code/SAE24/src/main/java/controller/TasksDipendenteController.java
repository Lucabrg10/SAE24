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

import java.awt.Color;
import java.io.IOException;

import javafx.event.ActionEvent;

public class TasksDipendenteController {
	@FXML
	private VBox mainContainer;

	@FXML
	private GridPane gridContainer;

	public void initialize() {
		int num_task = 100;

		for (int i = 0; i < num_task; i++) {
			final int taskIndex = i;
			GridPane newGrid = new GridPane();

			Label task = new Label("task " + (taskIndex + 1));
			task.setId("taskLabel" + taskIndex);
			newGrid.add(task, 0, 0);

			// Bottone Start
			Button start = new Button("Start");
			start.setId("start" + taskIndex);
			start.setOnAction(e -> startTask(taskIndex));
			newGrid.add(start, 0, 1);

			// Bottone Stop
			Button stop = new Button("Stop");
			stop.setId("stop" + taskIndex);
			stop.setOnAction(e -> stopTask(taskIndex));
			newGrid.add(stop, 1, 1);

			// Bottone Sospendi
			Button sospendi = new Button("Sospendi");
			sospendi.setId("sospendi" + taskIndex);
			sospendi.setOnAction(e -> {
				try {
					sospendiTask(taskIndex);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			newGrid.add(sospendi, 2, 1);

			mainContainer.getChildren().add(newGrid);
			newGrid.getStyleClass().add("taskgrid");
			newGrid.setId("" + taskIndex);
		}

	}

	// Event Listener on Button.onAction

	public void startTask(int taskIndex) {

		GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);
		if (gridPane != null) {
			Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);
			if (taskLabel != null) {
				String taskText = taskLabel.getText();
				System.out.println("inizia: " + taskText);
			}
		}
	}
	@FXML
	public void sospendiTask(int taskIndex) throws IOException {
		try {
			GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);

			if (gridPane != null) {
				Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);

				if (taskLabel != null) {
					String taskText = taskLabel.getText();
					System.out.println("sospendi: " + taskText);

					// Carica il file FXML
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/MotivazioneSospensione.fxml"));
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
	public void stopTask(int taskIndex) {

		GridPane gridPane = (GridPane) mainContainer.lookup("#" + taskIndex);

		if (gridPane != null) {

			Label taskLabel = (Label) gridPane.lookup("#taskLabel" + taskIndex);

			if (taskLabel != null) {
				String taskText = taskLabel.getText();
				System.out.println("stoppa: " + taskText);

			}
		}
	}

	public void terminaTurno(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/TerminaTurno.fxml"));
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

		stage.setScene(new Scene(root));
	}

}
