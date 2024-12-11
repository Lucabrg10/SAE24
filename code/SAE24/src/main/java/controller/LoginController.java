package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Dipendente;
import model.Manager;
import model.LoginService;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class LoginController {
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorLabel;

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
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
				root = loader.load();
				ManagerInterfaceController controller = loader.getController();			
				controller.setManager((Manager) user);
				Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
				stage.setScene(new Scene(root));
			} else if (user instanceof Dipendente) {
				Parent root = null;
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainViewDipendente.fxml"));
				root = loader.load();
				MainViewDipendenteController controller = loader.getController();
				controller.setDipendente((Dipendente) user);
				Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
				stage.setScene(new Scene(root));

			} else {
				System.out.println("Credenziali non valide.");
			}
		}
	}
}

