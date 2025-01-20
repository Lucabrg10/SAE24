package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.entity.Task;
import model.entity.TaskDipendente;
import model.service.CommessaService;

import static org.junit.Assert.*;

import java.util.List;

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
		String nome = "Commessa Test nome";
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
    @Test
    public void testStatusFigli_AllTasksCompleted_ReturnsTrue() {
        // Crea una commessa padre e figlia
        Commessa padre = new Commessa();
        padre.setNome("Padre");
        Commessa figlia = new Commessa();
        figlia.setNome("Figlia");
        padre.addCommessaFiglia(figlia);

        // Persisti le commesse
        em.getTransaction().begin();
        em.persist(padre);
        em.persist(figlia);
        em.getTransaction().commit();

        // Crea un'istanza di CommessaInstance
        CommessaInstance instance = new CommessaInstance();
        instance.setCommessa(padre);
        instance.setId(21L); 
        em.getTransaction().begin();
        em.persist(instance);
        em.getTransaction().commit();

        // Aggiungi un Task completato alla commessa figlia
        Task task = new Task(figlia, instance);
        em.getTransaction().begin();
        em.persist(task);
        
        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("123499");
      //  dipendente.setId(12331L);
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("Mario Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        TaskDipendente taskDipendente = new TaskDipendente(task, dipendente);
        taskDipendente.setStatus("COMPLETATA");
        em.persist(taskDipendente);
        em.getTransaction().commit();

        // Verifica che lo statusFigli ritorni true
        boolean result = commessaService.statusFigli(padre, instance);
        assertTrue("Tutti i task dei figli sono completati, ma il metodo ha restituito false.", result);
    }

    @Test
    public void testStatusFigli_OneTaskNotCompleted_ReturnsFalse() {
        // Crea una commessa padre e figlia
        Commessa padre = new Commessa();
        padre.setNome("Padre");
        Commessa figlia = new Commessa();
        figlia.setNome("Figlia");
        padre.addCommessaFiglia(figlia);

        // Persisti le commesse
        em.getTransaction().begin();
        em.persist(padre);
        em.persist(figlia);
        em.getTransaction().commit();

        // Crea un'istanza di CommessaInstance
        CommessaInstance instance = new CommessaInstance();
        instance.setCommessa(padre);
        instance.setId(29L); 
        em.getTransaction().begin();
        em.persist(instance);
        em.getTransaction().commit();

        // Aggiungi un Task non completato alla commessa figlia
        Task task = new Task(figlia, instance);
        em.getTransaction().begin();
        em.persist(task);

        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("123496");
      //  dipendente.setId(12331L);
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("Mario Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);
        
        TaskDipendente taskDipendente = new TaskDipendente(task, dipendente);
        taskDipendente.setStatus("IN_CORSO");
        em.persist(taskDipendente);
        em.getTransaction().commit();

        // Verifica che lo statusFigli ritorni false
        boolean result = commessaService.statusFigli(padre, instance);
        assertFalse("Esistono task non completati, ma il metodo ha restituito true.", result);
    }

    @Test
    public void testTasksAssegnate_ReturnsCorrectTasks() {
        // Crea una commessa e un task
    	 
        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Test");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM TaskDipendente").executeUpdate();
        em.persist(commessa);
        em.getTransaction().commit();

        CommessaInstance instance = new CommessaInstance();
        instance.setCommessa(commessa);
        instance.setId(22L); 
        em.getTransaction().begin();
        em.persist(instance);
        em.getTransaction().commit();

        Task task = new Task(commessa, instance);
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();

        // Aggiungi due TaskDipendente
        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("123492");
      //  dipendente.setId(12331L);
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("Mario Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);
        TaskDipendente td1 = new TaskDipendente(task, dipendente);
        TaskDipendente td2 = new TaskDipendente(task, dipendente);
        em.getTransaction().begin();
        em.persist(td1);
        em.persist(td2);
        em.getTransaction().commit();

        // Verifica che il metodo ritorni entrambi i task
        List<TaskDipendente> result = commessaService.tasksAssegnate(task);
        assertNotNull(result);
        assertEquals("Il numero di TaskDipendente non corrisponde.", 2, result.size());
    }

    @Test
    public void testScegliDipendente_ReturnsDipendente() {
        // Crea un Dipendente
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Dipendente Test");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Dipendente").executeUpdate();
        em.persist(dipendente);
        em.getTransaction().commit();
        em.clear();
        
        // Verifica che il metodo trovi il dipendente
        Dipendente result = commessaService.scegliDipendente(Reparto.CABLAGGIO);
        assertNotNull("Il metodo non ha trovato il dipendente.", result);
        assertEquals("Il dipendente trovato non corrisponde.", dipendente.getNome(), result.getNome());
    }

    @Test
    public void testAssegnaTasksSistema() {
        // Crea una commessa senza figli
        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Test22");
        commessa.setReparto(Reparto.CABLAGGIO);
        CommessaInstance instance = new CommessaInstance();
        instance.setCommessa(commessa);
        instance.setId(100034L); 
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Task").executeUpdate();
        em.createQuery("DELETE FROM CommessaInstance").executeUpdate();
        em.persist(commessa);
        

      
        em.persist(instance);
        em.getTransaction().commit();
        em.clear();
        CommessaInstance test = em.find(CommessaInstance.class, instance.getId());
       System.out.println("\n\ntest    "+test);
        
        // Chiama il metodo e verifica che il task sia assegnato
        int result = commessaService.assegnaTasksSistema(commessa, test);
        assertEquals("Il numero di task assegnati non corrisponde.", 1, result);

        List<Task> tasks = em.createQuery("SELECT t FROM Task t WHERE t.commessa = :commessa", Task.class)
                .setParameter("commessa", commessa).getResultList();
        assertEquals("Il numero di task creati non corrisponde.", 1, tasks.size());
    }

    @Test
    public void testCompletaTask() {
        // Crea una commessa padre e figlia
        Commessa padre = new Commessa();
        padre.setNome("Padre");
        Commessa figlia = new Commessa();
        figlia.setNome("Figlia");
        figlia.setReparto(Reparto.CABLAGGIO);
        figlia.setCommessaPadre(padre);
        padre.addCommessaFiglia(figlia);
        padre.setReparto(Reparto.CABLAGGIO);

        em.getTransaction().begin();
        em.createQuery("DELETE FROM TaskDipendente").executeUpdate();
        em.persist(padre);
        em.persist(figlia);
        em.getTransaction().commit();

        // Crea un'istanza di CommessaInstance
        CommessaInstance instance = new CommessaInstance();
        instance.setCommessa(padre);
        instance.setId(24L); 
        em.getTransaction().begin();
        em.persist(instance);
        em.getTransaction().commit();

        // Aggiungi un Task completato per la figlia
        Task taskFiglia = new Task(figlia, instance);
        em.getTransaction().begin();
        em.persist(taskFiglia);
        
        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("123493");
      //  dipendente.setId(12331L);
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("Mario Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        TaskDipendente tdFiglia = new TaskDipendente(taskFiglia, dipendente);
        tdFiglia.setStatus("ASSEGNATA");
        em.persist(tdFiglia);
        em.getTransaction().commit();

        

        // Completa il task della figlia e verifica che il padre sia aggiornato
        commessaService.completaTask(tdFiglia);
        em.clear();
        List<TaskDipendente> tasksPadre = em.createQuery("SELECT td FROM TaskDipendente td WHERE td.task.commessa = :padre",
                TaskDipendente.class).setParameter("padre", padre).getResultList();
       
        assertEquals("Il numero di task completati per il padre non corrisponde.", 1, tasksPadre.size());
    }
}
