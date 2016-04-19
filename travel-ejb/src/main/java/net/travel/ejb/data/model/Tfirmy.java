package net.travel.ejb.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tfirmy database table.
 * 
 */
@Entity
@NamedQuery(name="Tfirmy.findAll", query="SELECT t FROM Tfirmy t")
public class Tfirmy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int IDFirmy;

	private String nazwaFirmy;

	private String nip;

	private String regon;

	//bi-directional many-to-one association to Tadresy
	@ManyToOne
	@JoinColumn(name="IDAdresu")
	private Tadresy tadresy;

	//bi-directional many-to-one association to Tklient
	@ManyToOne
	@JoinColumn(name="IDKlienta")
	private Tklient tklient;

	public Tfirmy() {
	}

	
	public Tfirmy(String nazwaFirmy, String nip, String regon, Tadresy tadresy, Tklient tklient) {
		super();
		this.nazwaFirmy = nazwaFirmy;
		this.nip = nip;
		this.regon = regon;
		this.tadresy = tadresy;
		this.tklient = tklient;
	}


	public int getIDFirmy() {
		return this.IDFirmy;
	}

	public void setIDFirmy(int IDFirmy) {
		this.IDFirmy = IDFirmy;
	}

	public String getNazwaFirmy() {
		return this.nazwaFirmy;
	}

	public void setNazwaFirmy(String nazwaFirmy) {
		this.nazwaFirmy = nazwaFirmy;
	}

	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getRegon() {
		return this.regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
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

}