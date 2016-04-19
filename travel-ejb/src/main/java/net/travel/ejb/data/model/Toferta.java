package net.travel.ejb.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the toferta database table.
 * 
 */
@Entity
@NamedQuery(name = "Toferta.findAll", query = "SELECT t FROM Toferta t")
public class Toferta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDOferty;

	private boolean bWyjazd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataWyjazdu;

	private int iLiczbaOsob;

	private int liczbaDniTrwania;

	private BigDecimal mCena;

	private String miasto;

	private String miejsceWyjazdu;

	private String opis;

	// bi-directional many-to-one association to Tkliencioferty
	@OneToMany(mappedBy = "toferta", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE })
	private List<Tkliencioferty> tkliencioferties;

	// bi-directional many-to-one association to Tpanstwa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDPanstwa")
	private Tpanstwa tpanstwa;

	// bi-directional many-to-one association to Tatrakcjeuslugi
	@OneToMany(mappedBy = "toferta", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE })
	private List<Tatrakcjeuslugi> tatrakcjeuslugis;

	public Toferta() {
	}

	public Toferta(boolean bWyjazd, Date dataWyjazdu, int iLiczbaOsob, int liczbaDniTrwania,
			BigDecimal mCena, String miasto, String miejsceWyjazdu, String opis, Tpanstwa tpanstwa) {
		super();
		this.bWyjazd = bWyjazd;
		this.dataWyjazdu = dataWyjazdu;
		this.iLiczbaOsob = iLiczbaOsob;
		this.liczbaDniTrwania = liczbaDniTrwania;
		this.mCena = mCena;
		this.miasto = miasto;
		this.miejsceWyjazdu = miejsceWyjazdu;
		this.opis = opis;
		this.tpanstwa = tpanstwa;
		tatrakcjeuslugis = new ArrayList<>();
		tkliencioferties = new ArrayList<>();
	}

	public int getIDOferty() {
		return this.IDOferty;
	}

	public void setIDOferty(int IDOferty) {
		this.IDOferty = IDOferty;
	}

	public boolean getBWyjazd() {
		return this.bWyjazd;
	}

	public void setBWyjazd(boolean bWyjazd) {
		this.bWyjazd = bWyjazd;
	}

	public Date getDataWyjazdu() {
		return this.dataWyjazdu;
	}

	public void setDataWyjazdu(Date dataWyjazdu) {
		this.dataWyjazdu = dataWyjazdu;
	}

	public int getILiczbaOsob() {
		return this.iLiczbaOsob;
	}

	public void setILiczbaOsob(int iLiczbaOsob) {
		this.iLiczbaOsob = iLiczbaOsob;
	}

	public int getLiczbaDniTrwania() {
		return this.liczbaDniTrwania;
	}

	public void setLiczbaDniTrwania(int liczbaDniTrwania) {
		this.liczbaDniTrwania = liczbaDniTrwania;
	}

	public BigDecimal getMCena() {
		return this.mCena;
	}

	public void setMCena(BigDecimal mCena) {
		this.mCena = mCena;
	}

	public String getMiasto() {
		return this.miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getMiejsceWyjazdu() {
		return this.miejsceWyjazdu;
	}

	public void setMiejsceWyjazdu(String miejsceWyjazdu) {
		this.miejsceWyjazdu = miejsceWyjazdu;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public List<Tkliencioferty> getTkliencioferties() {
		return this.tkliencioferties;
	}

	public void setTkliencioferties(List<Tkliencioferty> tkliencioferties) {
		this.tkliencioferties = tkliencioferties;
	}

	public Tkliencioferty addTkliencioferty(Tkliencioferty tkliencioferty) {
		getTkliencioferties().add(tkliencioferty);
		tkliencioferty.setToferta(this);

		return tkliencioferty;
	}

	public Tkliencioferty removeTkliencioferty(Tkliencioferty tkliencioferty) {
		getTkliencioferties().remove(tkliencioferty);
		tkliencioferty.setToferta(null);

		return tkliencioferty;
	}

	public Tpanstwa getTpanstwa() {
		return this.tpanstwa;
	}

	public void setTpanstwa(Tpanstwa tpanstwa) {
		this.tpanstwa = tpanstwa;
	}

	public List<Tatrakcjeuslugi> getTatrakcjeuslugis() {
		return this.tatrakcjeuslugis;
	}

	public void setTatrakcjeuslugis(List<Tatrakcjeuslugi> tatrakcjeuslugis) {
		this.tatrakcjeuslugis = tatrakcjeuslugis;
	}

	public Tatrakcjeuslugi addTatrakcjeuslugi(Tatrakcjeuslugi tatrakcjeuslugi) {
		getTatrakcjeuslugis().add(tatrakcjeuslugi);
		tatrakcjeuslugi.setToferta(this);

		return tatrakcjeuslugi;
	}

	public Tatrakcjeuslugi removeTatrakcjeuslugi(Tatrakcjeuslugi tatrakcjeuslugi) {
		getTatrakcjeuslugis().remove(tatrakcjeuslugi);
		tatrakcjeuslugi.setToferta(null);

		return tatrakcjeuslugi;
	}

}