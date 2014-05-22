import java.io.File
import java.lang.reflect.Modifier
import mobs.InitialMobSpawns
import org.apollo.game.command.CommandDispatcher
import org.apollo.game.command.CommandListener
import org.apollo.game.interact.ButtonClickListener
import org.apollo.game.interact.ItemActionListener
import org.apollo.game.interact.ObjectActionListener
import org.apollo.game.model.World

class Bootstrap {

	def initObjects(World world) {
		classes('objects').forEach[world.interactionHandler.bind(it.newInstance as ObjectActionListener)]
	}

	def initButtons(World world) {
		classes('buttons').forEach[world.interactionHandler.bind(it.newInstance as ButtonClickListener)]
	}

	def initItems(World world) {
		classes('items').forEach[world.interactionHandler.bind(it.newInstance as ItemActionListener)]
	}

	def initSpawns(World world) {
		new InitialMobSpawns(world).init
	}

	def initCommands() {
		classes('commands').forEach[CommandDispatcher.getInstance.bind(it.newInstance as CommandListener)]
	}

	def classes(String dir) {
		val files = new File('bin/' + dir, '/').list
		val classes = newArrayList
		val filtered = files?.filter[it.endsWith('.class') && !it.contains('$')]

		filtered.forEach [
			val name = it.substring(0, it.indexOf('.'))
			val clazz = Class.forName(dir + '.' + name)
			if (!Modifier.isAbstract(clazz.modifiers) && !Modifier.isInterface(clazz.modifiers)) {
				classes += clazz
			}
		]

		return classes
	}

	new(World world) {
		world.initButtons
		world.initObjects
		world.initSpawns
		world.initItems

		initCommands
	}

}
