package plugin.items.onitem

import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ItemOnItemActionEvent

@SubscribesTo(ItemOnItemActionEvent)
class TinderboxOnCoins implements EventSubscriber<ItemOnItemActionEvent> {

	override subscribe(ItemOnItemActionEvent event) {
		event.getPlayer.sendMessage("You decide it's not a good idea to try and burn your coins!")
	}

	override test(ItemOnItemActionEvent event) {
		event.canCombine(995, 590)
	}

}
