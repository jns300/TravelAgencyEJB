package net.travel.ejb.request;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import net.travel.ejb.data.model.Tadresy;
import net.travel.ejb.data.model.Tadresy_;
import net.travel.ejb.data.model.Tatrakcjeuslugi;
import net.travel.ejb.data.model.Tklienciatrakcje;
import net.travel.ejb.data.model.Tkliencioferty;
import net.travel.ejb.data.model.Tklient;
import net.travel.ejb.data.model.Toferta;
import net.travel.ejb.data.model.Tosoby;
import net.travel.ejb.data.model.Tpanstwa;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TravelEjbPersistenceTest {
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(false, Tadresy.class.getPackage(), RequestBean.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@EJB
	private RequestBeanRemote request;

	private boolean isCommitted;

	@Before
	public void preparePersistenceTest() throws Exception {
		try {
			clearData();
			insertData();
			startTransaction();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		em.createQuery("delete from Tklienciatrakcjehistoria").executeUpdate();
		em.createQuery("delete from Tklienciofertyhistoria").executeUpdate();
		em.createQuery("delete from Tatrakcjehistoria").executeUpdate();
		em.createQuery("delete from Thistoriaofert").executeUpdate();

		em.createQuery("delete from Tklienciatrakcje").executeUpdate();
		em.createQuery("delete from Tkliencioferty").executeUpdate();
		em.createQuery("delete from Tatrakcjeuslugi").executeUpdate();
		em.createQuery("delete from Toferta").executeUpdate();

		em.createQuery("delete from Tosoby").executeUpdate();
		em.createQuery("delete from Tadresy").executeUpdate();
		em.createQuery("delete from Tklient").executeUpdate();
		em.createQuery("delete from Tfirmy").executeUpdate();
		em.createQuery("delete from Tpanstwa").executeUpdate();
		utx.commit();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		Tpanstwa country = new Tpanstwa("Polska");
		em.persist(country);
		Tadresy address = new Tadresy("ul. Wolska", "2101020", "31-100", "Cracov", "małpolska",
				"102030", country);
		em.persist(address);
		utx.commit();
		// clear the persistence context (first-level cache)
		em.clear();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
		isCommitted = false;
	}

	@After
	public void commitTransaction() throws Exception {
		if (!isCommitted) {
			utx.commit();
			isCommitted = true;
		}
	}

	@Test
	public void addPerson() throws Exception {
		assertCounts(1, 1, 0, 0);

		Tklient clientEntity;
		Tosoby personEntity;
		Tpanstwa countryEntity;
		Tadresy addressEntity;

		countryEntity = request.getCountry("Polska");
		addressEntity = new Tadresy("ul. Wolska", "2101020", "31-100", "City10", "region",
				"102030", countryEntity);
		clientEntity = new Tklient(false, "alice@ta.com", "abc123");
		personEntity = new Tosoby(false, "Jack", "Smith", "186-190-01-20", addressEntity,
				clientEntity);
		int personSize = clientEntity.getTosobies() == null ? 0 : clientEntity.getTosobies().size();
		Assert.assertEquals(1, personSize);
		request.addPerson(personEntity);

		assertCounts(1, 2, 1, 1);

		// Assert that manyToOne lists are populated
		List<Tosoby> personList = em.createQuery(
				"select o from Tosoby o where o.nazwisko like 'Smith'", Tosoby.class)
				.getResultList();
		Tosoby foundPerson = personList.get(0);
		Assert.assertEquals(1, foundPerson.getTklient().getTosobies().size());

		List<Tklient> clientList = em.createQuery(
				"select c from Tklient c where c.email like 'alice@ta.com'", Tklient.class)
				.getResultList();
		int personSize2 = clientList.get(0).getTosobies().size();
		Assert.assertEquals(1, personSize2);

		runUpdateTest();
	}

	private void runUpdateTest() throws Exception {
		try {
			String query = "select o from Tosoby o where o.nazwisko like 'Smith'";
			List<Tosoby> personList = em.createQuery(query, Tosoby.class).getResultList();
			Tosoby foundPerson = personList.get(0);
			int startClientId = foundPerson.getTklient().getIDKlienta();
			foundPerson.getTklient().setEmail("ela@ta.eu");
			foundPerson.setImie("Tom");
			foundPerson.getTadresy().setKod("33-100");
			em.merge(foundPerson);

			commitTransaction();

			// Clear the cache
			// NOTICE: transaction has to be committed first so that the entity
			// manager can see the changes.
			clearCache();

			startTransaction();
			List<Tosoby> personList2 = em.createQuery(query, Tosoby.class).getResultList();
			Tosoby newPerson = personList2.get(0);
			Assert.assertNotSame(foundPerson, newPerson);
			Assert.assertEquals("Tom", newPerson.getImie());
			Assert.assertEquals("33-100", newPerson.getTadresy().getKod());
			Assert.assertEquals("ela@ta.eu", newPerson.getTklient().getEmail());
			Assert.assertEquals(startClientId, newPerson.getTklient().getIDKlienta());
		} catch (Exception ex) {
			throw ex;
		}
	}

	private void clearCache() {
		em.clear();
		em.getEntityManagerFactory().getCache().evictAll();
	}

	private void assertCounts(int inCountryCount, int inAddressCount, int inClientCount,
			int inPersonCount) {
		long countryCount = em.createQuery("select count(a) from Tpanstwa a", Long.class)
				.getSingleResult();
		long addressCount = em.createQuery("select count(a) from Tadresy a", Long.class)
				.getSingleResult();
		long clientCount = em.createQuery("select count(a) from Tklient a", Long.class)
				.getSingleResult();
		long personCount = em.createQuery("select count(a) from Tosoby a", Long.class)
				.getSingleResult();
		Assert.assertEquals(inCountryCount, countryCount);
		Assert.assertEquals(inAddressCount, addressCount);
		Assert.assertEquals(inClientCount, clientCount);
		Assert.assertEquals(inPersonCount, personCount);
	}

	@Test
	public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
		// given
		String fetchingAllGamesInJpql = "select a from Tadresy a where a.miasto like 'Cracov' order by a.miasto";

		// when
		List<Tadresy> games = em.createQuery(fetchingAllGamesInJpql, Tadresy.class).getResultList();

		// then
		assertAddressList(games);
	}

	private static void assertAddressList(Collection<Tadresy> addresses) {
		Assert.assertEquals(1, addresses.size());
		Tadresy[] array = addresses.toArray(new Tadresy[0]);
		Assert.assertEquals("ul. Wolska", array[0].getAdres());
		Assert.assertEquals("Polska", array[0].getTpanstwa().getNazwaPanstwa());
	}

	@Test
	public void shouldFindAllGamesUsingCriteriaApi() throws Exception {
		// given
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tadresy> criteria = builder.createQuery(Tadresy.class);

		Root<Tadresy> address = criteria.from(Tadresy.class);
		criteria.select(address);
		criteria.where(builder.equal(address.get(Tadresy_.miasto), "Cracov"));
		criteria.orderBy(builder.asc(address.get(Tadresy_.miasto)));

		// when
		List<Tadresy> addressList = em.createQuery(criteria).getResultList();

		// then
		assertAddressList(addressList);
	}

	@InSequence(1)
	@Test
	public void addOffer() {
		try {
			Tklient clientEntity;
			Tosoby personEntity;
			Tpanstwa countryEntity;
			Tadresy addressEntity;

			countryEntity = request.getCountry("Polska");
			addressEntity = new Tadresy("ul. Wolska", "2101020", "31-100", "City10", "region",
					"102030", countryEntity);
			clientEntity = new Tklient(false, "alice@ta.com", "abc123");
			personEntity = new Tosoby(false, "Jack", "Smith", "186-190-01-20", addressEntity,
					clientEntity);
			em.persist(addressEntity);
			em.persist(countryEntity);
			em.persist(clientEntity);
			em.persist(personEntity);

			Toferta offer = new Toferta(false, new Date(), 2, 3, BigDecimal.valueOf(10), "Paris",
					"Kraków", null, countryEntity);
			Tatrakcjeuslugi attraction = new Tatrakcjeuslugi(2, BigDecimal.valueOf(5),
					"Zwiedzanie zamku", null);
			attraction.addTklienciatrakcje(new Tklienciatrakcje(new Date(), new Date(), attraction,
					clientEntity, personEntity));

			offer.addTatrakcjeuslugi(attraction);
			offer.addTkliencioferty(new Tkliencioferty(new Date(), new Date(), clientEntity, offer,
					personEntity));
			em.persist(offer);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@InSequence(2)
	@Test
	public void offersMove() throws Exception {
		addOffer();
		assertSizesEquals(1, 0);
		List<Toferta> offers = request.getOffers();
		request.markAsRealised(offers.get(0).getIDOferty());

		// clear the cache and start a new transaction
		commitTransaction();
		clearCache();
		startTransaction();

		assertSizesEquals(0, 1);
	}

	private void assertSizesEquals(int size, int sizeHistory) {
		Assert.assertEquals(size, (long) em.createQuery("select count(t) from Toferta t")
				.getSingleResult());
		Assert.assertEquals(size, (long) em.createQuery("select count(t) from Tatrakcjeuslugi t")
				.getSingleResult());
		Assert.assertEquals(sizeHistory,
				(long) em.createQuery("select count(t) from Thistoriaofert t").getSingleResult());
		Assert.assertEquals(size, (long) em.createQuery("select count(t) from Tklienciatrakcje t")
				.getSingleResult());
		Assert.assertEquals(sizeHistory,
				(long) em.createQuery("select count(t) from Tklienciatrakcjehistoria t")
						.getSingleResult());
		Assert.assertEquals(size, (long) em.createQuery("select count(t) from Tkliencioferty t")
				.getSingleResult());
		Assert.assertEquals(sizeHistory,
				(long) em.createQuery("select count(t) from Tklienciofertyhistoria t")
						.getSingleResult());
		Assert.assertEquals(sizeHistory,
				(long) em.createQuery("select count(t) from Tatrakcjehistoria t").getSingleResult());
	}
}