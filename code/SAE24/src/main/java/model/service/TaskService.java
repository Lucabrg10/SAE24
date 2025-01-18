package model.service;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import model.entity.Task;

public class TaskService {
	private EntityManager entityManager;

	public TaskService(String utilizzo) {
		if (utilizzo.equals("test")) {
			entityManager = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}

	}

	public void salvaTask(Task t) {
		entityManager.clear();
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
	}

}
