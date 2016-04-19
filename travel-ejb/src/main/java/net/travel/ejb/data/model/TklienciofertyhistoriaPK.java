package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tklienciofertyhistoria database table.
 * 
 */
@Embeddable
public class TklienciofertyhistoriaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false)
	private int IDOferty;

	@Column(insertable = false, updatable = false)
	private int IDOsoby;

	public TklienciofertyhistoriaPK() {
	}

	public TklienciofertyhistoriaPK(int iDOferty, int iDOsoby) {
		super();
		IDOferty = iDOferty;
		IDOsoby = iDOsoby;
	}

	public int getIDOferty() {
		return this.IDOferty;
	}

	public void setIDOferty(int IDOferty) {
		this.IDOferty = IDOferty;
	}

	public int getIDOsoby() {
		return this.IDOsoby;
	}

	public void setIDOsoby(int IDOsoby) {
		this.IDOsoby = IDOsoby;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TklienciofertyhistoriaPK)) {
			return false;
		}
		TklienciofertyhistoriaPK castOther = (TklienciofertyhistoriaPK) other;
		return (this.IDOferty == castOther.IDOferty) && (this.IDOsoby == castOther.IDOsoby);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDOferty;
		hash = hash * prime + this.IDOsoby;

		return hash;
	}
}