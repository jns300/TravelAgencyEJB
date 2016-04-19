package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the tklienciatrakcje database table.
 * 
 */
@Entity
@NamedQuery(name = "Tklienciatrakcje.findAll", query = "SELECT t FROM Tklienciatrakcje t")
public class Tklienciatrakcje implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TklienciatrakcjePK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRezerwacji;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataZaplaty;

	private String sposobZaplaty;

	private BigDecimal zaplaconaKwota;

	// bi-directional many-to-one association to Tatrakcjeuslugi
	@ManyToOne
	@JoinColumn(name = "IDAtrakcjiUslugi")
	private Tatrakcjeuslugi tatrakcjeuslugi;

	// bi-directional many-to-one association to Tklient
	@ManyToOne
	@JoinColumn(name = "IDKlienta")
	private Tklient tklient;

	// bi-directional many-to-one association to Tosoby
	@ManyToOne
	@JoinColumn(name = "IDOsobyAtrakcji")
	private Tosoby tosoby;

	public Tklienciatrakcje() {
	}

	public Tklienciatrakcje(Date dataRezerwacji, Date dataZaplaty, Tatrakcjeuslugi tatrakcjeuslugi, Tklient tklient,
			Tosoby tosoby) {
		super();
		this.dataRezerwacji = dataRezerwacji;
		this.dataZaplaty = dataZaplaty;
		this.tatrakcjeuslugi = tatrakcjeuslugi;
		this.tklient = tklient;
		this.tosoby = tosoby;
		id = new TklienciatrakcjePK(tatrakcjeuslugi.getIDAtrakcjiUslugi(), tosoby.getIDOsoby());
	}

	public TklienciatrakcjePK getId() {
		return this.id;
	}

	public void setId(TklienciatrakcjePK id) {
		this.id = id;
	}

	public Date getDataRezerwacji() {
		return this.dataRezerwacji;
	}

	public void setDataRezerwacji(Date dataRezerwacji) {
		this.dataRezerwacji = dataRezerwacji;
	}

	public Date getDataZaplaty() {
		return this.dataZaplaty;
	}

	public void setDataZaplaty(Date dataZaplaty) {
		this.dataZaplaty = dataZaplaty;
	}

	public String getSposobZaplaty() {
		return this.sposobZaplaty;
	}

	public void setSposobZaplaty(String sposobZaplaty) {
		this.sposobZaplaty = sposobZaplaty;
	}

	public BigDecimal getZaplaconaKwota() {
		return this.zaplaconaKwota;
	}

	public void setZaplaconaKwota(BigDecimal zaplaconaKwota) {
		this.zaplaconaKwota = zaplaconaKwota;
	}

	public Tatrakcjeuslugi getTatrakcjeuslugi() {
		return this.tatrakcjeuslugi;
	}

	public void setTatrakcjeuslugi(Tatrakcjeuslugi tatrakcjeuslugi) {
		this.tatrakcjeuslugi = tatrakcjeuslugi;
	}

	public Tklient getTklient() {
		return this.tklient;
	}

	public void setTklient(Tklient tklient) {
		this.tklient = tklient;
	}

	public Tosoby getTosoby() {
		return this.tosoby;
	}

	public void setTosoby(Tosoby tosoby) {
		this.tosoby = tosoby;
	}

}