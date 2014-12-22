package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.media.Rasterizer;
import com.runescape.net.Buffer;

public class IndexedImage extends Rasterizer {

	public int[] pixels;
	public int[] palette;
	public int width;
	public int height;
	public int xDrawOffset;
	public int yDrawOffset;
	public int maxWidth;
	public int maxHeight;

	public IndexedImage(Archive archive, String archiveName, int offset) {
		Buffer dataBuffer = new Buffer(archive.getFile(archiveName + ".dat"));
		Buffer indexBuffer = new Buffer(archive.getFile("index.dat"));
		indexBuffer.offset = dataBuffer.getUnsignedLEShort();
		maxWidth = indexBuffer.getUnsignedLEShort();
		maxHeight = indexBuffer.getUnsignedLEShort();
		int palleteLength = indexBuffer.getUnsignedByte();
		palette = new int[palleteLength];
		for (int index = 0; index < palleteLength - 1; index++) {
			palette[index + 1] = indexBuffer.get24BitInt();
		}
		for (int counter = 0; counter < offset; counter++) {
			indexBuffer.offset += 2;
			dataBuffer.offset += indexBuffer.getUnsignedLEShort() * indexBuffer.getUnsignedLEShort();
			indexBuffer.offset++;
		}
		xDrawOffset = indexBuffer.getUnsignedByte();
		yDrawOffset = indexBuffer.getUnsignedByte();
		width = indexBuffer.getUnsignedLEShort();
		height = indexBuffer.getUnsignedLEShort();
		int type = indexBuffer.getUnsignedByte();
		int pixelLength = width * height;
		pixels = new int[pixelLength];
		if (type == 0) {
			for (int pixel = 0; pixel < pixelLength; pixel++) {
				pixels[pixel] = dataBuffer.get();
			}
		} else if (type == 1) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					pixels[x + y * width] = dataBuffer.get();
				}
			}
		}
	}

	public void resizeToHalfMax() {
		maxWidth /= 2;
		maxHeight /= 2;
		int[] resizedPixels = new int[maxWidth * maxHeight];
		int pixelCount = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				resizedPixels[(x + xDrawOffset >> 1) + (y + yDrawOffset >> 1) * maxWidth] = pixels[pixelCount++];
			}
		}
		pixels = resizedPixels;
		width = maxWidth;
		height = maxHeight;
		xDrawOffset = 0;
		yDrawOffset = 0;
	}

	public void resizeToMax() {
		if (width != maxWidth || height != maxHeight) {
			int[] resizedPixels = new int[maxWidth * maxHeight];
			int pixelCount = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					resizedPixels[x + xDrawOffset + (y + yDrawOffset) * maxWidth] = pixels[pixelCount++];
				}
			}
			pixels = resizedPixels;
			width = maxWidth;
			height = maxHeight;
			xDrawOffset = 0;
			yDrawOffset = 0;
		}
	}

	public void flipHorizontal() {
		int[] flipedPixels = new int[width * height];
		int pixelCount = 0;
		for (int y = 0; y < height; y++) {
			for (int x = width - 1; x >= 0; x--) {
				flipedPixels[pixelCount++] = pixels[x + y * width];
			}
		}
		pixels = flipedPixels;
		xDrawOffset = maxWidth - width - xDrawOffset;
	}

	public void flipVertical() {
		int[] flipedPixels = new int[width * height];
		int pixelCount = 0;
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				flipedPixels[pixelCount++] = pixels[x + y * width];
			}
		}
		pixels = flipedPixels;
		yDrawOffset = maxHeight - height - yDrawOffset;
	}

	public void mixPalette(int red, int green, int blue) {
		for (int index = 0; index < palette.length; index++) {
			int r = palette[index] >> 16 & 0xff;
			r += red;
			if (r < 0) {
				r = 0;
			} else if (r > 255) {
				r = 255;
			}
			int g = palette[index] >> 8 & 0xff;
			g += green;
			if (g < 0) {
				g = 0;
			} else if (g > 255) {
				g = 255;
			}
			int b = palette[index] & 0xff;
			b += blue;
			if (b < 0) {
				b = 0;
			} else if (b > 255) {
				b = 255;
			}
			palette[index] = (r << 16) + (g << 8) + b;
		}
	}

	public void drawImage(int x, int y) {
		x += xDrawOffset;
		y += yDrawOffset;
		int offset = x + y * Rasterizer.width;
		int originalOffset = 0;
		int imageHeight = height;
		int imageWidth = width;
		int deviation = Rasterizer.width - imageWidth;
		int originalDeviation = 0;
		if (y < Rasterizer.topY) {
			int yOffset = Rasterizer.topY - y;
			imageHeight -= yOffset;
			y = Rasterizer.topY;
			originalOffset += yOffset * imageWidth;
			offset += yOffset * Rasterizer.width;
		}
		if (y + imageHeight > Rasterizer.bottomY) {
			imageHeight -= y + imageHeight - Rasterizer.bottomY;
		}
		if (x < Rasterizer.topX) {
			int xOffset = Rasterizer.topX - x;
			imageWidth -= xOffset;
			x = Rasterizer.topX;
			originalOffset += xOffset;
			offset += xOffset;
			originalDeviation += xOffset;
			deviation += xOffset;
		}
		if (x + imageWidth > Rasterizer.bottomX) {
			int xOffset = x + imageWidth - Rasterizer.bottomX;
			imageWidth -= xOffset;
			originalDeviation += xOffset;
			deviation += xOffset;
		}
		if (imageWidth > 0 && imageHeight > 0) {
			copyPixels(pixels, Rasterizer.pixels, imageWidth, imageHeight, offset, originalOffset, deviation, originalDeviation, palette);
		}
	}

	public void drawImage(int i, int k, int color) {
		int tempWidth = width + 2;
		int tempHeight = height + 2;
		int[] tempArray = new int[tempWidth * tempHeight];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (pixels[x + y * width] != 0)
					tempArray[(x + 1) + (y + 1) * tempWidth] = pixels[x + y * width];
			}
		}
		for (int x = 0; x < tempWidth; x++) {
			for (int y = 0; y < tempHeight; y++) {
				if (tempArray[(x) + (y) * tempWidth] == 0) {
					if (x < tempWidth - 1 && tempArray[(x + 1) + ((y) * tempWidth)] > 0 && tempArray[(x + 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (x > 0 && tempArray[(x - 1) + ((y) * tempWidth)] > 0 && tempArray[(x - 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (y < tempHeight - 1 && tempArray[(x) + ((y + 1) * tempWidth)] > 0 && tempArray[(x) + ((y + 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (y > 0 && tempArray[(x) + ((y - 1) * tempWidth)] > 0 && tempArray[(x) + ((y - 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
				}
			}
		}
		i--;
		k--;
		i += xDrawOffset;
		k += yDrawOffset;
		int l = i + k * Rasterizer.width;
		int i1 = 0;
		int j1 = tempHeight;
		int k1 = tempWidth;
		int l1 = Rasterizer.width - k1;
		int i2 = 0;
		if (k < Rasterizer.topY) {
			int j2 = Rasterizer.topY - k;
			j1 -= j2;
			k = Rasterizer.topY;
			i1 += j2 * k1;
			l += j2 * Rasterizer.width;
		}
		if (k + j1 > Rasterizer.bottomY) {
			j1 -= (k + j1) - Rasterizer.bottomY;
		}
		if (i < Rasterizer.topX) {
			int k2 = Rasterizer.topX - i;
			k1 -= k2;
			i = Rasterizer.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > Rasterizer.bottomX) {
			int l2 = (i + k1) - Rasterizer.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			copyPixels(tempArray, Rasterizer.pixels, i1, l, k1, j1, l1, i2, palette);
		}
	}

	private void copyPixels(int[] pixels, int[] rasterizerPixels, int width, int height, int offset, int originalOffset, int deviation, int originalDeviation, int[] pallete) {
		int shiftedWidth = -(width >> 2);
		width = -(width & 0x3);
		for (int heightCounter = -height; heightCounter < 0; heightCounter++) {
			for (int shiftedWidthCounter = shiftedWidth; shiftedWidthCounter < 0; shiftedWidthCounter++) {
				int pixel = pixels[originalOffset++];
				if (pixel != 0) {
					rasterizerPixels[offset++] = pallete[pixel & 0xff];
				} else {
					offset++;
				}
				pixel = pixels[originalOffset++];
				if (pixel != 0) {
					rasterizerPixels[offset++] = pallete[pixel & 0xff];
				} else {
					offset++;
				}
				pixel = pixels[originalOffset++];
				if (pixel != 0) {
					rasterizerPixels[offset++] = pallete[pixel & 0xff];
				} else {
					offset++;
				}
				pixel = pixels[originalOffset++];
				if (pixel != 0) {
					rasterizerPixels[offset++] = pallete[pixel & 0xff];
				} else {
					offset++;
				}
			}
			for (int widthCounter = width; widthCounter < 0; widthCounter++) {
				int pixel = pixels[originalOffset++];
				if (pixel != 0) {
					rasterizerPixels[offset++] = pallete[pixel & 0xff];
				} else {
					offset++;
				}
			}
			offset += deviation;
			originalOffset += originalDeviation;
		}
	}
}
