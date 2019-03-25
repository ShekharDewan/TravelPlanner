package edu.mta.groupa.planner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Trip object is an entity that generically maps to a database via
 * JPA. It has a many to one relationship with the Trip entity.
 * 
 * @author Maryse
 *
 */
@Entity
@Table(name = "trip")
public class Trip {

	
	/**
	 * The id is the unique row id for the entry used internally by JPA
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = true)
	private long userID;
	
	/**
	 * The title is the string that the user will give to his trip
	 */
	@Column(nullable = false, length = 25)
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
	@Column(length=2048)
	private String notes;
	
	/**
	 * destinations is a List of destinations that will contain the 
	 * destinations that the user will input for his trip.
	 */
	@Column
	private String destinations;
	
	/**
	 * reservations is a List of reservations that the user input for his trip
	 */
	@OneToMany(cascade = {CascadeType.ALL})
    private List<Reservation> reservations = new ArrayList<Reservation>();
	
	/**
	 * accommodations is a List of accommodations that the user input for his trip
	 */
	@OneToMany(cascade = {CascadeType.ALL})
    private List<Accommodation> accommodations = new ArrayList<Accommodation>();
	
	/**
	 * itineraries is the List of the daily itinerary of the user.
	 */
	@OneToMany(cascade = {CascadeType.ALL})
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();
	
	/**
	 * getTotalPrice calculates the total prices of all accommodations and 
	 * reservations for this trip.
	 * @return the total price of all accommodations and reservations.
	
	public double calculateTotalPrice(){
		double total = 0;
		for (Accommodation a: this.accomodations){
			total += a.getPrice();
		}
		for(Reservation r: this.reservations){
			total += r.getPrice();
		}
		return total;
	} */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
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

	public String getDestinations() {
		return destinations;
	}

	public void setDestinations(String destinations) {
		this.destinations = destinations;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Reservation> getReservations() {
		Collections.sort(reservations, new Comparator<Reservation>() {

			@Override
			public int compare(Reservation res1, Reservation res2) {
				return res1.getDate().compareTo(res2.getDate());
			}	
		});
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Accommodation> getAccommodations() {
		Collections.sort(accommodations, new Comparator<Accommodation>() {

			@Override
			public int compare(Accommodation accomm1, Accommodation accomm2) {
				return accomm1.getCheckIn().compareTo(accomm2.getCheckIn());
			}	
		});
		return accommodations;
	}

	public void setAccomodations(List<Accommodation> accommodations) {
		this.accommodations = accommodations;
	}

	public List<Itinerary> getItineraries() {
		Collections.sort(itineraries, new Comparator<Itinerary>() {

			@Override
			public int compare(Itinerary itin1, Itinerary itin2) {
				return itin1.getDate().compareTo(itin2.getDate());
			}	
		});
		return itineraries;
	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}

	@Override
	public String toString() {
		return "Trip [id=" + id + ", title=" + title + ", start=" + start + ", end=" + end + ", notes=" + notes
				+ ", destinations=" + destinations + ", reservations=" + reservations + ", accommodations="
				+ accommodations + ", itineraries=" + itineraries + "]";
	}
	
	
	
}