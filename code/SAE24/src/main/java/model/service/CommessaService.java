package model.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.entity.Task;
import model.entity.TaskDipendente;

public class CommessaService {
	protected EntityManager entityManager;

	public CommessaService(String utilizzo) {
		if (utilizzo.equals("test")) {
			this.entityManager = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			this.entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}
	}

	public void creaCommessaInstance(CommessaInstance c) {
		entityManager.getTransaction().begin();
		entityManager.persist(c);
		entityManager.getTransaction().commit();

	}

	// Metodo per aggiungere una commessa
	public String addCommessa(String nome, String desc, String durata, Reparto reparto, Commessa padre) {

		Commessa commessa = new Commessa();
		commessa.setNome(nome);
		commessa.setReparto(reparto);
		commessa.setDescrizione(desc);
		commessa.setTempoStimato(Long.valueOf(durata));
		commessa.setCommessaPadre(padre);

		this.entityManager.getTransaction().begin();
		try {
			if (padre != null) {
				Commessa managedPadre = entityManager.find(Commessa.class, padre.getId());
				if (managedPadre != null) {
					commessa.setCommessaPadre(managedPadre);
					managedPadre.addCommessaFiglia(commessa);
					entityManager.persist(managedPadre);

				} else {
					throw new IllegalStateException("Commessa padre non trovata nel contesto di persistenza.");
				}
			}

			entityManager.persist(commessa);
			entityManager.getTransaction().commit();
			entityManager.clear();
		} catch (PersistenceException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback(); // Fai il rollback in caso di errore
			}

			return ("Errore.");

		}
		return null;
	}

	public Commessa getCommessa(String nomeCommessa) {
		try {
			String jpql = "SELECT c FROM Commessa c WHERE c.nome = :nome";
			TypedQuery<Commessa> query = entityManager.createQuery(jpql, Commessa.class);
			query.setParameter("nome", nomeCommessa);
			return query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean deleteCommessa(Long id) {
		try {
			entityManager.clear();
			entityManager.getTransaction().begin();
			Commessa commessa = entityManager.find(Commessa.class, id);
			if (commessa != null && commessa.getCommesseFiglie().isEmpty()) {
				if (commessa.getCommessaPadre() != null) {
					commessa.getCommessaPadre().removeCommessaFiglia(commessa);
				}
				entityManager.remove(commessa);
			} else {
				return false;
			}
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		} 
		return false;
	}

	public int assegnaTasksSistema(Commessa c, CommessaInstance instance) {
		int cont = 0;// serve solo per il test
		TaskDipendenteService serviceTaskDip = new TaskDipendenteService("");
		TaskService serviceTask = new TaskService("");
		if (c.getCommesseFiglie().isEmpty()) {
			Task t = new Task(c, instance);
			serviceTask.salvaTask(t);
			Dipendente dipendente = scegliDipendente(c.getReparto());
			TaskDipendente td = new TaskDipendente(t, dipendente);
			serviceTaskDip.salvaTaskDipendente(td);
			cont++;
		} else {
			for (Commessa commessa : c.getCommesseFiglie()) {
				if (commessa.getCommesseFiglie().isEmpty()) {
					Task t = new Task(commessa, instance);
					serviceTask.salvaTask(t);
					Dipendente dipendente = scegliDipendente(commessa.getReparto());
					TaskDipendente td = new TaskDipendente(t, dipendente);
					serviceTaskDip.salvaTaskDipendente(td);
					cont++;
				} else {
					assegnaTasksSistema(commessa, instance);
				}

			}
		}

		return cont;
	}

	public void completaTask(TaskDipendente t) {

		TaskDipendenteService servicedipendente = new TaskDipendenteService("");
		TaskService service = new TaskService("");
		Commessa commessaPadre = t.getTask().getCommessa().getCommessaPadre();
		if (commessaPadre != null && statusFigli(commessaPadre, t.getTask().getCommessaInstance())) {
			Task newTask = new Task(commessaPadre, t.getTask().getCommessaInstance());
			service.salvaTask(newTask);
			Dipendente d = scegliDipendente(commessaPadre.getReparto());
			TaskDipendente td = new TaskDipendente(newTask, d);
			servicedipendente.salvaTaskDipendente(td);
		}
	}

	public boolean statusFigli(Commessa padre, CommessaInstance idInstance) {

		for (Commessa c : padre.getCommesseFiglie()) {
			if (c.getCommesseFiglie().isEmpty()) {
				Task t = getTask(c, idInstance);
				ArrayList<TaskDipendente> td = new ArrayList<>(tasksAssegnate(t));
				for (TaskDipendente taskDipendente : td) {
					if (!taskDipendente.getStatus().equals("COMPLETATA")) {
						return false;
					}
				}
			} else {
				if (!statusFigli(c, idInstance)) {
					return false;
				}
			}
		}
		return true;
	}

	public List<TaskDipendente> tasksAssegnate(Task t) {

		String query = "SELECT td FROM TaskDipendente td WHERE td.task = :task";
		return entityManager.createQuery(query, TaskDipendente.class).setParameter("task", t).getResultList();

	}

	private Task getTask(Commessa commessa, CommessaInstance iteratore) {
		try {

			String jpql = "SELECT t FROM Task t WHERE t.commessa = :commessa AND t.commessaInstance = :iteratore";
			TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);
			query.setParameter("commessa", commessa);
			query.setParameter("iteratore", iteratore);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Dipendente scegliDipendente(Reparto reparto) {

		Dipendente dipendente = null;
		try {
			entityManager.clear();
			// Creazione della query per trovare il dipendente con il minor numero di task
			// "ASSEGNATA"
			TypedQuery<Dipendente> query = entityManager.createQuery(
					"SELECT d FROM Dipendente d WHERE d.reparto = :reparto AND (SELECT COUNT(t) FROM TaskDipendente t WHERE t.dipendente = d AND t.status = 'ASSEGNATA') = (SELECT MIN((SELECT COUNT(t1) FROM TaskDipendente t1 WHERE t1.dipendente = d1 AND t1.status = 'ASSEGNATA')) FROM Dipendente d1 WHERE d1.reparto = :reparto)",
					Dipendente.class);
			query.setParameter("reparto", reparto);
			dipendente = query.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.getMessage();
		}

		return dipendente;
	}

}
