package com.runescape.cache.cfg;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public final class ChatCensor {

	public static int[] fragments;
	public static char[][] invalidWords;
	public static byte[][][] invalidWordCombinations;
	public static char[][] domains;
	public static char[][] topLevelDomain;
	public static int[] topLevelDomainTypes;
	public static final String[] ALLOWED_WORDS = { "cook", "cook's", "cooks",
			"seeks", "sheet", "woop", "woops", "faq", "noob", "noobs" };

	public static void load(Archive a) {
		Buffer fragmentsenc = new Buffer(a.getFile("fragmentsenc.txt"));
		Buffer badenc = new Buffer(a.getFile("badenc.txt"));
		Buffer domainenc = new Buffer(a.getFile("domainenc.txt"));
		Buffer tldlist = new Buffer(a.getFile("tldlist.txt"));
		load(fragmentsenc, badenc, domainenc, tldlist);
	}

	public static void load(Buffer fragmentsenc, Buffer badenc, Buffer domainenc, Buffer tldlist) {
		loadBadEnc(badenc);
		loadDomainEnc(domainenc);
		loadFragmentsEnc(fragmentsenc);
		loadTldList(tldlist);
	}

	public static void loadTldList(Buffer b) {
		int length = b.getInt();
		topLevelDomain = new char[length][];
		topLevelDomainTypes = new int[length];

		for (int n = 0; n < length; n++) {
			topLevelDomainTypes[n] = b.getUnsignedByte();

			char[] string = new char[b.getUnsignedByte()];
			for (int k = 0; k < string.length; k++) {
				string[k] = (char) b.getUnsignedByte();
			}

			topLevelDomain[n] = string;
		}
	}

	public static void loadBadEnc(Buffer b) {
		int length = b.getInt();
		invalidWords = new char[length][];
		invalidWordCombinations = new byte[length][][];
		loadBadEnc(b, invalidWordCombinations, invalidWords);
	}

	public static void loadDomainEnc(Buffer b) {
		int i = b.getInt();
		domains = new char[i][];
		loadDomainEnc(b, domains);
	}

	public static void loadFragmentsEnc(Buffer b) {
		fragments = new int[b.getInt()];
		for (int i = 0; i < fragments.length; i++) {
			fragments[i] = b.getShort();
		}
	}

	public static char getCharacterCode(int index) {
		if (index >= 0 && index <= 27) {
			return (char) ('a' + index);
		} else if (index == 28) {
			return '\'';
		} else if (index >= 29 && index <= 38) {
			return (char) ('0' + index);
		}
		return '\n';
	}

	public static void loadBadEnc(Buffer buffer, byte[][][] invalidWordCombinations, char[][] invalidWords) {
		for (int index = 0; index < invalidWords.length; index++) {
			char[] chars = new char[buffer.getUnsignedByte()];

			for (int i = 0; i < chars.length; i++) {
				chars[i] = (char) buffer.getUnsignedByte();
			}

			invalidWords[index] = chars;

			byte[][] combination = new byte[buffer.getUnsignedByte()][2];

			for (int l = 0; l < combination.length; l++) {
				combination[l][0] = (byte) buffer.getUnsignedByte();
				combination[l][1] = (byte) buffer.getUnsignedByte();
			}

			if (combination.length > 0) {
				invalidWordCombinations[index] = combination;
			}
		}

	}

	public static void loadDomainEnc(Buffer buffer, char[][] domains) {
		for (int index = 0; index < domains.length; index++) {
			char[] characters = new char[buffer.getUnsignedByte()];
			for (int character = 0; character < characters.length; character++) {
				characters[character] = (char) buffer.getUnsignedByte();
			}
			domains[index] = characters;
		}
	}

	public static void trimWhitespaces(char[] characters) {
		int index = 0;

		for (int n = 0; n < characters.length; n++) {
			if (isValidCharacter(characters[n])) {
				characters[index] = characters[n];
			} else {
				characters[index] = ' ';
			}

			if (index == 0 || characters[index] != ' ' || characters[index - 1] != ' ') {
				index++;
			}
		}

		for (int character = index; character < characters.length; character++) {
			characters[character] = ' ';
		}
	}

	public static boolean isValidCharacter(char c) {
		return c >= ' ' && c <= 127 || c == ' ' || c == '\n' || c == '\t' || c == '£' || c == '€';
	}

	public static String censorString(String s) {
		char[] chars = s.toCharArray();
		trimWhitespaces(chars);

		String trimmed = new String(chars).trim();
		chars = trimmed.toLowerCase().toCharArray();

		filterTopLevelDomains(chars);
		filterInvalid(chars);
		filterDomains(chars);
		filterNumericFragments(chars);

		String lowercase = trimmed.toLowerCase();

		for (String element : ALLOWED_WORDS) {
			for (int index = -1; (index = lowercase.indexOf(element, index + 1)) != -1;) {
				char[] allowedCharacters = element.toCharArray();
				for (int i = 0; i < allowedCharacters.length; i++) {
					chars[i + index] = allowedCharacters[i];
				}
			}
		}

		replaceUppercases(trimmed.toCharArray(), chars);
		formatUppercases(chars);
		return new String(chars).trim();
	}

	public static void replaceUppercases(char[] from, char[] to) {
		for (int i = 0; i < from.length; i++) {
			if (to[i] != '*' && isUppercaseAlphaCharacter(from[i])) {
				to[i] = from[i];
			}
		}
	}

	public static void formatUppercases(char[] chars) {
		boolean flag = true;
		for (int index = 0; index < chars.length; index++) {
			char character = chars[index];

			if (isAlphaCharacter(character)) {
				if (flag) {
					if (isLowercaseAlphaCharacter(character)) {
						flag = false;
					}
				} else if (isUppercaseAlphaCharacter(character)) {
					chars[index] = (char) (character + 'a' - 'A');
				}
			} else {
				flag = true;
			}
		}
	}

	public static void filterInvalid(char[] chars) {
		for (int iterations = 0; iterations < 2; iterations++) {
			for (int index = invalidWords.length - 1; index >= 0; index--) {
				filterInvalid(chars, invalidWords[index], invalidWordCombinations[index]);
			}
		}
	}

	public static void filterDomains(char[] characters) {
		char[] filteredAts = characters.clone();
		filterInvalid(filteredAts, new char[] { '(', 'a', ')' }, null);

		char[] filteredDots = characters.clone();
		filterInvalid(filteredDots, new char[] { 'd', 'o', 't' }, null);

		for (int index = domains.length - 1; index >= 0; index--) {
			filterDomain(characters, domains[index], filteredDots, filteredAts);
		}
	}

	public static void filterDomain(char[] characters, char[] domains, char[] filteredDots, char[] filteredAts) {
		if (domains.length > characters.length) {
			return;
		}

		int stride;
		for (int start = 0; start <= characters.length - domains.length; start += stride) {
			int end = start;
			int off = 0;
			stride = 1;
			while (end < characters.length) {
				int charLen = 0;
				char b = characters[end];
				char c = '\0';

				if (end + 1 < characters.length) {
					c = characters[end + 1];
				}

				if (off < domains.length && (charLen = getEmulatedDomainCharacterLength(domains[off], b, c)) > 0) {
					end += charLen;
					off++;
					continue;
				}

				if (off == 0) {
					break;
				}

				if ((charLen = getEmulatedDomainCharacterLength(domains[off - 1], b, c)) > 0) {
					end += charLen;
					if (off == 1) {
						stride++;
					}
					continue;
				}
				if (off >= domains.length || !isSymbol(b)) {
					break;
				}
				end++;
			}

			if (off >= domains.length) {
				boolean bad = false;
				int atStatus = getDomainAtFilterStatus(start, characters, filteredAts);
				int dotStatus = getDomainDotFilterStatus(end - 1, characters, filteredDots);

				if (atStatus > 2 || dotStatus > 2) {
					bad = true;
				}

				if (bad) {
					for (int i = start; i < end; i++) {
						characters[i] = '*';
					}
				}
			}
		}

	}

	public static int getDomainAtFilterStatus(int end, char[] characters, char[] filteredAts) {
		if (end == 0) {
			return 2;
		}

		for (int index = end - 1; index >= 0; index--) {
			if (!isSymbol(characters[index])) {
				break;
			}

			if (characters[index] == '@') {
				return 3;
			}
		}

		int asteriskCount = 0;
		for (int index = end - 1; index >= 0; index--) {
			if (!isSymbol(filteredAts[index])) {
				break;
			}

			if (filteredAts[index] == '*') {
				asteriskCount++;
			}
		}

		if (asteriskCount >= 3) {
			return 4;
		}

		return !isSymbol(characters[end - 1]) ? 0 : 1;
	}

	public static int getDomainDotFilterStatus(int start, char[] characters, char[] filteredDots) {
		if (start + 1 == characters.length) {
			return 2;
		}

		for (int i = start + 1; i < characters.length; i++) {
			if (!isSymbol(characters[i])) {
				break;
			}

			if (characters[i] == '.' || characters[i] == ',') {
				return 3;
			}
		}

		int asteriskCount = 0;
		for (int i = start + 1; i < characters.length; i++) {
			if (!isSymbol(filteredDots[i])) {
				break;
			}

			if (filteredDots[i] == '*') {
				asteriskCount++;
			}
		}

		if (asteriskCount >= 3) {
			return 4;
		}

		return !isSymbol(characters[start + 1]) ? 0 : 1;
	}

	public static void filterTopLevelDomains(char[] characters) {
		char filteredDot[] = characters.clone();
		filterInvalid(filteredDot, new char[] { 'd', 'o', 't' }, null);

		char filteredSlash[] = characters.clone();
		filterInvalid(filteredSlash, new char[] { 's', 'l', 'a', 's', 'h' }, null);

		for (int n = 0; n < topLevelDomain.length; n++) {
			filterTld(characters, topLevelDomain[n], topLevelDomainTypes[n], filteredDot, filteredSlash);
		}
	}

	public static void filterTld(char[] characters, char[] topLevelDomains, int type, char[] filteredDot, char[] filteredSlash) {
		if (topLevelDomains.length > characters.length) {
			return;
		}

		int stride;
		for (int start = 0; start <= characters.length - topLevelDomains.length; start += stride) {
			int end = start;
			int off = 0;
			stride = 1;

			while (end < characters.length) {
				int charLen = 0;
				char b = characters[end];
				char c = '\0';

				if (end + 1 < characters.length) {
					c = characters[end + 1];
				}

				if (off < topLevelDomains.length && (charLen = getEmulatedDomainCharacterLength(topLevelDomains[off], b, c)) > 0) {
					end += charLen;
					off++;
					continue;
				}

				if (off == 0) {
					break;
				}

				if ((charLen = getEmulatedDomainCharacterLength(topLevelDomains[off - 1], b, c)) > 0) {
					end += charLen;
					if (off == 1) {
						stride++;
					}
					continue;
				}

				if (off >= topLevelDomains.length || !isSymbol(b)) {
					break;
				}

				end++;
			}

			if (off >= topLevelDomains.length) {
				boolean bad = false;
				int status0 = getTldDotFilterStatus(characters, start, filteredDot);
				int status1 = getTldSlashFilterStatus(characters, end - 1, filteredSlash);

				if (type == 1 && status0 > 0 && status1 > 0) {
					bad = true;
				}
				if (type == 2 && (status0 > 2 && status1 > 0 || status0 > 0 && status1 > 2)) {
					bad = true;
				}
				if (type == 3 && status0 > 0 && status1 > 2) {
					bad = true;
				}

				if (bad) {
					int first = start;
					int last = end - 1;

					if (status0 > 2) {
						if (status0 == 4) {
							boolean findStart = false;
							for (int i = first - 1; i >= 0; i--) {
								if (findStart) {
									if (filteredDot[i] != '*') {
										break;
									}
									first = i;
								} else if (filteredDot[i] == '*') {
									first = i;
									findStart = true;
								}
							}
						}

						boolean findStart = false;
						for (int i = first - 1; i >= 0; i--) {
							if (findStart) {
								if (isSymbol(characters[i])) {
									break;
								}
								first = i;
							} else if (!isSymbol(characters[i])) {
								findStart = true;
								first = i;
							}
						}
					}

					if (status1 > 2) {
						if (status1 == 4) {
							boolean findLast = false;
							for (int i = last + 1; i < characters.length; i++) {
								if (findLast) {
									if (filteredSlash[i] != '*') {
										break;
									}
									last = i;
								} else if (filteredSlash[i] == '*') {
									last = i;
									findLast = true;
								}
							}
						}

						boolean findLast = false;
						for (int i = last + 1; i < characters.length; i++) {
							if (findLast) {
								if (isSymbol(characters[i])) {
									break;
								}
								last = i;
							} else if (!isSymbol(characters[i])) {
								findLast = true;
								last = i;
							}
						}
					}

					for (int i = first; i <= last; i++) {
						characters[i] = '*';
					}
				}
			}
		}
	}

	public static int getTldDotFilterStatus(char[] characters, int start, char[] filteredDot) {
		if (start == 0) {
			return 2;
		}

		for (int i = start - 1; i >= 0; i--) {
			if (!isSymbol(characters[i])) {
				break;
			}
			if (characters[i] == ',' || characters[i] == '.') {
				return 3;
			}
		}

		int asteriskCount = 0;
		for (int i = start - 1; i >= 0; i--) {
			if (!isSymbol(filteredDot[i])) {
				break;
			}

			if (filteredDot[i] == '*') {
				asteriskCount++;
			}
		}

		if (asteriskCount >= 3) {
			return 4;
		}
		return !isSymbol(characters[start - 1]) ? 0 : 1;
	}

	public static int getTldSlashFilterStatus(char[] characters, int end, char[] filteredSlash) {
		if (end + 1 == characters.length) {
			return 2;
		}

		for (int j = end + 1; j < characters.length; j++) {
			if (!isSymbol(characters[j])) {
				break;
			}
			if (characters[j] == '\\' || characters[j] == '/') {
				return 3;
			}
		}

		int asterisks = 0;
		for (int l = end + 1; l < characters.length; l++) {
			if (!isSymbol(filteredSlash[l])) {
				break;
			}
			if (filteredSlash[l] == '*') {
				asterisks++;
			}
		}

		if (asterisks >= 5) {
			return 4;
		}
		return !isSymbol(characters[end + 1]) ? 0 : 1;
	}

	public static void filterInvalid(char[] characters, char[] fragments, byte[][] invalidCombinations) {
		if (fragments.length > characters.length) {
			return;
		}

		int stride;
		for (int start = 0; start <= characters.length - fragments.length; start += stride) {
			int end = start;
			int fragmentCount = 0;
			int iterations = 0;
			stride = 1;

			boolean isSymbol = false;
			boolean isEmulated = false;
			boolean isNumeral = false;

			while (end < characters.length && (!isEmulated || !isNumeral)) {
				int charLen = 0;
				char currentCharacter = characters[end];
				char nextCharacter = '\0';

				if (end + 1 < characters.length) {
					nextCharacter = characters[end + 1];
				}

				if (fragmentCount < fragments.length && (charLen = getEmulatedInvalidCharacterLength(fragments[fragmentCount], currentCharacter, nextCharacter)) > 0) {
					if (charLen == 1 && isNumericCharacter(currentCharacter)) {
						isEmulated = true;
					}

					if (charLen == 2 && (isNumericCharacter(currentCharacter) || isNumericCharacter(nextCharacter))) {
						isEmulated = true;
					}

					end += charLen;
					fragmentCount++;
					continue;
				}

				if (fragmentCount == 0) {
					break;
				}

				if ((charLen = getEmulatedInvalidCharacterLength(fragments[fragmentCount - 1], currentCharacter, nextCharacter)) > 0) {
					end += charLen;

					if (fragmentCount == 1) {
						stride++;
					}

					continue;
				}

				if (fragmentCount >= fragments.length || !isNotLowercaseAlpha(currentCharacter)) {
					break;
				}

				if (isSymbol(currentCharacter) && currentCharacter != '\'') {
					isSymbol = true;
				}

				if (isNumericCharacter(currentCharacter)) {
					isNumeral = true;
				}

				end++;

				if (++iterations * 100 / (end - start) > 90) {
					break;
				}
			}

			if (fragmentCount >= fragments.length && (!isEmulated || !isNumeral)) {
				boolean invalid = true;

				if (!isSymbol) {
					char currentCharacter = ' ';

					if (start - 1 >= 0) {
						currentCharacter = characters[start - 1];
					}

					char nextCharacter = ' ';

					if (end < characters.length) {
						nextCharacter = characters[end];
					}

					if (invalidCombinations != null && comboMatches(getIndex(currentCharacter), getIndex(nextCharacter), invalidCombinations)) {
						invalid = false;
					}
				} else {
					boolean invalidCurrentCharacter = false;
					boolean invalidNextCharacter = false;

					if (start - 1 < 0 || isSymbol(characters[start - 1]) && characters[start - 1] != '\'') {
						invalidCurrentCharacter = true;
					}

					if (end >= characters.length || isSymbol(characters[end]) && characters[end] != '\'') {
						invalidNextCharacter = true;
					}

					if (!invalidCurrentCharacter || !invalidNextCharacter) {
						boolean validWord = false;
						int index = start - 2;

						if (invalidCurrentCharacter) {
							index = start;
						}

						for (; !validWord && index < end; index++) {
							if (index >= 0 && (!isSymbol(characters[index]) || characters[index] == '\'')) {
								char[] frag = new char[3];
								int off;
								for (off = 0; off < 3; off++) {
									if (index + off >= characters.length || isSymbol(characters[index + off]) && characters[index + off] != '\'') {
										break;
									}
									frag[off] = characters[index + off];
								}

								boolean valid = true;

								if (off == 0) {
									valid = false;
								}

								if (off < 3 && index - 1 >= 0 && (!isSymbol(characters[index - 1]) || characters[index - 1] == '\'')) {
									valid = false;
								}

								if (valid && !isBadFragment(frag)) {
									validWord = true;
								}
							}
						}

						if (!validWord) {
							invalid = false;
						}
					}
				}

				if (invalid) {
					int numericCount = 0;
					int alphaCount = 0;
					int alphaIndex = -1;

					for (int n = start; n < end; n++) {
						if (isNumericCharacter(characters[n])) {
							numericCount++;
						} else if (isAlphaCharacter(characters[n])) {
							alphaCount++;
							alphaIndex = n;
						}
					}

					if (alphaIndex > -1) {
						numericCount -= end - 1 - alphaIndex;
					}

					if (numericCount <= alphaCount) {
						for (int n = start; n < end; n++) {
							characters[n] = '*';
						}
					} else {
						stride = 1;
					}
				}
			}
		}

	}

	public static boolean comboMatches(byte first, byte second, byte[][] invalidCombinations) {
		int firstCombination = 0;
		if (invalidCombinations[firstCombination][0] == first && invalidCombinations[firstCombination][1] == second) {
			return true;
		}

		int lastCombination = invalidCombinations.length - 1;
		if (invalidCombinations[lastCombination][0] == first && invalidCombinations[lastCombination][1] == second) {
			return true;
		}

		do {
			int middle = (firstCombination + lastCombination) / 2;

			if (invalidCombinations[middle][0] == first && invalidCombinations[middle][1] == second) {
				return true;
			}

			if (first < invalidCombinations[middle][0] || first == invalidCombinations[middle][0] && second < invalidCombinations[middle][1]) {
				lastCombination = middle;
			} else {
				firstCombination = middle;
			}
		} while (firstCombination != lastCombination && firstCombination + 1 != lastCombination);

		return false;
	}

	public static int getEmulatedDomainCharacterLength(char first, char second, char third) {
		if (first == second) {
			return 1;
		}
		if (first == 'o' && second == '0') {
			return 1;
		}
		if (first == 'o' && second == '(' && third == ')') {
			return 2;
		}
		if (first == 'c' && (second == '(' || second == '<' || second == '[')) {
			return 1;
		}
		if (first == 'e' && second == '\u20AC') {
			return 1;
		}
		if (first == 's' && second == '$') {
			return 1;
		}
		if (first == 'l' && second == 'i') {
			return 1;
		}
		return 0;
	}

	public static int getEmulatedInvalidCharacterLength(char first, char second, char third) {
		if (first == second) {
			return 1;
		}

		if (first >= 'a' && first <= 'm') {
			if (first == 'a') {
				if (second == '4' || second == '@' || second == '^') {
					return 1;
				}
				return second != '/' || third != '\\' ? 0 : 2;
			}

			if (first == 'b') {
				if (second == '6' || second == '8') {
					return 1;
				}
				return (second != '1' || third != '3') && (second != 'i' || third != '3') ? 0 : 2;
			}

			if (first == 'c') {
				return second != '(' && second != '<' && second != '{' && second != '[' ? 0 : 1;
			}

			if (first == 'd') {
				return (second != '[' || third != ')') && (second != 'i' || third != ')') ? 0 : 2;
			}

			if (first == 'e') {
				return second != '3' && second != '\u20AC' ? 0 : 1;
			}

			if (first == 'f') {
				if (second == 'p' && third == 'h') {
					return 2;
				}
				return second != '\243' ? 0 : 1;
			}

			if (first == 'g') {
				return second != '9' && second != '6' && second != 'q' ? 0 : 1;
			}

			if (first == 'h') {
				return second != '#' ? 0 : 1;
			}

			if (first == 'i') {
				return second != 'y' && second != 'l' && second != 'j' && second != '1' && second != '!' && second != ':' && second != ';' && second != '|' ? 0 : 1;
			}

			if (first == 'j') {
				return 0;
			}

			if (first == 'k') {
				return 0;
			}

			if (first == 'l') {
				return second != '1' && second != '|' && second != 'i' ? 0 : 1;
			}

			if (first == 'm') {
				return 0;
			}
		}
		if (first >= 'n' && first <= 'z') {
			if (first == 'n') {
				return 0;
			}

			if (first == 'o') {
				if (second == '0' || second == '*') {
					return 1;
				}
				return (second != '(' || third != ')') && (second != '[' || third != ']') && (second != '{' || third != '}') && (second != '<' || third != '>') ? 0 : 2;
			}

			if (first == 'p') {
				return 0;
			}

			if (first == 'q') {
				return 0;
			}

			if (first == 'r') {
				return 0;
			}

			if (first == 's') {
				return second != '5' && second != 'z' && second != '$' && second != '2' ? 0 : 1;
			}

			if (first == 't') {
				return second != '7' && second != '+' ? 0 : 1;
			}

			if (first == 'u') {
				if (second == 'v') {
					return 1;
				}
				return (second != '\\' || third != '/') && (second != '\\' || third != '|') && (second != '|' || third != '/') ? 0 : 2;
			}

			if (first == 'v') {
				return (second != '\\' || third != '/') && (second != '\\' || third != '|') && (second != '|' || third != '/') ? 0 : 2;
			}

			if (first == 'w') {
				return second != 'v' || third != 'v' ? 0 : 2;
			}

			if (first == 'x') {
				return (second != ')' || third != '(') && (second != '}' || third != '{') && (second != ']' || third != '[') && (second != '>' || third != '<') ? 0 : 2;
			}

			if (first == 'y') {
				return 0;
			}

			if (first == 'z') {
				return 0;
			}
		}

		if (first >= '0' && first <= '9') {
			if (first == '0') {
				if (second == 'o' || second == 'O') {
					return 1;
				}
				return (second != '(' || third != ')') && (second != '{' || third != '}') && (second != '[' || third != ']') ? 0 : 2;
			}

			if (first == '1') {
				return second != 'l' ? 0 : 1;
			} else {
				return 0;
			}
		}

		if (first == ',') {
			return second != '.' ? 0 : 1;
		}

		if (first == '.') {
			return second != ',' ? 0 : 1;
		}

		if (first == '!') {
			return second != 'i' ? 0 : 1;
		} else {
			return 0;
		}
	}

	public static byte getIndex(char character) {
		if (character >= 'a' && character <= 'z') {
			return (byte) (character - 'a' + 1);
		} else if (character == '\'') {
			return 28;
		} else if (character >= '0' && character <= '9') {
			return (byte) (character - '0' + 29);
		}
		return 27;
	}

	public static void filterNumericFragments(char[] characters) {
		int index = 0;
		int end = 0;
		int count = 0;
		int start = 0;

		while ((index = indexOfNumber(characters, end)) != -1) {
			boolean foundLowercase = false;

			for (int i = end; i >= 0 && i < index && !foundLowercase; i++) {
				if (!isSymbol(characters[i]) && !isNotLowercaseAlpha(characters[i])) {
					foundLowercase = true;
				}
			}

			if (foundLowercase) {
				count = 0;
			}

			if (count == 0) {
				start = index;
			}

			end = indexOfNonNumber(characters, index);

			int value = 0;
			for (int n = index; n < end; n++) {
				value = value * 10 + characters[n] - '0';
			}

			if (value > 255 || end - index > 8) {
				count = 0;
			} else {
				count++;
			}

			if (count == 4) {
				for (int n = start; n < end; n++) {
					characters[n] = '*';
				}
				count = 0;
			}
		}
	}

	public static int indexOfNumber(char[] characters, int off) {
		for (int i = off; i < characters.length && i >= 0; i++) {
			if (characters[i] >= '0' && characters[i] <= '9') {
				return i;
			}
		}
		return -1;
	}

	public static int indexOfNonNumber(char[] characters, int off) {
		for (int index = off; index < characters.length && index >= 0; index++) {
			if (characters[index] < '0' || characters[index] > '9') {
				return index;
			}
		}
		return characters.length;
	}

	public static boolean isSymbol(char character) {
		return !isAlphaCharacter(character) && !isNumericCharacter(character);
	}

	public static boolean isNotLowercaseAlpha(char character) {
		if (character < 'a' || character > 'z') {
			return true;
		}
		return character == 'v' || character == 'x' || character == 'j' || character == 'q' || character == 'z';
	}

	public static boolean isAlphaCharacter(char character) {
		return character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z';
	}

	public static boolean isNumericCharacter(char character) {
		return character >= '0' && character <= '9';
	}

	public static boolean isLowercaseAlphaCharacter(char character) {
		return character >= 'a' && character <= 'z';
	}

	public static boolean isUppercaseAlphaCharacter(char character) {
		return character >= 'A' && character <= 'Z';
	}

	public static boolean isBadFragment(char[] characters) {
		boolean skip = true;

		for (int i = 0; i < characters.length; i++) {
			if (!isNumericCharacter(characters[i]) && characters[i] != 0) {
				skip = false;
			}
		}

		if (skip) {
			return true;
		}

		int fragment = getFragment(characters);
		int start = 0;
		int end = fragments.length - 1;

		if (fragment == fragments[start] || fragment == fragments[end]) {
			return true;
		}

		do {
			int middle = (start + end) / 2;

			if (fragment == fragments[middle]) {
				return true;
			}

			if (fragment < fragments[middle]) {
				end = middle;
			} else {
				start = middle;
			}
		} while (start != end && start + 1 != end);
		return false;
	}

	public static int getFragment(char[] characters) {
		if (characters.length > 6) {
			return 0;
		}

		int fragment = 0;
		for (int n = 0; n < characters.length; n++) {
			char character = characters[characters.length - n - 1];

			if (character >= 'a' && character <= 'z') {
				fragment = fragment * 38 + character - 'a' + 1;
			} else if (character == '\'') {
				fragment = fragment * 38 + 27;
			} else if (character >= '0' && character <= '9') {
				fragment = fragment * 38 + character - '0' + 28;
			} else if (character != 0) {
				return 0;
			}

		}

		return fragment;
	}

}