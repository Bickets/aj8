package org.apollo.game.msg.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apollo.game.msg.Message;

/**
 * Indicates that the annotated type encodes the message specified by the value
 * of {@link #value()}.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EncodesMessage {

    /**
     * Returns some class that extends {@link Message}, which is the message to
     * be encoded.
     */
    Class<? extends Message> value();

}