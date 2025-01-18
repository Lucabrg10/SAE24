package controller;

import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.entity.Commessa;
import model.entity.TaskDipendente;
import model.service.TaskDipendenteService;

public class AndamentoCommessaController {
	@FXML
	private LineChart<String, Number> graficoAndamentoCommesse;
	private List<TaskDipendente> taskDipendenti;

	public void show(Commessa commessa) {

		TaskDipendenteService service = new TaskDipendenteService("");
		taskDipendenti = service.getListOfTasksDipendenteFromCommessa(commessa);
		XYChart.Series<String, Number> estimatedSeries = new XYChart.Series<>();
		estimatedSeries.setName("Durata Stimata");

		XYChart.Series<String, Number> actualSeries = new XYChart.Series<>();
		actualSeries.setName("Durata Effettiva");

		// Popolamento delle serie
		int esecuzione = 1; // Contatore delle esecuzioni
		for (TaskDipendente taskDipendente : taskDipendenti) {
			// Durata stimata
			estimatedSeries.getData().add(new XYChart.Data<>(String.valueOf(esecuzione), commessa.getTempoStimato()));

			// Durata effettiva
			actualSeries.getData()
					.add(new XYChart.Data<>(String.valueOf(esecuzione), (taskDipendente.getDurataInMinuti())));

			esecuzione++;
		}

		// Pulisci il grafico e aggiungi le serie
		graficoAndamentoCommesse.getData().clear();
		graficoAndamentoCommesse.setTitle("Andamento commessa: " + commessa.getNome());
		graficoAndamentoCommesse.getData().addAll(estimatedSeries, actualSeries);
	}

}
