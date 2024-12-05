package model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commessa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String reparto;
    private Double tempoStimato;
    private Double tempoCalcolato;

    @ManyToOne
    @JoinColumn(name = "commessa_padre_id")
    private Commessa commessaPadre;

    @OneToMany(mappedBy = "commessaPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commessa> commesseFiglie = new ArrayList<>();

    public Commessa() {}

    public Commessa(String nome, String reparto, Double tempoStimato, Double tempoCalcolato) {
        this.nome = nome;
        this.reparto = reparto;
        this.tempoStimato = tempoStimato;
        this.tempoCalcolato = tempoCalcolato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public Double getTempoStimato() {
        return tempoStimato;
    }

    public void setTempoStimato(Double tempoStimato) {
        this.tempoStimato = tempoStimato;
    }

    public Double getTempoCalcolato() {
        return tempoCalcolato;
    }

    public void setTempoCalcolato(Double tempoCalcolato) {
        this.tempoCalcolato = tempoCalcolato;
    }

    public Commessa getCommessaPadre() {
        return commessaPadre;
    }

    public void setCommessaPadre(Commessa commessaPadre) {
        this.commessaPadre = commessaPadre;
    }

    public List<Commessa> getCommesseFiglie() {
        return commesseFiglie;
    }

    public void addCommessaFiglia(Commessa commessaFiglia) {
        commesseFiglie.add(commessaFiglia);
        commessaFiglia.setCommessaPadre(this);
    }

    public void removeCommessaFiglia(Commessa commessaFiglia) {
        commesseFiglie.remove(commessaFiglia);
        commessaFiglia.setCommessaPadre(null);
    }

    @Override
    public String toString() {
        return "Commessa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", reparto='" + reparto + '\'' +
                ", tempoStimato=" + tempoStimato +
                ", tempoCalcolato=" + tempoCalcolato +
                '}';
    }
}
