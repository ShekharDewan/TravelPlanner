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
 * The Reservation object is an entity that generically maps to a database via
 * JPA. It has a many to one relationship with the Trip entity.
 * 
 * @author Maryse
 *
 */
@Entity
public class Reservation {
	
	
	
	/**
	 * The id is the unique row id for the entry used internally by JPA
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public Reservation(){
		super();
	}
	
	public Reservation(Trip trip, Address address, String title, Date date, String notes, Double price,
			String confirmation) {
		super();
		this.trip = trip;
		this.address = address;
		this.title = title;
		this.date = date;
		this.notes = notes;
		this.price = price;
		this.confirmation = confirmation;
	}
		
	/**
	 * Reservation has a many-to-one relationship with a trip.
	 * There can be many reservations for one trip.
	 */
	@ManyToOne(optional=true, cascade = {CascadeType.ALL}) 
    @JoinColumn(name="TRIP_ID")
    private Trip trip;

	
	/**
	 * The address has a one-to-one relationship with the reservation
	 */
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ADDRESS_ID")
	private Address address;
	
	/**
	 * The title is a string containing the title of the reservation 
	 */
	@Column
	private String title;
	
	/**
	 * The date is a Date type that contains the date of the reservation
	 */
	@Column
	private Date date;
	
	/**
	 * The time is a Date type that contains the time of the reservation
	 */
	@Column
	private String reserveTime;
	
	/**
	 * The notes is a String that contains the notes about the reservation
	 */
	@Column(length=2048)
	private String notes;
	
	/**
	 * The price is a Double type that contains the price of the reservation
	 */
	@Column
	private Double price;
	
	/**
	 * confirmation is a String that contains the confirmation number of the reservation
	 */
	@Column
	private String confirmation;
	
	/**
	 * type is a String that has the type of reservation that is booked.
	 */
	@Column
	private String type;
	
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNotes() {
		return notes;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public long getId() {
		return id;
	}

	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	
	
}
