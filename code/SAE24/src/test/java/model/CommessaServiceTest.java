package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entity.Commessa;
import model.entity.Reparto;
import model.service.CommessaService;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CommessaServiceTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private CommessaService commessaService;

	public CommessaServiceTest() {
		commessaService= new CommessaService("test");
	}
	
	@Before
	public void setUp() {
		try {
			emf = Persistence.createEntityManagerFactory("dip-test");
			em = emf.createEntityManager();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		// Pulire i dati di test e chiudere EntityManager
		if (em.isOpen())
			em.close();
		if (emf.isOpen())
			emf.close();
	}

	@Test
	public void testAddCommessaSuccess() {
		String nome = "Commessa Test";
		String descrizione = "Descrizione di test";
		String durata = "60";
		Reparto reparto = Reparto.CONFIGURAZIONE; // Supponiamo che il reparto sia già presente nel database

		// Chiama il metodo addCommessa
		String risultato = commessaService.addCommessa(nome, descrizione, durata, reparto, null);

		// Verifica che il risultato sia nullo (indica successo)
		assertNull("La commessa non è stata aggiunta correttamente", risultato);

		// Verifica che la commessa sia stata effettivamente salvata nel database
		Commessa commessa = em.createQuery("SELECT c FROM Commessa c WHERE c.nome = :nome", Commessa.class)
				.setParameter("nome", nome).getSingleResult();

		assertNotNull("La commessa non è stata trovata nel database", commessa);
		assertEquals("Il nome della commessa non è corretto", nome, commessa.getNome());
		assertEquals("La descrizione della commessa non è corretta", descrizione, commessa.getDescrizione());
	}

	@Test
    public void testDeleteCommessa_Success() {
		 em.getTransaction().begin();
        Commessa commessa = new Commessa();
        commessa.setNome("Commessa 1");
        em.persist(commessa);

        // Effettua il commit per rendere persistente la commessa
        em.getTransaction().commit();

        // Prova a eliminare la commessa
        commessaService.deleteCommessa(commessa.getId());
        em.clear();
        em.getTransaction().begin();
        Commessa deletedCommessa = em.find(Commessa.class, commessa.getId());
        em.getTransaction().commit();
        assertNull("La commessa dovrebbe essere eliminata.",deletedCommessa);
    }

    @Test
    public void testDeleteCommessa_Fail_HasFiglie() {
    	commessaService = new CommessaService("test");
    	 

        Commessa parentCommessa = new Commessa();
        parentCommessa.setNome("Commessa Principale");
        em.getTransaction().begin();
        em.persist(parentCommessa);
        em.getTransaction().commit();
        commessaService.addCommessa("Figlia", "prova", "20", Reparto.MONTAGGIO, parentCommessa);
        
        
        commessaService.deleteCommessa(parentCommessa.getId());
        em.clear();
        Commessa foundCommessa = em.find(Commessa.class, parentCommessa.getId());
        assertNotNull("La commessa non è stata eliminata", foundCommessa);
    }
}
