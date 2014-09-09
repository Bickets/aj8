package plugin.buttons

import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.inter.InterfaceType
import org.apollo.game.model.inter.dialog.DialogueOption

@SubscribesTo(ButtonActionEvent)
class DialogueButton implements EventSubscriber<ButtonActionEvent> {

	val static ids = buildIds;

	override subscribe(ButtonActionEvent event) {
		val player = event.player
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
		val player = event.player
		val hasId = ids.filter[it == event.id].size > 0

		player.interfaceSet.contains(InterfaceType.DIALOGUE) && hasId
	}

}
