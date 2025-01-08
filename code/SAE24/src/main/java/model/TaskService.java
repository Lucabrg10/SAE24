package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TaskService {
	private EntityManager entityManager;

	public TaskService(String utilizzo) {
		if (utilizzo.equals("test")) {
			entityManager = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}

	}

	public String creaTask(Task t) {

		this.entityManager.getTransaction().begin();

		entityManager.persist(t);
		entityManager.getTransaction().commit();

		return "ok";

	}

	public List<Object[]> findTaskByMatricola(Long id) {
		System.out.println("Long ID: " + id);

		String sql = "SELECT t.id, c.nome FROM Task t " + "JOIN Commessa c ON t.commessa_id = c.id "
				+ "WHERE t.dipendente_id = 1";

		Query query = entityManager.createNativeQuery(sql);

		// query.setParameter("dipendenteId", 1);
		System.out.println("query: " + sql);
		return query.getResultList();
		// SELECT t.commessa FROM Task t WHERE t.dipendente.id = :id
	}

	public void iniziaAttività(Long task) {
		// Inizializzazione dell'EntityManager e della transazione
		EntityTransaction transaction = entityManager.getTransaction();

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
			Task taskEntity = entityManager.find(Task.class, task);

			if (taskEntity != null) {
				// Impostiamo i nuovi valori
		//		taskEntity.setOrarioInizio(orarioInizio);
		//		taskEntity.setData(data);
		//		taskEntity.setStato("in lavorazione");

				// Merge delle modifiche
				entityManager.merge(taskEntity);

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
		EntityTransaction transaction = entityManager.getTransaction();

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
			Task taskEntity = entityManager.find(Task.class, task);

			if (taskEntity != null) {
				// Impostiamo i nuovi valori
			//	taskEntity.setOrarioFine(orarioFine);
				// taskEntity.setData(data);
		//		taskEntity.setStato("Terminata");
//
				// Merge delle modifiche
				entityManager.merge(taskEntity);

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
