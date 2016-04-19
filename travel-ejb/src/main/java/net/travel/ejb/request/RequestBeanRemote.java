package net.travel.ejb.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import net.travel.ejb.data.model.Thistoriaofert;
import net.travel.ejb.data.model.Tklient;
import net.travel.ejb.data.model.Toferta;
import net.travel.ejb.data.model.Tosoby;
import net.travel.ejb.data.model.Tpanstwa;

@Remote
public interface RequestBeanRemote {

	List<Thistoriaofert> getOffersHistory(int start, int end, int clientId);

	List<Toferta> getOffers(int start, int end, Integer minDurationValue, Integer maxDurationValue,
			BigDecimal minPriceValue, BigDecimal maxPriceValue, String countryValue,
			Date departureStartDate, Date departureEndDate, boolean expiredOnly);

	List<Toferta> getOffers();

	long getOfferCount();

	long getOfferCount(Integer minDurationValue, Integer maxDurationValue, BigDecimal minPriceValue,
			BigDecimal maxPriceValue, String countryValue, Date departureStartDate,
			Date departureEndDate, boolean expiredOnly);

	long getOfferHistoryCount(int clientId);

	Tpanstwa getCountry(String countryName);

	void addPerson(Tosoby person);

	Tklient getClient(String email);

	void clearAll();

	void addCountry(String countryName);

	long getCountryCount();

	long getAddressCount();

	long getClientCount();

	long getPersonCount();

	String getUserName(int clientId);

	Tosoby getPerson(int clientId);

	UserData getUserData(int clientId);

	Tklient getClient(int clientId);

	void updateProfileData(int clientId, UserData userData);

	void updatePassword(int clientId, String newHash);

	void reserveOffer(Integer clientId, int offerId);

	boolean isOfferReserved(Integer clientId, int offerId);

	boolean isAttractionReserved(int clientId, int attractionId);

	void reserveAttraction(Integer clientId, int attractionId);

	void markAsRealised(int offerId);

	boolean isAttractionHistoryReserved(int clientId, int attractionId);

	Toferta getOfferById(int offerId);

	Thistoriaofert getOfferHistoryById(int offerId);
}
