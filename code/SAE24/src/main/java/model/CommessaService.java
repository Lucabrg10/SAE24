package model;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class CommessaService {
	protected  EntityManager entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();

	public CommessaService(EntityManager e) {
		this.entityManager = e;
	}
	
	
 // Metodo per aggiungere una commessa
 public String addCommessa(String nome, String desc, String durata, Reparto reparto) {

		Commessa commessa=new Commessa();
	
		 
		commessa.setNome(nome);
		commessa.setReparto(reparto);
		commessa.setDescrizione(desc);
		commessa.setTempoStimato(durata);
		commessa.setTempoCalcolato("0");

		this.entityManager.getTransaction().begin();
		try {
			entityManager.persist(commessa);
			entityManager.getTransaction().commit();
     } catch (PersistenceException e) {
     	if (entityManager.getTransaction().isActive()) {
     		entityManager.getTransaction().rollback();  // Fai il rollback in caso di errore
         }
     	
        return("Errore.");
         
     }
		return null;
 }

 // Metodo per eliminare una commessa
 public void deleteCommessa(Commessa commessa) {
     // Logica per eliminare la commessa dal database
 }
}
