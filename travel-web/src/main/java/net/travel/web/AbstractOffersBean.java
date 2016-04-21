package net.travel.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import net.travel.ejb.request.RequestBeanRemote;

/**
 * Represents an abstract class which provides offers to display in the offer
 * list.
 * 
 * @param <T>
 *            the type of the offer to display
 */
public abstract class AbstractOffersBean<T, TA> implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	protected RequestBeanRemote request;

	protected int startPage;

	protected final int PAGE_SIZE = 15;

	protected int pageSize = PAGE_SIZE;

	protected transient ListDataModel<T> offersModel;

	protected int allOfferCount = -1;

	private transient T current;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int value) {
		pageSize = value;
		clearOfferCache();
	}

	protected void clearOfferCache() {
		// nothing to do
		offersModel = null;
		allOfferCount = -1;
	}

	protected void resetStartPage() {
		startPage = 0;
		clearOfferCache();
	}

	public int getPageFirstItem() {
		return startPage;
	}

	public int getPageLastItem() {
		return Math.min(startPage + pageSize, getItemCount());
	}

	public boolean getHasPreviousPage() {
		return startPage > 0;
	}

	public boolean getHasNextPage() {
		return getPageLastItem() < getItemCount();
	}

	protected abstract int getItemCount();

	public String firstPage() {
		if (getHasPreviousPage()) {
			startPage = 0;
			clearOfferCache();
		}
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public String previous() {
		if (getHasPreviousPage()) {
			startPage = Math.max(0, startPage - pageSize);
			clearOfferCache();
		}
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public String next() {
		if (getHasNextPage()) {
			startPage = Math.min(getItemCount(), startPage + pageSize);
			clearOfferCache();
		}
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public String lastPage() {
		if (getHasNextPage()) {
			int count = getItemCount();
			int lastStart = count - count % pageSize;
			if (lastStart == count) {
				lastStart = count - pageSize;
			}
			startPage = lastStart;
			clearOfferCache();
		}
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public String reload() {
		resetStartPage();
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public String reset() {
		resetStartPage();
		if (pageSize < 1) {
			setPageSize(PAGE_SIZE);
		}
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public T getCurrent() {
		return current;
	}

	protected abstract int getOfferId(T offer);

	public String showDetails(int offerId) {
		current = null;
		return getOfferDetailsPage() + "?oid=" + offerId;
	}

	public String showDetails(T offer) {
		if (offer instanceof Integer) {
			return showDetails((Integer) offer);
		}
		current = offer;
		return getOfferDetailsPage();
	}

	protected abstract T getOfferById(int offerId);

	protected abstract String getOfferDetailsPage();

	public abstract boolean isHistory();

	public abstract List<TA> getAttractions();

	public abstract int getAttractionCount(T offer);

	public abstract int getOfferClientCount();

	public abstract int getAttractionClientCount(TA attraction);

	public abstract String getReservedText(TA attraction);

	public abstract boolean isAttractionReserved(TA attraction);

	public abstract boolean hasOffers();
}
