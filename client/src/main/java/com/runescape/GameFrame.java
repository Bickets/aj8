package com.runescape;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JMenuBar;

public final class GameFrame extends Frame {

	private static final long serialVersionUID = 3026034244991236878L;

	private final GameShell gameShell;

	public GameFrame(GameShell gameShell, int width, int height) {
		super("Jagex");
		this.gameShell = gameShell;

		setBackground(Color.BLACK);

		JMenuBar bar = new JMenuBar();
		add(BorderLayout.NORTH, bar);

		Insets insets = getInsets();
		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);

		setFocusTraversalKeysEnabled(false);
		setUndecorated(false);
		setResizable(true);
		setLocationRelativeTo(null);

		requestFocus();
		toFront();

		setVisible(true);
	}

	@Override
	public Graphics getGraphics() {
		Graphics graphics = super.getGraphics();
		Insets insets = getInsets();
		graphics.translate(insets.left, insets.top);
		return graphics;
	}

	@Override
	public final void update(Graphics graphics) {
		gameShell.update(graphics);
	}

	@Override
	public final void paint(Graphics graphics) {
		gameShell.paint(graphics);
	}

}