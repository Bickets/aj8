package buttons

import java.util.Objects
import org.apollo.game.event.EventSubscriber
import org.apollo.game.model.InterfaceType
import org.apollo.game.model.inter.dialog.DialogueOption
import org.apollo.game.interact.ButtonActionEvent

class DialogueButton implements EventSubscriber<ButtonActionEvent> {

	val static ids = buildIds;

	override subscribe(ButtonActionEvent event) {
		ids.forEach [
			if (it == event.getId) {
				var player = event.getPlayer();

				if (player.interfaceSet.contains(InterfaceType.DIALOGUE)) {
					val option = Objects.requireNonNull(DialogueOption.fromId(it))
					val success = player.interfaceSet.optionClicked(option)
					if (success) {
						player.interfaceSet.continueRequested
					}
				}
			}
		]
	}

	def static buildIds() {
		val ids = newArrayList
		val vals = DialogueOption.values
		vals.forEach[it.ids.forEach[ids += it]]
		return ids
	}

}
