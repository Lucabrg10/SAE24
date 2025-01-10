package model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.entity.Task;
import model.entity.TaskDipendente;

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
