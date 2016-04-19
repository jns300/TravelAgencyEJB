package net.travel.ejb.data.model;

import java.io.Serializable;


/**
 * The persistent class for the vwszyscyklienci database table.
 * 
 */
// @NamedQuery(name="Vwszyscyklienci.findAll", query="SELECT v FROM Vwszyscyklienci v")
public class Vwszyscyklienci implements Serializable {
	private static final long serialVersionUID = 1L;

	private String adres;

	private boolean bFirma;

	private boolean bPracownik;

	private String email;

	private String faks;

	private String haslo;

	private int IDAdresu;

	private int IDKlienta;

	private int IDOsobyFirmy;

	private String imie;

	private String kod;

	private String miasto;

	private String nazwa;

	private String nazwaPanstwa;

	private String nazwisko;

	private String nip;

	private int panstwo;

	private String region;

	private String regon;

	private String telefon;

	public Vwszyscyklienci() {
	}

	public String getAdres() {
		return this.adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public boolean getBFirma() {
		return this.bFirma;
	}

	public void setBFirma(boolean bFirma) {
		this.bFirma = bFirma;
	}

	public boolean getBPracownik() {
		return this.bPracownik;
	}

	public void setBPracownik(boolean bPracownik) {
		this.bPracownik = bPracownik;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaks() {
		return this.faks;
	}

	public void setFaks(String faks) {
		this.faks = faks;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public int getIDAdresu() {
		return this.IDAdresu;
	}

	public void setIDAdresu(int IDAdresu) {
		this.IDAdresu = IDAdresu;
	}

	public int getIDKlienta() {
		return this.IDKlienta;
	}

	public void setIDKlienta(int IDKlienta) {
		this.IDKlienta = IDKlienta;
	}

	public int getIDOsobyFirmy() {
		return this.IDOsobyFirmy;
	}

	public void setIDOsobyFirmy(int IDOsobyFirmy) {
		this.IDOsobyFirmy = IDOsobyFirmy;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getKod() {
		return this.kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getMiasto() {
		return this.miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getNazwaPanstwa() {
		return this.nazwaPanstwa;
	}

	public void setNazwaPanstwa(String nazwaPanstwa) {
		this.nazwaPanstwa = nazwaPanstwa;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public int getPanstwo() {
		return this.panstwo;
	}

	public void setPanstwo(int panstwo) {
		this.panstwo = panstwo;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegon() {
		return this.regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}