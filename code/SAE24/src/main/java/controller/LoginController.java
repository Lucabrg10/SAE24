package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.entity.Dipendente;
import model.entity.Manager;
import model.service.LoginService;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorLabel;
	@FXML
	private Label titleLabel;

	// Event Listener on Button.onAction
	@FXML

	public void handleLogin(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();

		LoginService service = new LoginService();
		Object user = service.authenticate(username, password);
		
		
		if (user != null) {
			System.out.println(user);
			if (user instanceof Manager) {
				Parent root = null;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager/MainView.fxml"));
				root = loader.load();
				ManagerInterfaceController controller = loader.getController();			
				controller.setManager((Manager) user);
				controller.show();
				Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
				stage.setScene(new Scene(root));
			} else if (user instanceof Dipendente) {
				Parent root = null;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/dipendente/MainViewDipendente.fxml"));
				root = loader.load();
				MainViewDipendenteController controller = loader.getController();
				controller.setDipendente((Dipendente) user);
				controller.show();
				Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
				stage.setScene(new Scene(root));

			}
		} else {
			System.out.println("Error");

			errorLabel.setText("Credenziali non valide.");
		}
	}
}

