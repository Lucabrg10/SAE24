package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class loginController {
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorLabel;

	// Event Listener on Button.onAction
	@FXML
	public void handleLogin(ActionEvent event) {
		 String username = usernameField.getText();
	        String password = passwordField.getText();
	       
	            try {
	                Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
	                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	                
	                Scene scene = new Scene(root, 1400, 780);

	                String css = this.getClass().getResource("/application.css").toExternalForm();
	            	scene.getStylesheets().add(css);//

	                stage.setScene(scene);
	                stage.centerOnScreen();


	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        
	    }
}
