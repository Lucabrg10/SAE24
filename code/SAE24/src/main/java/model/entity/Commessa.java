package model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Commessa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descrizione;

	@Enumerated(EnumType.STRING)
	private Reparto reparto;
	private Long tempoStimato;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "commessa_padre_id")
	private Commessa commessaPadre;

	@OneToMany(mappedBy = "commessaPadre", cascade = CascadeType.ALL)
	private List<Commessa> commesseFiglie = new ArrayList<>();

	public Commessa() {
	}

	public Commessa(String nome, Reparto reparto, Long tempoStimato) {
		this.nome = nome;
		this.reparto = reparto;
		this.tempoStimato = tempoStimato;
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

	public Long getTempoStimato() {
		return tempoStimato;
	}

	public void setTempoStimato(Long tempoStimato) {
		this.tempoStimato = tempoStimato;
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false; 
		Commessa commessa = (Commessa) obj;
		return Objects.equals(id, commessa.id); // Confronto basato su ID
	}
	@Override
    public int hashCode() {
        return Objects.hash(id); // Calcolo dell'hash basato su ID
    }
}
