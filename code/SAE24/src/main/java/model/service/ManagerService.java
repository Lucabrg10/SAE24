package model.service;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.entity.Commessa;
import model.entity.Dipendente;
import model.entity.Manager;
import model.entity.Reparto;

import java.util.ArrayList;
import java.util.List;

public class ManagerService extends DipendenteService {

	public ManagerService(String utilizzo) {
		super(utilizzo);
	}

	public String addDipendente(String nome, String cognome, String matricola, Reparto reparto)
			throws IllegalStateException {

		Dipendente dipendente = null;
		if (reparto.toString().equals("MANAGER")) {
			dipendente = new Manager();
		} else {
			dipendente = new Dipendente();
		}

		dipendente.setNome(nome);
		dipendente.setCognome(cognome);
		dipendente.setMatricola(matricola);
		dipendente.setPassword(matricola);
		dipendente.setReparto(reparto);

		this.entityManager.getTransaction().begin();
		try {
			entityManager.persist(dipendente);
			entityManager.getTransaction().commit();
		} catch (PersistenceException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback(); // Fai il rollback in caso di errore
			}

			return ("Errore: la matricola non è univoca o si è verificato un altro errore.");

		}
		return null;
	}

	/**
	 * Recupera tutti i dipendenti (inclusi i manager).
	 *
	 * @return Lista di dipendenti.
	 */
	public List<Dipendente> getAllDipendenti() {
		TypedQuery<Dipendente> query = entityManager.createQuery("SELECT e FROM Dipendente e", Dipendente.class);
		return query.getResultList();
	}

	public List<Commessa> getAllCommesse() {
		TypedQuery<Commessa> query = entityManager.createQuery("SELECT e FROM Commessa e", Commessa.class);
		return query.getResultList();
	}

	public List<Commessa> getAllCommessePrincipali() {
		TypedQuery<Commessa> query = entityManager.createQuery("SELECT e FROM Commessa e WHERE e.commessaPadre IS NULL",
				Commessa.class);
		return query.getResultList();
	}

	public void deleteDipendente(Long long1) {
		// Inizia una transazione

		try {
			entityManager.getTransaction().begin();

			// Trova il Dipendente in base all'ID
			Dipendente dipendente = entityManager.find(Dipendente.class, long1);
			if (dipendente != null) {
				entityManager.remove(dipendente);
			} 
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			entityManager.close(); // Chiudere l'EntityManager
		}
	}

	public List<Commessa> getListOfSubCommesse(Commessa selectedItem) {
		List<Commessa> result = new ArrayList<>();
		fetchSubCommesseRecursive(selectedItem, result);
		return result;
	}

	private void fetchSubCommesseRecursive(Commessa parent, List<Commessa> result) {
		String jpql = "SELECT c FROM Commessa c WHERE c.commessaPadre = :parent";
		TypedQuery<Commessa> query = entityManager.createQuery(jpql, Commessa.class);
		query.setParameter("parent", parent);

		List<Commessa> subCommesse = query.getResultList();
		for (Commessa subCommessa : subCommesse) {
			result.add(subCommessa); // Aggiunge la sottocommessa alla lista
			fetchSubCommesseRecursive(subCommessa, result); // Chiama il metodo ricorsivamente
		}
	}
}
