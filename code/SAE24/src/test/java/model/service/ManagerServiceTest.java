package model.service;

import model.entity.Commessa;
import model.entity.Dipendente;
import model.entity.Manager;
import model.entity.Reparto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class ManagerServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private ManagerService managerService;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("dip-test");
        em = emf.createEntityManager();
        managerService = new ManagerService("test");
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
    public void testGetAllCommesse() {
  
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Commessa").executeUpdate();
        Commessa commessa1 = new Commessa();
        commessa1.setNome("Commessa 1");
        commessa1.setReparto(Reparto.CABLAGGIO);
        commessa1.setTempoStimato(100L);
        em.persist(commessa1);

        Commessa commessa2 = new Commessa();
        commessa2.setNome("Commessa 2");
        commessa2.setReparto(Reparto.CABLAGGIO);
        commessa2.setTempoStimato(100L);
        em.persist(commessa2);

        em.getTransaction().commit();
        
        em.clear();
        List<Commessa> commesse = managerService.getAllCommesse();

        assertNotNull("La lista delle commesse non dovrebbe essere null", commesse);
        assertEquals("Il numero di commesse nel database non è corretto", 2, commesse.size());
    }
    @Test
    public void testAddDipendente_Success() {
        String nome = "Mario";
        String cognome = "Rossi";
        String matricola = "12345";
        Reparto reparto = Reparto.CABLAGGIO;

        String risultato = managerService.addDipendente(nome, cognome, matricola, reparto);

        assertNull("L'aggiunta del dipendente dovrebbe avere successo", risultato);

        // Verifica che il dipendente sia stato salvato nel database
        Dipendente dipendente = em.createQuery("SELECT d FROM Dipendente d WHERE d.matricola = :matricola", Dipendente.class)
                .setParameter("matricola", matricola).getSingleResult();

        assertNotNull("Il dipendente dovrebbe essere trovato nel database", dipendente);
        assertEquals("Il nome del dipendente non corrisponde", nome, dipendente.getNome());
        assertEquals("Il cognome del dipendente non corrisponde", cognome, dipendente.getCognome());
    }

    @Test
    public void testAddDipendente_Fail_DuplicateMatricola() {
        em.getTransaction().begin();
        Dipendente existingDipendente = new Dipendente();
        existingDipendente.setNome("Luigi");
        existingDipendente.setCognome("Bianchi");
        existingDipendente.setMatricola("54321");
        em.persist(existingDipendente);
        em.getTransaction().commit();

        String risultato = managerService.addDipendente("Marco", "Verdi", "54321", Reparto.MONTAGGIO);

        assertEquals("Dovrebbe essere restituito un messaggio di errore per matricola duplicata",
                "Errore: la matricola non è univoca o si è verificato un altro errore.", risultato);
    }

    @Test
    public void testGetAllDipendenti() {
        em.getTransaction().begin();

        Dipendente dipendente1 = new Dipendente();
        dipendente1.setNome("Alessandro");
        dipendente1.setMatricola("001");
        em.persist(dipendente1);

        Manager manager = new Manager();
        manager.setNome("Federica");
        manager.setMatricola("002");
        em.persist(manager);

        em.getTransaction().commit();

        List<Dipendente> dipendenti = managerService.getAllDipendenti();

        assertNotNull("La lista dei dipendenti non dovrebbe essere null", dipendenti);
        assertEquals("Il numero di dipendenti nel database non è corretto", 2, dipendenti.size());
    }



    @Test
    public void testGetAllCommessePrincipali() {
        em.getTransaction().begin();

        Commessa principale = new Commessa();
        principale.setNome("Commessa Principale");
        em.persist(principale);

        Commessa subCommessa = new Commessa();
        subCommessa.setNome("Commessa Figlia");
        subCommessa.setCommessaPadre(principale);
        em.persist(subCommessa);

        em.getTransaction().commit();

        List<Commessa> principali = managerService.getAllCommessePrincipali();

        assertNotNull("La lista delle commesse principali non dovrebbe essere null", principali);
        assertEquals("Il numero di commesse principali nel database non è corretto", 1, principali.size());
    }

    @Test
    public void testDeleteDipendente_Success() {
        em.getTransaction().begin();

        Dipendente dipendente = new Dipendente();
        dipendente.setNome("Giulia");
        dipendente.setMatricola("99999");
        em.persist(dipendente);

        em.getTransaction().commit();

        managerService.deleteDipendente(dipendente.getId());
        em.clear();
        Dipendente deletedDipendente = em.find(Dipendente.class, dipendente.getId());


        assertNull("Il dipendente dovrebbe essere eliminato", deletedDipendente);
    }

    @Test
    public void testGetListOfSubCommesse() {
        em.getTransaction().begin();

        Commessa principale = new Commessa();
        principale.setNome("Commessa Principale");
        em.persist(principale);

        Commessa subCommessa1 = new Commessa();
        subCommessa1.setNome("SubCommessa 1");
        subCommessa1.setCommessaPadre(principale);
        em.persist(subCommessa1);

        Commessa subCommessa2 = new Commessa();
        subCommessa2.setNome("SubCommessa 2");
        subCommessa2.setCommessaPadre(subCommessa1);
        em.persist(subCommessa2);

        em.getTransaction().commit();

        List<Commessa> subCommesse = managerService.getListOfSubCommesse(principale);

        assertNotNull("La lista delle sottocommesse non dovrebbe essere null", subCommesse);
        assertEquals("Il numero di sottocommesse non è corretto", 2, subCommesse.size());
    }
    

}
