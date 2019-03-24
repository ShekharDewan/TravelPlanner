package edu.mta.groupa.planner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import edu.mta.groupa.planner.model.Address;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressTest
{
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCivicNumber()
	{
		// given
		Address target = new Address();
		Integer civNum = new Integer(42);
		target.setCivicNumber(civNum);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		Integer found = target.getCivicNumber();
		
		// then
		Assert.assertEquals(found,civNum);
	}
	
	@Test
	public void testStreet()
	{
		// given
		Address target = new Address();
		String streetName = "Mulberry Lane";
		target.setStreet(streetName);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		String found = target.getStreet();
		
		// then
		Assert.assertEquals(found,streetName);
	}
	
	@Test
	public void testCity()
	{
		// given
		Address target = new Address();
		String cityName = "Measleton";
		target.setCity(cityName);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		String found = target.getCity();
		
		// then
		Assert.assertEquals(found,cityName);
	}
	
	@Test
	public void testProvince()
	{
		// given
		Address target = new Address();
		String provinceName = "Noob runswick";
		target.setProvince(provinceName);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		String found = target.getProvince();
		
		// then
		Assert.assertEquals(found,provinceName);
	}
	
	@Test
	public void testCountry()
	{
		// given
		Address target = new Address();
		String countryName = "Kanda";
		target.setCountry(countryName);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		String found = target.getCountry();
		
		// then
		Assert.assertEquals(found,countryName);
	}
	
	@Test
	public void testCode()
	{
		// given
		Address target = new Address();
		String codeName = "007";
		target.setCode(codeName);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		String found = target.getCode();
		
		// then
		Assert.assertEquals(found,codeName);
	}
	
	@Test
	public void testLatitude()
	{
		// given
		Address target = new Address();
		Double lat = new Double(3.1415926535897932384626433832795028841971693993751);
		target.setLatitude(lat);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		Double found = target.getLatitude();
		
		// then
		Assert.assertEquals(found,lat);
	}
	
	@Test
	public void testLongitude()
	{
		// given
		Address target = new Address();
		Double lon = new Double(2.718281828459045);
		target.setLongitude(lon);
		entityManager.persist(target);
		entityManager.flush();
		
		// when
		Double found = target.getLongitude();
		
		// then
		Assert.assertEquals(found,lon);
	}
}
