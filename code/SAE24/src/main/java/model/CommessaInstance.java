package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommessaInstance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	Commessa commessa;
	Date dataInizio;
	
	
	public CommessaInstance(Commessa c) {
		this.commessa=c;
		
		
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


	public Date getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
}
