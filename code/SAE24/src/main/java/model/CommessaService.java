package model;

import javax.persistence.EntityManager;
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
	
	public void assegnaTasks(Commessa c, long id) {
		
		for (Commessa commessa : c.getCommesseFiglie()) {
			if(commessa.getCommesseFiglie().isEmpty())
			{
				Task t = new Task(commessa,id);
				Dipendente dipendente = scegliDipendente(commessa.getReparto());
				TaskDipendente td= new TaskDipendente(t, dipendente);
			}else {
				assegnaTasks(commessa, id);
			}
			
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
