package edu.mta.groupa.planner.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The Accomodation object is an entity that generically maps to a database via
 * JPA. It has a many to one relationship with the Trip entity.
 * 
 * @author Maryse
 *
 */
@Entity
public class Accommodation {

	/**
	 * The id is the unique row id for the entry used internally by JPA
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * This has a many to one relationship with a Trip. 
	 * In other words, there can be many accommodations for each trip.
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "TRIP_ID")
	private Trip trip;

	/**
	 * This has a one to one relationship with address.
	 * Every accommodation has a single address.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;

	/**
	 * The title is a string populated by the user. For example
	 * it could be used to put the name of the hotel.
	 */
	@Column(nullable = false, unique = true)
	private String title;

	/**
	 * checkIn is the date that the user will check in to the accommodation
	 */
	@Column(nullable = false)
	private Date checkIn;

	/**
	 * checkOut is the date that the user will check out of the accommodation
	 */
	@Column(nullable = false)
	private Date checkOut;

	/**
	 * notes can be used by the user to add additional comments about this entry.
	 */
	@Column
	private String notes;

	/**
	 * price is the cost of the accommodation
	 */
	@Column
	private Double price;

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
