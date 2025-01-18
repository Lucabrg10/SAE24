package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import model.entity.Manager;

public class ManagerInterfaceController {

	@FXML
	private AnchorPane contentPane; // Pannello centrale per i contenuti

	private Manager manager;

	public void show() throws IOException {
		visualizzaProfilo(null);
	}

	public void inserimentoTask(ActionEvent e) throws IOException {
		setContent("/manager/AssegnazioneTask.fxml");
	}

	public void visualizzaCommesse(ActionEvent e) throws IOException {
		setContent("/manager/VisualizzaCommesse.fxml");
	}

	public void visualizzaAndamento(ActionEvent e) throws IOException {
		setContent("/manager/AndamentoCommesse.fxml");
	}

	public void gestionePersonale(ActionEvent e) throws IOException {
		setContent("/manager/GestionePersonale.fxml");
	}

	public void visualizzaProfilo(ActionEvent e) throws IOException {
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/profilo.fxml"));
		root = loader.load();
		ProfiloController controller = loader.getController();
		controller.setDipendente(manager);
		controller.show();
		contentPane.getChildren().clear();
		contentPane.getChildren().add(root);
		String css = this.getClass().getResource("/application.css").toExternalForm();
		root.getStylesheets().add(css);//
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public void setContent(String path) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource(path));
		contentPane.getChildren().clear();
		contentPane.getChildren().add(root);
		AnchorPane.setTopAnchor(root, 0.0);
		AnchorPane.setLeftAnchor(root, 0.0);
		AnchorPane.setRightAnchor(root, 0.0);
		AnchorPane.setBottomAnchor(root, 0.0);
	}

}
