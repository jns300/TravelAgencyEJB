package net.travel.ejb.data.model;

import java.io.Serializable;
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
 * The persistent class for the tosoby database table.
 * 
 */
@Entity
@NamedQuery(name = "Tosoby.findAll", query = "SELECT t FROM Tosoby t")
public class Tosoby implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDOsoby;

	private boolean bPracownik;

	private String imie;

	private String nazwisko;

	private String nip;

	// bi-directional many-to-one association to Tkliencioferty
	@OneToMany(mappedBy = "tosoby")
	private List<Tkliencioferty> tkliencioferties;

	// bi-directional many-to-one association to Tadresy
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "IDAdresu")
	private Tadresy tadresy;

	// bi-directional many-to-one association to Tklient
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "IDKlienta")
	private Tklient tklient;

	// bi-directional many-to-one association to Tklienciatrakcje
	@OneToMany(mappedBy = "tosoby", cascade = { CascadeType.REMOVE })
	private List<Tklienciatrakcje> tklienciatrakcjes;

	// bi-directional many-to-one association to Tklienciatrakcjehistoria
	@OneToMany(mappedBy = "tosoby", cascade = { CascadeType.REMOVE })
	private List<Tklienciatrakcjehistoria> tklienciatrakcjehistorias;

	// bi-directional many-to-one association to Tklienciofertyhistoria
	@OneToMany(mappedBy = "tosoby", cascade = { CascadeType.REMOVE })
	private List<Tklienciofertyhistoria> tklienciofertyhistorias;

	public Tosoby() {
	}

	public Tosoby(boolean bPracownik, String imie, String nazwisko, String nip, Tadresy tadresy,
			Tklient tklient) {
		super();
		this.bPracownik = bPracownik;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.nip = nip;
		tadresy.addTosoby(this);
		tklient.addTosoby(this);
	}

	public int getIDOsoby() {
		return this.IDOsoby;
	}

	public void setIDOsoby(int IDOsoby) {
		this.IDOsoby = IDOsoby;
	}

	public boolean getBPracownik() {
		return this.bPracownik;
	}

	public void setBPracownik(boolean bPracownik) {
		this.bPracownik = bPracownik;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public List<Tkliencioferty> getTkliencioferties() {
		return this.tkliencioferties;
	}

	public void setTkliencioferties(List<Tkliencioferty> tkliencioferties) {
		this.tkliencioferties = tkliencioferties;
	}

	public Tkliencioferty addTkliencioferty(Tkliencioferty tkliencioferty) {
		getTkliencioferties().add(tkliencioferty);
		tkliencioferty.setTosoby(this);

		return tkliencioferty;
	}

	public Tkliencioferty removeTkliencioferty(Tkliencioferty tkliencioferty) {
		getTkliencioferties().remove(tkliencioferty);
		tkliencioferty.setTosoby(null);

		return tkliencioferty;
	}

	public Tadresy getTadresy() {
		return this.tadresy;
	}

	public void setTadresy(Tadresy tadresy) {
		this.tadresy = tadresy;
	}

	public Tklient getTklient() {
		return this.tklient;
	}

	public void setTklient(Tklient tklient) {
		this.tklient = tklient;
	}

	public List<Tklienciatrakcje> getTklienciatrakcjes() {
		return this.tklienciatrakcjes;
	}

	public void setTklienciatrakcjes(List<Tklienciatrakcje> tklienciatrakcjes) {
		this.tklienciatrakcjes = tklienciatrakcjes;
	}

	public Tklienciatrakcje addTklienciatrakcje(Tklienciatrakcje tklienciatrakcje) {
		getTklienciatrakcjes().add(tklienciatrakcje);
		tklienciatrakcje.setTosoby(this);

		return tklienciatrakcje;
	}

	public Tklienciatrakcje removeTklienciatrakcje(Tklienciatrakcje tklienciatrakcje) {
		getTklienciatrakcjes().remove(tklienciatrakcje);
		tklienciatrakcje.setTosoby(null);

		return tklienciatrakcje;
	}

	public List<Tklienciatrakcjehistoria> getTklienciatrakcjehistorias() {
		return this.tklienciatrakcjehistorias;
	}

	public void setTklienciatrakcjehistorias(
			List<Tklienciatrakcjehistoria> tklienciatrakcjehistorias) {
		this.tklienciatrakcjehistorias = tklienciatrakcjehistorias;
	}

	public Tklienciatrakcjehistoria addTklienciatrakcjehistoria(
			Tklienciatrakcjehistoria tklienciatrakcjehistoria) {
		getTklienciatrakcjehistorias().add(tklienciatrakcjehistoria);
		tklienciatrakcjehistoria.setTosoby(this);

		return tklienciatrakcjehistoria;
	}

	public Tklienciatrakcjehistoria removeTklienciatrakcjehistoria(
			Tklienciatrakcjehistoria tklienciatrakcjehistoria) {
		getTklienciatrakcjehistorias().remove(tklienciatrakcjehistoria);
		tklienciatrakcjehistoria.setTosoby(null);

		return tklienciatrakcjehistoria;
	}

	public List<Tklienciofertyhistoria> getTklienciofertyhistorias() {
		return this.tklienciofertyhistorias;
	}

	public void setTklienciofertyhistorias(List<Tklienciofertyhistoria> tklienciofertyhistorias) {
		this.tklienciofertyhistorias = tklienciofertyhistorias;
	}

	public Tklienciofertyhistoria addTklienciofertyhistoria(
			Tklienciofertyhistoria tklienciofertyhistoria) {
		getTklienciofertyhistorias().add(tklienciofertyhistoria);
		tklienciofertyhistoria.setTosoby(this);

		return tklienciofertyhistoria;
	}

	public Tklienciofertyhistoria removeTklienciofertyhistoria(
			Tklienciofertyhistoria tklienciofertyhistoria) {
		getTklienciofertyhistorias().remove(tklienciofertyhistoria);
		tklienciofertyhistoria.setTosoby(null);

		return tklienciofertyhistoria;
	}

}