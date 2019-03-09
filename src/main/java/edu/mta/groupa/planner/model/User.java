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


@Entity
@Table(name = "user")
public class User{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(cascade=CascadeType.ALL)
    private Set<Role> roles;
	
	public User() {}	
	
	public User(String firstname, String lastname, String password, String email) {
		this.email = email;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String newPass) {
		password = newPass;
	}

	public String getPassword() {
		return password;
	}
	
	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public String getEmail() {
		return email;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstName
				+ ", lastname=" + lastName + ", roles=" + roles + "]";
	}
	
}