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
	        if ("".equals(username) && "".equals(password)) {
	            try {
	                Parent root = FXMLLoader.load(getClass().getResource("/MainViewDipendente.fxml"));
	                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

	                stage.setScene(new Scene(root));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else {
	            
	            errorLabel.setText("Invalid username or password.");
	        }
	    }
}
