package net.travel.ejb.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the tpanstwa database table.
 * 
 */
@Entity
@NamedQuery(name = "Tpanstwa.findAll", query = "SELECT t FROM Tpanstwa t")
public class Tpanstwa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDPanstwa;

	private String nazwaPanstwa;

	// bi-directional many-to-one association to Tadresy
	@OneToMany(mappedBy = "tpanstwa")
	private List<Tadresy> tadresies;

	// bi-directional many-to-one association to Toferta
	@OneToMany(mappedBy = "tpanstwa")
	private List<Toferta> tofertas;
	
	@OneToMany(mappedBy = "tpanstwa")
	private List<Thistoriaofert> thistoriaofertas;

	public Tpanstwa() {
	}

	public Tpanstwa(String nazwaPanstwa) {
		super();
		this.nazwaPanstwa = nazwaPanstwa;
	}

	public int getIDPanstwa() {
		return this.IDPanstwa;
	}

	public void setIDPanstwa(int IDPanstwa) {
		this.IDPanstwa = IDPanstwa;
	}

	public String getNazwaPanstwa() {
		return this.nazwaPanstwa;
	}

	public void setNazwaPanstwa(String nazwaPanstwa) {
		this.nazwaPanstwa = nazwaPanstwa;
	}

	public List<Tadresy> getTadresies() {
		return this.tadresies;
	}

	public void setTadresies(List<Tadresy> tadresies) {
		this.tadresies = tadresies;
	}

	public Tadresy addTadresy(Tadresy tadresy) {
		getTadresies().add(tadresy);
		tadresy.setTpanstwa(this);

		return tadresy;
	}

	public Tadresy removeTadresy(Tadresy tadresy) {
		getTadresies().remove(tadresy);
		tadresy.setTpanstwa(null);

		return tadresy;
	}

	public List<Toferta> getTofertas() {
		return this.tofertas;
	}

	public void setTofertas(List<Toferta> tofertas) {
		this.tofertas = tofertas;
	}

	public Toferta addToferta(Toferta toferta) {
		getTofertas().add(toferta);
		toferta.setTpanstwa(this);

		return toferta;
	}

	public Toferta removeToferta(Toferta toferta) {
		getTofertas().remove(toferta);
		toferta.setTpanstwa(null);

		return toferta;
	}

}