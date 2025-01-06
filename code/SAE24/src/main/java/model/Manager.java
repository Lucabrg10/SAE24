package model;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("M")
public class Manager extends Dipendente {

	@Transient
	private List<Dipendente> dipendenti;
	@Transient
	private List<Commessa> commesse;
	public Manager() {
		super();
	}

	public List<Dipendente> getDipendenti() {
		return dipendenti;
	}

    public void setDipendenti(List<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }
}
