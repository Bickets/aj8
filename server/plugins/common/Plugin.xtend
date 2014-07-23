package common

import java.util.concurrent.ThreadLocalRandom
import java.util.List

abstract class Plugin {

	val random = ThreadLocalRandom.current

	/* logic expressions */
	val isNum = [String str|str.matches("-?\\d+")]
	val toInt = [String str|if(isNum(str)) Integer.valueOf(str) else throw new NumberFormatException]
	val randomNoZero = [int range|exclusiveRandom.apply(range) + 1]
	val exclusiveRandom = [int range|random.nextInt(range)]
	val inclusiveRandom = [int min, int max|exclusiveRandom.apply((max - min) + 1) + min]
	val inclusiveRandomExcludes = [ int min, int max, List<Integer> excludes |
		var result = inclusiveRandom.apply(min, max)
		while (excludes.contains(result)) {
			result = inclusiveRandom.apply(min, max)
		}
	]
	val randomFloat = [float range|random.nextFloat() * range]

	def toInt(String str) {
		toInt.apply(str)
	}

	def isNum(String str) {
		isNum.apply(str)
	}

	def random(int range) {
		exclusiveRandom.apply(range)
	}

	def randomExcludesZero(int range) {
		randomNoZero.apply(range)
	}

	def inclusiveRandom(int min, int max) {
		inclusiveRandom.apply(min, max)
	}

	def inclusiveRandomExcludes(int min, int max, List<Integer> excludes) {
		inclusiveRandomExcludes.apply(min, max, excludes)
	}

	def random(float range) {
		randomFloat.apply(range)
	}

	def camelize(String str) {
		str.replace("/(?:^|_)(.)/", str.toFirstUpper)
	}

}
