package org.apollo.game.msg.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated type decodes the message specified by the value
 * of {@link #value()}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DecodesMessage {

	/**
	 * Returns an <code>int[]</code> array of opcodes that the message decodes.
	 */
	int[] value();

}