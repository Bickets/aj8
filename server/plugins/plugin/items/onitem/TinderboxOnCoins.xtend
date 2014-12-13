package plugin.items.onitem

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ItemOnItemActionEvent
import org.apollo.game.model.Player

@SubscribesTo(ItemOnItemActionEvent)
class TinderboxOnCoins implements EventSubscriber<ItemOnItemActionEvent> {

	override subscribe(EventContext context, Player player, ItemOnItemActionEvent event) {
		player.sendMessage("You decide it's not a good idea to try and burn your coins!")
	}

	override test(ItemOnItemActionEvent event) {
		event.canCombine(995, 590)
	}

}
