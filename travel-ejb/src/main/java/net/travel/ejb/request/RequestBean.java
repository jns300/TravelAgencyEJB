package net.travel.ejb.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.travel.ejb.data.model.Tadresy;
import net.travel.ejb.data.model.Tatrakcjehistoria;
import net.travel.ejb.data.model.Tatrakcjehistoria_;
import net.travel.ejb.data.model.Tatrakcjeuslugi;
import net.travel.ejb.data.model.Tatrakcjeuslugi_;
import net.travel.ejb.data.model.Tfirmy;
import net.travel.ejb.data.model.Tfirmy_;
import net.travel.ejb.data.model.Thistoriaofert;
import net.travel.ejb.data.model.Thistoriaofert_;
import net.travel.ejb.data.model.Tklienciatrakcje;
import net.travel.ejb.data.model.Tklienciatrakcje_;
import net.travel.ejb.data.model.Tklienciatrakcjehistoria;
import net.travel.ejb.data.model.Tklienciatrakcjehistoria_;
import net.travel.ejb.data.model.Tkliencioferty;
import net.travel.ejb.data.model.Tkliencioferty_;
import net.travel.ejb.data.model.Tklienciofertyhistoria;
import net.travel.ejb.data.model.Tklienciofertyhistoria_;
import net.travel.ejb.data.model.Tklient;
import net.travel.ejb.data.model.Tklient_;
import net.travel.ejb.data.model.Toferta;
import net.travel.ejb.data.model.Toferta_;
import net.travel.ejb.data.model.Tosoby;
import net.travel.ejb.data.model.Tosoby_;
import net.travel.ejb.data.model.Tpanstwa;
import net.travel.ejb.data.model.Tpanstwa_;

/**
 * Session Bean implementation class RequestBean
 */
@Stateful
public class RequestBean implements RequestBeanRemote {

	private static final Logger logger = Logger.getLogger(RequestBean.class.getName());

	@PersistenceContext(unitName = "travelApp")
	private EntityManager em;

	private CriteriaBuilder cb;

	/**
	 * Default constructor.
	 */
	public RequestBean() {

	}

	@PostConstruct
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	private void init() {
		cb = em.getCriteriaBuilder();
	}

	@SuppressWarnings("unchecked")
	public List<Toferta> getOffers(int start, int end) {
		javax.persistence.criteria.CriteriaQuery<Toferta> cq = em.getCriteriaBuilder()
				.createQuery(Toferta.class);
		cq.select(cq.from(Toferta.class));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(end - start + 1);
		q.setFirstResult(start);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Thistoriaofert> getOffersHistory(int start, int end, int clientId) {
		javax.persistence.criteria.CriteriaQuery<Thistoriaofert> cq = em.getCriteriaBuilder()
				.createQuery(Thistoriaofert.class);
		Root<Thistoriaofert> offer = cq.from(Thistoriaofert.class);
		ListJoin<Thistoriaofert, Tklienciofertyhistoria> client = offer
				.join(Thistoriaofert_.tklienciofertyhistorias);
		cq.where(cb.equal(client.get(Tklienciofertyhistoria_.IDKlienta), clientId));
		cq.orderBy(cb.asc(offer.get(Thistoriaofert_.dataWyjazdu)), cb.asc(offer.get(Thistoriaofert_.tpanstwa)));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(end - start);
		q.setFirstResult(start);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Toferta> getOffers(int start, int end, Integer minDurationValue,
			Integer maxDurationValue, BigDecimal minPriceValue, BigDecimal maxPriceValue,
			String countryValue, Date departureStartDate, Date departureEndDate,
			boolean expiredOnly) {
		javax.persistence.criteria.CriteriaQuery<Toferta> cq = em.getCriteriaBuilder()
				.createQuery(Toferta.class);
		Root<Toferta> offer = cq.from(Toferta.class);
		Join<Toferta, Tpanstwa> panstwa = offer.join(Toferta_.tpanstwa);
		cq.select(offer);
		addWhereClauses(minDurationValue, maxDurationValue, minPriceValue, maxPriceValue,
				countryValue, departureStartDate, departureEndDate, cq, offer, panstwa,
				expiredOnly);
		cq.orderBy(cb.asc(offer.get(Toferta_.dataWyjazdu)), cb.asc(offer.get(Toferta_.tpanstwa)));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(end - start);
		q.setFirstResult(start);
		return q.getResultList();

	}

	public List<Toferta> getOffers() {
		return em.createNamedQuery("Toferta.findAll", Toferta.class).getResultList();
	}

	public long getOfferCount() {
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		if (cq != null) {
			Root<Toferta> offer = cq.from(Toferta.class);

			cq.select(cb.count(offer));
			return em.createQuery(cq).getSingleResult();
		}
		return 0;
	}

	@Override
	public long getOfferHistoryCount(int clientId) {
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<Thistoriaofert> offer = cq.from(Thistoriaofert.class);
		ListJoin<Thistoriaofert, Tklienciofertyhistoria> client = offer
				.join(Thistoriaofert_.tklienciofertyhistorias);
		cq.where(cb.equal(client.get(Tklienciofertyhistoria_.IDKlienta), clientId));
		cq.select(cb.count(offer));
		return em.createQuery(cq).getSingleResult();
	}

	@Override
	public long getOfferCount(Integer minDurationValue, Integer maxDurationValue,
			BigDecimal minPriceValue, BigDecimal maxPriceValue, String countryValue,
			Date departureStartDate, Date departureEndDate, boolean expiredOnly) {
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		Root<Toferta> offer = cq.from(Toferta.class);
		Join<Toferta, Tpanstwa> panstwa = offer.join(Toferta_.tpanstwa);

		addWhereClauses(minDurationValue, maxDurationValue, minPriceValue, maxPriceValue,
				countryValue, departureStartDate, departureEndDate, cq, offer, panstwa,
				expiredOnly);
		cq.select(cb.count(offer));
		return em.createQuery(cq).getSingleResult();
	}

	private void addWhereClauses(Integer minDurationValue, Integer maxDurationValue,
			BigDecimal minPriceValue, BigDecimal maxPriceValue, String countryValue,
			Date departureStartDate, Date departureEndDate, CriteriaQuery<?> cq,
			Root<Toferta> offer, Join<Toferta, Tpanstwa> countries, boolean expiredOnly) {
		List<Predicate> predicateList = new LinkedList<Predicate>();
		if (countryValue != null && countryValue.trim().length() > 0) {
			predicateList.add(cb.and(cb.equal(countries.get(Tpanstwa_.nazwaPanstwa), countryValue)));
		}

		if (minPriceValue != null && minPriceValue.doubleValue() > 0.0) {
			predicateList.add(cb.ge(offer.get(Toferta_.mCena), minPriceValue));
		}
		if (maxPriceValue != null && maxPriceValue.doubleValue() > 0.0) {
			predicateList.add(cb.le(offer.get(Toferta_.mCena), maxPriceValue));
		}

		if (departureStartDate != null && departureStartDate.getTime() > 0L) {
			predicateList.add(
					cb.greaterThanOrEqualTo(offer.get(Toferta_.dataWyjazdu), departureStartDate));
		}
		if (departureEndDate != null && departureEndDate.getTime() > 0L) {
			predicateList
					.add(cb.lessThanOrEqualTo(offer.get(Toferta_.dataWyjazdu), departureEndDate));
		}

		if (minDurationValue != null && minDurationValue > 0) {
			predicateList.add(cb.ge(offer.get(Toferta_.liczbaDniTrwania), minDurationValue));
		}
		if (maxDurationValue != null && maxDurationValue > 0) {
			predicateList.add(cb.le(offer.get(Toferta_.liczbaDniTrwania), maxDurationValue));
		}
		if (expiredOnly) {
			predicateList.add(cb.lessThanOrEqualTo(offer.get(Toferta_.dataWyjazdu),
					new Date(System.currentTimeMillis())));
		}
		cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
	}

	@Override
	public void addCountry(String countryName) {
		Tpanstwa country = new Tpanstwa(countryName);
		em.persist(country);
	}

	@Override
	public Tpanstwa getCountry(String countryName) {
		if (countryName == null || countryName.trim().length() == 0) {
			return null;
		}

		javax.persistence.criteria.CriteriaQuery<Tpanstwa> cq = em.getCriteriaBuilder()
				.createQuery(Tpanstwa.class);
		Root<Tpanstwa> country = cq.from(Tpanstwa.class);
		cq.select(country);
		cq.where(cb.equal(country.get(Tpanstwa_.nazwaPanstwa), countryName));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			return (Tpanstwa) list.get(0);
		}
		return null;
	}

	@Override
	public void addPerson(Tosoby person) {
		try {
			em.persist(person);
		} catch (RuntimeException ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw ex;
		}
	}

	public Tosoby getPerson(int clientId) {
		CriteriaQuery<Tosoby> cq = em.getCriteriaBuilder().createQuery(Tosoby.class);
		Root<Tosoby> person = cq.from(Tosoby.class);
		Join<Tosoby, Tklient> client = person.join(Tosoby_.tklient);
		cq.where(cb.equal(client.get(Tklient_.IDKlienta), clientId));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			return (Tosoby) list.get(0);
		}
		return null;
	}

	public Toferta getOffer(int offerId) {
		CriteriaQuery<Toferta> cq = em.getCriteriaBuilder().createQuery(Toferta.class);
		Root<Toferta> offer = cq.from(Toferta.class);
		cq.where(cb.equal(offer.get(Toferta_.IDOferty), offerId));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			return (Toferta) list.get(0);
		}
		return null;
	}

	public Tatrakcjeuslugi getAttraction(int attractionId) {
		CriteriaQuery<Tatrakcjeuslugi> cq = em.getCriteriaBuilder()
				.createQuery(Tatrakcjeuslugi.class);
		Root<Tatrakcjeuslugi> offer = cq.from(Tatrakcjeuslugi.class);
		cq.where(cb.equal(offer.get(Tatrakcjeuslugi_.IDAtrakcjiUslugi), attractionId));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			return (Tatrakcjeuslugi) list.get(0);
		}
		return null;
	}

	@Override
	public Tklient getClient(String email) {
		CriteriaQuery<Tklient> cq = em.getCriteriaBuilder().createQuery(Tklient.class);
		Root<Tklient> client = cq.from(Tklient.class);
		cq.select(client);
		cq.where(cb.equal(client.get(Tklient_.email), email));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			return (Tklient) list.get(0);
		}
		return null;
	}

	@Override
	public Tklient getClient(int clientId) {
		CriteriaQuery<Tklient> cq = em.getCriteriaBuilder().createQuery(Tklient.class);
		Root<Tklient> client = cq.from(Tklient.class);
		cq.select(client);
		cq.where(cb.equal(client.get(Tklient_.IDKlienta), clientId));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			return (Tklient) list.get(0);
		}
		return null;
	}

	@Override
	public void clearAll() {
		em.createQuery("delete from Tosoby").executeUpdate();
		em.createQuery("delete from Tadresy").executeUpdate();
		em.createQuery("delete from Tklient").executeUpdate();
		em.createQuery("delete from Tpanstwa").executeUpdate();
	}

	@Override
	public long getCountryCount() {
		return em.createQuery("select count(a) from Tpanstwa a", Long.class).getSingleResult();
	}

	@Override
	public long getAddressCount() {
		long addressCount = em.createQuery("select count(a) from Tadresy a", Long.class)
				.getSingleResult();
		return addressCount;
	}

	@Override
	public long getClientCount() {
		long clientCount = em.createQuery("select count(a) from Tklient a", Long.class)
				.getSingleResult();
		return clientCount;
	}

	@Override
	public long getPersonCount() {
		long personCount = em.createQuery("select count(a) from Tosoby a", Long.class)
				.getSingleResult();
		return personCount;
	}

	@Override
	public String getUserName(int clientId) {
		CriteriaQuery<String> cq = em.getCriteriaBuilder().createQuery(String.class);
		Root<Tosoby> person = cq.from(Tosoby.class);
		Join<Tosoby, Tklient> client = person.join(Tosoby_.tklient);
		cq.select(cb.concat(person.get(Tosoby_.imie),
				cb.concat(" ", cb.concat(person.get(Tosoby_.nazwisko),
						cb.concat(" (", cb.concat(client.get(Tklient_.email), ")"))))));
		cq.where(cb.equal(client.get(Tklient_.IDKlienta), clientId));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		if (list.size() > 0) {
			String first = (String) list.get(0);
			return first;
		}
		return null;
	}

	@Override
	public UserData getUserData(int clientId) {
		UserData data = null;
		Tklient client = getClient(clientId);
		if (client != null) {
			if (client.getBFirma()) {
				CriteriaQuery<Tfirmy> cq = em.getCriteriaBuilder().createQuery(Tfirmy.class);
				Root<Tfirmy> firm = cq.from(Tfirmy.class);
				Join<Tfirmy, Tklient> clientJoin = firm.join(Tfirmy_.tklient);
				cq.where(cb.equal(clientJoin.get(Tklient_.IDKlienta), clientId));
				javax.persistence.Query q = em.createQuery(cq);
				List<?> list = q.getResultList();
				if (list.size() > 0) {
					Tfirmy first = (Tfirmy) list.get(0);
					Tadresy address = first.getTadresy();
					data = UserData.createFirmData(client, address, first);
				}
			} else {
				CriteriaQuery<Tosoby> cq = em.getCriteriaBuilder().createQuery(Tosoby.class);
				Root<Tosoby> person = cq.from(Tosoby.class);
				Join<Tosoby, Tklient> clientJoin = person.join(Tosoby_.tklient);
				cq.where(cb.equal(clientJoin.get(Tklient_.IDKlienta), clientId));
				javax.persistence.Query q = em.createQuery(cq);
				List<?> list = q.getResultList();
				if (list.size() > 0) {
					Tosoby first = (Tosoby) list.get(0);
					Tadresy address = first.getTadresy();
					data = UserData.createPersonData(client, address, first);
				}
			}
		}
		return data;
	}

	@Override
	public void updateProfileData(int clientId, UserData userData) {
		Tpanstwa country = getCountry(userData.getCountry());
		userData.getAddressData(country);
		userData.getClientData();
		if (userData.isFirm()) {
			Tfirmy firm = userData.getFirm();
			em.merge(firm);
		} else {
			Tosoby person = userData.getPerson();
			em.merge(person);
		}

	}

	@Override
	public void updatePassword(int clientId, String newHash) {
		Tklient client = getClient(clientId);
		client.setHaslo(newHash);
		em.merge(client);
	}

	@Override
	public void reserveOffer(Integer clientId, int offerId) {
		Tosoby person = getPerson(clientId);
		if (person == null) {
			throw new IllegalStateException("person is not found");
		}
		Toferta offer = getOffer(offerId);
		if (offer == null) {
			throw new IllegalStateException("offer is not found");
		}
		Tkliencioferty entity = new Tkliencioferty(new Date(), null, person.getTklient(), offer,
				person);
		em.persist(entity);
	}

	@Override
	public boolean isOfferReserved(Integer clientId, int offerId) {
		CriteriaQuery<Tkliencioferty> cq = em.getCriteriaBuilder()
				.createQuery(Tkliencioferty.class);
		Root<Tkliencioferty> clientOffer = cq.from(Tkliencioferty.class);
		Join<Tkliencioferty, Toferta> offer = clientOffer.join(Tkliencioferty_.toferta);
		Join<Tkliencioferty, Tklient> client = clientOffer.join(Tkliencioferty_.tklient);
		cq.where(cb.and(cb.equal(client.get(Tklient_.IDKlienta), clientId),
				cb.equal(offer.get(Toferta_.IDOferty), offerId)));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		return list.size() > 0;
	}

	@Override
	public void reserveAttraction(Integer clientId, int attractionId) {
		Tosoby person = getPerson(clientId);
		if (person == null) {
			throw new IllegalStateException("person is not found");
		}
		Tatrakcjeuslugi attraction = getAttraction(attractionId);
		if (attraction == null) {
			throw new IllegalStateException("attraction is not found");
		}
		Tklienciatrakcje entity = new Tklienciatrakcje(new Date(), null, attraction,
				person.getTklient(), person);
		em.persist(entity);
	}

	@Override
	public boolean isAttractionReserved(int clientId, int attractionId) {
		CriteriaQuery<Tklienciatrakcje> cq = em.getCriteriaBuilder()
				.createQuery(Tklienciatrakcje.class);
		Root<Tklienciatrakcje> clientOffer = cq.from(Tklienciatrakcje.class);
		Join<Tklienciatrakcje, Tatrakcjeuslugi> attraction = clientOffer
				.join(Tklienciatrakcje_.tatrakcjeuslugi);
		Join<Tklienciatrakcje, Tklient> client = clientOffer.join(Tklienciatrakcje_.tklient);
		cq.where(cb.and(cb.equal(client.get(Tklient_.IDKlienta), clientId),
				cb.equal(attraction.get(Tatrakcjeuslugi_.IDAtrakcjiUslugi), attractionId)));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		return list.size() > 0;
	}

	@Override
	public void markAsRealised(int offerId) {
		Toferta offer = getOffer(offerId);
		Thistoriaofert ho = new Thistoriaofert(offer);
		em.persist(ho);
		em.remove(offer);
	}

	@Override
	public boolean isAttractionHistoryReserved(int clientId, int attractionId) {
		CriteriaQuery<Tklienciatrakcjehistoria> cq = em.getCriteriaBuilder()
				.createQuery(Tklienciatrakcjehistoria.class);
		Root<Tklienciatrakcjehistoria> clientOffer = cq.from(Tklienciatrakcjehistoria.class);
		Join<Tklienciatrakcjehistoria, Tatrakcjehistoria> attraction = clientOffer
				.join(Tklienciatrakcjehistoria_.tatrakcjehistoria);
		Join<Tklienciatrakcjehistoria, Tklient> client = clientOffer
				.join(Tklienciatrakcjehistoria_.tklient);
		cq.where(cb.and(cb.equal(client.get(Tklient_.IDKlienta), clientId),
				cb.equal(attraction.get(Tatrakcjehistoria_.IDAtrakcjiUslugi), attractionId)));
		javax.persistence.Query q = em.createQuery(cq);
		List<?> list = q.getResultList();
		return list.size() > 0;
	}

	@Override
	public Toferta getOfferById(int offerId) {
		javax.persistence.criteria.CriteriaQuery<Toferta> cq = em.getCriteriaBuilder()
				.createQuery(Toferta.class);
		Root<Toferta> root = cq.from(Toferta.class);
		cq.select(root);
		cq.where(cb.equal(root.get(Toferta_.IDOferty), offerId));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(1);
		return (Toferta) q.getSingleResult();
	}
	
	@Override
	public Thistoriaofert getOfferHistoryById(int offerId) {
		javax.persistence.criteria.CriteriaQuery<Thistoriaofert> cq = em.getCriteriaBuilder()
				.createQuery(Thistoriaofert.class);
		Root<Thistoriaofert> root = cq.from(Thistoriaofert.class);
		cq.select(root);
		cq.where(cb.equal(root.get(Thistoriaofert_.IDOferty), offerId));
		javax.persistence.Query q = em.createQuery(cq);
		q.setMaxResults(1);
		return (Thistoriaofert) q.getSingleResult();
	}
}
