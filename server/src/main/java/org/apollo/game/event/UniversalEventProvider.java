package org.apollo.game.event;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.eventbus.EventBus;

/**
 * A universal event provider which provides using Google Guava's
 * {@link EventBus}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class UniversalEventProvider extends EventBus implements EventProvider {

    @Override
    public <E extends Event> void provideSubscriber(EventSubscriber<E> subscriber) {
	register(checkNotNull(subscriber));
    }

    @Override
    public <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber) {
	unregister(checkNotNull(subscriber));
    }

    @Override
    public <E extends Event> void post(E event) {
	post(checkNotNull(event));
    }

}