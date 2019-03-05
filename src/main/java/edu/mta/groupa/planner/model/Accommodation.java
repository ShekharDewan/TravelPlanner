package edu.mta.groupa.planner.model;

import java.util.Date;

import javax.persistence.CascadeType;
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
 * The Accommodation object is an entity that generically maps to a database via
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
	
	public Accommodation(){
	}
	
	public Accommodation(Trip trip, Address address, String title, Date checkIn, Date checkOut, String notes,
			Double price) {
		super();
		this.trip = trip;
		this.address = address;
		this.title = title;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.notes = notes;
		this.price = price;
	}

	/**
	 * This has a many to one relationship with a Trip. 
	 * In other words, there can be many accommodations for each trip.
	 */
	@ManyToOne(optional = true, cascade = {CascadeType.ALL})
	@JoinColumn(name = "TRIP_ID")
	private Trip trip;

	/**
	 * This has a one to one relationship with address.
	 * Every accommodation has a single address.
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "address_id")
	private Address address;

	/**
	 * The title is a string populated by the user. For example
	 * it could be used to put the name of the hotel.
	 */
	@Column(nullable = false)
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
	@Column(length=2048)
	private String notes;

	/**
	 * price is the cost of the accommodation
	 */
	@Column (precision =2)
	private Double price;
	
	
	
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public String getNotes() {
		return notes;
	}

	public Double getPrice() {
		return price;
	}

	public Address getAddress() {
		return address;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
}
