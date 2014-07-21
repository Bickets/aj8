package buttons

import java.util.Objects
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.model.InterfaceType
import org.apollo.game.model.inter.dialog.DialogueOption

@SubscribesTo(ButtonActionEvent)
class DialogueButton implements EventSubscriber<ButtonActionEvent> {

	val static ids = buildIds;

	override subscribe(ButtonActionEvent event) {
		var player = event.player

		val option = Objects.requireNonNull(DialogueOption.fromId(event.id))
		val success = player.interfaceSet.optionClicked(option)
		if (success) {
			player.interfaceSet.continueRequested
		}
	}

	def static buildIds() {
		val ids = newArrayList
		val vals = DialogueOption.values
		vals.forEach[it.ids.forEach[ids += it]]
		return ids
	}

	override test(ButtonActionEvent event) {
		val player = event.player
		var hasId = false

		for (id : ids) {
			if (id == event.id) {
				hasId = true
			}
		}

		player.interfaceSet.contains(InterfaceType.DIALOGUE) && hasId
	}

}
