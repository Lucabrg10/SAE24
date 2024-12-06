package model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TaskService {
	private EntityManager entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();

   public TaskService()
   {
   }
	
	
	
	public List<Object[]> findTaskByMatricola(Long id) {
		   System.out.println("Long ID: " +id);  
		   	
		   String sql = "SELECT t.id, c.nome FROM Task t " +
		             "JOIN Commessa c ON t.commessa_id = c.id " +
		             "WHERE t.dipendente_id = 1";
		   
		Query query = entityManager.createNativeQuery(sql);
		
	       // query.setParameter("dipendenteId", 1);
	        System.out.println("query: " +sql);
	        return query.getResultList();
	        //SELECT t.commessa FROM Task t WHERE t.dipendente.id = :id
	    }

}
