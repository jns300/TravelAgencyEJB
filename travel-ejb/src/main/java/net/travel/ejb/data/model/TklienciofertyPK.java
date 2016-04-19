package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tkliencioferty database table.
 * 
 */
@Embeddable
public class TklienciofertyPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false)
	private int IDOferty;

	@Column(insertable = false, updatable = false)
	private int IDOsobyImprezy;

	public TklienciofertyPK() {
	}

	public TklienciofertyPK(int iDOferty, int iDOsobyImprezy) {
		super();
		IDOferty = iDOferty;
		IDOsobyImprezy = iDOsobyImprezy;
	}

	public int getIDOferty() {
		return this.IDOferty;
	}

	public void setIDOferty(int IDOferty) {
		this.IDOferty = IDOferty;
	}

	public int getIDOsobyImprezy() {
		return this.IDOsobyImprezy;
	}

	public void setIDOsobyImprezy(int IDOsobyImprezy) {
		this.IDOsobyImprezy = IDOsobyImprezy;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TklienciofertyPK)) {
			return false;
		}
		TklienciofertyPK castOther = (TklienciofertyPK) other;
		return (this.IDOferty == castOther.IDOferty)
				&& (this.IDOsobyImprezy == castOther.IDOsobyImprezy);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDOferty;
		hash = hash * prime + this.IDOsobyImprezy;

		return hash;
	}
}