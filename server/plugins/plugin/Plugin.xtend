package plugin

import java.util.List
import java.util.concurrent.ThreadLocalRandom
import org.apollo.game.model.Player

final class Plugin {

	val static random = ThreadLocalRandom.current

	val static isNum = [ String str |
		str.matches("-?\\d+")
	]

	val static toInt = [ String str |
		if(str.isNum) Integer.valueOf(str) else throw new NumberFormatException
	]

	val static randomNoZero = [ int range |
		exclusiveRandom.apply(range) + 1
	]

	val static exclusiveRandom = [ int range |
		random.nextInt(range)
	]

	val static inclusiveRandom = [ int min, int max |
		exclusiveRandom.apply((max - min) + 1) + min
	]

	val static inclusiveRandomExcludes = [ int min, int max, List<Integer> excludes |
		var result = inclusiveRandom.apply(min, max)
		while (excludes.contains(result)) {
			result = inclusiveRandom.apply(min, max)
		}
	]

	val static randomFloat = [ float range |
		random.nextFloat * range
	]

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
