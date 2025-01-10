package model.entity;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("D")
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    private String cognome;
    
    @Column(unique=true)
    private String matricola;
    
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Reparto reparto;
    
    public Dipendente() {}
    
    //serve per test CommessaService assegnatasks
    public Dipendente(String string, Reparto reparto) {
		this.nome=string;
		this.reparto=reparto;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Reparto getReparto() {
		return reparto;
	}

	public void setReparto(Reparto string) {
		this.reparto = string;
	}
	@Override
	public String toString() {
		return (nome +" "+cognome);
	}
}
