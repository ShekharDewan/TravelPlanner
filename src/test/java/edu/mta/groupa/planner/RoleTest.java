package edu.mta.groupa.planner;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.Role;
import edu.mta.groupa.planner.repository.RoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleTest {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void whenFindByID_thenReturnItinerary() {
		// given
		Role target = new Role();
		target.setName("Wee Fergus");
		entityManager.persist(target);
		entityManager.flush();

		// when
		Role found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getId(), target.getId());
	}
}
