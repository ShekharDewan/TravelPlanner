package edu.mta.groupa.planner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The Trip object is an entity that generically maps to a database via
 * JPA. It has a many to one relationship with the Trip entity.
 * 
 * @author Maryse
 *
 */
@Entity
public class Trip {

	
	/**
	 * The id is the unique row id for the entry used internally by JPA
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/**
	 * The title is the string that the user will give to his trip
	 */
	@Column(nullable = false, unique = true)
	private String title;
	
	/**
	 * a general description of the trip
	 */
	@Column(nullable = true)
	private String description;

	/**
	 * start is the start date of the trip
	 */
	@Column(nullable = false)
	private Date start;
	
	/**
	 * end is the end date of the trip
	 */
	@Column(nullable = true)
	private Date end;
	
	/**
	 * notes are strings that the user can add to a trip if he needs
	 * to add notes.
	 */
	@Column
	private String notes;
	
	/**
	 * destinations is a List of destinations that will contain the 
	 * destinations that the user will input for his trip.
	 */
	@ElementCollection
	private List<String> destinations = new ArrayList<String>();
	
	/**
	 * reservations is a List of reservations that the user input for his trip
	 */
	@OneToMany
    private List<Reservation> reservations = new ArrayList<Reservation>();
	
	/**
	 * accommodations is a List of accommodations that the user input for his trip
	 */
	@OneToMany
    private List<Accommodation> accomodations = new ArrayList<Accommodation>();
	
	/**
	 * itineraries is the List of the daily itinerary of the user.
	 */
	@OneToMany(cascade = {CascadeType.ALL})
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();
	
	/**
	 * getTotalPrice calculates the total prices of all accommodations and 
	 * reservations for this trip.
	 * @return the total price of all accommodations and reservations.
	 */
	public double getTotalPrice(){
		double total = 0;
		for (Accommodation a: this.accomodations){
			total += a.getPrice();
		}
		for(Reservation r: this.reservations){
			total += r.getPrice();
		}
		return total;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<String> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<String> destinations) {
		this.destinations = destinations;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Accommodation> getAccomodations() {
		return accomodations;
	}

	public void setAccomodations(List<Accommodation> accomodations) {
		this.accomodations = accomodations;
	}

	public List<Itinerary> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", title=" + title + ", start=" + start + ", end=" + end + ", notes=" + notes
				+ ", destinations=" + destinations + ", reservations=" + reservations + ", accomodations="
				+ accomodations + ", itineraries=" + itineraries + "]";
	}
	
	
	
}