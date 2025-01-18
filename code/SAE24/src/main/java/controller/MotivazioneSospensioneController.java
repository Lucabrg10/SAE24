package controller;

import javafx.scene.control.Label;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

public class MotivazioneSospensioneController {
	@FXML
	private ChoiceBox selezioneMotivazione;
	@FXML
	private Label taskSospesa;
	@FXML
	private TextArea testoMotivazione;
	
	
	private String taskText;
	
	public void initialize() {
		ObservableList<String> motivazioni = selezioneMotivazione.getItems();
		 motivazioni.addAll("Fine turno", "Mancanza pezzi", "Pausa","Altro");
	}
	
	public void setTaskText(String taskText) {
        this.taskText = taskText;
        taskSospesa.setText("stai sospendendo la task: "+ taskText); 
        
    }
	public void confermaSospensione() throws IOException
	{
		String selezione = (String) selezioneMotivazione.getValue();
		String testo = testoMotivazione.getText();
		String fxmlPath;
		if(selezione.equals("Fine turno"))
		{
			//registrare sospensione
			fxmlPath = "/dipendente/TerminaTurno.fxml";
		}
		else if(selezione.equals("Pausa"))
		{
			//registrare sospensione
			fxmlPath = "/dipendente/login.fxml";
		}
		else
		{
			//registrare sospensione
			fxmlPath = "/dipendente/MainViewDipendente.fxml";
			
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	    Scene newScene = new Scene(loader.load());

	    // Ottieni lo Stage corrente
	    Stage stage = (Stage) selezioneMotivazione.getScene().getWindow();
	    stage.setScene(newScene);
	    stage.show();

	}
	public void annulla() throws IOException
	{
		String fxmlPath;
			fxmlPath = "/dipendente/MainViewDipendente.fxml";
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	    Scene newScene = new Scene(loader.load());
	    Stage stage = (Stage) 	selezioneMotivazione.getScene().getWindow();
	    stage.setScene(newScene);
	    stage.show();
	}

}
