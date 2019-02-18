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
		
	@ManyToOne(optional=true, cascade = {CascadeType.ALL}) 
    @JoinColumn(name="TRIP_ID")
    private Trip trip;

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "ADDRESS_ID")
	private Address address;
	
	@Column
	private String title;
	
	@Column
	private Date date;
	
	@Column(length=2048)
	private String notes;
	
	@Column
	private Double price;
	
	@Column
	private String confirmation;
	
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
}
