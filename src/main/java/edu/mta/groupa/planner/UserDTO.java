package edu.mta.groupa.planner;
/**
 * A data transfer object for taking user input when creating
 * a new User account.
 * 
 * @author Jennifer
 *
 */
public class UserDTO {
	/**
	 * Holds inputed first name.
	 */
	private String firstName;
	/**
	 * Holds inputed last name.
	 */
	private String lastName;
	/**
	 * Holds inputed email.
	 */
	private String email;
	/**
	 * Holds inputed password.
	 */
	private String password;
	/**
	 * Retrieves the first name field.
	 * 
	 * @return	the first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Sets the first name field.
	 * 
	 * @param firstName	the first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Retrieves the last name field.
	 * 
	 * @return	the last name.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Sets the last name field.
	 * 
	 * @param lastName	the last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Retrieves the email field.
	 * 
	 * @return	the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the email field.
	 * 
	 * @param email	the email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Retrieves the password field.
	 * 
	 * @return	the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password field.
	 * 
	 * @param password	the password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
