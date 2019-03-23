package edu.mta.groupa.planner;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.Reservation;
import edu.mta.groupa.planner.repository.ReservationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationTest {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testId() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setNotes("my notes");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getId(), target.getId());
	}
	
	@Test
	public void testNotes() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setNotes("my notes");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getNotes(), target.getNotes());
	}
	
	@Test
	public void testConfirmation() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setConfirmation("12345");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getConfirmation(), target.getConfirmation());
	}
	
	@Test
	public void testPrice() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setPrice(50.50);
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getPrice(), target.getPrice());
	}
	
	@Test
	public void testReserveTime() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setReserveTime("5:00pm");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getReserveTime(), target.getReserveTime());
	}
	
	@Test
	public void testTitle() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setTitle("title");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getTitle(), target.getTitle());
	}
	
	@Test
	public void testType() {
		// given
		Reservation target = new Reservation();
		target.setDate(new Date());
		target.setType("type");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Reservation found = reservationRepository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getType(), target.getType());
	}
}
