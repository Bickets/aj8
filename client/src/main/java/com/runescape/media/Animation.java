package com.runescape.media;

import com.runescape.net.Buffer;

public class Animation {

	private static Animation[] cache;

	public int duration;
	public Skins skins;
	public int transformationCount;
	public int[] translations;
	public int[] translationX;
	public int[] translationY;
	public int[] translationZ;

	public static void init(int size) {
		Animation.cache = new Animation[size + 1];
	}

	public static void load(byte[] data) {
		Buffer buffer = new Buffer(data);
		buffer.offset = data.length - 8;

		int attributesOffset = buffer.getUnsignedLEShort();
		int translationsOffset = buffer.getUnsignedLEShort();
		int durationsOffset = buffer.getUnsignedLEShort();
		int baseOffset = buffer.getUnsignedLEShort();
		int offset = 0;

		Buffer idBuffer = new Buffer(data);
		idBuffer.offset = offset;
		offset += attributesOffset + 2;

		Buffer attributeBuffer = new Buffer(data);
		attributeBuffer.offset = offset;
		offset += translationsOffset;

		Buffer translationsBuffer = new Buffer(data);
		translationsBuffer.offset = offset;
		offset += durationsOffset;

		Buffer durationsBuffer = new Buffer(data);
		durationsBuffer.offset = offset;
		offset += baseOffset;

		Buffer baseBuffer = new Buffer(data);
		baseBuffer.offset = offset;

		Skins skins = new Skins(baseBuffer);
		int animationAmount = idBuffer.getUnsignedLEShort();

		int[] transformations = new int[500];
		int[] transformationX = new int[500];
		int[] transformationY = new int[500];
		int[] transformationZ = new int[500];

		for (int animationCount = 0; animationCount < animationAmount; animationCount++) {
			int animationId = idBuffer.getUnsignedLEShort();

			Animation animation = Animation.cache[animationId] = new Animation();
			animation.duration = durationsBuffer.getUnsignedByte();
			animation.skins = skins;

			int transformationAmount = idBuffer.getUnsignedByte();
			int lastIndex = -1;
			int transformationCount = 0;

			for (int index = 0; index < transformationAmount; index++) {
				int attributeId = attributeBuffer.getUnsignedByte();

				if (attributeId > 0) {
					if (skins.opcodes[index] != 0) {
						for (int nextIndex = index - 1; nextIndex > lastIndex; nextIndex--) {
							if (skins.opcodes[nextIndex] == 0) {
								transformations[transformationCount] = nextIndex;
								transformationX[transformationCount] = 0;
								transformationY[transformationCount] = 0;
								transformationZ[transformationCount] = 0;
								transformationCount++;
								break;
							}
						}
					}

					transformations[transformationCount] = index;

					int value = 0;
					if (skins.opcodes[index] == 3) {
						value = 128;
					}

					if ((attributeId & 0x1) != 0) {
						transformationX[transformationCount] = translationsBuffer.getSmartA();
					} else {
						transformationX[transformationCount] = value;
					}

					if ((attributeId & 0x2) != 0) {
						transformationY[transformationCount] = translationsBuffer.getSmartA();
					} else {
						transformationY[transformationCount] = value;
					}

					if ((attributeId & 0x4) != 0) {
						transformationZ[transformationCount] = translationsBuffer.getSmartA();
					} else {
						transformationZ[transformationCount] = value;
					}

					lastIndex = index;
					transformationCount++;
				}
			}

			animation.transformationCount = transformationCount;
			animation.translations = new int[transformationCount];
			animation.translationX = new int[transformationCount];
			animation.translationY = new int[transformationCount];
			animation.translationZ = new int[transformationCount];

			for (int index = 0; index < transformationCount; index++) {
				animation.translations[index] = transformations[index];
				animation.translationX[index] = transformationX[index];
				animation.translationY[index] = transformationY[index];
				animation.translationZ[index] = transformationZ[index];
			}
		}
	}

	public static void reset() {
		Animation.cache = null;
	}

	public static Animation getAnimation(int animationId) {
		if (Animation.cache == null) {
			return null;
		}
		return Animation.cache[animationId];
	}

	public static boolean exists(int i) {
		return i == -1;
	}

}