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
public class Accomodation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "TRIP_ID")
	private Trip trip;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false)
	private Date checkIn;

	@Column(nullable = false)
	private Date checkOut;

	@Column
	private String notes;

	@Column
	private Double price;

}
