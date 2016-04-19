package net.travel.ejb.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tklienciatrakcjehistoria database table.
 * 
 */
@Embeddable
public class TklienciatrakcjehistoriaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false)
	private int IDAtrakcjiUslugi;

	@Column(insertable = false, updatable = false)
	private int IDOsobyAtrakcji;

	public TklienciatrakcjehistoriaPK() {
	}

	public TklienciatrakcjehistoriaPK(int iDAtrakcjiUslugi, int iDOsobyAtrakcji) {
		super();
		IDAtrakcjiUslugi = iDAtrakcjiUslugi;
		IDOsobyAtrakcji = iDOsobyAtrakcji;
	}

	public int getIDAtrakcjiUslugi() {
		return this.IDAtrakcjiUslugi;
	}

	public void setIDAtrakcjiUslugi(int IDAtrakcjiUslugi) {
		this.IDAtrakcjiUslugi = IDAtrakcjiUslugi;
	}

	public int getIDOsobyAtrakcji() {
		return this.IDOsobyAtrakcji;
	}

	public void setIDOsobyAtrakcji(int IDOsobyAtrakcji) {
		this.IDOsobyAtrakcji = IDOsobyAtrakcji;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TklienciatrakcjehistoriaPK)) {
			return false;
		}
		TklienciatrakcjehistoriaPK castOther = (TklienciatrakcjehistoriaPK) other;
		return (this.IDAtrakcjiUslugi == castOther.IDAtrakcjiUslugi)
				&& (this.IDOsobyAtrakcji == castOther.IDOsobyAtrakcji);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDAtrakcjiUslugi;
		hash = hash * prime + this.IDOsobyAtrakcji;

		return hash;
	}
}