import java.io.File
import java.lang.reflect.Modifier
import java.util.ArrayList
import org.apollo.game.command.CommandDispatcher
import org.apollo.game.command.CommandListener
import org.apollo.game.interact.ButtonEventHandler
import org.apollo.game.interact.ObjectActionEventHandler
import org.apollo.game.model.World

/* TODO: Should I rewrite this in Java? */
class Bootstrap {

	def initObjects(World world) {
		val classes = getClasses('objects')
		classes.forEach [ clazz |
			val handler = clazz.newInstance as ObjectActionEventHandler
			world.interactionHandler.bind(handler)
		]
	}

	def initButtons(World world) {
		val classes = getClasses('buttons')
		classes.forEach [ clazz |
			val handler = clazz.newInstance as ButtonEventHandler
			world.interactionHandler.bind(handler)
		]
	}

	def initCommands() {
		val classes = getClasses('commands')
		classes.forEach [ clazz |
			val listener = clazz.newInstance as CommandListener
			CommandDispatcher::getInstance.bind(listener)
		]
	}

	def getClasses(String dir) {

		/* create an array of files in the specified {@code dir}, may be <code>null</code> */
		val files = new File('bin/' + dir, '/').list

		/* create a new {@link ArrayList}, used to store found classes */
		val classes = newArrayList

		/* <code>null</code>-safe for each loop, if {@code files} is <code>null</code> this loop will not happen */
		files?.forEach [ file |
			/* filter other files and classes containing illegal characters */
			if (file.endsWith('.class') && !file.contains('$')) {

				/* create a class instance from the directory and file name. */
				val clazz = Class::forName(dir + '.' + file.substring(0, file.lastIndexOf('.')))

				/*  if the class is abstract or an interface, don't continue */
				if (!Modifier::isAbstract(clazz.modifiers) && !Modifier::isInterface(clazz.modifiers)) {

					/* add the class to the {@code classes} list */
					classes.add(clazz)
				}
			}
		]

		/* return the classes. {@code return} is implicit, used for readability */
		return classes
	}

	new(World world) {
		world.initButtons
		world.initObjects

		initCommands
	}

}
