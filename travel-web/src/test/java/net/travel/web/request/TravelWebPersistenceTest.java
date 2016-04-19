package net.travel.web.request;

import java.util.Collection;
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
import net.travel.ejb.data.model.Tklient;
import net.travel.ejb.data.model.Tosoby;
import net.travel.ejb.data.model.Tpanstwa;
import net.travel.ejb.request.RequestBean;
import net.travel.ejb.request.RequestBeanRemote;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
public class TravelWebPersistenceTest {
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

	@Before
	public void preparePersistenceTest() throws Exception {
		try {
			clearData();
			insertData();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		startTransaction();
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
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	public void addPerson() throws Exception {
		assertCounts(1, 1, 0, 0);
		Tpanstwa country = request.getCountry("Polska");
		Tadresy address = new Tadresy("ul. Wolska", "2101020", "31-100", "City10", "region",
				"102030", country);
		Tklient client = new Tklient(false, "alice@ta.com", "abc123");
		Tosoby person = new Tosoby(false, "Jack", "Smith", "186-190-01-20", address, client);
		int personSize = client.getTosobies() == null ? 0 : client.getTosobies().size();
		Assert.assertEquals(1, personSize);
		request.addPerson(person);

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
}