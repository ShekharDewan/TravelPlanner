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
	
	public Address(){
		
	}
	
	public Address(Integer civicNumber, String street, String city, String province, String country, String code, Double latitude,
			Double longitude) {
		super();
		this.civicNumber = civicNumber;
		this.street = street;
		this.city = city;
		this.province = province;
		this.country = country;
		this.code = code;
		this.latitude = latitude;
		this.longitude = longitude;
	}

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
	 * province is the name of the province of the address
	 */
	@Column
	private String province;

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

	

	public Integer getCivicNumber() {
		return civicNumber;
	}

	public void setCivicNumber(Integer civicNumber) {
		this.civicNumber = civicNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
}
