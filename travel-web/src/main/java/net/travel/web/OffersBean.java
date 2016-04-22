package net.travel.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import net.travel.ejb.data.model.Tatrakcjeuslugi;
import net.travel.ejb.data.model.Toferta;

/**
 * Managed Bean which is related to offers page. Provides offers and contains
 * logic of the pagination.
 */
@ManagedBean(name = "offerslist")
@SessionScoped
public class OffersBean extends AbstractOffersBean<Toferta, Tatrakcjeuslugi>
		implements Serializable {

	private static final String VIEW_ID = "offers";

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(OffersBean.class.getName());

	private Integer minDurationValue;

	private Integer maxDurationValue;

	private BigDecimal minPriceValue;

	private BigDecimal maxPriceValue;

	private String countryValue;

	private Date departureStartDate;

	private Date departureEndDate;

	private boolean showExpired;

	public OffersBean() {
	}

	public DataModel<?> getOffers() {
		if (offersModel == null) {
			offersModel = new ListDataModel<Toferta>(request.getOffers(startPage,
					startPage + pageSize, getMinDurationValue(), getMaxDurationValue(),
					getMinPriceValue(), getMaxPriceValue(), getCountryValue(),
					getDepartureStartDate(), getDepartureEndDate(), showExpired));
		}
		return offersModel;
	}

	public void setShowExpired(boolean value) {
		showExpired = value;
		clearOfferCache();
	}

	public boolean getShowExpired() {
		return showExpired;
	}

	public int getItemCount() {
		if (allOfferCount < 0) {
			try {
				allOfferCount = (int) request.getOfferCount(getMinDurationValue(),
						getMaxDurationValue(), getMinPriceValue(), getMaxPriceValue(),
						getCountryValue(), getDepartureStartDate(), getDepartureEndDate(),
						showExpired);
			} catch (Exception ex) {
				logger.log(Level.SEVERE, ex.getMessage());
			}
		}
		return allOfferCount;
	}

	public String reset() {
		setMinDurationValue(null);
		setMaxDurationValue(null);
		setMinPriceValue(null);
		setMaxPriceValue(null);
		setDepartureStartDate(null);
		setDepartureEndDate(null);
		setCountryValue(null);
		setShowExpired(false);
		return super.reset();
	}

	protected String getOfferDetailsPage() {
		return "offerDetails";
	}

	public boolean getRenderReserved() {
		return true;
	}

	public boolean isOfferReserved() {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			return request.isOfferReserved(clientId, getCurrent().getIDOferty());
		}
		return false;
	}

	public boolean isOfferReserved(Toferta offer) {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			return request.isOfferReserved(clientId, offer.getIDOferty());
		}
		return false;
	}

	public String reserve() {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			request.reserveOffer(clientId, getCurrent().getIDOferty());
		}
		return null;
	}

	public boolean isAttractionReservedById(Integer attractionId) {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			return request.isAttractionReserved(clientId, attractionId);
		}
		return false;
	}

	public String reserveAttraction(int attractionId) {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			request.reserveAttraction(clientId, attractionId);
		}
		return null;
	}

	public String markAsRealised() {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			request.markAsRealised(getCurrent().getIDOferty());
			clearOfferCache();
			DataModel<?> offerList = getOffers();
			if (offerList.getRowCount() == 0) {
				startPage = Math.max(0, startPage - pageSize);
				clearOfferCache();
			}
		}
		return VIEW_ID;
	}

	// / BEGIN properties for the search criteria
	public Integer getMinDurationValue() {
		if (minDurationValue != null && minDurationValue <= 0) {
			minDurationValue = null;
		}
		return minDurationValue;
	}

	public void setMinDurationValue(Integer minDurationValue) {
		this.minDurationValue = minDurationValue;
		clearOfferCache();
	}

	public Integer getMaxDurationValue() {
		if (maxDurationValue != null && maxDurationValue <= 0) {
			maxDurationValue = null;
		}
		return maxDurationValue;
	}

	public void setMaxDurationValue(Integer maxDurationValue) {
		this.maxDurationValue = maxDurationValue;
		clearOfferCache();
	}

	public BigDecimal getMinPriceValue() {
		if (minPriceValue != null && minPriceValue.longValue() <= 0) {
			minPriceValue = null;
		}
		return minPriceValue;
	}

	public void setMinPriceValue(BigDecimal minPriceValue) {
		this.minPriceValue = minPriceValue;
		clearOfferCache();
	}

	public BigDecimal getMaxPriceValue() {
		if (maxPriceValue != null && maxPriceValue.longValue() <= 0) {
			maxPriceValue = null;
		}
		return maxPriceValue;
	}

	public void setMaxPriceValue(BigDecimal maxPriceValue) {
		this.maxPriceValue = maxPriceValue;
		clearOfferCache();
	}

	public String getCountryValue() {
		return countryValue;
	}

	public void setCountryValue(String countryValue) {
		this.countryValue = countryValue;
		clearOfferCache();
	}

	public Date getDepartureStartDate() {
		return departureStartDate;
	}

	public void setDepartureStartDate(Date departureStartDate) {
		this.departureStartDate = departureStartDate;
		clearOfferCache();
	}

	public Date getDepartureEndDate() {
		return departureEndDate;
	}

	public void setDepartureEndDate(Date departureEndDate) {
		this.departureEndDate = departureEndDate;
		clearOfferCache();
	}

	// / END properties for the search criteria

	@Override
	public boolean isHistory() {
		return false;
	}

	@Override
	public List<Tatrakcjeuslugi> getAttractions() {
		if (getCurrent() != null) {
			return getCurrent().getTatrakcjeuslugis();
		}
		return null;
	}

	@Override
	public int getOfferClientCount() {
		if (getCurrent() != null) {
			return getCurrent().getTkliencioferties().size();
		}
		return 0;
	}

	@Override
	public int getAttractionClientCount(Tatrakcjeuslugi attraction) {
		return attraction.getTklienciatrakcjes().size();
	}

	@Override
	public boolean isAttractionReserved(Tatrakcjeuslugi attraction) {
		return isAttractionReservedById(attraction.getIDAtrakcjiUslugi());
	}

	@Override
	public String getReservedText(Tatrakcjeuslugi attraction) {
		if (isAttractionReserved(attraction)) {
			return "Reserved";
		} else {
			return "Not Reserved";
		}
	}

	@Override
	public int getAttractionCount(Toferta offer) {
		return offer.getTatrakcjeuslugis().size();
	}

	@Override
	public boolean hasOffers() {
		return getOffers().getRowCount() > 0;
	}

	@Override
	protected Toferta getOfferById(int offerId) {
		return request.getOfferById(offerId);
	}

	@Override
	protected int getOfferId(Toferta offer) {
		return offer.getIDOferty();
	}
}
