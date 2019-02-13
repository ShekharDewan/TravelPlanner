package edu.mta.groupa.planner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Address entity encapsulates all of the field associated with a civic
 * address, used in several parent entities.
 * 
 * @author Maryse
 *
 */
@Entity
public class Address {

	/**
	 * The id is the unique row id for the entry used internally by JPA
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * civicNumber is the civic number of the address
	 */
	@Column
	private Integer civicNumber;

	/**
	 * street is the name of the street of the address
	 */
	@Column
	private String street;

	/**
	 * city is the name of the city of the address
	 */
	@Column
	private String city;

	/**
	 * country is the name of the country in the address
	 */
	@Column
	private String country;

	/**
	 * code is the postal code or zip code of the address
	 */
	@Column
	private String code;

	/**
	 * latitude is the latitude of the address
	 */
	@Column
	private Double latitude;

	/**
	 * longitude is the longitude of the address
	 */
	@Column
	private Double longitude;

}
