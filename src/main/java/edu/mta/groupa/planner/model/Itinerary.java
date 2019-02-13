package edu.mta.groupa.planner.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The Itinerary object is an entity that generically maps to a database via
 * JPA. It has a many to one relationship with the Trip entity.
 * 
 * @author Maryse
 *
 */
@Entity
public class Itinerary {
	
	/**
	 * The id is the unique row id for the entry used internally by JPA
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
		
	/**
	 * The itinerary has a many to one relationship with a trip.
	 * There is one itinerary per day of the trip.
	 */
	@ManyToOne(optional=true) 
    @JoinColumn(name="TRIP_ID")
    private Trip trip;
	
	
	/**
	 * date is the date of the itinerary
	 */
	@Column(nullable = false)
	private Date date;
	
	/**
	 * notes is a string used to write notes about the itinerary
	 */
	@Column
	private String notes;

}
