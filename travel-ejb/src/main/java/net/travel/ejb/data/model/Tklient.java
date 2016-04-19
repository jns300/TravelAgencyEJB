package net.travel.ejb.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the tklient database table.
 * 
 */
@Entity
@NamedQuery(name = "Tklient.findAll", query = "SELECT t FROM Tklient t")
public class Tklient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDKlienta;

	private boolean bFirma;

	private String email;

	private String haslo;

	// bi-directional many-to-one association to Tfirmy
	@OneToMany(mappedBy = "tklient")
	private List<Tfirmy> tfirmies;

	// bi-directional many-to-one association to Tkliencioferty
	@OneToMany(mappedBy = "tklient")
	private List<Tkliencioferty> tkliencioferties;

	// bi-directional many-to-one association to Tosoby
	@OneToMany(mappedBy = "tklient")
	private List<Tosoby> tosobies;

	// bi-directional many-to-one association to Tklienciatrakcje
	@OneToMany(mappedBy = "tklient")
	private List<Tklienciatrakcje> tklienciatrakcjes;

	// bi-directional many-to-one association to Tklienciatrakcjehistoria
	@OneToMany(mappedBy = "tklient")
	private List<Tklienciatrakcjehistoria> tklienciatrakcjehistorias;

	public Tklient() {
	}

	public Tklient(boolean bFirma, String email, String haslo) {
		super();
		this.bFirma = bFirma;
		this.email = email;
		this.haslo = haslo;
		tosobies = new ArrayList<>();
		tfirmies = new ArrayList<>();
		tkliencioferties = new ArrayList<>();
		tklienciatrakcjes = new ArrayList<>();
		tklienciatrakcjehistorias = new ArrayList<>();
	}

	public int getIDKlienta() {
		return this.IDKlienta;
	}

	public void setIDKlienta(int IDKlienta) {
		this.IDKlienta = IDKlienta;
	}

	public boolean getBFirma() {
		return this.bFirma;
	}

	public void setBFirma(boolean bFirma) {
		this.bFirma = bFirma;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public List<Tfirmy> getTfirmies() {
		return this.tfirmies;
	}

	public void setTfirmies(List<Tfirmy> tfirmies) {
		this.tfirmies = tfirmies;
	}

	public Tfirmy addTfirmy(Tfirmy tfirmy) {
		getTfirmies().add(tfirmy);
		tfirmy.setTklient(this);

		return tfirmy;
	}

	public Tfirmy removeTfirmy(Tfirmy tfirmy) {
		getTfirmies().remove(tfirmy);
		tfirmy.setTklient(null);

		return tfirmy;
	}

	public List<Tkliencioferty> getTkliencioferties() {
		return this.tkliencioferties;
	}

	public void setTkliencioferties(List<Tkliencioferty> tkliencioferties) {
		this.tkliencioferties = tkliencioferties;
	}

	public Tkliencioferty addTkliencioferty(Tkliencioferty tkliencioferty) {
		getTkliencioferties().add(tkliencioferty);
		tkliencioferty.setTklient(this);

		return tkliencioferty;
	}

	public Tkliencioferty removeTkliencioferty(Tkliencioferty tkliencioferty) {
		getTkliencioferties().remove(tkliencioferty);
		tkliencioferty.setTklient(null);

		return tkliencioferty;
	}

	public List<Tosoby> getTosobies() {
		return this.tosobies;
	}

	public void setTosobies(List<Tosoby> tosobies) {
		this.tosobies = tosobies;
	}

	public Tosoby addTosoby(Tosoby tosoby) {
		getTosobies().add(tosoby);
		tosoby.setTklient(this);

		return tosoby;
	}

	public Tosoby removeTosoby(Tosoby tosoby) {
		getTosobies().remove(tosoby);
		tosoby.setTklient(null);

		return tosoby;
	}

	public List<Tklienciatrakcje> getTklienciatrakcjes() {
		return this.tklienciatrakcjes;
	}

	public void setTklienciatrakcjes(List<Tklienciatrakcje> tklienciatrakcjes) {
		this.tklienciatrakcjes = tklienciatrakcjes;
	}

	public Tklienciatrakcje addTklienciatrakcje(Tklienciatrakcje tklienciatrakcje) {
		getTklienciatrakcjes().add(tklienciatrakcje);
		tklienciatrakcje.setTklient(this);

		return tklienciatrakcje;
	}

	public Tklienciatrakcje removeTklienciatrakcje(Tklienciatrakcje tklienciatrakcje) {
		getTklienciatrakcjes().remove(tklienciatrakcje);
		tklienciatrakcje.setTklient(null);

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
		tklienciatrakcjehistoria.setTklient(this);

		return tklienciatrakcjehistoria;
	}

	public Tklienciatrakcjehistoria removeTklienciatrakcjehistoria(
			Tklienciatrakcjehistoria tklienciatrakcjehistoria) {
		getTklienciatrakcjehistorias().remove(tklienciatrakcjehistoria);
		tklienciatrakcjehistoria.setTklient(null);

		return tklienciatrakcjehistoria;
	}

}