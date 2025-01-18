package model.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CommessaInstance {
	private static long instanceCounter = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "commessa_id", nullable = false)
	Commessa commessa;
	@Column
	private long instance;
	LocalDate dataInizio;

	public CommessaInstance() {
	}

	public CommessaInstance(Commessa c, long i) {
		this.commessa = c;
		this.instance = i;
		this.dataInizio = LocalDate.now();
	}


	public Commessa getCommessa() {
		return commessa;
	}

	public void setCommessa(Commessa commessa) {
		this.commessa = commessa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public long getInstance() {
		return instance;
	}

	public void setInstance(long instance) {
		this.instance = instance;
	}
	
	
	
}
