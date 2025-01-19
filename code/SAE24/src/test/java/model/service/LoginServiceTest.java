package model.service;

import model.entity.Dipendente;
import model.entity.Manager;
import model.entity.Reparto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class LoginServiceTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private LoginService loginService;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("dip-test");
        em = emf.createEntityManager();
        loginService = new LoginService();
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
    public void testAuthenticateDipendente_Success() {
        em.getTransaction().begin();

        // Crea un dipendente di test
        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("123456");
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("l");
        dipendente.setCognome("f");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        em.getTransaction().commit();

        // Prova ad autenticare
    
       
        Object result = loginService.authenticate("123456", "passwordDipendente");

        assertNotNull("L'autenticazione del dipendente dovrebbe riuscire", result);
        assertTrue("Il risultato dovrebbe essere un'istanza di Dipendente", result instanceof Dipendente);
        assertEquals("La matricola del dipendente autenticato non corrisponde", "123456", ((Dipendente) result).getMatricola());
    }

    @Test
    public void testAuthenticateManager_Success() {
        em.getTransaction().begin();

        // Crea un manager di test
        Manager manager = new Manager();
        manager.setMatricola("54321");
        manager.setPassword("passwordManager");
        manager.setNome("mario");
        manager.setCognome("Rossi");
        manager.setReparto(Reparto.MANAGER);
        em.persist(manager);

        em.getTransaction().commit();

        // Prova ad autenticare
        Object result = loginService.authenticate("54321", "passwordManager");
        assertNotNull("L'autenticazione del manager dovrebbe riuscire", result);
        assertTrue("Il risultato dovrebbe essere un'istanza di Manager", result instanceof Manager);
        assertEquals("La matricola del manager autenticato non corrisponde", "54321", ((Manager) result).getMatricola());
    }

    @Test
    public void testAuthenticate_Fail_WrongPassword() {
        em.getTransaction().begin();

        // Crea un dipendente di test
        Dipendente dipendente = new Dipendente();
        dipendente.setMatricola("12345");
        dipendente.setPassword("passwordDipendente");
        dipendente.setNome("mario");
        dipendente.setCognome("Rossi");
        dipendente.setReparto(Reparto.CABLAGGIO);
        em.persist(dipendente);

        em.getTransaction().commit();

        // Prova ad autenticare con password errata
        Object result = loginService.authenticate("12345", "wrongPassword");

        assertNull("L'autenticazione dovrebbe fallire con una password errata", result);
    }

    @Test
    public void testAuthenticate_Fail_NonExistentUser() {
        // Prova ad autenticare un utente inesistente
        Object result = loginService.authenticate("99999", "passwordNonEsistente");

        assertNull("L'autenticazione dovrebbe fallire per un utente inesistente", result);
    }
}