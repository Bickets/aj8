package com.runescape.media;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public final class ProducingGraphicsBuffer {

	public final int[] pixels;
	public final int width;
	public final int height;
	private final BufferedImage bufferedImage;

	public ProducingGraphicsBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		createRasterizer();
	}

	public void drawGraphics(int x, int y, Graphics graphics) {
		graphics.drawImage(bufferedImage, x, y, null);
	}

	public void createRasterizer() {
		Rasterizer.createRasterizer(pixels, width, height);
	}

}