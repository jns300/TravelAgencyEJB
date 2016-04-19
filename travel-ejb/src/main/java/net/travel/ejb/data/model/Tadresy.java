package net.travel.ejb.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the tadresy database table.
 * 
 */
@Entity
@NamedQuery(name = "Tadresy.findAll", query = "SELECT t FROM Tadresy t")
public class Tadresy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDAdresu;

	private String adres;

	private String faks;

	private String kod;

	private String miasto;

	private String region;

	private String telefon;

	// bi-directional many-to-one association to Tpanstwa
	@ManyToOne
	@JoinColumn(name = "Panstwo")
	private Tpanstwa tpanstwa;

	// bi-directional many-to-one association to Tfirmy
	@OneToMany(mappedBy = "tadresy")
	private List<Tfirmy> tfirmies;

	// bi-directional many-to-one association to Tosoby
	@OneToMany(mappedBy = "tadresy")
	private List<Tosoby> tosobies;

	public Tadresy() {
	}

	public Tadresy(String adres, String faks, String kod, String miasto, String region,
			String telefon, Tpanstwa tpanstwa) {
		super();
		this.adres = adres;
		this.faks = faks;
		this.kod = kod;
		this.miasto = miasto;
		this.region = region;
		this.telefon = telefon;
		this.tpanstwa = tpanstwa;
		tosobies = new ArrayList<>();
		tfirmies = new ArrayList<>();
	}

	public int getIDAdresu() {
		return this.IDAdresu;
	}

	public void setIDAdresu(int IDAdresu) {
		this.IDAdresu = IDAdresu;
	}

	public String getAdres() {
		return this.adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getFaks() {
		return this.faks;
	}

	public void setFaks(String faks) {
		this.faks = faks;
	}

	public String getKod() {
		return this.kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getMiasto() {
		return this.miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public Tpanstwa getTpanstwa() {
		return this.tpanstwa;
	}

	public void setTpanstwa(Tpanstwa tpanstwa) {
		this.tpanstwa = tpanstwa;
	}

	public List<Tfirmy> getTfirmies() {
		return this.tfirmies;
	}

	public void setTfirmies(List<Tfirmy> tfirmies) {
		this.tfirmies = tfirmies;
	}

	public Tfirmy addTfirmy(Tfirmy tfirmy) {
		getTfirmies().add(tfirmy);
		tfirmy.setTadresy(this);

		return tfirmy;
	}

	public Tfirmy removeTfirmy(Tfirmy tfirmy) {
		getTfirmies().remove(tfirmy);
		tfirmy.setTadresy(null);

		return tfirmy;
	}

	public List<Tosoby> getTosobies() {
		return this.tosobies;
	}

	public void setTosobies(List<Tosoby> tosobies) {
		this.tosobies = tosobies;
	}

	public Tosoby addTosoby(Tosoby tosoby) {
		getTosobies().add(tosoby);
		tosoby.setTadresy(this);

		return tosoby;
	}

	public Tosoby removeTosoby(Tosoby tosoby) {
		getTosobies().remove(tosoby);
		tosoby.setTadresy(null);

		return tosoby;
	}

}