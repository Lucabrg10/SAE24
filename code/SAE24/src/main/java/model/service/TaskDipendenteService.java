package model.service;

import java.util.List;

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
	
	public List<TaskDipendente> retrieveListOfTasksDipendente(){
		  return em.createQuery("SELECT t FROM TaskDipendente t", TaskDipendente.class).getResultList();
	}
	
	
}
