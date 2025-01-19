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

import static org.junit.Assert.*;

import java.time.LocalDateTime;

public class TaskServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private TaskService taskService;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("dip-test");
        em = emf.createEntityManager();
        taskService = new TaskService("test");
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
    public void testSalvaTask() {
        em.getTransaction().begin();

        // Crea un Task di test
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
        commessaInstance.setId(6L);
        em.persist(commessaInstance);
        
    em.getTransaction().commit();
        Task task = new Task();
        task.setCommessa(commessa);
        task.setCommessaInstance(commessaInstance);


    

        // Salva il Task
        taskService.salvaTask(task);

        // Verifica che sia stato salvato correttamente
        Task trovato = em.find(Task.class, task.getId());
        assertNotNull("Il task dovrebbe essere stato salvato nel database.", trovato);
        assertEquals("L'ID del task salvato non corrisponde.", task.getId(), trovato.getId());
    }
}