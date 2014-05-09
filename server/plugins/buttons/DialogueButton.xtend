package buttons

import org.apollo.game.interact.ButtonClickListener
import org.apollo.game.model.InterfaceType
import org.apollo.game.model.Player
import org.apollo.game.model.inter.dialog.DialogueOption

class DialogueButton extends ButtonClickListener {

	def static buildIds() {
		var ids = newArrayList
		for (option : DialogueOption::values) {
			for (id : option.ids) {
				ids += id
			}
		}
		return ids
	}

	new() {
		super(buildIds)
	}

	override handle(int id, Player player) {
		if (player.interfaceSet.contains(InterfaceType::DIALOGUE)) {
			var option = DialogueOption::fromId(id)
			/* I dislike this inverted boolean expression :( */
			if (option == null || !player.interfaceSet.optionClicked(option)) {
				player.interfaceSet.close
				return
			}
		}
	}

}
