package model.service;

import model.entity.Dipendente;
import model.entity.Reparto;
import model.entity.Task;
import model.entity.TaskDipendente;
import model.entity.Commessa;
import model.entity.CommessaInstance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class TerminaTurnoServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private TerminaTurnoService terminaTurnoService;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("dip-test");
        em = emf.createEntityManager();
        terminaTurnoService = new TerminaTurnoService("dip-test");
    }

    @After
    public void tearDown() {
        terminaTurnoService.close();
        if (em.isOpen()) {
            em.close();
        }
        if (emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testFindTasksInRange_Success() {
        em.getTransaction().begin();

      

       
        

 
        
        
        
        
        // Crea un dipendente
        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Mario Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
   
        em.persist(dipendente);

        // Crea una commessa e una CommessaInstance
        Commessa commessa = new Commessa();
        commessa.setNome("Commessa Test");
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


        // Crea alcuni TaskDipendente
        TaskDipendente task1 = new TaskDipendente();
        task1.setTask(task);
        task1.setDipendente(dipendente);
        task1.setStatus("COMPLETATA");
        task1.setInizio(LocalDateTime.now().minusHours(3));
        task1.setFine(LocalDateTime.now().minusHours(1));
        em.persist(task1);

        TaskDipendente task2 = new TaskDipendente();
        task2.setTask(task);
        task2.setDipendente(dipendente);
        task2.setStatus("COMPLETATA");
        task2.setInizio(LocalDateTime.now().minusHours(2));
        task2.setFine(LocalDateTime.now());
        em.persist(task2);

        em.getTransaction().commit();

        // Chiama il metodo da testare
        List<TaskDipendente> tasksInRange = terminaTurnoService.findTasksInRange(dipendente);

        // Verifica che il risultato sia corretto
        assertNotNull("La lista dei task non dovrebbe essere null", tasksInRange);
        assertEquals("Il numero di task restituiti non è corretto", 2, tasksInRange.size());
    }

    @Test
    public void testFindTasksInRange_InvalidDipendente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            terminaTurnoService.findTasksInRange(null);
        });

        assertEquals("Il dipendente o il suo ID non può essere null.", exception.getMessage());
    }

    @Test
    public void testAggiornaTaskDipendente_Success() {
        em.getTransaction().begin();

        // Crea un TaskDipendente
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


        TaskDipendente taskDipendente = new TaskDipendente();
        taskDipendente.setDipendente(dipendente);
        taskDipendente.setTask(task);
        taskDipendente.setStatus("IN_LAVORAZIONE");
        taskDipendente.setInizio(LocalDateTime.now().minusHours(2));
        taskDipendente.setFine(null);
        em.persist(taskDipendente);

        em.getTransaction().commit();

        // Aggiorna i dettagli del task
        taskDipendente.setFine(LocalDateTime.now());

        terminaTurnoService.aggiornaTaskDipendente(taskDipendente);

        // Recupera il task aggiornato
        TaskDipendente updatedTask = em.find(TaskDipendente.class, taskDipendente.getId());

        // Verifica che il task sia stato aggiornato correttamente
        assertNotNull("Il task aggiornato non dovrebbe essere null", updatedTask);
        assertEquals("Lo stato del task non è corretto", taskDipendente.getFine(), updatedTask.getFine());
    }

    @Test
    public void testAggiornaTaskDipendente_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            terminaTurnoService.aggiornaTaskDipendente(null);
        });

        assertEquals("Il task o il suo ID non può essere null.", exception.getMessage());
    }

    @Test
    public void testAggiornaTaskDipendente_TaskSenzaId() {
        TaskDipendente taskDipendente = new TaskDipendente();
        taskDipendente.setFine(LocalDateTime.now());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            terminaTurnoService.aggiornaTaskDipendente(taskDipendente);
        });

        assertEquals("Il task o il suo ID non può essere null.", exception.getMessage());
    }
}