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

	

}
