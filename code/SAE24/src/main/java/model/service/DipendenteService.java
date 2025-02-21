package model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import model.entity.Dipendente;

public class DipendenteService {
	protected final EntityManager entityManager;

	public DipendenteService(String utilizzo) {
		if(utilizzo.equals("test")) {
			this.entityManager = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		}else {
			this.entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}
	}
	
	
	/**
	 * Modifica la password di un dipendente.
	 *
	 * @param matricola La matricola del dipendente.
	 * @param nuovaPassword La nuova password da impostare.
	 * @return Una stringa che indica il risultato dell'operazione.
	 */
	public String modificaPasswordDipendente(String matricola, String nuovaPassword) {
	    entityManager.getTransaction().begin();
	    try {
	        // Trova il dipendente tramite la matricola
	        TypedQuery<Dipendente> query = entityManager.createQuery(
	            "SELECT d FROM Dipendente d WHERE d.matricola = :matricola", Dipendente.class);
	        query.setParameter("matricola", matricola);
	        Dipendente dipendente = query.getSingleResult();

	        // Modifica la password
	        dipendente.setPassword(nuovaPassword);

	        // Salva le modifiche
	        entityManager.getTransaction().commit();
	        return "Password aggiornata con successo.";
	    } catch (PersistenceException e) {
	        if (entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback(); // Rollback in caso di errore
	        }
	        return "Errore durante l'aggiornamento della password.";
	    }
	}	
	public Dipendente getDipendenteByMatricola(String matricola) {
	    TypedQuery<Dipendente> query = entityManager.createQuery("SELECT d FROM Dipendente d WHERE d.matricola = :matricola", Dipendente.class);
	    query.setParameter("matricola", matricola);
	    List<Dipendente> result = query.getResultList();
	    return result.isEmpty() ? null : result.get(0);
	}
}
