package common

import java.io.File
import java.lang.reflect.Modifier
import mobs.MobSpawn
import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.interact.ObjectActionEvent
import org.apollo.game.model.World

class Bootstrap {

	def initObjects(World world) {
		classes('objects').forEach[world.provideSubscriber(it.newInstance as EventSubscriber<ObjectActionEvent>)]
	}

	def initButtons(World world) {
		classes('buttons').forEach[world.provideSubscriber(it.newInstance as EventSubscriber<ButtonActionEvent>)]
	}

	def initItems(World world) {
		classes('items').forEach[world.provideSubscriber(it.newInstance as EventSubscriber<ItemActionEvent>)]
	}

	def initSpawns(World world) {
		classes('location').forEach [
			val mob = it.getConstructor(World).newInstance(world) as MobSpawn
			mob.spawn
		]
	}

	def initCommands(World world) {
		classes('commands').forEach[world.provideSubscriber(it.getConstructor(World).newInstance(world) as EventSubscriber<CommandEvent>)]
	}

	def classes(String dir) {
		val files = new File('bin/' + dir, '/').list
		val classes = newArrayList
		val filtered = files.filter[it.endsWith('.class') && !it.contains('$')]

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
		world.initSpawns
		world.initItems
		world.initObjects
		world.initCommands
	}

}
