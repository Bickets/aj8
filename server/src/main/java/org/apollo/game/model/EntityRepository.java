package org.apollo.game.model;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * A {@link EntityRepository} is a repository of {@link Entity}s that are
 * currently active in the game world.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @param <T> The type of entity.
 */
public final class EntityRepository<T extends Entity> extends AbstractCollection<T> implements Iterable<T> {

    /**
     * The array of entities in this repository.
     */
    private final Entity[] entities;

    /**
     * The current size of this repository.
     */
    private int size = 0;

    /**
     * Creates a new entity repository with the specified capacity.
     *
     * @param capacity The maximum number of entities that can be present in the
     *            repository.
     */
    public EntityRepository(int capacity) {
	entities = new Entity[capacity];
    }

    @Override
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
     * Tests the specified index to ensure it is valid.
     *
     * @param index The index to test.
     * @throws ArrayIndexOutOfBoundsException If the specified index is invalid.
     */
    private void testIndex(int index) {
	if (index < 1 || index >= entities.length + 1) {
	    throw new ArrayIndexOutOfBoundsException();
	}
    }

    @Override
    public boolean add(T entity) {
	for (int index = 0; index < entities.length; index++) {
	    if (entities[index] != null) {
		continue;
	    }

	    entities[index] = entity;
	    entity.setIndex(index + 1);
	    size++;

	    return true;
	}

	return false;
    }

    /**
     * Attempts to remove the specified entity from this collection.
     *
     * @param entity The entity to try to remove.
     * @return {@code true} if and only if the specified entity was removed
     *         otherwise {@code false}.
     */
    public boolean remove(T entity) {
	int index = entity.getIndex();

	Entity other = get(index);
	assert other == entity;

	remove(index);
	return true;
    }

    /**
     * Looks up an entity at the specified index and attempts to remove it from
     * this collection.
     *
     * @param index The index within this collection to remove the specified
     *            entity.
     * @return {@code true} if and only if the entity for the specified index
     *         was removed.
     */
    public boolean remove(int index) {
	Entity entity = get(index);
	if (entity == null) {
	    return false;
	}

	if (entity.getIndex() != index) {
	    throw new IllegalStateException("Unexpected index: " + index + ", expected: " + entity.getIndex());
	}

	entities[index - 1] = null;
	entity.resetIndex();
	size--;
	return true;
    }

    /**
     * Attempts to get the entity at the specified index.
     *
     * @param index The index of the entity to get.
     * @return The entity at the specified index if and only if it exists
     *         otherwise {@code null} is returned.
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
	testIndex(index);
	return (T) entities[index - 1];
    }

    @Override
    public Iterator<T> iterator() {
	return new EntityRepositoryIterator();
    }

    /**
     * The {@link Iterator} implementation for the {@link EntityRepository}
     * class.
     *
     * @author Graham
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     */
    private final class EntityRepositoryIterator implements Iterator<T> {

	/**
	 * The current index of this iterator.
	 */
	private int currentIndex;

	/**
	 * The amount of indexes found.
	 */
	private int foundIndex;

	@Override
	public boolean hasNext() {
	    if (foundIndex == size) {
		return false;
	    }

	    while (currentIndex < capacity()) {
		if (entities[currentIndex++] != null) {
		    foundIndex++;
		    return true;
		}
	    }
	    return false;
	}

	@Override
	public T next() {
	    return get(currentIndex);
	}

	@Override
	public void remove() {
	    EntityRepository.this.remove(currentIndex + 1);
	}
    }

}