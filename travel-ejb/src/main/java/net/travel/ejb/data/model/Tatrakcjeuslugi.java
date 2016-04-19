package net.travel.ejb.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the tatrakcjeuslugi database table.
 * 
 */
@Entity
@NamedQuery(name = "Tatrakcjeuslugi.findAll", query = "SELECT t FROM Tatrakcjeuslugi t")
public class Tatrakcjeuslugi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDAtrakcjiUslugi;

	private int iLiczbaOsob;

	private BigDecimal mCena;

	private String nazwa;

	private String opis;

	// bi-directional many-to-one association to Toferta
	@ManyToOne
	@JoinColumn(name = "IDOferty")
	private Toferta toferta;

	// bi-directional many-to-one association to Tklienciatrakcje
	@OneToMany(mappedBy = "tatrakcjeuslugi", fetch = FetchType.EAGER, cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Tklienciatrakcje> tklienciatrakcjes;

	public Tatrakcjeuslugi() {
	}

	public Tatrakcjeuslugi(int iLiczbaOsob, BigDecimal mCena, String nazwa, String opis) {
		super();
		tklienciatrakcjes = new ArrayList<>();
		this.iLiczbaOsob = iLiczbaOsob;
		this.mCena = mCena;
		this.nazwa = nazwa;
		this.opis = opis;
	}

	public int getIDAtrakcjiUslugi() {
		return this.IDAtrakcjiUslugi;
	}

	public void setIDAtrakcjiUslugi(int IDAtrakcjiUslugi) {
		this.IDAtrakcjiUslugi = IDAtrakcjiUslugi;
	}

	public int getILiczbaOsob() {
		return this.iLiczbaOsob;
	}

	public void setILiczbaOsob(int iLiczbaOsob) {
		this.iLiczbaOsob = iLiczbaOsob;
	}

	public BigDecimal getMCena() {
		return this.mCena;
	}

	public void setMCena(BigDecimal mCena) {
		this.mCena = mCena;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Toferta getToferta() {
		return this.toferta;
	}

	public void setToferta(Toferta toferta) {
		this.toferta = toferta;
	}

	public List<Tklienciatrakcje> getTklienciatrakcjes() {
		return this.tklienciatrakcjes;
	}

	public void setTklienciatrakcjes(List<Tklienciatrakcje> tklienciatrakcjes) {
		this.tklienciatrakcjes = tklienciatrakcjes;
	}

	public Tklienciatrakcje addTklienciatrakcje(Tklienciatrakcje tklienciatrakcje) {
		getTklienciatrakcjes().add(tklienciatrakcje);
		tklienciatrakcje.setTatrakcjeuslugi(this);

		return tklienciatrakcje;
	}

	public Tklienciatrakcje removeTklienciatrakcje(Tklienciatrakcje tklienciatrakcje) {
		getTklienciatrakcjes().remove(tklienciatrakcje);
		tklienciatrakcje.setTatrakcjeuslugi(null);

		return tklienciatrakcje;
	}
}