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
    private Dipendente dipendente;

    private String stato; 
    private Double tempoEffettivo; 
    private LocalDateTime orarioInizio; 
    private LocalDateTime orarioFine;   
    private LocalDate data;             

    public Task() {}

    public Task(Commessa commessa, Dipendente dipendente, String stato, LocalDateTime orarioInizio, LocalDateTime orarioFine, LocalDate data) {
        this.commessa = commessa;
        this.dipendente = dipendente;
        this.stato = stato;
        this.orarioInizio = orarioInizio;
        this.orarioFine = orarioFine;
        this.data = data;
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
    public LocalDateTime getOrarioInizio() {
        return orarioInizio;
    }
    public void setOrarioInizio(LocalDateTime orarioInizio) {
        this.orarioInizio = orarioInizio;
    }
    public LocalDateTime getOrarioFine() {
        return orarioFine;
    }
    public void setOrarioFine(LocalDateTime orarioFine) {
        this.orarioFine = orarioFine;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", commessa=" + (commessa != null ? commessa.getId() : null) +
                ", dipendente=" + (dipendente != null ? dipendente.getId() : null) +
                ", stato='" + stato + '\'' +
                ", tempoEffettivo=" + tempoEffettivo +
                ", orarioInizio=" + orarioInizio +
                ", orarioFine=" + orarioFine +
                ", data=" + data +
                '}';
    }
}

       
