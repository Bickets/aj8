package plugin.buttons

import org.apollo.game.event.EventContext
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.Player
import org.apollo.game.model.inter.InterfaceType
import org.apollo.game.model.inter.dialog.DialogueOption

@SubscribesTo(ButtonActionEvent)
class DialogueButton implements EventSubscriber<ButtonActionEvent> {

	val static ids = buildIds;

	override subscribe(EventContext context, Player player, ButtonActionEvent event) {
		if (!player.interfaceSet.contains(InterfaceType.DIALOGUE)) {
			context.breakSubscriberChain
			return
		}

		val option = DialogueOption.fromId(event.id)
		player.interfaceSet.optionClicked(option)
		player.interfaceSet.continueRequested
	}

	def static buildIds() {
		val ids = newArrayList
		val vals = DialogueOption.values
		vals.forEach[it.ids.forEach[ids += it]]
		return ids
	}

	override test(ButtonActionEvent event) {
		ids.filter[it == event.id].size > 0
	}

}
