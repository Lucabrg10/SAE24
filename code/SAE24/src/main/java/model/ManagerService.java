package model;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ManagerService {

    private final EntityManager entityManager;

    public ManagerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Aggiungi un nuovo dipendente.
     *
     * @param name Nome del dipendente.
     */
    public void addEmployee(String nome, String cognome, String matricola, String reparto) {
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(nome);
        dipendente.setCognome(cognome);
        dipendente.setMatricola(matricola);
        dipendente.setPassword(matricola);
        dipendente.setReparto(reparto);

        entityManager.getTransaction().begin();
        entityManager.persist(dipendente);
        entityManager.getTransaction().commit();
    }

    /**
     * Aggiungi un nuovo manager.
     *
     * @param name Nome del manager.
     */
    public void addManager(String nome, String cognome, String matricola, String reparto) {
        Manager manager = new Manager();
        manager.setNome(nome);
        manager.setCognome(cognome);
        manager.setMatricola(matricola);
        manager.setReparto("manager");
        manager.setPassword(matricola);

        entityManager.getTransaction().begin();
        entityManager.persist(manager);
        entityManager.getTransaction().commit();
    }

    /**
     * Recupera tutti i dipendenti (inclusi i manager).
     *
     * @return Lista di dipendenti.
     */
    public List<Dipendente> getAllDipendenti() {
        TypedQuery<Dipendente> query = entityManager.createQuery("SELECT e FROM Employee e", Dipendente.class);
        return query.getResultList();
    }

    /**
     * Recupera tutte le task assegnate a un dipendente.
     *
     * @param employeeId ID del dipendente.
     * @return Lista di task del dipendente.
     */
    public List<Task> getTasksByDipendente(Long dipendenteId) {
        TypedQuery<Task> query = entityManager.createQuery(
            "SELECT t FROM Task t WHERE t.dipendente.id = :dipendenteId", Task.class
        );
        query.setParameter("dipendenteId", dipendenteId);
        return query.getResultList();
    }
}
