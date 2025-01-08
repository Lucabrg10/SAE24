package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class CommessaService {
	protected EntityManager entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();

	public CommessaService(String utilizzo) {
		if (utilizzo.equals("test")) {
			this.entityManager = Persistence.createEntityManagerFactory("dip-test").createEntityManager();
		} else {
			this.entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();
		}
	}

	// Metodo per aggiungere una commessa
	public String addCommessa(String nome, String desc, String durata, Reparto reparto, Commessa padre) {

		Commessa commessa = new Commessa();
		commessa.setNome(nome);
		commessa.setReparto(reparto);
		commessa.setDescrizione(desc);
		commessa.setTempoStimato(durata);
		commessa.setTempoCalcolato("0");
		commessa.setCommessaPadre(padre);

		this.entityManager.getTransaction().begin();
		try {
			if (padre != null) {
			    Commessa managedPadre = entityManager.find(Commessa.class, padre.getId());
			    if (managedPadre != null) {
			        managedPadre.addCommessaFiglia(commessa);
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

	public boolean deleteCommessa(Long id) {
		try {
			entityManager.clear();
			entityManager.getTransaction().begin();
			Commessa commessa = entityManager.find(Commessa.class, id);
			if (commessa != null  && commessa.getCommesseFiglie().isEmpty()) {
				if(commessa.getCommessaPadre()!=null) {
					commessa.getCommessaPadre().removeCommessaFiglia(commessa);
				}
				entityManager.remove(commessa);
				System.out.println("Commessa con ID " + id + " eliminata. "+commessa.getCommesseFiglie());
			} else {
				System.out.println("Commessa non eliminata."+commessa.getCommesseFiglie());
				return false;
			}
			entityManager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		return false;
	}
	
	public void assegnaTasksSistema(Commessa c, long idInstance) {
//		TaskService service = new TaskService("");
		
		for (Commessa commessa : c.getCommesseFiglie()) {
			if(commessa.getCommesseFiglie().isEmpty())
			{
				Task t = new Task(commessa,idInstance);
				Dipendente dipendente = scegliDipendente(commessa.getReparto());
				TaskDipendente td= new TaskDipendente(t, dipendente);
			}else {
				assegnaTasksSistema(commessa, idInstance);
			}
			
		}
	}
	
	public void completaTask(TaskDipendente t) {
		
		t.setStatus("COMPLETATO");
		Commessa commessaPadre = t.getTask().getCommessa().getCommessaPadre();
		if(commessaPadre!=null) {
			if(statusFigli(commessaPadre, t.getTask().getCommessaInstance())) {
				Task newTask = new Task(commessaPadre,t.getTask().getId());
				Dipendente d = scegliDipendente(commessaPadre.getReparto());
				TaskDipendente td = new TaskDipendente(newTask, d);
			}
			
		}
		
	}
	
	public boolean statusFigli(Commessa padre, Long idInstance) {
		
		for (Commessa c : padre.getCommesseFiglie()) {
			if(c.getCommesseFiglie().isEmpty()) {
				Task t = getTask(c, idInstance);
				ArrayList<TaskDipendente>td = new ArrayList<>(tasksAssegnate(t));
				for (TaskDipendente taskDipendente : td) {
					if(!taskDipendente.getStatus().equals("COMPLETATA")) {
						return false;
					}
				}
			}else {
				if(!statusFigli(c,idInstance)) {
					return false;
				}
			}
		}
		return true;
	}

	public List<TaskDipendente> tasksAssegnate(Task t) {
        try {
            String query = "SELECT td FROM TaskDipendente td WHERE td.task = :task";
            return entityManager.createQuery(query, TaskDipendente.class)
                     .setParameter("task", t)
                     .getResultList();
        } finally {
        	entityManager.close();
        }
    }
		
	private Task getTask(Commessa commessa, Long iteratore) {
	    try {
	        String jpql = "SELECT t FROM Task t WHERE t.comessa = :commessa AND t.iteratore = :iteratore";
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
            TypedQuery<Dipendente> query = entityManager.createQuery(
                "SELECT d FROM Dipendente d WHERE d.reparto = :reparto ORDER BY d.id ASC", Dipendente.class);
            query.setParameter("reparto", reparto);

            dipendente = query.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            System.err.println("Errore durante la selezione del dipendente: " + e.getMessage());
        } finally {
        	entityManager.close();
        }

        return dipendente;
    }

}
