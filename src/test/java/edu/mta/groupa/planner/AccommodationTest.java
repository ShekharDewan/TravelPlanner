package edu.mta.groupa.planner;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.Accommodation;
import edu.mta.groupa.planner.repository.AccommodationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccommodationTest {

	@Autowired
	private AccommodationRepository accommodationRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testTitle() {
		// given
		Accommodation accom = new Accommodation();
		accom.setTitle("Test title");
		accom.setCheckIn(new Date());
		accom.setCheckOut(new Date());
		entityManager.persist(accom);
		entityManager.flush();

		// when
		Accommodation found = accommodationRepository.findByTitle(accom.getTitle()).get(0);

		// then
		Assert.assertEquals(found.getTitle(), accom.getTitle());
	}
	
	@Test
	public void testCheckIn() {
		// given
		Accommodation accom = new Accommodation();
		accom.setTitle("Test title");
		accom.setCheckIn(new Date());
		accom.setCheckOut(new Date());
		entityManager.persist(accom);
		entityManager.flush();

		// when
		Accommodation found = accommodationRepository.findById(accom.getId()).get();

		// then
		Assert.assertEquals(found.getCheckIn(), accom.getCheckIn());
	}
	
	@Test
	public void testCheckOut() {
		// given
		Accommodation accom = new Accommodation();
		accom.setTitle("Test title");
		accom.setCheckIn(new Date());
		accom.setCheckOut(new Date());
		entityManager.persist(accom);
		entityManager.flush();

		// when
		Accommodation found = accommodationRepository.findById(accom.getId()).get();

		// then
		Assert.assertEquals(found.getCheckOut(), accom.getCheckOut());
	}
	
	@Test
	public void testNotes() {
		// given
		Accommodation accom = new Accommodation();
		accom.setTitle("Test title");
		accom.setCheckIn(new Date());
		accom.setCheckOut(new Date());
		accom.setNotes("test notes");
		entityManager.persist(accom);
		entityManager.flush();

		// when
		Accommodation found = accommodationRepository.findById(accom.getId()).get();

		// then
		Assert.assertEquals(found.getNotes(), accom.getNotes());
	}
	
	@Test
	public void testPrice() {
		// given
		Accommodation accom = new Accommodation();
		accom.setTitle("Test title");
		accom.setCheckIn(new Date());
		accom.setCheckOut(new Date());
		accom.setPrice(48.99);
		entityManager.persist(accom);
		entityManager.flush();

		// when
		Accommodation found = accommodationRepository.findById(accom.getId()).get();

		// then
		Assert.assertEquals(found.getPrice(), accom.getPrice());
	}
	
}
