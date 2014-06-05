package buttons

import java.util.Objects
import org.apollo.game.interact.ButtonClickListener
import org.apollo.game.model.InterfaceType
import org.apollo.game.model.Player
import org.apollo.game.model.inter.dialog.DialogueOption

class DialogueButton extends ButtonClickListener {

	def static buildIds() {
		val ids = newArrayList
		val vals = DialogueOption.values
		vals.forEach[it.ids.forEach[ids += it]]
		return ids
	}

	new() {
		super(buildIds)
	}

	override handle(int id, Player player) {
		if (player.interfaceSet.contains(InterfaceType.DIALOGUE)) {
			val option = Objects.requireNonNull(DialogueOption.fromId(id))
			val success = player.interfaceSet.optionClicked(option)
			if (success) {
				player.interfaceSet.continueRequested
			}
		}
	}

}
