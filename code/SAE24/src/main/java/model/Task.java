package model;
import javax.persistence.*;

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
    private Dipendente dipendente;
    private String stato; 
    private Double tempoEffettivo;
    public Task() {}

    public Task(Commessa commessa, Dipendente dipendente, String stato, Double tempoEffettivo) {
        this.commessa = commessa;
        this.dipendente = dipendente;
        this.stato = stato;
        this.tempoEffettivo = tempoEffettivo;
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

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Double getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(Double tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", commessa=" + commessa.getId() +
                ", dipendente=" + dipendente.getId() +
                ", stato='" + stato + '\'' +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}
