package model.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.entity.CommessaInstance;
import model.entity.TaskDipendente;

public class CommessaInstanceService {
	protected EntityManager em ;

	public CommessaInstanceService(String utilizzo) {
		if (utilizzo.equals("test")) {
			this.em = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			this.em = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}
	}
	
	public void salvaCommessaInstance(CommessaInstance c) {
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
	}
	
}
