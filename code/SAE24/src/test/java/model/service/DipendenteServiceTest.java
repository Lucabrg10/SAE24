package model.service;

import model.entity.Dipendente;
import model.entity.Reparto;
import model.entity.Task;
import model.entity.Commessa;
import model.entity.CommessaInstance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.Assert.*;

public class DipendenteServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private DipendenteService dipendenteService;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("dip-test");
        em = emf.createEntityManager();
        dipendenteService = new DipendenteService("test");
    }

    @After
    public void tearDown() {
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }
    	//testGetTasksByDipendente funziona??viene usato?
    @Test
    public void testGetTasksByDipendente_Success() {
        em.getTransaction().begin();

        // Crea un dipendente di test
        
        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("12345");
      //  dipendente.setId(12331L);
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("Mario Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        // Crea commessa e commessaInstance per i task
        Commessa commessa = new Commessa();
        commessa.setNome("Commessa 1");
        commessa.setReparto(Reparto.CABLAGGIO);
        commessa.setTempoStimato(100L);
        em.persist(commessa);
        
 

        CommessaInstance commessaInstance = new CommessaInstance();
        commessaInstance.setCommessa(commessa);
        commessaInstance.setId(11L); // Supponendo che CommessaInstance abbia un ID
        em.persist(commessaInstance);

        // Crea alcuni task assegnati al dipendente
        Task task1 = new Task(commessa, commessaInstance);
        task1.setCommessa(commessa);
        task1.setCommessaInstance(commessaInstance);
        em.persist(task1);

        Task task2 = new Task(commessa, commessaInstance);
        task2.setCommessa(commessa);
        task2.setCommessaInstance(commessaInstance);
        em.persist(task2);

        em.getTransaction().commit();
        em.clear();
        // Recupera i task tramite il servizio
        List<Task> tasks = dipendenteService.getTasksByDipendente(dipendente.getId());
       
        assertNotNull("La lista dei task non dovrebbe essere null", tasks);
        assertEquals("Il numero di task assegnati al dipendente non è corretto", 2, tasks.size());
    }

    @Test
    public void testModificaPasswordDipendente_Success() {
        em.getTransaction().begin();

        // Crea un dipendente di test
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Giovanni Verdi");
        dipendente.setMatricola("1");
        dipendente.setPassword("passwordVecchia");
        em.persist(dipendente);

        em.getTransaction().commit();

        // Modifica la password
        String risultato = dipendenteService.modificaPasswordDipendente("1", "passwordNuova");

        assertEquals("La password dovrebbe essere aggiornata con successo.", "Password aggiornata con successo.", risultato);
        em.clear();
        // Verifica la modifica
        Dipendente aggiornato = em.find(Dipendente.class, dipendente.getId());
        assertNotNull("Il dipendente dovrebbe essere presente nel database", aggiornato);
        System.out.println("pass     "+aggiornato.getPassword());
        assertEquals("La password non è stata aggiornata correttamente", "passwordNuova", aggiornato.getPassword());
    }

    @Test
    public void testModificaPasswordDipendente_Fail_MatricolaInesistente() {
        // Tenta di modificare la password di un dipendente inesistente
        String risultato = dipendenteService.modificaPasswordDipendente("99999", "passwordNuova");

        assertEquals("L'operazione dovrebbe fallire per matricola inesistente.", "Errore durante l'aggiornamento della password.", risultato);
    }

    @Test
    public void testGetDipendenteByMatricola_Success() {
        em.getTransaction().begin();

        // Crea un dipendente di test
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Luigi Bianchi");
        dipendente.setMatricola("54321");
        em.persist(dipendente);

        em.getTransaction().commit();

        // Recupera il dipendente tramite la matricola
        Dipendente trovato = dipendenteService.getDipendenteByMatricola("54321");

        assertNotNull("Il dipendente dovrebbe essere trovato", trovato);
        assertEquals("Il nome del dipendente non corrisponde", "Luigi Bianchi", trovato.getNome());
    }

    @Test
    public void testGetDipendenteByMatricola_Fail() {
        // Recupera un dipendente con una matricola inesistente
        Dipendente trovato = dipendenteService.getDipendenteByMatricola("99999");

        assertNull("Il dipendente non dovrebbe essere trovato", trovato);
    }
}

