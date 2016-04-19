package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the tklienciatrakcjehistoria database table.
 * 
 */
@Entity
@NamedQuery(name = "Tklienciatrakcjehistoria.findAll", query = "SELECT t FROM Tklienciatrakcjehistoria t")
public class Tklienciatrakcjehistoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TklienciatrakcjehistoriaPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataZaplaty;

	private String sposobZaplaty;

	private BigDecimal zaplaconaKwota;

	// bi-directional many-to-one association to Tatrakcjehistoria
	@ManyToOne
	@JoinColumn(name = "IDAtrakcjiUslugi")
	private Tatrakcjehistoria tatrakcjehistoria;

	// bi-directional many-to-one association to Tklient
	@ManyToOne
	@JoinColumn(name = "IDKlienta")
	private Tklient tklient;

	// bi-directional many-to-one association to Tosoby
	@ManyToOne
	@JoinColumn(name = "IDOsobyAtrakcji")
	private Tosoby tosoby;

	public Tklienciatrakcjehistoria() {
	}

	public Tklienciatrakcjehistoria(Tklienciatrakcje client) {
		dataZaplaty = client.getDataZaplaty();
		if (dataZaplaty == null) {
			dataZaplaty = client.getDataRezerwacji();
		}
		sposobZaplaty = client.getSposobZaplaty();
		if (sposobZaplaty == null) {
			sposobZaplaty = "P";
		}
		zaplaconaKwota = client.getZaplaconaKwota();
		tklient = client.getTklient();
		tosoby = client.getTosoby();
		id = new TklienciatrakcjehistoriaPK(client.getId().getIDAtrakcjiUslugi(), client.getId().getIDOsobyAtrakcji());
	}

	public TklienciatrakcjehistoriaPK getId() {
		return this.id;
	}

	public void setId(TklienciatrakcjehistoriaPK id) {
		this.id = id;
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

	public Tatrakcjehistoria getTatrakcjehistoria() {
		return this.tatrakcjehistoria;
	}

	public void setTatrakcjehistoria(Tatrakcjehistoria tatrakcjehistoria) {
		this.tatrakcjehistoria = tatrakcjehistoria;
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