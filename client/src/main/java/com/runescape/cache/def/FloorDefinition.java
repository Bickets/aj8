package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class FloorDefinition {

	public static int count;
	public static FloorDefinition[] cache;
	public String name;
	public int rgb;
	public int textureId = -1;
	public boolean shadowing = true;
	public int hue;
	public int saturation;
	public int luminance;
	public int weightedHue;
	public int chroma;
	public int color;

	public static void load(Archive archive) {
		Buffer buffer = new Buffer(archive.getFile("flo.dat"));
		FloorDefinition.count = buffer.getUnsignedLEShort();
		if (FloorDefinition.cache == null) {
			FloorDefinition.cache = new FloorDefinition[FloorDefinition.count];
		}
		for (int floor = 0; floor < FloorDefinition.count; floor++) {
			if (FloorDefinition.cache[floor] == null) {
				FloorDefinition.cache[floor] = new FloorDefinition();
			}
			FloorDefinition.cache[floor].loadDefinition(true, buffer);
		}
	}

	public void loadDefinition(boolean bool, Buffer buffer) {
		while (true) {
			int attributeId = buffer.getUnsignedByte();
			if (attributeId == 0) {
				break;
			}
			if (attributeId == 1) {
				rgb = buffer.get24BitInt();
				blend(rgb);
			} else if (attributeId == 2) {
				textureId = buffer.getUnsignedByte();
			} else if (attributeId == 5) {
				shadowing = false;
			} else if (attributeId == 6) {
				name = buffer.getString();
			} else if (attributeId == 7) {
				int hue = this.hue;
				int saturation = this.saturation;
				int luminance = this.luminance;
				int weightedHue = this.weightedHue;
				blend(buffer.get24BitInt());
				this.hue = hue;
				this.saturation = saturation;
				this.luminance = luminance;
				this.weightedHue = weightedHue;
				chroma = weightedHue;
			} else {
				System.out.println("Error unrecognised config code: " + attributeId);
			}
		}
	}

	private void blend(int color) {
		double r = (color >> 16 & 0xff) / 256.0;
		double b = (color >> 8 & 0xff) / 256.0;
		double g = (color & 0xff) / 256.0;
		double darkest = r;

		if (b < darkest) {
			darkest = b;
		}
		if (g < darkest) {
			darkest = g;
		}

		double brightest = r;
		if (b > brightest) {
			brightest = b;
		}
		if (g > brightest) {
			brightest = g;
		}

		double hue = 0.0;
		double saturation = 0.0;
		double lumination = (darkest + brightest) / 2.0;

		if (darkest != brightest) {
			if (lumination < 0.5) {
				saturation = (brightest - darkest) / (brightest + darkest);
			}
			if (lumination >= 0.5) {
				saturation = (brightest - darkest) / (2.0 - brightest - darkest);
			}
			if (r == brightest) {
				hue = (b - g) / (brightest - darkest);
			} else if (b == brightest) {
				hue = 2.0 + (g - r) / (brightest - darkest);
			} else if (g == brightest) {
				hue = 4.0 + (r - b) / (brightest - darkest);
			}
		}

		hue /= 6.0;

		this.hue = (int) (hue * 256.0);
		this.saturation = (int) (saturation * 256.0);
		this.luminance = (int) (lumination * 256.0);

		if (this.saturation < 0) {
			this.saturation = 0;
		} else if (this.saturation > 255) {
			this.saturation = 255;
		}

		if (this.luminance < 0) {
			this.luminance = 0;
		} else if (this.luminance > 255) {
			this.luminance = 255;
		}

		if (lumination > 0.5) {
			this.chroma = (int) ((1.0 - lumination) * saturation * 512.0);
		} else {
			this.chroma = (int) (lumination * saturation * 512.0);
		}

		if (this.chroma < 1) {
			this.chroma = 1;
		}

		this.weightedHue = (int) (hue * this.chroma);
		this.color = encode(this.weightedHue, this.saturation, this.luminance);
	}

	private final int encode(int hue, int saturation, int luminance) {
		if (luminance > 179) {
			saturation /= 2;
		}
		if (luminance > 192) {
			saturation /= 2;
		}
		if (luminance > 217) {
			saturation /= 2;
		}
		if (luminance > 243) {
			saturation /= 2;
		}
		return (hue / 4 << 10) + (saturation / 32 << 7) + luminance / 2;
	}

}