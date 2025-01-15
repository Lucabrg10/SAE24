package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.time.LocalTime;
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
        this.service = new TerminaTurnoService("dip"); // Sostituisci con il nome della tua unità di persistenza
    }

    public void show() {
        // Recupera i task dal servizio
        List<TaskDipendente> tasks = service.findTasksInRange(dipendente);

        // Verifica se ci sono risultati
        if (tasks == null || tasks.isEmpty()) {
            Label noTasksLabel = new Label("Nessun task trovato per il dipendente.");
            mainContainer.getChildren().add(noTasksLabel);
            return;
        }

        // Cicla sui task e aggiungi le informazioni nella griglia
        for (TaskDipendente task : tasks) {
            GridPane newGrid = new GridPane();

            // Etichetta per il nome del task
            Label taskLabel = new Label("Task: " + task.getTask().getCommessa().getNome());
            taskLabel.setId("taskLabel" + task.getId());
            newGrid.add(taskLabel, 0, 0);

            // TextField per l'orario di inizio
            TextField startTextField = new TextField(task.getInizio().toString());
            startTextField.setId("start" + task.getId());
            newGrid.add(startTextField, 1, 0);

            // TextField per l'orario di fine
            TextField stopTextField = new TextField(task.getFine().toString());
            stopTextField.setId("stop" + task.getId());
            newGrid.add(stopTextField, 2, 0);

            // Etichetta per lo stato
            Label statusLabel = new Label("Stato: " + task.getStatus());
            statusLabel.setId("status" + task.getId());
            newGrid.add(statusLabel, 3, 0);

            // Aggiungi la nuova griglia al contenitore principale
            mainContainer.getChildren().add(newGrid);
            newGrid.getStyleClass().add("taskgrid");
            newGrid.setId("grid" + task.getId());
        }
    }

    @FXML
    public void terminaGiornata() throws IOException {
    	
        List<TaskDipendente> tasks = service.findTasksInRange(dipendente);

        // Cicla sui task e raccoglie i dati dai TextField
        for (TaskDipendente task : tasks) {
            // Trova il TextField corrispondente al task (associato tramite ID)
            TextField startTextField = (TextField) mainContainer.lookup("#start" + task.getId());
            TextField stopTextField = (TextField) mainContainer.lookup("#stop" + task.getId());

            if (startTextField != null && stopTextField != null) {
                // Ottieni i nuovi orari dai TextField
                String inizioStr = startTextField.getText();
                String fineStr = stopTextField.getText();

                // Converte i valori di inizio e fine in oggetti LocalTime
                try {
                	LocalDateTime inizio = LocalDateTime.parse(inizioStr);
                	LocalDateTime fine = LocalDateTime.parse(fineStr);

                    // Aggiorna il task con i nuovi valori
                    task.setInizio(inizio);
                    task.setFine(fine);

                    // Chiamata al servizio per aggiornare il task nel database
                    service.aggiornaTaskDipendente(task);
                } catch (DateTimeParseException e) {
                    // Gestisci l'errore se i valori inseriti non sono nel formato corretto
                    System.out.println("Errore nella conversione dell'orario.");
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
		System.out.println("dipendente"+dipendente.getMatricola());
		controller.setDipendente(dipendente);
		controller.show();
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(new Scene(root));
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}
