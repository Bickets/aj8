package plugin

import java.lang.reflect.Modifier
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import org.apollo.game.command.CommandEvent
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.interact.ButtonActionEvent
import org.apollo.game.interact.ItemActionEvent
import org.apollo.game.interact.ItemOnItemActionEvent
import org.apollo.game.interact.ObjectActionEvent
import org.apollo.game.model.World
import plugin.mobs.MobSpawnEvent

import static java.nio.file.FileVisitResult.*
import org.apollo.game.interact.ItemOnPlayerActionEvent

class Bootstrap {

	def init(World world) {
		val classes = classes("bin/plugin")

		classes.forEach [
			val subscribesTo = it.getAnnotation(SubscribesTo)
			if (subscribesTo != null) {
				switch (subscribesTo.value) {
					case ButtonActionEvent: {
						world.provideSubscriber(it.newInstance as EventSubscriber<ButtonActionEvent>)
					}
					case CommandEvent: {
						world.provideSubscriber(
							it.getConstructor(World).newInstance(world) as EventSubscriber<CommandEvent>)
					}
					case ItemActionEvent: {
						world.provideSubscriber(it.newInstance as EventSubscriber<ItemActionEvent>)
					}
					case ItemOnItemActionEvent: {
						world.provideSubscriber(it.newInstance as EventSubscriber<ItemOnItemActionEvent>)
					}
					case MobSpawnEvent: {
						world.provideSubscriber(it.newInstance as EventSubscriber<MobSpawnEvent>)
					}
					case ObjectActionEvent: {
						world.provideSubscriber(it.newInstance as EventSubscriber<ObjectActionEvent>)
					}
					case ItemOnPlayerActionEvent: {
						world.provideSubscriber(it.newInstance as EventSubscriber<ItemOnPlayerActionEvent>)
					}
				}
			}
		]
	}

	def classes(String root) {
		val classes = <Class<?>>newHashSet

		Files.walkFileTree(Paths.get(root),
			new SimpleFileVisitor<Path> {
				override visitFile(Path path, BasicFileAttributes attrs) {
					val file = path.toFile
					val name = file.name

					if (!name.endsWith(".class") || name.contains("$")) {
						return CONTINUE
					}

					val normalized = file.path.replace("\\", ".").substring(0, file.path.lastIndexOf(".")).
						replace("bin.", "")
					val clazz = Class.forName(normalized)

					if (!Modifier.isAbstract(clazz.modifiers) && !Modifier.isInterface(clazz.modifiers)) {
						classes += clazz
					}

					return CONTINUE
				}
			})

		return classes
	}

	def spawnMobs(World world) {
		world.post(new MobSpawnEvent(world))
	}

	new(World world) {
		world.init
		world.spawnMobs
	}

}
