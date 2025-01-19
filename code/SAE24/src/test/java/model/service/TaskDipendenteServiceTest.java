package model.service;

import model.entity.Commessa;
import model.entity.CommessaInstance;
import model.entity.Dipendente;
import model.entity.Reparto;
import model.entity.Task;
import model.entity.TaskDipendente;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class TaskDipendenteServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private TaskDipendenteService taskDipendenteService;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("dip-test");
        em = emf.createEntityManager();
        taskDipendenteService = new TaskDipendenteService("test");
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

    @Test
    public void testSalvaTaskDipendente() {
        em.getTransaction().begin();

        // Crea entità necessarie per il test
        // Crea entità necessarie per il test
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Giovanni giovannini");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Completa 3");
        commessa.setReparto(Reparto.CABLAGGIO);
        commessa.setTempoStimato(100L);
        em.persist(commessa);
        
        CommessaInstance commessaInstance= new CommessaInstance();
        commessaInstance.setCommessa(commessa);
        commessaInstance.setId(4L);
        em.persist(commessaInstance);

        Task task = new Task();
        task.setCommessa(commessa);
        task.setCommessaInstance(commessaInstance);
        em.persist(task);
        em.getTransaction().commit();

        TaskDipendente taskDipendente = new TaskDipendente();
        taskDipendente.setDipendente(dipendente);
        taskDipendente.setTask(task);
        taskDipendente.setStatus("COMPLETATA");
        taskDipendente.setInizio(LocalDateTime.now());
        taskDipendente.setFine(LocalDateTime.now());
        
      

        

        // Salva il TaskDipendente
        taskDipendenteService.salvaTaskDipendente(taskDipendente);

        // Verifica che sia stato salvato correttamente
        List<TaskDipendente> tasks = taskDipendenteService.getListOfTasksDipendente();
        assertTrue("Il task dipendente dovrebbe essere presente nella lista.", tasks.contains(taskDipendente));
    }

    @Test
    public void testFindTasksDipendenteCompletata() {
        em.getTransaction().begin();

        // Crea entità necessarie per il test
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Giovanni Verdi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Completa");
        commessa.setReparto(Reparto.CABLAGGIO);
        commessa.setTempoStimato(100L);
        em.persist(commessa);
        
        CommessaInstance commessaInstance= new CommessaInstance();
        commessaInstance.setCommessa(commessa);
        commessaInstance.setId(1L);
        em.persist(commessaInstance);

        Task task = new Task();
        task.setCommessa(commessa);
        task.setCommessaInstance(commessaInstance);
        em.persist(task);

        TaskDipendente taskDipendente = new TaskDipendente();
        taskDipendente.setDipendente(dipendente);
        taskDipendente.setTask(task);
        taskDipendente.setStatus("COMPLETATA");
        em.persist(taskDipendente);

        em.getTransaction().commit();

        // Recupera i task completati
        List<TaskDipendente> completati = taskDipendenteService.findTasksDipendenteCompletata(dipendente);

        assertNotNull("La lista dei task completati non dovrebbe essere null", completati);
        assertEquals("Dovrebbe esserci un task completato", 1, completati.size());
        assertEquals("Il task completato non corrisponde", taskDipendente.getId(), completati.getFirst().getId());
    }

    @Test
    public void testIniziaAttivita() {
        em.getTransaction().begin();

        // Crea entità necessarie per il test
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Giovanni rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Completa 2");
        commessa.setReparto(Reparto.CABLAGGIO);
        commessa.setTempoStimato(100L);
        em.persist(commessa);
        
        CommessaInstance commessaInstance= new CommessaInstance();
        commessaInstance.setCommessa(commessa);
        commessaInstance.setId(2L);
        em.persist(commessaInstance);

        Task task = new Task();
        task.setCommessa(commessa);
        task.setCommessaInstance(commessaInstance);
        em.persist(task);

        TaskDipendente taskDipendente = new TaskDipendente();
        taskDipendente.setDipendente(dipendente);
        taskDipendente.setTask(task);
        taskDipendente.setStatus("ASSEGNATA");
        em.persist(taskDipendente);


        em.getTransaction().commit();

        // Avvia l'attività
        taskDipendenteService.iniziaAttività(taskDipendente.getId());

        // Recupera l'oggetto aggiornato
        TaskDipendente aggiornato = em.find(TaskDipendente.class, taskDipendente.getId());
        assertNotNull("Il task dovrebbe essere aggiornato", aggiornato.getInizio());
        assertEquals("Lo stato dovrebbe essere IN_LAVORAZIONE", "IN_LAVORAZIONE", aggiornato.getStatus());
    }

    @Test
    public void testStopAttivita() {
        em.getTransaction().begin();

        // Crea entità necessarie per il test
        // Crea entità necessarie per il test
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Giovanni gialli");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Completa 3");
        commessa.setReparto(Reparto.CABLAGGIO);
        commessa.setTempoStimato(100L);
        em.persist(commessa);
        
        CommessaInstance commessaInstance= new CommessaInstance();
        commessaInstance.setCommessa(commessa);
        commessaInstance.setId(3L);
        em.persist(commessaInstance);

        Task task = new Task();
        task.setCommessa(commessa);
        task.setCommessaInstance(commessaInstance);
        em.persist(task);

        TaskDipendente taskDipendente = new TaskDipendente();
        taskDipendente.setDipendente(dipendente);
        taskDipendente.setTask(task);
        taskDipendente.setStatus("ASSEGNATA");
        em.persist(taskDipendente);

        em.getTransaction().commit();

        // Termina l'attività
        taskDipendenteService.stopAttività(taskDipendente.getId());

        // Recupera l'oggetto aggiornato
        TaskDipendente aggiornato = em.find(TaskDipendente.class, taskDipendente.getId());
        System.out.println("stato   "+aggiornato.getStatus());
        assertNotNull("Il task dovrebbe avere una fine", aggiornato.getFine());
        
        assertEquals("Lo stato dovrebbe essere COMPLETATA", "COMPLETATA", aggiornato.getStatus());
    }
}