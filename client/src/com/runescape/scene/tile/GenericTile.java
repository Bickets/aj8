
package com.runescape.scene.tile;

public class GenericTile
{

	public int anInt292;
	public int anInt293;
	public int anInt294;
	public int anInt295;
	public int texture;
	public boolean flat = true;
	public int rgbColor;


	public GenericTile( int i, int i_0_, int i_1_, int i_2_, int texture, int rgbColor, boolean flat )
	{
		anInt292 = i;
		anInt293 = i_0_;
		anInt294 = i_1_;
		anInt295 = i_2_;
		this.texture = texture;
		this.rgbColor = rgbColor;
		this.flat = flat;
	}
}
