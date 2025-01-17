package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.entity.Commessa;
import model.entity.Dipendente;
import model.entity.TaskDipendente;
import model.service.TaskDipendenteService;

public class PerformanceDipendentiController {
	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	
	List<TaskDipendente> tasksDipendente = new ArrayList<>();
	ArrayList<String> nomiCommesseDipendente = new ArrayList<>();
	ArrayList<Long> tempoStimatoCommessa = new ArrayList<>();
	ArrayList<Long> tempoCalcolatoCommessa = new ArrayList<>();
	
	public void show(Dipendente dipendente) {
	    this.getDatasDipendente(dipendente);

	    // Cancella i dati precedenti
	    xAxis.getCategories().clear();
	    barChart.getData().clear();

	    // Categorie uniche per l'asse X
	    xAxis.getCategories().addAll(nomiCommesseDipendente);

	    // Serie per i dati
	    XYChart.Series<String, Number> seriesStimati = new XYChart.Series<>();
	    XYChart.Series<String, Number> seriesEffettivi = new XYChart.Series<>();
	    
	    seriesStimati.setName("Minuti previsti per commessa");
	    seriesEffettivi.setName("Minuti effettivi per commessa");

	    for (int i = 0; i < nomiCommesseDipendente.size(); i++) {
	        seriesStimati.getData().add(new XYChart.Data<>(nomiCommesseDipendente.get(i), tempoStimatoCommessa.get(i)));
	        seriesEffettivi.getData().add(new XYChart.Data<>(nomiCommesseDipendente.get(i), tempoCalcolatoCommessa.get(i)));
	    }

	    barChart.getData().addAll(seriesEffettivi, seriesStimati);
	}

	
	public void getDatasDipendente(Dipendente dip) {
		this.tasksDipendente.clear();
		this.nomiCommesseDipendente.clear();
		this.tempoCalcolatoCommessa.clear();
		this.tempoStimatoCommessa.clear();
		
		TaskDipendenteService tds = new TaskDipendenteService("");
		tasksDipendente = tds.findTasksDipendenteCompletata(dip);
		for (TaskDipendente task : tasksDipendente) {
			Commessa c = task.getTask().getCommessa();
			
					String nomeCommessa = c.getNome();
					this.nomiCommesseDipendente.add(nomeCommessa+"("+ task.getTask().getCommessaInstance().getInstance()+")");
					
					Long tempoStimato = (c.getTempoStimato());
					this.tempoStimatoCommessa.add(tempoStimato);
					System.out.println(this.tempoStimatoCommessa);
					Long tempoCalcolato = (task.getDurataInMinuti());
					this.tempoCalcolatoCommessa.add(tempoCalcolato);
		}
	}
}


