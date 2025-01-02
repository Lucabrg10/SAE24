package model;

import model.Dipendente;
import model.Manager;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class LoginService {

    private EntityManager entityManager = Persistence.createEntityManagerFactory("dip").createEntityManager();

    public LoginService() {
        
    }

    public Object authenticate(String matricola, String password) {
        // Verifica prima se è un dipendente
        Dipendente dipendente = findDipendenteByMatricola(matricola);
        if (dipendente != null ) {
        	if(dipendente.getPassword().equals(password)) {
				return dipendente; // Autenticazione riuscita per dipendente
			}else
				return null;
        }

        // Se non è un dipendente, verifica se è un manager
        Manager manager = findManagerByMatricola(matricola);
        if (manager != null ) {
        	if(manager.getPassword().equals(password)) {
				return manager; // Autenticazione riuscita per manager
			}else
				return null;
        }
        // Se nessuna delle due autenticazioni ha avuto successo, ritorna null
        return null;
    }

    private Dipendente findDipendenteByMatricola(String matricola) {
        TypedQuery<Dipendente> query = entityManager.createQuery("SELECT d FROM Dipendente d WHERE d.matricola = :matricola", Dipendente.class);
        query.setParameter("matricola", matricola);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    private Manager findManagerByMatricola(String matricola) {
        TypedQuery<Manager> query = entityManager.createQuery("SELECT d FROM Dipendente d WHERE d.matricola = :matricola", Manager.class);
        query.setParameter("matricola", matricola);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}