package plugin.mobs

import java.nio.file.Files
import java.nio.file.Paths
import org.apollo.fs.FileSystem
import org.apollo.fs.parser.MobDefinitionParser
import org.apollo.game.model.Direction
import org.apollo.game.model.^def.MobDefinition

import static plugin.Plugin.*

class SpawnConverter {

	def static void main(String[] args) {

		/* Load the file system so we can access mob information */
		val fs = FileSystem.create("./data/fs")

		/* Parse mob information */
		val defs = MobDefinitionParser.parse(fs)
		MobDefinition.init(defs)

		val read = Paths.get("spawn")
		val write = Paths.get("spawns_new")

		val reader = Files.newBufferedReader(read)
		val writer = Files.newBufferedWriter(write)

		for (var String str; (str = reader.readLine) != null; str.trim) {
			val spot = str.indexOf("=")

			if (spot > -1) {
				var key = str.substring(0, spot).trim
				var line = str.substring(spot + 1).trim

				var formattedLine = line.replaceAll("\t\t", "\t")
				formattedLine = formattedLine.replaceAll("\t\t", "\t")
				formattedLine = formattedLine.replaceAll("\t\t", "\t")
				formattedLine = formattedLine.replaceAll("\t\t", "\t")
				formattedLine = formattedLine.replaceAll("\t\t", "\t")

				var values = formattedLine.split("\t")

				if (key == "spawn") {
					val id = toInt(values.get(0))
					val x = toInt(values.get(1))
					val y = toInt(values.get(2))
					val h = toInt(values.get(3))
					val dir = toInt(values.get(4))

					writer.write(
						"event.spawn(\"" + MobDefinition.forId(id).name + "\", " + id + ", new Position(" + x + ", " + y +
							", " + h + "), 4" + if(dir == 1 || dir == 0) ")" else ", " + direction(dir) + ")")
					writer.newLine
				}
			}
		}

		writer.flush
	}

	def static direction(int dir) {
		switch dir {
			case 2: Direction.NORTH
			case 3: Direction.SOUTH
			case 4: Direction.EAST
			case 5: Direction.WEST
		}
	}

}
