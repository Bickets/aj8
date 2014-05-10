package org.apollo.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apollo.game.model.Entity;

/**
 * A {@link EntityRepository} is a repository of {@link Entity}s that
 * are currently active in the game world.
 * 
 * @author Graham
 * @param <T> The type of entity.
 */
public final class EntityRepository<T extends Entity> implements Iterable<T> {

    /**
     * The array of entities in this repository.
     */
    private final Entity[] entities;

    /**
     * The current size of this repository.
     */
    private int size = 0;

    /**
     * The position of the next free index.
     */
    private int pointer = 0;

    /**
     * Creates a new entity repository with the specified capacity.
     * 
     * @param capacity The maximum number of entities that can be present
     *            in the repository.
     */
    public EntityRepository(int capacity) {
	this.entities = new Entity[capacity];
    }

    /**
     * Gets the size of this repository.
     * 
     * @return The number of entities in this repository.
     */
    public int size() {
	return size;
    }

    /**
     * Gets the capacity of this repository.
     * 
     * @return The maximum size of this repository.
     */
    public int capacity() {
	return entities.length;
    }

    /**
     * Adds an entity to the repository.
     * 
     * @param entity The entity to add.
     * @return {@code true} if the entity was added, {@code false} if the
     *         size has reached the capacity of this repository.
     */
    public boolean add(T entity) {
	if (size == entities.length) {
	    return false;
	}
	int index = -1;
	for (int i = pointer; i < entities.length; i++) {
	    if (entities[i] == null) {
		index = i;
		break;
	    }
	}
	if (index == -1) {
	    for (int i = 0; i < pointer; i++) {
		if (entities[i] == null) {
		    index = i;
		    break;
		}
	    }
	}
	if (index == -1) {
	    return false; // shouldn't happen, but just in case
	}
	entities[index] = entity;
	entity.setIndex(index + 1);
	if (index == (entities.length - 1)) {
	    pointer = 0;
	} else {
	    pointer = index;
	}
	size++;
	return true;
    }

    /**
     * Removes a entity from the repository.
     * 
     * @param entity The entity to remove.
     * @return {@code true} if the entity was removed, {@code false} if it
     *         was not (e.g. if it was never added or has been removed already).
     */
    public boolean remove(T entity) {
	int index = entity.getIndex() - 1;
	if (index < 0 || index >= entities.length) {
	    return false;
	}
	if (entities[index] == entity) {
	    entities[index] = null;
	    entity.setIndex(-1);
	    size--;
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public Iterator<T> iterator() {
	return new CharacterRepositoryIterator();
    }

    /**
     * The {@link Iterator} implementation for the {@link EntityRepository}
     * class.
     * 
     * @author Graham
     */
    private final class CharacterRepositoryIterator implements Iterator<T> {

	/**
	 * The previous index of this iterator.
	 */
	private int previousIndex = -1;

	/**
	 * The current index of this iterator.
	 */
	private int index = 0;

	@Override
	public boolean hasNext() {
	    for (int i = index; i < entities.length; i++) {
		if (entities[i] != null) {
		    index = i;
		    return true;
		}
	    }
	    return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T next() {
	    T entity = null;
	    for (int i = index; i < entities.length; i++) {
		if (entities[i] != null) {
		    entity = (T) entities[i];
		    index = i;
		    break;
		}
	    }
	    if (entity == null) {
		throw new NoSuchElementException();
	    }
	    previousIndex = index;
	    index++;
	    return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove() {
	    if (previousIndex == -1) {
		throw new IllegalStateException();
	    }
	    EntityRepository.this.remove((T) entities[previousIndex]);
	    previousIndex = -1;
	}

    }

}
