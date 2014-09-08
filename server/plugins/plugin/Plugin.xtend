package plugin

import java.util.List
import java.util.concurrent.ThreadLocalRandom
import org.apollo.game.model.Player

final class Plugin {

	val static random = ThreadLocalRandom.current

	val static (String)=>boolean isNum = [matches("-?\\d+")]

	val static (String)=>int toInt = [
		if(it.isNum) Integer.valueOf(it) else throw new NumberFormatException
	]

	val static exclusiveRandom = [random.nextInt(it)]

	val static randomNoZero = [exclusiveRandom.apply(it) + 1]

	val static (int, int)=>int inclusiveRandom = [ min, max |
		exclusiveRandom.apply((max - min) + 1) + min
	]

	val static (int, int, List<Integer>)=>int inclusiveRandomExcludes = [ min, max, excludes |
		var result = inclusiveRandom.apply(min, max)
		while (excludes.contains(result)) {
			result = inclusiveRandom.apply(min, max)
		}
		return result
	]

	val static (float)=>float randomFloat = [random.nextFloat * it]

	def static toInt(String str) {
		toInt.apply(str)
	}

	def static isNum(String str) {
		isNum.apply(str)
	}

	def static random(int range) {
		exclusiveRandom.apply(range)
	}

	def static randomExcludesZero(int range) {
		randomNoZero.apply(range)
	}

	def static inclusiveRandom(int min, int max) {
		inclusiveRandom.apply(min, max)
	}

	def static inclusiveRandomExcludes(int min, int max, List<Integer> excludes) {
		inclusiveRandomExcludes.apply(min, max, excludes)
	}

	def static random(float range) {
		randomFloat.apply(range)
	}

	def static closeInterfaces(Player player) {
		player.interfaceSet.close
	}

}
