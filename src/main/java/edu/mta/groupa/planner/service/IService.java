package edu.mta.groupa.planner.service;

public interface IService<E, T> {
	public E add(T parent, E item);
	public E update(T parent, long oldItemId, E newItem);
	public void delete(long parentId, long itemId);
}
