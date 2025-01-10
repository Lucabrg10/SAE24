package model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.entity.Dipendente;
import model.entity.Task;
import model.entity.TaskDipendente;

public class TaskDipendenteService {

	protected EntityManager em ;

	public TaskDipendenteService(String utilizzo) {
		if (utilizzo.equals("test")) {
			this.em = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			this.em = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}
	}
	
	public void salvaTaskDipendente(TaskDipendente c) {
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
	}
	
	public List<TaskDipendente> retrieveListOfTasksDipendente(){
		em.clear();
		  return em.createQuery("SELECT t FROM TaskDipendente t", TaskDipendente.class).getResultList();
	}

	 public List<TaskDipendente> findTasksDipendente(Dipendente dipendente) {
	        if (dipendente == null || dipendente.getId() == null) {
	            throw new IllegalArgumentException("Il dipendente o il suo ID non può essere null.");
	        }

	        String jpql = "SELECT td FROM TaskDipendente td WHERE td.dipendente.id = :dipendenteId";
	        TypedQuery<TaskDipendente> query = em.createQuery(jpql, TaskDipendente.class);
	        query.setParameter("dipendenteId", dipendente.getId());

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
			LocalDate data = LocalDate.now(); // Data attuale

			System.out.println("Commessa ID: " + task);
			System.out.println("Orario Inizio: " + orarioInizio);
			System.out.println("Data: " + data);

			// Otteniamo il Task dalla base di dati
			Task taskEntity = em.find(Task.class, task);

			if (taskEntity != null) {
				// Impostiamo i nuovi valori
		//		taskEntity.setOrarioInizio(orarioInizio);
		//		taskEntity.setData(data);
		//		taskEntity.setStato("in lavorazione");

				// Merge delle modifiche
				em.merge(taskEntity);

				// Commit della transazione
				transaction.commit();

				System.out.println("Attività iniziata correttamente per la task ID: " + task);
			} else {
				System.out.println("Task non trovato per l'ID: " + task);
			}

		} catch (RuntimeException e) {
			// Se c'è un errore, facciamo il rollback
			if (transaction.isActive()) {
				transaction.rollback();
			}
			// Stampa dell'errore
			System.out.println("Errore durante l'inizio dell'attività: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void stopAttività(Long task) {
		// Inizializzazione dell'EntityManager e della transazione
		EntityTransaction transaction = em.getTransaction();

		try {
			// Avvio della transazione
			transaction.begin();

			// Otteniamo l'orario di inizio e la data attuale
			LocalDateTime orarioFine = LocalDateTime.now(); // Orario di inizio attuale
			// LocalDate data = LocalDate.now(); // Data attuale

			// Stampiamo i parametri per il debug
			System.out.println("task ID: " + task);
			System.out.println("Orario fine: " + orarioFine);

			// Otteniamo il Task dalla base di dati
			Task taskEntity = em.find(Task.class, task);

			if (taskEntity != null) {
				// Impostiamo i nuovi valori
			//	taskEntity.setOrarioFine(orarioFine);
				// taskEntity.setData(data);
		//		taskEntity.setStato("Terminata");
//
				// Merge delle modifiche
				em.merge(taskEntity);

				// Commit della transazione
				transaction.commit();

				System.out.println("Attività terminata correttamente per la task ID: " + task);
			} else {
				System.out.println("Task non trovato per l'ID: " + task);
			}

		} catch (RuntimeException e) {
			// Se c'è un errore, facciamo il rollback
			if (transaction.isActive()) {
				transaction.rollback();
			}
			// Stampa dell'errore
			System.out.println("Errore durante l'inizio dell'attività: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
