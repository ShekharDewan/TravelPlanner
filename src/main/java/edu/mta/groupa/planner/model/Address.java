package edu.mta.groupa.planner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private Integer civicNumber;
	
	@Column
	private String street;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String code;
	
	@Column
	private Double latitude;
	
	@Column
	private Double longitude;

}
