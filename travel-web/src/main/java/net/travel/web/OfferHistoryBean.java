package net.travel.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import net.travel.ejb.data.model.Tatrakcjehistoria;
import net.travel.ejb.data.model.Thistoriaofert;
import net.travel.ejb.request.RequestBeanRemote;

@ManagedBean(name = "offerHistoryList")
@SessionScoped
public class OfferHistoryBean extends AbstractOffersBean<Thistoriaofert, Tatrakcjehistoria>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RequestBeanRemote request;

	public OfferHistoryBean() {

	}

	public boolean isOfferReserved(Thistoriaofert history) {
		return true;
	}

	public boolean getRenderReserved() {
		return false;
	}

	public DataModel<?> getOffers() {
		if (offersModel == null) {
			if (LoginFormBean.getClientIdOrNull() == null) {
				offersModel = new ListDataModel<Thistoriaofert>();
			} else {
				offersModel = new ListDataModel<Thistoriaofert>(request.getOffersHistory(startPage,
						startPage + pageSize, LoginFormBean.getClientId()));
			}
		}
		return offersModel;
	}

	public int getItemCount() {
		if (allOfferCount < 0) {
			allOfferCount = (int) request.getOfferHistoryCount(LoginFormBean.getClientId());
		}
		return allOfferCount;
	}

	@Override
	protected String getOfferDetailsPage() {
		return "offerHistoryDetails";
	}

	@Override
	public boolean isHistory() {
		return true;
	}

	@Override
	public List<Tatrakcjehistoria> getAttractions() {
		if (getCurrent() != null) {
			return getCurrent().getTatrakcjehistorias();
		}
		return null;
	}

	@Override
	public int getOfferClientCount() {
		if (getCurrent() != null) {
			return getCurrent().getTklienciofertyhistorias().size();
		}
		return 0;
	}

	@Override
	public int getAttractionClientCount(Tatrakcjehistoria atraction) {
		return atraction.getTklienciatrakcjehistorias().size();
	}

	@Override
	public boolean isAttractionReserved(Tatrakcjehistoria attraction) {
		Integer clientId = LoginFormBean.getClientIdOrNull();
		if (clientId != null) {
			return request.isAttractionHistoryReserved(clientId, attraction.getIDAtrakcjiUslugi());
		}
		return false;
	}

	@Override
	public String getReservedText(Tatrakcjehistoria attraction) {
		if (isAttractionReserved(attraction)) {
			return "Attended";
		} else {
			return "Not Attended";
		}
	}

	@Override
	public int getAttractionCount(Thistoriaofert offer) {
		return offer.getTatrakcjehistorias().size();
	}

	@Override
	public boolean hasOffers() {
		return getOffers().getRowCount() > 0;
	}

	@Override
	protected Thistoriaofert getOfferById(int offerId) {
		return request.getOfferHistoryById(offerId);
	}

	@Override
	protected int getOfferId(Thistoriaofert offer) {
		return offer.getIDOferty();
	}
}
