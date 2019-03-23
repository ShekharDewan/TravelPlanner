package edu.mta.groupa.planner;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.Trip;
import edu.mta.groupa.planner.repository.TripRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TripTest {

	@Autowired
	private TripRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testId() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setDescription("my trip to the moon");
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getId(), target.getId());
	}
	
	@Test
	public void testDescription() {
		// given
		Trip target = new Trip();
		target.setDescription("my trip to the moon");
		target.setTitle("title");
		target.setStart(new Date());
		target.setEnd(new Date());
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getDescription(), target.getDescription());
	}
	
	@Test
	public void testDestinations() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setDestinations("destinations");
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getDestinations(), target.getDestinations());
	}
	
	@Test
	public void testEnd() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getEnd(), target.getEnd());
	}
	
	@Test
	public void testNotes() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setNotes("notes");
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getNotes(), target.getNotes());
	}
	
	@Test
	public void testStart() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getStart(), target.getStart());
	}
	
	@Test
	public void testTitle() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getTitle(), target.getTitle());
	}
	
	@Test
	public void testUserId() {
		// given
		Trip target = new Trip();
		target.setStart(new Date());
		target.setEnd(new Date());
		target.setUserID(42);
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Trip found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getUserID(), target.getUserID());
	}
	
}
