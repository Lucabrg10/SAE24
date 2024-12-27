package model;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class ManagerService {

	private final EntityManager entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();

	/**
	 * Aggiungi un nuovo dipendente/manager.
	 *
	 * @param name Nome del dipendente.
	 */

	public String addDipendente(String nome, String cognome, String matricola, Reparto reparto) throws IllegalStateException{

		Dipendente dipendente=null;
		if(reparto.toString().equals("MANAGER")) {
			dipendente = new Manager();
		}else {
			dipendente = new Dipendente();
		}
		 
		dipendente.setNome(nome);
		dipendente.setCognome(cognome);
		dipendente.setMatricola(matricola);
		dipendente.setPassword(matricola);
		dipendente.setReparto(reparto);

		entityManager.getTransaction().begin();
		try {
			entityManager.persist(dipendente);
			entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
        	if (entityManager.getTransaction().isActive()) {
        		entityManager.getTransaction().rollback();  // Fai il rollback in caso di errore
            }
        	
           return("Errore: la matricola non è univoca o si è verificato un altro errore.");
            
        }
		return null;
	}

	/**
	 * Recupera tutti i dipendenti (inclusi i manager).
	 *
	 * @return Lista di dipendenti.
	 */
	public List<Dipendente> getAllDipendenti() {
		TypedQuery<Dipendente> query = entityManager.createQuery("SELECT e FROM Dipendente e", Dipendente.class);
		return query.getResultList();
	}

	/**
	 * Recupera tutte le task assegnate a un dipendente.
	 *
	 * @param employeeId ID del dipendente.
	 * @return Lista di task del dipendente.
	 */
	public List<Task> getTasksByDipendente(Long dipendenteId) {
		TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.dipendente.id = :dipendenteId",
				Task.class);
		query.setParameter("dipendenteId", dipendenteId);
		return query.getResultList();
	}
}
