package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CommessaServiceTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private CommessaService commessaService;

	@Before
	public void setUp() {
		// Usa l'unità di persistenza di test
		 try {
		        emf = Persistence.createEntityManagerFactory("dip-test");  // Usa "dip"
		        em = emf.createEntityManager();
		        commessaService = new CommessaService(em);
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
		// Dati di test
		String nome = "Commessa Test";
		String descrizione = "Descrizione di test";
		String durata = "10 giorni";
		Reparto reparto = Reparto.CONFIGURAZIONE; // Supponiamo che il reparto sia già presente nel database

		// Chiama il metodo addCommessa
		String risultato = commessaService.addCommessa(nome, descrizione, durata, reparto);

		// Verifica che il risultato sia nullo (indica successo)
		assertNull("La commessa non è stata aggiunta correttamente", risultato);

		// Verifica che la commessa sia stata effettivamente salvata nel database
		Commessa commessa = em.createQuery("SELECT c FROM Commessa c WHERE c.nome = :nome", Commessa.class)
				.setParameter("nome", nome).getSingleResult();

		assertNotNull("La commessa non è stata trovata nel database", commessa);
		assertEquals("Il nome della commessa non è corretto", nome, commessa.getNome());
		assertEquals("La descrizione della commessa non è corretta", descrizione, commessa.getDescrizione());
		assertEquals("La durata della commessa non è corretta", durata, commessa.getTempoStimato());
	}

	@Test
	public void testAddCommessaFailure() {
		
	}

}
