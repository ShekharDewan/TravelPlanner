package edu.mta.groupa.planner.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * A class which represents a User, and is registered as an Entity.
 * A User is used to register an account and login to use the 
 * application.
 * 
 * @author Jennifer
 *
 */
@Entity
@Table(name = "user")
public class User{
	/**
	 * The automatically generated id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	/**
	 * The User's email.
	 */
	@Column(nullable = false, unique = true)
	private String email;
	/**
	 * The User's first name.
	 */
	@Column(nullable = false)
	private String firstName;
	/**
	 * The User's last name.
	 */
	@Column(nullable = false)
	private String lastName;
	/**
	 * The User's password.
	 */
	@Column(nullable = false)
	private String password;
	/**
	 * The User's roles.
	 */
	@ManyToMany(cascade=CascadeType.ALL)
    private Set<Role> roles;
	/**
	 * Constructor.
	 */
	public User() {}	
	/**
	 * Constructor.
	 * 
	 * @param firstname	the User's first name.
	 * @param lastname	the User's last name.
	 * @param password	the User's password.
	 * @param email		the User's email.
	 */
	public User(String firstname, String lastname, 
			String password, String email) {
		this.email = email;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
	}
	/**
	 * Retrieves this User's first name.
	 * 
	 * @return	the first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Sets this User's first name.
	 * 
	 * @param firstName	the first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Retrieves this User's last name.
	 * 
	 * @return	the last name.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Sets this User's last name.
	 * 
	 * @param lastName	the last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Sets this User's password.
	 * 
	 * @param newPass	the password.
	 */
	public void setPassword(String newPass) {
		password = newPass;
	}
	/**
	 * Retrieves this User's password.
	 * 
	 * @return	the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets this User's email.
	 * 
	 * @param newEmail	the email.
	 */
	public void setEmail(String newEmail) {
		email = newEmail;
	}
	/**
	 * Retrieves this User's email.
	 * 
	 * @return	the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Retrieves this User's id.
	 * 
	 * @return	the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * Sets this User's id.
	 * 
	 * @param id	the id.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * Retrieves this User's roles.
	 * 
	 * @return	the roles.
	 */
	public Set<Role> getRoles() {
		return roles;
	}
	/**
	 * Sets this User's roles.
	 * 
	 * @param roles	the roles.
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/**
	 * Returns this User's information as a String.
	 * 
	 * @return	the User's information.
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" 
	+ password + ", firstname=" + firstName + ", lastname=" 
	+ lastName + ", roles=" + roles + "]";
	}
}