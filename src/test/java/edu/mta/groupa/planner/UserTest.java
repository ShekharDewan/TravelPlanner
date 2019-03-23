package edu.mta.groupa.planner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mta.groupa.planner.model.User;
import edu.mta.groupa.planner.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

	@Autowired
	private UserRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testId() {
		// given
		User target = new User();
		target.setFirstName("Todd");
		target.setLastName("Codrington");
		target.setPassword("password");
		target.setEmail("test@test.com");
		entityManager.persist(target);
		entityManager.flush();

		// when
		User found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getId(), target.getId());
	}
	
	@Test
	public void testFirstName() {
		// given
		User target = new User();
		target.setFirstName("Todd");
		target.setLastName("Codrington");
		target.setPassword("password");
		target.setEmail("test@test.com");
		entityManager.persist(target);
		entityManager.flush();

		// when
		User found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getFirstName(), target.getFirstName());
	}
	
	@Test
	public void testLastName() {
		// given
		User target = new User();
		target.setFirstName("Todd");
		target.setLastName("Codrington");
		target.setPassword("password");
		target.setEmail("test@test.com");
		entityManager.persist(target);
		entityManager.flush();

		// when
		User found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getLastName(), target.getLastName());
	}
	
	@Test
	public void testEmail() {
		// given
		User target = new User();
		target.setFirstName("Todd");
		target.setLastName("Codrington");
		target.setEmail("test@test.com");
		target.setPassword("password");
		entityManager.persist(target);
		entityManager.flush();

		// when
		User found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getEmail(), target.getEmail());
	}
	
	@Test
	public void testPassword() {
		// given
		User target = new User();
		target.setFirstName("Todd");
		target.setLastName("Codrington");
		target.setPassword("testPassword");
		target.setEmail("test@test.com");
		entityManager.persist(target);
		entityManager.flush();

		// when
		User found = repository.findById(target.getId()).get();

		// then
		Assert.assertEquals(found.getPassword(), target.getPassword());
	}
	
}
