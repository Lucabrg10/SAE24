package model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "commessa_id")
	private Commessa commessa;
	@ManyToOne
	@JoinColumn(name = "commessa_instance_id", nullable = false) // Colonna nel DB
	private CommessaInstance commessaInstance;

	public Task() {
	}

	public Task(Commessa commessa2, CommessaInstance id2) {
		this.commessa = commessa2;
		this.commessaInstance = id2;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Commessa getCommessa() {
		return commessa;
	}

	public void setCommessa(Commessa commessa) {
		this.commessa = commessa;
	}

	public CommessaInstance getCommessaInstance() {
		return commessaInstance;
	}

	public void setCommessaInstance(CommessaInstance commessaInstance) {
		this.commessaInstance = commessaInstance;
	}

	@Override
	public String toString() {
		return commessa.getNome();
	}

}
