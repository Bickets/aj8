package org.apollo.game.event;

import java.util.Collection;

import org.apollo.game.event.annotate.SubscribesTo;
import org.apollo.game.model.Player;

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

	/**
	 * The universal context of this event provider.
	 */
	private final EventContext context = new UniversalEventContext();

	@Override
	public void provideSubscriber(EventSubscriber<?> subscriber) {
		SubscribesTo annotation = subscriber.getClass().getAnnotation(SubscribesTo.class);
		if (annotation == null) {
			throw new NullPointerException(subscriber.toString() + " is not annotated with @SubscribesTo");
		}

		events.put(annotation.value(), subscriber);
	}

	@Override
	public void depriveSubscriber(EventSubscriber<?> subscriber) {
		SubscribesTo annotation = subscriber.getClass().getAnnotation(SubscribesTo.class);
		if (annotation == null) {
			throw new NullPointerException(subscriber.toString() + " is not annotated with @SubscribesTo");
		}

		events.remove(annotation.value(), subscriber);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <E extends Event> void post(Player player, E event) {
		Collection<EventSubscriber> subscribers = events.get(event.getClass());

		for (EventSubscriber<E> subscriber : subscribers) {
			/* Check to be sure we can subscribe to the event. */
			if (subscriber.test(event)) {
				subscriber.subscribe(context, player, event);

				/* If the chain is broken, don't continue parsing subscribers. */
				if (context.isChainBroken()) {
					break;
				}
			}
		}

		context.repairSubscriberChain();
	}

}