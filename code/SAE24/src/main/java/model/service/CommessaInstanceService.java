package model.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.entity.Commessa;
import model.entity.CommessaInstance;

public class CommessaInstanceService {
	protected EntityManager em;

	public CommessaInstanceService(String utilizzo) {
		if (utilizzo.equals("test")) {
			this.em = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			this.em = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}
	}

	public void salvaCommessaInstance(CommessaInstance c) {
		em.clear();
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
	}

	public CommessaInstance creaNewCommessaInstance(Commessa commessa) {
		TypedQuery<CommessaInstance> query = em.createQuery(
				"SELECT ci FROM CommessaInstance ci WHERE ci.commessa = :commessa ORDER BY ci.instance DESC",
				CommessaInstance.class);
		query.setParameter("commessa", commessa);
		query.setMaxResults(1); // Limita il risultato all'ultima riga

		// Step 2: Ottieni l'ultima CommessaInstance
		CommessaInstance lastCommessaInstance = null;
		try {
			lastCommessaInstance = query.getSingleResult();
		} catch (Exception e) {
			// Se non ci sono risultati (prima CommessaInstance per quella Commessa)
		}

		CommessaInstance newCommessaInstance;
		if (lastCommessaInstance != null) {
			newCommessaInstance = new CommessaInstance(commessa,lastCommessaInstance.getInstance() + 1);
		} else {
			newCommessaInstance = new CommessaInstance(commessa,1);
		}

		return newCommessaInstance;
	}

}
