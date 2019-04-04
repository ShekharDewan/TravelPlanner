package edu.mta.groupa.planner.service;
/**
 * An interface which defines operations for an element 
 * belonging to a Trip.
 * Currently supports creation, deletion, and updating.
 * 
 * @author Jennifer
 *
 * @param <E>	an element within a Trip.
 * @param <T>	a Trip.
 */
public interface IService<E, T> {
	public E add(T parent, E item);
	public E update(T parent, long oldItemId, E newItem);
	public void delete(long parentId, long itemId);
}
