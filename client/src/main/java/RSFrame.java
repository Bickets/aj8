import java.awt.Frame;
import java.awt.Graphics;

@SuppressWarnings("serial")
public final class RSFrame extends Frame {

	// Frame_Sub1

	GameApplet applet;

	public RSFrame(GameApplet applet, int width, int height) {
		this.applet = applet;

		setTitle("Jagex");
		setResizable(false);
		setVisible(true);
		toFront();
		setSize(width + 8, height + 28);
	}

	@Override
	public Graphics getGraphics() {
		Graphics graphics = super.getGraphics();
		graphics.translate(4, 24);
		return graphics;
	}

	@Override
	public final void paint(Graphics graphics) {
		applet.paint(graphics);
	}

	@Override
	public final void update(Graphics graphics) {
		applet.update(graphics);
	}

}