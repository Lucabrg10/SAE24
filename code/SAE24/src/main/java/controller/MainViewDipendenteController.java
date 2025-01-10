package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import model.entity.Dipendente;
import model.entity.Task;

public class MainViewDipendenteController {
	@FXML
	private AnchorPane contentPane;
	@FXML
	private Label titleLabel;

	private Dipendente dipendente;
	private List<Task>tasks;

	public void show() throws IOException {
		visualizzaTask(null);

	}
	// Event Listener on Button.onAction
	@FXML
	public void visualizzaTask(ActionEvent event) throws IOException {
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dipendente/TasksDipendente.fxml"));
		root=loader.load();
		TasksDipendenteController controller = loader.getController();
		controller.setDipendente(dipendente);
		controller.show();
		contentPane.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane.getChildren().add(root); // Aggiungi il nuovo contenuto
		String css = this.getClass().getResource("/application.css").toExternalForm();
		root.getStylesheets().add(css);//

	}

	public void visualizzaProfilo(ActionEvent event) throws IOException {
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profilo.fxml"));
		root=loader.load();
		ProfiloController controller = loader.getController();
		controller.setDipendente(dipendente);
		controller.show();
		contentPane.getChildren().clear(); // Rimuovi il contenuto precedente
		contentPane.getChildren().add(root); // Aggiungi il nuovo contenuto
		String css = this.getClass().getResource("/application.css").toExternalForm();
		root.getStylesheets().add(css);//

	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

}
