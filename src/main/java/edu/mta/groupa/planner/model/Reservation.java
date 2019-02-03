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

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
		
	@ManyToOne(optional=true) 
    @JoinColumn(name="TRIP_ID")
    private Trip trip;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
	private Address address;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false)
	private Date date;
	
	@Column
	private String notes;
	
	@Column
	private Double price;
	
	@Column
	private String confirmation;
}
