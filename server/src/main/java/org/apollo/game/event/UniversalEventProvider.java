package org.apollo.game.event;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.eventbus.EventBus;

/**
 * A universal event provider which provides using Google Guava's
 * {@link EventBus}.
 * 
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class UniversalEventProvider implements EventProvider {

    /**
     * The default event bus, used to provide and deprive
     * {@link EventSubscriber}s as well as post {@link Event}s
     */
    private final EventBus eventBus = new EventBus();

    @Override
    public <E extends Event> void provideSubscriber(EventSubscriber<E> subscriber) {
	eventBus.register(checkNotNull(subscriber));
    }

    @Override
    public <E extends Event> void depriveSubscriber(EventSubscriber<E> subscriber) {
	eventBus.unregister(checkNotNull(subscriber));
    }

    @Override
    public <E extends Event> void post(E event) {
	eventBus.post(checkNotNull(event));
    }

}