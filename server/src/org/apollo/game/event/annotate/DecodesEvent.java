
package org.apollo.game.event.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated type decodes the event specified by the value of {@link #value()}.
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface DecodesEvent {

	/**
	 * Returns an <code>int[]</code> array of opcodes that the event decodes.
	 */
	int[] value();

}
