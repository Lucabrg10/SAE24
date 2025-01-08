package model;
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
    @JoinColumn(name = "dipendente_id")
    private long idCommessaInstance;

    public Task() {}
    
  
    public Task(Commessa commessa2, long id2) {
    	
    	
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
   
}

       
