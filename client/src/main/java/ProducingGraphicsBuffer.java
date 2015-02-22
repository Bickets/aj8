import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public final class ProducingGraphicsBuffer implements ImageProducer, ImageObserver {

	public int height;
	public Image image;
	public int[] pixels;
	public int width;
	ImageConsumer consumer;
	ColorModel model;

	public ProducingGraphicsBuffer(Component component, int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
		model = new DirectColorModel(32, 0xFF0000, 65280, 255);
		image = component.createImage(this);

		setPixels();
		component.prepareImage(image, this);
		setPixels();
		component.prepareImage(image, this);
		setPixels();
		component.prepareImage(image, this);
		initializeRasterizer();
	}

	@Override
	public synchronized void addConsumer(ImageConsumer consumer) {
		this.consumer = consumer;
		consumer.setDimensions(width, height);
		consumer.setProperties(null);
		consumer.setColorModel(model);
		consumer.setHints(14);
	}

	public void drawImage(Graphics graphics, int x, int y) {
		setPixels();
		graphics.drawImage(image, x, y, this);
	}

	@Override
	public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
		return true;
	}

	public void initializeRasterizer() {
		Raster.init(height, width, pixels);
	}

	@Override
	public synchronized boolean isConsumer(ImageConsumer consumer) {
		return this.consumer == consumer;
	}

	@Override
	public synchronized void removeConsumer(ImageConsumer consumer) {
		if (this.consumer == consumer) {
			this.consumer = null;
		}
	}

	@Override
	public void requestTopDownLeftRightResend(ImageConsumer consumer) {
		System.out.println("TDLR");
	}

	public synchronized void setPixels() {
		if (consumer == null) {
			return;
		}

		consumer.setPixels(0, 0, width, height, model, pixels, 0, width);
		consumer.imageComplete(2);
	}

	@Override
	public void startProduction(ImageConsumer consumer) {
		addConsumer(consumer);
	}

}