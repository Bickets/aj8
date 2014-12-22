package com.runescape.cache.media;

import java.awt.Color;

import com.runescape.cache.Archive;
import com.runescape.media.Rasterizer;
import com.runescape.net.Buffer;

public final class TypeFace extends Rasterizer {

	public static final String NBSP_EFFECT = "nbsp";
	public static final String START_TRANSPARENCY_EFFECT = "trans=";
	public static final String DEFAULT_SHADOW_EFFECT = "shad";
	public static final String END_SHADOW_EFFECT = "/shad";
	public static final String GT_EFFECT = "gt";
	public static final String END_STRIKE_THROUGH_EFFECT = "/str";
	public static final String EURO_EFFECT = "euro";
	public static final String START_COLOR_EFFECT = "col=";
	public static final String BREAK_LINE_EFFECT = "br";
	public static final String START_STRIKE_THROUGH_EFFECT = "str=";
	public static final String END_COLOR_EFFECT = "/col";
	public static final String START_IMAGE_EFFECT = "img=";
	public static final String END_UNDERLINE_EFFECT = "/u";
	public static final String DEFAULT_STRIKE_THROUGH_EFFECT = "str";
	public static final String START_SHADOW_EFFECT = "shad=";
	public static final String LT_EFFECT = "lt";
	public static final String END_TRANSPARENCY_EFFECT = "/trans";
	public static final String START_UNDERLINE_EFFECT = "u=";
	public static final String DEFAULT_UNDERLINE_EFFECT = "u";

	public final byte[][] characterPixels = new byte[256][];
	public final int[] characterHeights = new int[256];
	public final int[] characterWidths = new int[256];
	public final int[] characterXOffsets = new int[256];
	public final int[] characterYOffsets = new int[256];
	public final int[] characterScreenWidths = new int[256];
	private IndexedImage[] chatImages;

	public int characterDefaultHeight = 0;
	private int defaultColor = 0;
	private int textShadowColor = -1;
	private int strikethroughColor = -1;
	private int defaultTransparency = 256;
	private int underlineColor = -1;
	private int defaultShadow = -1;
	private int transparency = 256;
	private int textColor = 0;

	public TypeFace(boolean large, String archiveName, Archive archive) {
		Buffer dataBuffer = new Buffer(archive.getFile(archiveName + ".dat"));
		Buffer indexBuffer = new Buffer(archive.getFile("index.dat"));

		indexBuffer.offset = dataBuffer.getUnsignedLEShort() + 7;
		indexBuffer.getUnsignedByte();

		for (int character = 0; character < 256; character++) {
			characterXOffsets[character] = indexBuffer.getUnsignedByte();
			characterYOffsets[character] = indexBuffer.getUnsignedByte();
			int characterWidth = characterWidths[character] = indexBuffer.getUnsignedLEShort();
			int characterHeight = characterHeights[character] = indexBuffer.getUnsignedLEShort();
			int characterType = indexBuffer.getUnsignedByte();
			int characterSize = characterWidth * characterHeight;

			characterPixels[character] = new byte[characterSize];

			if (characterType == 0) {
				for (int pixel = 0; pixel < characterSize; pixel++) {
					characterPixels[character][pixel] = dataBuffer.get();
				}
			} else if (characterType == 1) {
				for (int characterX = 0; characterX < characterWidth; characterX++) {
					for (int characterY = 0; characterY < characterHeight; characterY++) {
						characterPixels[character][characterX + characterY * characterWidth] = dataBuffer.get();
					}
				}
			}

			if (characterHeight > characterDefaultHeight && character < 128) {
				characterDefaultHeight = characterHeight;
			}

			characterXOffsets[character] = 1;
			characterScreenWidths[character] = characterWidth + 2;

			int pixelCount = 0;
			for (int characterY = characterHeight / 7; characterY < characterHeight; characterY++) {
				pixelCount += characterPixels[character][characterY * characterWidth];
			}

			if (pixelCount <= characterHeight / 7) {
				characterScreenWidths[character]--;
				characterXOffsets[character] = 0;
			}

			pixelCount = 0;
			for (int characterY = characterHeight / 7; characterY < characterHeight; characterY++) {
				pixelCount += characterPixels[character][characterWidth - 1 + characterY * characterWidth];
			}

			if (pixelCount <= characterHeight / 7) {
				characterScreenWidths[character]--;
			}
		}

		if (large) {
			characterScreenWidths[32] = characterScreenWidths[73];
		} else {
			characterScreenWidths[32] = characterScreenWidths[105];
		}
	}

	public void drawCenteredStringWaveY(String string, int x, int y, int wave, int color) {
		if (string != null) {
			x = getTextWidth(string) / 2;
			y = characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(character, x + characterXOffsets[character], y + characterYOffsets[character] + (int) (Math.sin(index / 2.0 + wave / 5.0) * 5.0), characterWidths[character], characterHeights[character], color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawCeneteredStringWaveXY(String string, int x, int y, int wave, int color) {
		if (string != null) {
			x -= getTextWidth(string) / 2;
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(character, x + characterXOffsets[character] + (int) (Math.sin(index / 5.0 + wave / 5.0) * 5.0), y + characterYOffsets[character] + (int) (Math.sin(index / 3.0 + wave / 5.0) * 5.0), characterWidths[character], characterHeights[character], color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawCenteredStringWaveXYMove(String string, int x, int y, int waveAmount, int waveSpeed, int color) {
		if (string != null) {
			double speed = 7.0 - waveSpeed / 8.0;
			if (speed < 0.0) {
				speed = 0.0;
			}
			x -= getTextWidth(string) / 2;
			y -= characterDefaultHeight;
			for (int index = 0; index < string.length(); index++) {
				char character = string.charAt(index);
				if (character != ' ') {
					drawCharacter(character, x + characterXOffsets[character], y + characterYOffsets[character] + (int) (Math.sin(index / 1.5 + waveAmount) * speed), characterWidths[character], characterHeights[character], color);
				}
				x += characterScreenWidths[character];
			}
		}
	}

	public void drawShadowedString(String string, int x, int y, boolean shadow, int color) {
		strikethroughColor = -1;
		int originalX = x;
		if (string != null) {
			y -= characterDefaultHeight;
			for (int character = 0; character < string.length(); character++) {
				char c = string.charAt(character);
				if (c != ' ') {
					if (shadow) {
						drawCharacter(c, x + characterXOffsets[c] + 1, y + characterYOffsets[c] + 1, characterWidths[c], characterHeights[c], 0);
					}
					drawCharacter(c, x + characterXOffsets[c], y + characterYOffsets[c], characterWidths[c], characterHeights[c], color);
				}
				x += characterScreenWidths[c];
			}
			if (strikethroughColor != -1) {
				Rasterizer.drawHorizontalLine(originalX, y + (int) (characterDefaultHeight * 0.7), x - originalX, 8388608);
			}
		}
	}

	public void unpackChatImages(IndexedImage[] chatImages) {
		this.chatImages = chatImages;
	}

	public int getCharacterWidth(int character) {
		return characterScreenWidths[character & 0xFF];
	}

	public void setDefaults(int color, int shadow) {
		setDefaults(color, shadow, 256);
	}

	public void setDefaults(int color, int shadow, int trans) {
		strikethroughColor = -1;
		underlineColor = -1;
		textShadowColor = defaultShadow = shadow;
		textColor = defaultColor = color;
		transparency = defaultTransparency = trans;
	}

	public void drawString(String string, int x, int y, int color, int shadow) {
		if (string != null) {
			setDefaults(color, shadow);
			drawString(string, x, y);
		}
	}

	public void drawStringCenter(String string, int drawX, int drawY, int color, int shadow) {
		if (string != null) {
			setDefaults(color, shadow);
			drawString(string, drawX - getTextWidth(string) / 2, drawY);
		}
	}

	public void drawStringRight(String string, int x, int y, int color, int shadow) {
		if (string != null) {
			setDefaults(color, shadow);
			drawString(string, x - getTextWidth(string), y);
		}
	}

	public void drawString(String string, int x, int y) {
		y -= characterDefaultHeight;
		int startIndex = -1;
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(LT_EFFECT)) {
						character = 60;
					} else if (effectString.equals(GT_EFFECT)) {
						character = 62;
					} else if (effectString.equals(NBSP_EFFECT)) {
						character = 160;
					} else if (effectString.equals(EURO_EFFECT)) {
						character = 128;
					} else {
						if (effectString.startsWith(START_IMAGE_EFFECT)) {
							int index = Integer.valueOf(effectString.substring(4));
							if (index < 0 || index >= chatImages.length) {
								return;
							}
							IndexedImage image = chatImages[index];
							int iconModY = image.height;
							if (transparency == 256) {
								image.drawImage(x, y + characterDefaultHeight - iconModY);
							} else {
								image.drawImage(x, y + characterDefaultHeight - iconModY, transparency);
							}
							x += image.width;
						} else {
							setTextEffects(effectString);
						}
						continue;
					}
				}
				if (startIndex == -1) {
					int width = characterWidths[character];
					int height = characterHeights[character];
					if (character != 32) {
						if (transparency == 256) {
							if (textShadowColor != -1) {
								drawCharacter(character, x + characterXOffsets[character] + 1, y + characterYOffsets[character] + 1, width, height, textShadowColor);
							}
							drawCharacter(character, x + characterXOffsets[character], y + characterYOffsets[character], width, height, textColor);
						} else {
							if (textShadowColor != -1) {
								drawAlphaCharacter(character, x + characterXOffsets[character] + 1, y + characterYOffsets[character] + 1, width, height, textShadowColor, transparency);
							}
							drawAlphaCharacter(character, x + characterXOffsets[character], y + characterYOffsets[character], width, height, textColor, transparency);
						}
					}
					int lineWidth = characterScreenWidths[character];
					if (strikethroughColor != -1) {
						Rasterizer.drawHorizontalLine(x, y + (int) (characterDefaultHeight * 0.69999999999999996D), lineWidth, strikethroughColor);
					}
					if (underlineColor != -1) {
						Rasterizer.drawHorizontalLine(x, y + characterDefaultHeight, lineWidth, underlineColor);
					}
					x += lineWidth;
				}
			}
		}
	}

	public void setTextEffects(String string) {
		if (string.startsWith(START_COLOR_EFFECT)) {
			String color = string.substring(4);
			textColor = color.length() < 6 ? Color.decode(color).getRGB() : Integer.parseInt(color, 16);
		} else if (string.equals(END_COLOR_EFFECT)) {
			textColor = defaultColor;
		} else if (string.startsWith(START_TRANSPARENCY_EFFECT)) {
			transparency = Integer.valueOf(string.substring(6));
		} else if (string.equals(END_TRANSPARENCY_EFFECT)) {
			transparency = defaultTransparency;
		} else if (string.startsWith(START_STRIKE_THROUGH_EFFECT)) {
			strikethroughColor = Integer.valueOf(string.substring(4));
		} else if (string.equals(DEFAULT_STRIKE_THROUGH_EFFECT)) {
			strikethroughColor = 8388608;
		} else if (string.equals(END_STRIKE_THROUGH_EFFECT)) {
			strikethroughColor = -1;
		} else if (string.startsWith(START_UNDERLINE_EFFECT)) {
			underlineColor = Integer.valueOf(string.substring(2));
		} else if (string.equals(DEFAULT_UNDERLINE_EFFECT)) {
			underlineColor = 0;
		} else if (string.equals(END_UNDERLINE_EFFECT)) {
			underlineColor = -1;
		} else if (string.startsWith(START_SHADOW_EFFECT)) {
			textShadowColor = Integer.valueOf(string.substring(5));
		} else if (string.equals(DEFAULT_SHADOW_EFFECT)) {
			textShadowColor = 0;
		} else if (string.equals(END_SHADOW_EFFECT)) {
			textShadowColor = defaultShadow;
		} else {
			if (!string.equals(BREAK_LINE_EFFECT)) {
				return;
			}
			setDefaults(defaultColor, defaultShadow, defaultTransparency);
		}
	}

	public int getTextWidth(String string) {
		if (string == null) {
			return 0;
		}
		int startIndex = -1;
		int finalWidth = 0;
		for (int currentCharacter = 0; currentCharacter < string.length(); currentCharacter++) {
			int character = string.charAt(currentCharacter);
			if (character > 255) {
				character = 32;
			}
			if (character == 60) {
				startIndex = currentCharacter;
			} else {
				if (character == 62 && startIndex != -1) {
					String effectString = string.substring(startIndex + 1, currentCharacter);
					startIndex = -1;
					if (effectString.equals(LT_EFFECT)) {
						character = 60;
					} else if (effectString.equals(GT_EFFECT)) {
						character = 62;
					} else if (effectString.equals(NBSP_EFFECT)) {
						character = 160;
					} else if (effectString.equals(EURO_EFFECT)) {
						character = 128;
					} else {
						if (effectString.startsWith(START_IMAGE_EFFECT)) {
							int index = Integer.valueOf(effectString.substring(4));
							if (index < 0 || index >= chatImages.length) {
								return finalWidth;
							}
							finalWidth += chatImages[index].width;
						}
						continue;
					}
				}
				if (startIndex == -1) {
					finalWidth += characterScreenWidths[character];
				}
			}
		}
		return finalWidth;
	}

	public static void drawCharacterPixelsAlpha(int[] rasterizerPixels, byte[] characterPixels, int color, int characterPixel, int rasterizerPixel, int width, int height, int rasterizerPixelOffset, int characterPixelOffset, int alpha) {
		color = ((color & 0xff00ff) * alpha & ~0xff00ff) + ((color & 0xff00) * alpha & 0xff0000) >> 8;
		alpha = 256 - alpha;
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = -width; widthCounter < 0; widthCounter++) {
				if (characterPixels[characterPixel++] != 0) {
					int rasterizerPixelColor = rasterizerPixels[rasterizerPixel];
					rasterizerPixels[rasterizerPixel++] = (((rasterizerPixelColor & 0xff00ff) * alpha & ~0xff00ff) + ((rasterizerPixelColor & 0xff00) * alpha & 0xff0000) >> 8) + color;
				} else {
					rasterizerPixel++;
				}
			}
			rasterizerPixel += rasterizerPixelOffset;
			characterPixel += characterPixelOffset;
		}
	}

	public void drawAlphaCharacter(int character, int x, int y, int width, int height, int color, int alpha) {
		int rasterizerPixel = x + y * Rasterizer.width;
		int rasterizerPixelOffset = Rasterizer.width - width;
		int characterPixelOffset = 0;
		int characterPixel = 0;
		if (y < Rasterizer.topY) {
			int offsetY = Rasterizer.topY - y;
			height -= offsetY;
			y = Rasterizer.topY;
			characterPixel += offsetY * width;
			rasterizerPixel += offsetY * Rasterizer.width;
		}
		if (y + height > Rasterizer.bottomY) {
			height -= y + height - Rasterizer.bottomY;
		}
		if (x < Rasterizer.topX) {
			int offsetX = Rasterizer.topX - x;
			width -= offsetX;
			x = Rasterizer.topX;
			characterPixel += offsetX;
			rasterizerPixel += offsetX;
			characterPixelOffset += offsetX;
			rasterizerPixelOffset += offsetX;
		}
		if (x + width > Rasterizer.bottomX) {
			int widthOffset = x + width - Rasterizer.bottomX;
			width -= widthOffset;
			characterPixelOffset += widthOffset;
			rasterizerPixelOffset += widthOffset;
		}
		if (width > 0 && height > 0) {
			drawCharacterPixelsAlpha(Rasterizer.pixels, characterPixels[character], color, characterPixel, rasterizerPixel, width, height, rasterizerPixelOffset, characterPixelOffset, alpha);
		}
	}

	public static void drawCharacterPixels(int[] rasterizerPixels, byte[] characterPixels, int color, int characterPixel, int rasterizerPixel, int width, int height, int rasterizerPixelOffset, int characterPixelOffset) {
		int negativeQuaterWidth = -(width >> 2);
		int negativeFirstTwoWidthBits = -(width & 0x3);
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int widthCounter = negativeQuaterWidth; widthCounter < 0; widthCounter++) {
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
			}
			for (int widthCounter = negativeFirstTwoWidthBits; widthCounter < 0; widthCounter++) {
				if (characterPixels[characterPixel++] != 0) {
					rasterizerPixels[rasterizerPixel++] = color;
				} else {
					rasterizerPixel++;
				}
			}
			rasterizerPixel += rasterizerPixelOffset;
			characterPixel += characterPixelOffset;
		}
	}

	public void drawCharacter(int character, int x, int y, int width, int height, int color) {
		int rasterizerPixel = x + y * Rasterizer.width;
		int rasterizerPixelOffset = Rasterizer.width - width;
		int characterPixelOffset = 0;
		int characterPixel = 0;
		if (y < Rasterizer.topY) {
			int offsetY = Rasterizer.topY - y;
			height -= offsetY;
			y = Rasterizer.topY;
			characterPixel += offsetY * width;
			rasterizerPixel += offsetY * Rasterizer.width;
		}
		if (y + height > Rasterizer.bottomY) {
			height -= y + height - Rasterizer.bottomY;
		}
		if (x < Rasterizer.topX) {
			int offsetX = Rasterizer.topX - x;
			width -= offsetX;
			x = Rasterizer.topX;
			characterPixel += offsetX;
			rasterizerPixel += offsetX;
			characterPixelOffset += offsetX;
			rasterizerPixelOffset += offsetX;
		}
		if (x + width > Rasterizer.bottomX) {
			int endOffsetX = x + width - Rasterizer.bottomX;
			width -= endOffsetX;
			characterPixelOffset += endOffsetX;
			rasterizerPixelOffset += endOffsetX;
		}
		if (width > 0 && height > 0) {
			drawCharacterPixels(Rasterizer.pixels, characterPixels[character], color, characterPixel, rasterizerPixel, width, height, rasterizerPixelOffset, characterPixelOffset);
		}
	}

}