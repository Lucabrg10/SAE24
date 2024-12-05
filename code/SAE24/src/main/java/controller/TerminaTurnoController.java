package controller;

import javafx.scene.control.TextField;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TerminaTurnoController {
	@FXML
	private VBox mainContainer;
	@FXML
	private GridPane gridContainer;
	
	public void initialize() {
		
		
    	int num_task=100;

    	for (int i = 0; i < num_task; i++) {
            final int taskIndex = i;  
            GridPane newGrid = new GridPane();
            
            Label task = new Label("task " + (taskIndex + 1));
            task.setId("taskLabel" + taskIndex); 
            newGrid.add(task, 0, 0);
            
            // Bottone Start
            TextField start = new TextField("start");
            start.setId("start" + taskIndex);   
      
            newGrid.add(start, 1, 0);
            
            // Bottone Stop
            TextField stop = new TextField("stop"); 
            stop.setId("stop" + taskIndex);  
          
            newGrid.add(stop, 2, 0);
            
            // Bottone Sospendi
            Label sospendi = new Label("minuti");
            sospendi.setId("sospendi" + taskIndex);   
            newGrid.add(sospendi, 3, 0);

           
            mainContainer.getChildren().add(newGrid);
            newGrid.getStyleClass().add("taskgrid");
            newGrid.setId("" + taskIndex);
        }
        
    }
	@FXML
	public void terminaGiornata() throws IOException
	{
		String fxmlPath;
			fxmlPath = "/loginController.fxml";
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	    Scene newScene = new Scene(loader.load());
	    Stage stage = (Stage) mainContainer.getScene().getWindow();
	    stage.setScene(newScene);
	    stage.show();
	}
	@FXML
	public void annulla() throws IOException
	{
		String fxmlPath;
			fxmlPath = "/MainViewDipendente.fxml";
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	    Scene newScene = new Scene(loader.load());
	    Stage stage = (Stage) mainContainer.getScene().getWindow();
	    stage.setScene(newScene);
	    stage.show();
	}
	
	

}
