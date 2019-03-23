package edu.mta.groupa.planner;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.Itinerary;
import edu.mta.groupa.planner.repository.ItineraryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItineraryTest {

	@Autowired
	private ItineraryRepository itineraryRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testId() {
		// given
		Itinerary itin = new Itinerary();
		itin.setDate(new Date());
		itin.setNotes("my notes");
		entityManager.persist(itin);
		entityManager.flush();

		// when
		Itinerary found = itineraryRepository.findById(itin.getId()).get();

		// then
		Assert.assertEquals(found.getId(), itin.getId());
	}
	
	@Test
	public void testNotes() {
		// given
		Itinerary itin = new Itinerary();
		itin.setDate(new Date());
		itin.setNotes("my notes");
		entityManager.persist(itin);
		entityManager.flush();

		// when
		Itinerary found = itineraryRepository.findById(itin.getId()).get();

		// then
		Assert.assertEquals(found.getNotes(), itin.getNotes());
	}
}
