package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the tkliencioferty database table.
 * 
 */
@Entity
@NamedQuery(name="Tkliencioferty.findAll", query="SELECT t FROM Tkliencioferty t")
public class Tkliencioferty implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TklienciofertyPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRezerwacji;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataZaplaty;

	private String sposobZaplaty;

	private BigDecimal zaplaconaKwota;

	//bi-directional many-to-one association to Tklient
	@ManyToOne
	@JoinColumn(name="IDKlienta")
	private Tklient tklient;

	//bi-directional many-to-one association to Toferta
	@ManyToOne
	@JoinColumn(name="IDOferty")
	private Toferta toferta;

	//bi-directional many-to-one association to Tosoby
	@ManyToOne
	@JoinColumn(name="IDOsobyImprezy")
	private Tosoby tosoby;

	public Tkliencioferty() {
	}

	public Tkliencioferty(Date dataRezerwacji, Date dataZaplaty, Tklient tklient, Toferta toferta, Tosoby tosoby) {
		super();
		this.dataRezerwacji = dataRezerwacji;
		this.dataZaplaty = dataZaplaty;
		this.tklient = tklient;
		this.toferta = toferta;
		this.tosoby = tosoby;
		id = new TklienciofertyPK(toferta.getIDOferty(), tosoby.getIDOsoby());
	}

	public TklienciofertyPK getId() {
		return this.id;
	}

	public void setId(TklienciofertyPK id) {
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

	public Tklient getTklient() {
		return this.tklient;
	}

	public void setTklient(Tklient tklient) {
		this.tklient = tklient;
	}

	public Toferta getToferta() {
		return this.toferta;
	}

	public void setToferta(Toferta toferta) {
		this.toferta = toferta;
	}

	public Tosoby getTosoby() {
		return this.tosoby;
	}

	public void setTosoby(Tosoby tosoby) {
		this.tosoby = tosoby;
	}

}