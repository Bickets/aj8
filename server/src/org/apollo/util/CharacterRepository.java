
package org.apollo.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apollo.game.model.GameCharacter;

/**
 * A {@link CharacterRepository} is a repository of {@link GameCharacter}s that are
 * currently active in the game world.
 * @author Graham
 * @param <T> The type of character.
 */
public final class CharacterRepository<T extends GameCharacter> implements Iterable<T>
{

	/**
	 * The array of gameCharacters in this repository.
	 */
	private final GameCharacter[] gameCharacters;

	/**
	 * The current size of this repository.
	 */
	private int size = 0;

	/**
	 * The position of the next free index.
	 */
	private int pointer = 0;


	/**
	 * Creates a new character repository with the specified capacity.
	 * @param capacity The maximum number of gameCharacters that can be present in
	 *            the repository.
	 */
	public CharacterRepository( int capacity )
	{
		this.gameCharacters = new GameCharacter[ capacity ];
	}


	/**
	 * Gets the size of this repository.
	 * @return The number of gameCharacters in this repository.
	 */
	public int size()
	{
		return size;
	}


	/**
	 * Gets the capacity of this repository.
	 * @return The maximum size of this repository.
	 */
	public int capacity()
	{
		return gameCharacters.length;
	}


	/**
	 * Adds a character to the repository.
	 * @param character The character to add.
	 * @return {@code true} if the character was added, {@code false} if the
	 *         size has reached the capacity of this repository.
	 */
	public boolean add( T character )
	{
		if( size == gameCharacters.length ) {
			return false;
		}
		int index = - 1;
		for( int i = pointer; i < gameCharacters.length; i ++ ) {
			if( gameCharacters[ i ] == null ) {
				index = i;
				break;
			}
		}
		if( index == - 1 ) {
			for( int i = 0; i < pointer; i ++ ) {
				if( gameCharacters[ i ] == null ) {
					index = i;
					break;
				}
			}
		}
		if( index == - 1 ) {
			return false; // shouldn't happen, but just in case
		}
		gameCharacters[ index ] = character;
		character.setIndex( index + 1 );
		if( index == ( gameCharacters.length - 1 ) ) {
			pointer = 0;
		} else {
			pointer = index;
		}
		size ++ ;
		return true;
	}


	/**
	 * Removes a character from the repository.
	 * @param character The character to remove.
	 * @return {@code true} if the character was removed, {@code false} if it
	 *         was not (e.g. if it was never added or has been removed already).
	 */
	public boolean remove( T character )
	{
		int index = character.getIndex() - 1;
		if( index < 0 || index >= gameCharacters.length ) {
			return false;
		}
		if( gameCharacters[ index ] == character ) {
			gameCharacters[ index ] = null;
			character.setIndex( - 1 );
			size -- ;
			return true;
		} else {
			return false;
		}
	}


	@Override
	public Iterator<T> iterator()
	{
		return new CharacterRepositoryIterator();
	}

	/**
	 * The {@link Iterator} implementation for the {@link CharacterRepository} class.
	 * @author Graham
	 */
	private final class CharacterRepositoryIterator implements Iterator<T>
	{

		/**
		 * The previous index of this iterator.
		 */
		private int previousIndex = - 1;

		/**
		 * The current index of this iterator.
		 */
		private int index = 0;


		@Override
		public boolean hasNext()
		{
			for( int i = index; i < gameCharacters.length; i ++ ) {
				if( gameCharacters[ i ] != null ) {
					index = i;
					return true;
				}
			}
			return false;
		}


		@SuppressWarnings( "unchecked" )
		@Override
		public T next()
		{
			T character = null;
			for( int i = index; i < gameCharacters.length; i ++ ) {
				if( gameCharacters[ i ] != null ) {
					character = ( T )gameCharacters[ i ];
					index = i;
					break;
				}
			}
			if( character == null ) {
				throw new NoSuchElementException();
			}
			previousIndex = index;
			index ++ ;
			return character;
		}


		@SuppressWarnings( "unchecked" )
		@Override
		public void remove()
		{
			if( previousIndex == - 1 ) {
				throw new IllegalStateException();
			}
			CharacterRepository.this.remove( ( T )gameCharacters[ previousIndex ] );
			previousIndex = - 1;
		}

	}

}
