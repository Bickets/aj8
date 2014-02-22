
package com.runescape;

import java.awt.Frame;
import java.awt.Graphics;

public class GameFrame extends Frame
{

	private static final long serialVersionUID = 3026034244991236878L;

	private final GameShell gameStub;


	public GameFrame( GameShell gameStub, int width, int height )
	{
		this.gameStub = gameStub;
		setTitle( "Jagex" );
		setResizable( false );
		setVisible( true );
		toFront();
		this.setSize( width + 8, height + 28 );
	}


	@Override
	public Graphics getGraphics()
	{
		Graphics graphics = super.getGraphics();
		graphics.translate( 4, 24 );
		return graphics;
	}


	@Override
	public final void update( Graphics graphics )
	{
		gameStub.update( graphics );
	}


	@Override
	public final void paint( Graphics graphics )
	{
		gameStub.paint( graphics );
	}
}
