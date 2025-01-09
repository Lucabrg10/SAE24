package model.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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
	
	
}
