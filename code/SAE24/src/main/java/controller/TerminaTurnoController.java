package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Dipendente;
import model.entity.TaskDipendente;
import model.service.TerminaTurnoService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TerminaTurnoController {

    @FXML
    private VBox mainContainer;

    @FXML
    private GridPane gridContainer;

    private Dipendente dipendente;

    private final TerminaTurnoService service;

    public TerminaTurnoController() {
        this.service = new TerminaTurnoService("dip"); 
    }

    public void show() {
        List<TaskDipendente> tasks = service.findTasksInRange(dipendente);

        // Verifica se ci sono risultati
        if (tasks == null || tasks.isEmpty()) {
            Label noTasksLabel = new Label("Nessun task trovato per il dipendente.");
            mainContainer.getChildren().add(noTasksLabel);
            return;
        }
   
        GridPane mainGrid = new GridPane();
        mainGrid.getStyleClass().add("mainGrid");
        mainGrid.setHgap(10); 
        mainGrid.setVgap(10); 
        mainGrid.setPadding(new Insets(10)); 

        Label taskHeader = new Label("Task");
        taskHeader.getStyleClass().add("headerLabel");
        Label startHeader = new Label("Inizio");
        startHeader.getStyleClass().add("headerLabel");
        Label stopHeader = new Label("Fine");
        stopHeader.getStyleClass().add("headerLabel");
        Label statusHeader = new Label("Stato");
        statusHeader.getStyleClass().add("headerLabel");

      
        mainGrid.add(taskHeader, 0, 0);
        mainGrid.add(startHeader, 1, 0);
        mainGrid.add(stopHeader, 2, 0);
        mainGrid.add(statusHeader, 3, 0);

        
        int rowIndex = 1; 
        for (TaskDipendente task : tasks) {
           
            Label taskLabel = new Label(task.getTask().getCommessa().getNome());
            taskLabel.setId("taskLabel" + task.getId());
            mainGrid.add(taskLabel, 0, rowIndex);

       
            TextField startTextField = new TextField(task.getInizio().toString());
            startTextField.setId("start" + task.getId());
            mainGrid.add(startTextField, 1, rowIndex);

         
            TextField stopTextField = new TextField(task.getFine().toString());
            stopTextField.setId("stop" + task.getId());
            mainGrid.add(stopTextField, 2, rowIndex);

         
            Label statusLabel = new Label(task.getStatus());
            statusLabel.setId("status" + task.getId());
            mainGrid.add(statusLabel, 3, rowIndex);

       
            rowIndex++;
        }

      
        mainContainer.getChildren().add(mainGrid);
    }


    @FXML
    public void terminaGiornata() throws IOException {
    	
        List<TaskDipendente> tasks = service.findTasksInRange(dipendente);
        for (TaskDipendente task : tasks) {
          
            TextField startTextField = (TextField) mainContainer.lookup("#start" + task.getId());
            TextField stopTextField = (TextField) mainContainer.lookup("#stop" + task.getId());

            if (startTextField != null && stopTextField != null) {
            
                String inizioStr = startTextField.getText();
                String fineStr = stopTextField.getText();

              
                try {
                	LocalDateTime inizio = LocalDateTime.parse(inizioStr);
                	LocalDateTime fine = LocalDateTime.parse(fineStr);

                   
                    task.setInizio(inizio);
                    task.setFine(fine);

                  
                    service.aggiornaTaskDipendente(task);
                } catch (DateTimeParseException e) {
                	e.getMessage();
                }
            }
            

    	
    	
        }
        String fxmlPath = "/Login.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene newScene = new Scene(loader.load());
        Stage stage = (Stage) mainContainer.getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    public void annulla (ActionEvent event) throws IOException {
     
        Parent root = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/dipendente/MainViewDipendente.fxml"));
		root = loader.load();
		MainViewDipendenteController controller = loader.getController();	
		controller.setDipendente(dipendente);
		controller.show();
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(new Scene(root));
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}
