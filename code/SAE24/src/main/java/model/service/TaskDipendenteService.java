package model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entity.Commessa;
import model.entity.Dipendente;
import model.entity.TaskDipendente;

public class TaskDipendenteService {

	protected EntityManager em;
	private ObservableList<TaskDipendente> taskDipendenti;

	public TaskDipendenteService(String utilizzo) {
		if (utilizzo.equals("test")) {
			this.em = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
			taskDipendenti = FXCollections.observableArrayList(getListOfTasksDipendente());
		} else {
			this.em = Persistence.createEntityManagerFactory("dip").createEntityManager();
			taskDipendenti = FXCollections.observableArrayList(getListOfTasksDipendente());
		}
	}

	public ObservableList<TaskDipendente> getTaskDipendenti() {
		return taskDipendenti;
	}

	public void salvaTaskDipendente(TaskDipendente c) {
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		taskDipendenti.add(c);
	}

	public List<TaskDipendente> getListOfTasksDipendente() {
		return em.createQuery("SELECT t FROM TaskDipendente t", TaskDipendente.class)
				.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
	}

	public List<TaskDipendente> getListOfTasksDipendenteFromCommessa(Commessa commessa) {

		return taskDipendenti.stream().filter(taskDipendente -> taskDipendente.getTask().getCommessa().equals(commessa))
				.filter(taskDipendente -> "COMPLETATA".equals(taskDipendente.getStatus())).collect(Collectors.toList());
	}

	public List<TaskDipendente> findTasksDipendenteCompletata(Dipendente dipendente) {
		if (dipendente == null || dipendente.getId() == null) {
			throw new IllegalArgumentException("Il dipendente o il suo ID non può essere null.");
		}
		String statoc = "COMPLETATA";
		String jpql = "SELECT td FROM TaskDipendente td WHERE td.dipendente.id = :dipendenteId and td.status = :statoc ";
		TypedQuery<TaskDipendente> query = em.createQuery(jpql, TaskDipendente.class);
		query.setParameter("dipendenteId", dipendente.getId());
		query.setParameter("statoc", statoc);

		return query.getResultList();
	}

	public List<TaskDipendente> findTasksDipendente(Dipendente dipendente) {
		if (dipendente == null || dipendente.getId() == null) {
			throw new IllegalArgumentException("Il dipendente o il suo ID non può essere null.");
		}
		String statoc = "COMPLETATA";
		String jpql = "SELECT td FROM TaskDipendente td WHERE td.dipendente.id = :dipendenteId and td.status <> :statoc ";
		TypedQuery<TaskDipendente> query = em.createQuery(jpql, TaskDipendente.class);
		query.setParameter("dipendenteId", dipendente.getId());
		query.setParameter("statoc", statoc);

		return query.getResultList();
	}

	public void iniziaAttività(Long task) {
		// Inizializzazione dell'EntityManager e della transazione
		EntityTransaction transaction = em.getTransaction();

		try {
			// Avvio della transazione
			transaction.begin();

			// Otteniamo l'orario di inizio e la data attuale
			LocalDateTime orarioInizio = LocalDateTime.now(); // Orario di inizio attuale

			// Otteniamo il Task dalla base di dati
			TaskDipendente taskEntity = em.find(TaskDipendente.class, task);

			if (taskEntity != null) {

				taskEntity.setInizio(orarioInizio);
				taskEntity.setStatus("IN_LAVORAZIONE");

				// Merge delle modifiche
				em.merge(taskEntity);

				// Commit della transazione
				transaction.commit();
			} 

		} catch (RuntimeException e) {
			// Se c'è un errore, facciamo il rollback
			if (transaction.isActive()) {
				transaction.rollback();
			}
			// Stampa dell'errore
			e.printStackTrace();
		}
	}

	public void stopAttività(Long task) {
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			LocalDateTime orarioFine = LocalDateTime.now();
			TaskDipendente taskEntity = em.find(TaskDipendente.class, task);
			if (taskEntity != null) {
				taskEntity.setFine(orarioFine);
				taskEntity.setStatus("COMPLETATA");
				transaction.commit();
				CommessaService serviceCommessa = new CommessaService("");
				serviceCommessa.completaTask(taskEntity);
			} 

		} catch (RuntimeException e) {

			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

}
