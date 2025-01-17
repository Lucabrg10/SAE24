package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;

import javafx.scene.chart.CategoryAxis;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.entity.Commessa;
import model.entity.Dipendente;
import model.entity.TaskDipendente;
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
	ArrayList<Long> tempoStimatoCommessa = new ArrayList<>();
	ArrayList<Long> tempoCalcolatoCommessa = new ArrayList<>();
	
	public void show(Dipendente dipendente) {
	    this.getDatasDipendente(dipendente);

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
	    XYChart.Series<String, Number> series = new XYChart.Series<>();
	    XYChart.Series<String, Number> seriesEffettivi = new XYChart.Series<>();
	    seriesEffettivi.setName("PerformanceDipendente");
	    series.setName("Tempi stimati");
	    for (int i = 0; i < uniqueCategories.size(); i++) {
	        series.getData().add(new XYChart.Data<>(uniqueCategories.get(i), tempoStimatoCommessa.get(i)));
	        seriesEffettivi.getData().add(new XYChart.Data<>(uniqueCategories.get(i), tempoCalcolatoCommessa.get(i)));
	    }

	    lineChart.getData().clear();
	    lineChart.getData().add(seriesEffettivi);
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
			Commessa c = task.getTask().getCommessa();
			
					String nomeCommessa = c.getNome();
					this.nomiCommesseDipendente.add(nomeCommessa);
					
					Long tempoStimato = (c.getTempoStimato());
					this.tempoStimatoCommessa.add(tempoStimato);
					System.out.println(this.tempoStimatoCommessa);
					Long tempoCalcolato = (task.getDurataInMinuti());
					this.tempoCalcolatoCommessa.add(tempoCalcolato);
		}
	}
}


