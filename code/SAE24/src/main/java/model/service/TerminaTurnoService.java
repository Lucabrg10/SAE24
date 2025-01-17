package model.service;

import model.entity.Dipendente;
import model.entity.TaskDipendente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TerminaTurnoService {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public TerminaTurnoService(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.em = emf.createEntityManager();
    }

    public List<TaskDipendente> findTasksInRange(Dipendente dipendente) {
        if (dipendente == null || dipendente.getId() == null) {
            throw new IllegalArgumentException("Il dipendente o il suo ID non può essere null.");
        }

        // Calcola l'inizio e la fine della giornata
        LocalDateTime inizio = LocalDate.now().atStartOfDay();
        LocalDateTime fine = LocalDateTime.now();

        // Definisci lo stato da escludere
        String statoc = "COMPLETATA";

        // Scrivi la query JPQL
        String jpql = "SELECT td FROM TaskDipendente td " +
                      "WHERE td.dipendente.id = :dipendenteId " +
                      "AND td.status = :statoc " +
                      "AND td.inizio >= :oraInizio " +
                      "AND td.fine <= :oraFine";

        TypedQuery<TaskDipendente> query = em.createQuery(jpql, TaskDipendente.class);
        query.setParameter("dipendenteId", dipendente.getId());
        query.setParameter("statoc", statoc);
        query.setParameter("oraInizio", inizio);
        query.setParameter("oraFine", fine);

        // Esegui la query e restituisci i risultati
        return query.getResultList();
    }

    public void close() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }

    public void aggiornaTaskDipendente(TaskDipendente task) {
        // Verifica che il task e il suo ID non siano nulli
        if (task == null || task.getId() == null) {
            throw new IllegalArgumentException("Il task o il suo ID non può essere null.");
        }

        // Inizia la transazione
        EntityTransaction transaction = em.getTransaction();
        try {
            // Avvia la transazione
            transaction.begin();

            // Calcola la query JPQL per aggiornare il task
            String jpql = "UPDATE TaskDipendente td SET td.inizio = :inizio, td.fine = :fine WHERE td.id = :taskId";

            // Crea la query e imposta i parametri
            Query query = em.createQuery(jpql);
            query.setParameter("inizio", task.getInizio());
            query.setParameter("fine", task.getFine());
            query.setParameter("taskId", task.getId());

            // Esegui l'aggiornamento
            int rowsUpdated = query.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Task aggiornato con successo.");
            } else {
                System.out.println("Nessun task trovato con l'ID fornito.");
            }

            // Commetti la transazione
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();  // Rollback in caso di errore
            }
            throw e;  // Rilancia l'eccezione per una gestione appropriata
        }
    }
}