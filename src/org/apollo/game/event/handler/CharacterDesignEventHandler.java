
package org.apollo.game.event.handler;

import org.apollo.game.event.EventHandler;
import org.apollo.game.event.impl.CharacterDesignEvent;
import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.game.model.Player;

/**
 * A handler which handles {@link CharacterDesignEvent}s.
 * @author Graham
 */
public final class CharacterDesignEventHandler extends EventHandler<CharacterDesignEvent>
{

	@Override
	public void handle( Player player, CharacterDesignEvent event )
	{
		if( ! valid( event.getAppearance() ) || player.hasDesignedCharacter() ) {
			return;
		}

		player.setAppearance( event.getAppearance() );
		player.setDesignedCharacter( true );
		player.send( new CloseInterfaceEvent() );
	}


	/**
	 * Checks if an appearance combination is valid.
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private boolean valid( Appearance appearance )
	{
		int[] colors = appearance.getColors();
		int[] maxColors = new int[] {
			11, 15, 15, 5, 7
		};
		for( int i = 0; i < colors.length; i ++ ) {
			if( colors[ i ] < 0 || colors[ i ] > maxColors[ i ] ) {
				return false;
			}
		}

		Gender gender = appearance.getGender();
		if( gender == Gender.MALE ) {
			return validMaleStyle( appearance );
		} else if( gender == Gender.FEMALE ) {
			return validFemaleStyle( appearance );
		} else {
			return false; // maybe null?
		}
	}


	/**
	 * Checks if a {@link Gender#MALE} style combination is valid.
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private boolean validMaleStyle( Appearance appearance )
	{
		int[] styles = appearance.getStyle();
		int[] minStyles = new int[] {
			0, 10, 18, 26, 33, 36, 42
		};
		int[] maxStyles = new int[] {
			8, 17, 25, 31, 34, 40, 43
		};
		for( int i = 0; i < styles.length; i ++ ) {
			if( styles[ i ] < minStyles[ i ] || styles[ i ] > maxStyles[ i ] ) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Checks if a {@link Gender#FEMALE} style combination is valid.
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private boolean validFemaleStyle( Appearance appearance )
	{
		int[] styles = appearance.getStyle();
		int[] minStyles = new int[] {
			45, 255, 56, 61, 67, 70, 79
		};
		int[] maxStyles = new int[] {
			54, 255, 60, 65, 68, 77, 80
		};
		for( int i = 0; i < styles.length; i ++ ) {
			if( styles[ i ] < minStyles[ i ] || styles[ i ] > maxStyles[ i ] ) {
				return false;
			}
		}
		return true;
	}

}
