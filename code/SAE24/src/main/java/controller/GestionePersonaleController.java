package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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




public class GestionePersonaleController {
	@FXML
	private AnchorPane contentPane2;
	@FXML
	private VBox mainContainer;
	@FXML
	private GridPane gridContainer;

    public void initialize() {
    	int num_dipendenti=40;

    	for (int i = 0; i < num_dipendenti; i++) {
            final int dipendenteIndex = i;  
            GridPane newGrid = new GridPane();
            
            Label dipendente = new Label("dipendente " + (dipendenteIndex + 1));
            dipendente.setId("dipendenteLabel" + dipendenteIndex); 
            newGrid.add(dipendente, 0, 0);
            
            // Bottone Start
            Button modifica = new Button("Modifica");
            modifica.setId("modifica" + dipendenteIndex);  
           // start.setOnAction(e -> startTask(dipendenteIndex));  
            newGrid.add(modifica, 1, 1);
            
            mainContainer.getChildren().add(newGrid);
            newGrid.getStyleClass().add("taskgrid");
            newGrid.setId("" + dipendenteIndex);
            
            
        }
    }
    
	
	//---------------------------------------------------------------------------------------------------------------
		
	// Event Listener on Button.onAction
	@FXML
	public void SwitchToInserimento(ActionEvent event) throws IOException {
		// TODO Autogenerated
		
		 System.out.println("sono entrato in modalità inserimento personale");

	        // Carica il file FXML di inserimentoTask.fxml
	        Parent root1 = FXMLLoader.load(getClass().getResource("/InserimentoDipendenti.fxml"));

	        contentPane2.getChildren().clear();  // Rimuovi il contenuto precedente
	        contentPane2.getChildren().add(root1);  // Aggiungi il nuovo contenuto
	}
	
	

	
	
}
