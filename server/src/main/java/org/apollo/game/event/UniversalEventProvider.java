package org.apollo.game.event;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import org.apollo.game.event.annotate.SubscribesTo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * A universal event provider which posts, provides and deprives subscribers.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class UniversalEventProvider implements EventProvider {

    /**
     * A {@link Multimap} of {@link Event} classes to subscribers.
     */
    @SuppressWarnings("rawtypes")
    private final Multimap<Class<? extends Event>, EventSubscriber> events = ArrayListMultimap.create();

    @Override
    public <E extends Event> void provideSubscriber(EventSubscriber<E> subscriber) {
	SubscribesTo annotation = checkNotNull(subscriber).getClass().getAnnotation(SubscribesTo.class);
	if (annotation == null) {
	    throw new NullPointerException(subscriber.toString() + " is not annotated with @SubscribesTo");
	}

	/* Cache the event */
	events.put(annotation.value(), subscriber);
    }

    @Override
    public <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber) {
	SubscribesTo annotation = checkNotNull(subscriber).getClass().getAnnotation(SubscribesTo.class);
	if (annotation == null) {
	    throw new NullPointerException(subscriber.toString() + " is not annotated with @SubscribesTo");
	}

	/* Removed the cached event */
	events.remove(annotation.value(), subscriber);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <E extends Event> void post(E event) {
	Collection<EventSubscriber> subscribers = events.get(event.getClass());
	subscribers.stream().filter(s -> s.test(event)).forEach(s -> s.subscribe(event));
    }

}