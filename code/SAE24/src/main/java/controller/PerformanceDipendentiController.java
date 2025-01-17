package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;

import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.entity.Commessa;
import model.entity.Dipendente;
import model.entity.Task;
import model.entity.TaskDipendente;
import model.service.DipendenteService;
import model.service.ManagerService;
import model.service.TaskDipendenteService;

public class PerformanceDipendentiController {
	@FXML
	private LineChart lineChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	
	List<TaskDipendente> tasksDipendente = new ArrayList<>();
	ArrayList<String> nomiCommesseDipendente = new ArrayList<>();
	ArrayList<Integer> tempoStimatoCommessa = new ArrayList<>();
	ArrayList<Integer> tempoCalcolatoCommessa = new ArrayList<>();
	
	
	
	public void show(Dipendente dipendente) {
	    this.getDatasDipendente(dipendente);

	    // Crea una mappa per gestire le categorie duplicate
	    int counter = 1;
	    ArrayList<String> uniqueCategories = new ArrayList<>();
	    for (String nome : nomiCommesseDipendente) {
	        String uniqueName = nome;
	        while (uniqueCategories.contains(uniqueName)) {
	            uniqueName = nome + " (" + counter + ")";
	            counter++;
	        }
	        uniqueCategories.add(uniqueName);
	    }

	    xAxis.getCategories().clear();
	    xAxis.getCategories().addAll(uniqueCategories);

	    // Creazione dei dati per il grafico
	    XYChart.Series<String, Number> series = new XYChart.Series<>();
	    series.setName("PerformanceDipendente");

	    // Aggiunta dei punti al grafico
	    for (int i = 0; i < uniqueCategories.size(); i++) {
	        series.getData().add(new XYChart.Data<>(uniqueCategories.get(i), tempoStimatoCommessa.get(i)));
	    }

	    // Aggiunta della serie al grafico
	    lineChart.getData().clear();
	    lineChart.getData().add(series);
	}

	
	public void getDatasDipendente(Dipendente dip) {
		this.tasksDipendente.clear();
		this.nomiCommesseDipendente.clear();
		this.tempoCalcolatoCommessa.clear();
		this.tempoStimatoCommessa.clear();
		
		TaskDipendenteService tds = new TaskDipendenteService("");
		tasksDipendente = tds.findTasksDipendente(dip);
		for (TaskDipendente task : tasksDipendente) {
		//	if(task.getStatus().equals("COMPLETATA"))){
				
		//	}
			Commessa c = task.getTask().getCommessa();
			
					String nomeCommessa = c.getNome();
					this.nomiCommesseDipendente.add(nomeCommessa);
					
					Integer tempoStimato = Integer.parseInt(c.getTempoStimato());
					this.tempoStimatoCommessa.add(tempoStimato);
					System.out.println(this.tempoStimatoCommessa);
				//	Integer tempoCalcolato = Integer.parseInt(c.getTempoCalcolato());
				//	this.tempoCalcolatoCommessa.add(tempoCalcolato);
			
		}
	}
}


