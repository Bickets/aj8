package org.apollo.game.event;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apollo.game.event.annotate.SubscribesTo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;

/**
 * A universal event provider which provides using Google Guava's
 * {@link EventBus}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class UniversalEventProvider extends EventBus implements EventProvider {

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

	/* Register the subscriber */
	register(subscriber);
    }

    @Override
    public <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber) {
	SubscribesTo annotation = checkNotNull(subscriber).getClass().getAnnotation(SubscribesTo.class);
	if (annotation == null) {
	    throw new NullPointerException(subscriber.toString() + " is not annotated with @SubscribesTo");
	}

	/* Removed the cached event */
	events.remove(annotation.value(), subscriber);

	/* Unregister the subscriber */
	unregister(subscriber);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <E extends Event> void post(E event) {
	// FIXME: This method is a temporary workaround to unregistering
	// subscribers if their predicates (if applicable) exist and test {@code
	// false}.

	Collection<EventSubscriber> subscribers = events.get(event.getClass());
	Set<EventSubscriber<E>> tested = new HashSet<>();

	subscribers.forEach(subscriber -> {
	    if (!subscriber.test(event)) {
		unregister(subscriber);
	    }
	});

	super.post(event);

	tested.forEach(subscriber -> register(subscriber));
    }

}