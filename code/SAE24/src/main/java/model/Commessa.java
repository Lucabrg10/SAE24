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
    private String descrizione;
    public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	@Enumerated(EnumType.STRING)
	private Reparto reparto;
    private String tempoStimato;
    private String tempoCalcolato;

    @ManyToOne
    @JoinColumn(name = "commessa_padre_id")
    private Commessa commessaPadre;

    @OneToMany(mappedBy = "commessaPadre",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commessa> commesseFiglie = new ArrayList<>();

    public Commessa() {}

    public Commessa(String nome, Reparto reparto, String tempoStimato, String tempoCalcolato) {
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

    public Reparto getReparto() {
        return reparto;
    }

    public void setReparto(Reparto reparto) {
        this.reparto = reparto;
    }

    public String getTempoStimato() {
        return tempoStimato;
    }

    public void setTempoStimato(String tempoStimato) {
        this.tempoStimato = tempoStimato;
    }

    public String getTempoCalcolato() {
        return tempoCalcolato;
    }

    public void setTempoCalcolato(String tempoCalcolato) {
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
    }

    public void removeCommessaFiglia(Commessa commessaFiglia) {
        commesseFiglie.remove(commessaFiglia);
    }

    @Override
    public String toString() {
        return nome;
    }
}
