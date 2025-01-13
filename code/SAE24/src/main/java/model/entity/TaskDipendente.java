package model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TaskDipendente {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automaticamente l'ID
    private Long id;

	@ManyToOne
    @JoinColumn(name = "task_id", nullable = false) // Collega alla tabella Task
    private Task task;

    @ManyToOne
    @JoinColumn(name = "dipendente_id", nullable = false) // Collega alla tabella Dipendente
    private Dipendente dipendente;

    @Column
    private String status;
    private LocalDateTime inizio;
    private LocalDateTime fine;
    
	public TaskDipendente() {}
    
	public TaskDipendente(Task t, Dipendente d) {
		this.task=t;
		this.dipendente=d;
		this.status="ASSEGNATA";
	}
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task t) {
		this.task = t;
	}
	public Dipendente getDipendente() {
		return dipendente;
	}
	public void setDipendente(Dipendente d) {
		this.dipendente = d;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

    public Long getId() {
		return id;
	}

	
}
