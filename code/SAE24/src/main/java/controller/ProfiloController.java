package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import model.Dipendente;

public class ProfiloController {
	@FXML
	private PasswordField password;
	@FXML
	private Label nome;
	@FXML
	private Label cognome;
	@FXML
	private Label reparto;

	Dipendente dipendente;

	public void show() throws IOException {
		if(dipendente == null) {
			System.out.println("null");}
		else {	nome.setText(dipendente.getNome());
		cognome.setText(dipendente.getCognome());
		reparto.setText(dipendente.getReparto());
	}
	}
	
	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}
	
	
	
	
	
}
