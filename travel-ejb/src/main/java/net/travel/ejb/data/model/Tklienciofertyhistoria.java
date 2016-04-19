package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the tklienciofertyhistoria database table.
 * 
 */
@Entity
@NamedQuery(name = "Tklienciofertyhistoria.findAll", query = "SELECT t FROM Tklienciofertyhistoria t")
public class Tklienciofertyhistoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TklienciofertyhistoriaPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataZaplaty;

	private int IDKlienta;

	private String sposobZaplaty;

	private BigDecimal zaplaconaKwota;

	// bi-directional many-to-one association to Thistoriaofert
	@ManyToOne
	@JoinColumn(name = "IDOferty")
	private Thistoriaofert thistoriaofert;

	// bi-directional many-to-one association to Tosoby
	@ManyToOne
	@JoinColumn(name = "IDOsoby")
	private Tosoby tosoby;

	public Tklienciofertyhistoria() {
	}

	public Tklienciofertyhistoria(Tkliencioferty client) {
		dataZaplaty = client.getDataZaplaty();
		if (dataZaplaty == null) {
			dataZaplaty = client.getDataRezerwacji();
		}
		IDKlienta = client.getTklient().getIDKlienta();
		sposobZaplaty = client.getSposobZaplaty();
		if (sposobZaplaty == null) {
			sposobZaplaty = "P";
		}
		zaplaconaKwota = client.getZaplaconaKwota();
		tosoby = client.getTosoby();
		id = new TklienciofertyhistoriaPK(client.getId().getIDOferty(), client.getId().getIDOsobyImprezy());
	}

	public TklienciofertyhistoriaPK getId() {
		return this.id;
	}

	public void setId(TklienciofertyhistoriaPK id) {
		this.id = id;
	}

	public Date getDataZaplaty() {
		return this.dataZaplaty;
	}

	public void setDataZaplaty(Date dataZaplaty) {
		this.dataZaplaty = dataZaplaty;
	}

	public int getIDKlienta() {
		return this.IDKlienta;
	}

	public void setIDKlienta(int IDKlienta) {
		this.IDKlienta = IDKlienta;
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

	public Thistoriaofert getThistoriaofert() {
		return this.thistoriaofert;
	}

	public void setThistoriaofert(Thistoriaofert thistoriaofert) {
		this.thistoriaofert = thistoriaofert;
	}

	public Tosoby getTosoby() {
		return this.tosoby;
	}

	public void setTosoby(Tosoby tosoby) {
		this.tosoby = tosoby;
	}

}