package plugin

import java.lang.reflect.Modifier
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import org.apollo.game.event.Event
import org.apollo.game.event.EventSubscriber
import org.apollo.game.event.annotate.SubscribesTo
import org.apollo.game.model.World
import plugin.mobs.MobSpawnEvent

import static java.nio.file.FileVisitResult.*

class Bootstrap {

	def init(World world) {
		val classes = classes("bin/plugin")

		classes.forEach [
			val subscribesTo = it.getAnnotation(SubscribesTo)
			if (subscribesTo != null) {
				world.provide(it)
			}
		]
	}

	def <T extends Event> provide(World world, Class<?> clazz) {
		clazz.constructors.forEach [
			val parameterTypes = it.parameterTypes
			if (parameterTypes.length == 0) {
				world.provideSubscriber(clazz.newInstance as EventSubscriber<T>)
				return
			}
			if (parameterTypes.length == 1 && parameterTypes.get(0) == World) {
				world.provideSubscriber(clazz.getConstructor(World).newInstance(world) as EventSubscriber<T>)
				return
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
