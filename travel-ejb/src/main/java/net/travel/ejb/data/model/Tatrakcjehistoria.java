package net.travel.ejb.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the tatrakcjehistoria database table.
 * 
 */
@Entity
@NamedQuery(name = "Tatrakcjehistoria.findAll", query = "SELECT t FROM Tatrakcjehistoria t")
public class Tatrakcjehistoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDAtrakcjiUslugi;

	private int iLiczbaOsob;

	private BigDecimal mCena;

	private String nazwa;

	private String opis;

	// bi-directional many-to-one association to Thistoriaofert
	@ManyToOne
	@JoinColumn(name = "IDOferty")
	private Thistoriaofert thistoriaofert;

	// bi-directional many-to-one association to Tklienciatrakcjehistoria
	@OneToMany(mappedBy = "tatrakcjehistoria", cascade = { CascadeType.PERSIST }, fetch=FetchType.EAGER)
	private List<Tklienciatrakcjehistoria> tklienciatrakcjehistorias;

	public Tatrakcjehistoria() {

	}

	public Tatrakcjehistoria(Tatrakcjeuslugi attraction) {
		iLiczbaOsob = attraction.getILiczbaOsob();
		mCena = attraction.getMCena();
		nazwa = attraction.getNazwa();
		opis = attraction.getOpis();
		tklienciatrakcjehistorias = new ArrayList<>();
		if (attraction.getTklienciatrakcjes().size() > 0) {
			for (Tklienciatrakcje client : attraction.getTklienciatrakcjes()) {
				addTklienciatrakcjehistoria(new Tklienciatrakcjehistoria(client));
			}
		}
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

	public Thistoriaofert getThistoriaofert() {
		return this.thistoriaofert;
	}

	public void setThistoriaofert(Thistoriaofert thistoriaofert) {
		this.thistoriaofert = thistoriaofert;
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
		tklienciatrakcjehistoria.setTatrakcjehistoria(this);

		return tklienciatrakcjehistoria;
	}

	public Tklienciatrakcjehistoria removeTklienciatrakcjehistoria(
			Tklienciatrakcjehistoria tklienciatrakcjehistoria) {
		getTklienciatrakcjehistorias().remove(tklienciatrakcjehistoria);
		tklienciatrakcjehistoria.setTatrakcjehistoria(null);

		return tklienciatrakcjehistoria;
	}

}