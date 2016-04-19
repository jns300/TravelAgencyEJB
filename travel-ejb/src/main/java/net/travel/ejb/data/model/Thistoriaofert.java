package net.travel.ejb.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the thistoriaofert database table.
 * 
 */
@Entity
@NamedQuery(name = "Thistoriaofert.findAll", query = "SELECT t FROM Thistoriaofert t")
public class Thistoriaofert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDOferty;

	private boolean bWyjazd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataWyjazdu;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDPanstwa")
	private Tpanstwa tpanstwa;

	private int iLiczbaOsob;

	private int liczbaDniTrwania;

	private BigDecimal mCena;

	private String miasto;

	private String miejsceWyjazdu;

	private String opis;

	// bi-directional many-to-one association to Tatrakcjehistoria
	@OneToMany(mappedBy = "thistoriaofert", cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private List<Tatrakcjehistoria> tatrakcjehistorias;

	// bi-directional many-to-one association to Tklienciofertyhistoria
	@OneToMany(mappedBy = "thistoriaofert", cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private List<Tklienciofertyhistoria> tklienciofertyhistorias;

	public Thistoriaofert() {
	}

	public Thistoriaofert(Toferta offer) {
		bWyjazd = offer.getBWyjazd();
		dataWyjazdu = offer.getDataWyjazdu();
		tpanstwa = offer.getTpanstwa();
		iLiczbaOsob = offer.getILiczbaOsob();
		liczbaDniTrwania = offer.getLiczbaDniTrwania();
		mCena = offer.getMCena();
		miasto = offer.getMiasto();
		miejsceWyjazdu = offer.getMiejsceWyjazdu();
		opis = offer.getOpis();
		tatrakcjehistorias = new ArrayList<>();
		tklienciofertyhistorias = new ArrayList<>();
		if (offer.getTatrakcjeuslugis().size() > 0) {
			for (Tatrakcjeuslugi attraction : offer.getTatrakcjeuslugis()) {
				addTatrakcjehistoria(new Tatrakcjehistoria(attraction));
			}
		}
		if (offer.getTkliencioferties().size() > 0) {
			for (Tkliencioferty client : offer.getTkliencioferties()) {
				addTklienciofertyhistoria(new Tklienciofertyhistoria(client));
			}
		}
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

	public Tpanstwa getTpanstwa() {
		return this.tpanstwa;
	}

	public void setTpanstwa(Tpanstwa value) {
		this.tpanstwa = value;
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

	public List<Tatrakcjehistoria> getTatrakcjehistorias() {
		return this.tatrakcjehistorias;
	}

	public void setTatrakcjehistorias(List<Tatrakcjehistoria> tatrakcjehistorias) {
		this.tatrakcjehistorias = tatrakcjehistorias;
	}

	public Tatrakcjehistoria addTatrakcjehistoria(Tatrakcjehistoria tatrakcjehistoria) {
		getTatrakcjehistorias().add(tatrakcjehistoria);
		tatrakcjehistoria.setThistoriaofert(this);

		return tatrakcjehistoria;
	}

	public Tatrakcjehistoria removeTatrakcjehistoria(Tatrakcjehistoria tatrakcjehistoria) {
		getTatrakcjehistorias().remove(tatrakcjehistoria);
		tatrakcjehistoria.setThistoriaofert(null);

		return tatrakcjehistoria;
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
		tklienciofertyhistoria.setThistoriaofert(this);

		return tklienciofertyhistoria;
	}

	public Tklienciofertyhistoria removeTklienciofertyhistoria(
			Tklienciofertyhistoria tklienciofertyhistoria) {
		getTklienciofertyhistorias().remove(tklienciofertyhistoria);
		tklienciofertyhistoria.setThistoriaofert(null);

		return tklienciofertyhistoria;
	}

}