package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entity.Dipendente;
import model.service.ManagerService;

public class ProfiloController {
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField textPw;
	@FXML
	private Label nome;
	@FXML
	private Label cognome;
	@FXML
	private Label reparto;
	@FXML
	private Label messaggio;
	@FXML
	private Label matricola;
	@FXML
	private Button modificaPassword;
	@FXML
	private CheckBox showPasswordCheckBox;
	
	Dipendente dipendente;

	public void show() throws IOException {
		if (dipendente == null) {
			System.out.println("null");
		} else {
			ManagerService service = new ManagerService("");
			dipendente = service.getDipendenteByMatricola(dipendente.getMatricola());
			
			nome.setText(dipendente.getNome());
			cognome.setText(dipendente.getCognome());
			reparto.setText(dipendente.getReparto().toString());
			password.setText(dipendente.getPassword());
			matricola.setText(dipendente.getMatricola());
			textPw.textProperty().bindBidirectional(password.textProperty());

		        // Listener per il CheckBox
		        showPasswordCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
		            if (newVal) {
		            	textPw.setVisible(true);
		            	textPw.setManaged(true);
		            	password.setVisible(false);
		                password.setManaged(false);
		            } else {
		            	textPw.setVisible(false);
		            	textPw.setManaged(false);
		            	password.setVisible(true);
		            	password.setManaged(true);
		            }
		        });
		}
	}
	
	public void modificaPassword() {
		String newPassword = password.getText();
		ManagerService service = new ManagerService("");
		String mess = service.modificaPasswordDipendente(dipendente.getMatricola(), newPassword);
		messaggio.setText(mess);
	}
	
	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

}
