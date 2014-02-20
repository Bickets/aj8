
package org.apollo.game.event.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apollo.game.event.Event;

/**
 * Indicates that the annotated type encodes the event specified by the value of {@link #value()}.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface EncodesEvent {

	/**
	 * Returns some class that extends {@link Event}, which is the event to be encoded.
	 */
	Class< ? extends Event> value();

}
