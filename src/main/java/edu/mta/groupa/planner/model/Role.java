package edu.mta.groupa.planner.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * A class which represents a Role, and is registered as an Entity.
 * A Role determines the authority of a User.
 * 
 * @author Jennifer
 *
 */
@Entity
@Table(name = "role")
public class Role {
	/**
	 * The automatically generated id.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The name of the role.
     */
    private String name;
    /**
     * The Users with this role.
     */
    @ManyToMany(mappedBy = "roles", cascade=CascadeType.ALL)
    private Set<User> users;
    /**
     * Constructor.
     */
    public Role() {
        super();
    }
    /**
     * Constructor.
     * 
     * @param name	the role's name.
     */
    public Role(final String name) {
        super();
        this.name = name;
    }
    /**
     * Retrieves this Role's id.
     * 
     * @return	the id.
     */
    public Long getId() {
        return id;
    }
    /**
     * Sets this Role's id.
     * 
     * @param id	the id.
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Retrieves this Role's name.
     * 
     * @return	the name.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets this Role's name.
     * 
     * @param name	the name.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Retrieves the set of Users with
     * this role.
     * 
     * @return	the set of Users.
     */
    public Set<User> getUsers() {
        return users;
    }
    /**
     * Gives a set of Users this role.
     * 
     * @param users	the Users to assign this role.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
