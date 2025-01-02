package model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

public class ManagerService extends DipendenteService {

	
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

		this.entityManager.getTransaction().begin();
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
	 public void deleteDipendente(Long long1) {
	        // Inizia una transazione
	      
	        try {
	        	entityManager.getTransaction().begin();

	            // Trova il Dipendente in base all'ID
	            Dipendente dipendente = entityManager.find(Dipendente.class, long1);
	            if (dipendente != null) {
	                // Rimuovi il dipendente dal database
	            	entityManager.remove(dipendente);
	                System.out.println("Dipendente con ID " + long1 + " eliminato.");
	            } else {
	                System.out.println("Dipendente non trovato.");
	            }

	            // Completare la transazione
	            entityManager.getTransaction().commit();
	        } catch (Exception e) {
	            // Se c'è un errore, fare il rollback
	            if (entityManager.getTransaction().isActive()) {
	            	entityManager.getTransaction().rollback();
	            }
	            e.printStackTrace();
	        } finally {
	        	entityManager.close();  // Chiudere l'EntityManager
	        }
	    }
	

}
