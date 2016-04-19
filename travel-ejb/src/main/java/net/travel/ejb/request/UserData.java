package net.travel.ejb.request;

import java.io.Serializable;

import net.travel.ejb.data.model.Tadresy;
import net.travel.ejb.data.model.Tfirmy;
import net.travel.ejb.data.model.Tklient;
import net.travel.ejb.data.model.Tosoby;
import net.travel.ejb.data.model.Tpanstwa;

public class UserData implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean isFirm;

	private String email;

	private String password;

	private String passwordVerification;

	private String passwordHash;

	private String name;

	private String lastName;

	private String country;

	private String address;

	private String city;

	private String zipCode;

	private String district;

	private String phone;

	private String fax;

	private String nip;

	private String firmName;

	private String regon;

	private Tklient clientEntity;

	private Tadresy addressEntity;

	private Tfirmy firmEntity;

	private Tosoby personEntity;

	public UserData() {

	}

	public UserData(boolean isFirm, String email, String passwordHash, String name,
			String lastName, String country, String address, String city, String zipCode,
			String district, String phone, String fax, String nip, String firmName, String regon) {
		super();
		this.isFirm = isFirm;
		this.email = email;
		this.passwordHash = passwordHash;
		this.name = name;
		this.lastName = lastName;
		this.country = country;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.district = district;
		this.phone = phone;
		this.fax = fax;
		this.nip = nip;
		this.firmName = firmName;
		this.regon = regon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerification() {
		return passwordVerification;
	}

	public void setPasswordVerification(String passwordVerification) {
		this.passwordVerification = passwordVerification;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isFirm() {
		return isFirm;
	}

	public void setFirm(boolean isFirm) {
		this.isFirm = isFirm;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getRegon() {
		return regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public static UserData createFirmData(Tklient client, Tadresy address, Tfirmy firm) {
		UserData userData = new UserData(true, client.getEmail(), client.getHaslo(), null, null,
				address.getTpanstwa().getNazwaPanstwa(), address.getAdres(), address.getMiasto(),
				address.getKod(), address.getRegion(), address.getTelefon(), address.getFaks(),
				firm.getNip(), firm.getNazwaFirmy(), firm.getRegon());
		userData.clientEntity = client;
		userData.addressEntity = address;
		userData.firmEntity = firm;
		return userData;
	}

	public static UserData createPersonData(Tklient client, Tadresy address, Tosoby person) {
		UserData userData = new UserData(false, client.getEmail(), client.getHaslo(),
				person.getImie(), person.getNazwisko(), address.getTpanstwa().getNazwaPanstwa(),
				address.getAdres(), address.getMiasto(), address.getKod(), address.getRegion(),
				address.getTelefon(), address.getFaks(), person.getNip(), null, null);
		userData.clientEntity = client;
		userData.addressEntity = address;
		userData.personEntity = person;
		return userData;
	}

	public Tadresy getAddressData(Tpanstwa country) {
		addressEntity.setAdres(address);
		addressEntity.setFaks(fax);
		addressEntity.setKod(zipCode);
		addressEntity.setMiasto(city);
		addressEntity.setRegion(district);
		addressEntity.setTelefon(phone);
		addressEntity.setTpanstwa(country);
		return addressEntity;
	}

	public Tklient getClientData() {
		clientEntity.setEmail(email);
		clientEntity.setHaslo(passwordHash);
		return clientEntity;
	}

	public Tfirmy getFirm() {
		firmEntity.setNazwaFirmy(firmName);
		firmEntity.setNip(nip);
		firmEntity.setRegon(regon);
		firmEntity.setTadresy(addressEntity);
		firmEntity.setTklient(clientEntity);
		return firmEntity;
	}

	public Tosoby getPerson() {
		personEntity.setImie(name);
		personEntity.setNazwisko(lastName);
		personEntity.setNip(nip);
		personEntity.setTadresy(addressEntity);
		personEntity.setTklient(clientEntity);
		return personEntity;
	}

}
