import java.io.File
import java.lang.reflect.Modifier
import java.util.logging.Logger
import mobs.InitialMobSpawns
import org.apollo.game.command.CommandDispatcher
import org.apollo.game.command.CommandListener
import org.apollo.game.interact.ButtonClickListener
import org.apollo.game.interact.ItemActionListener
import org.apollo.game.interact.ObjectActionListener
import org.apollo.game.model.World

class Bootstrap {

	val logger = Logger::getLogger(Bootstrap.name)

	def initObjects(World world) {
		val classes = getClasses('objects')
		classes.forEach [ clazz |
			val handler = clazz.newInstance as ObjectActionListener
			world.interactionHandler.bind(handler)
		]
		logger.info('Loaded ' + classes.size + ' object plugins.')
	}

	def initButtons(World world) {
		val classes = getClasses('buttons')
		classes.forEach [ clazz |
			val handler = clazz.newInstance as ButtonClickListener
			world.interactionHandler.bind(handler)
		]
		logger.info('Loaded ' + classes.size + ' button plugins.')
	}

	def initItems(World world) {
		val classes = getClasses('items')
		classes.forEach [ clazz |
			val handler = clazz.newInstance as ItemActionListener
			world.interactionHandler.bind(handler)
		]
		logger.info('Loaded ' + classes.size + ' item plugins.')
	}

	def initSpawns(World world) {
		val spawns = new InitialMobSpawns(world)
		spawns.init
	}

	def initCommands() {
		val classes = getClasses('commands')
		classes.forEach [ clazz |
			val listener = clazz.newInstance as CommandListener
			CommandDispatcher::getInstance.bind(listener)
		]
		logger.info('Loaded ' + classes.size + ' command plugins.')
	}

	def getClasses(String dir) {
		val files = new File('bin/' + dir, '/').list
		val classes = newArrayList

		files?.forEach [ file |
			if (file.endsWith('.class') && !file.contains('$')) {
				val clazz = Class::forName(dir + '.' + file.substring(0, file.lastIndexOf('.')))
				if (!Modifier::isAbstract(clazz.modifiers) && !Modifier::isInterface(clazz.modifiers)) {
					classes += clazz
				}
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
