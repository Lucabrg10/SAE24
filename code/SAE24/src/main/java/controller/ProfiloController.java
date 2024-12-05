package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import model.Dipendente;

public class ProfiloController {
	@FXML
	private Label titleLabel;
	Dipendente dipendente;
	
	
	
	public Dipendente getDipendente() {
		return dipendente;
	}
	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
		aggiornaSchermata();
	}
	private void aggiornaSchermata() {
        if (dipendente != null) {
            titleLabel.setText(dipendente.getNome());
          
        }
    }
	
	
}
