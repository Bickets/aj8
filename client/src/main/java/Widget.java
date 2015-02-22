public class Widget {

	public static Widget widgets[];
	static Cache models = new Cache(30);
	private static Cache sprites;

	public static void clearModels(int id, int type, Model model) {
		models.unlink();
		if (model != null && type != 4) {
			models.put(model, (type << 16) + id);
		}
	}

	public static void load(Archive interfaces, Archive graphics, Font[] fonts) {
		sprites = new Cache(50000);
		Buffer buffer = new Buffer(interfaces.extract("data"));
		int i = -1;
		int count = buffer.readUShort();
		widgets = new Widget[count];

		while (buffer.position < buffer.payload.length) {
			int id = buffer.readUShort();
			if (id == 65535) {
				i = buffer.readUShort();
				id = buffer.readUShort();
			}

			Widget widget = widgets[id] = new Widget();
			widget.id = id;
			widget.anInt236 = i;
			widget.anInt262 = buffer.readUByte();
			widget.anInt217 = buffer.readUByte();
			widget.anInt214 = buffer.readUShort();
			widget.width = buffer.readUShort();
			widget.height = buffer.readUShort();
			widget.aByte254 = (byte) buffer.readUByte();
			widget.anInt230 = buffer.readUByte();

			if (widget.anInt230 != 0) {
				widget.anInt230 = (widget.anInt230 - 1 << 8) + buffer.readUByte();
			} else {
				widget.anInt230 = -1;
			}

			int i1 = buffer.readUByte();
			if (i1 > 0) {
				widget.anIntArray245 = new int[i1];
				widget.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					widget.anIntArray245[j1] = buffer.readUByte();
					widget.anIntArray212[j1] = buffer.readUShort();
				}

			}

			int scriptCount = buffer.readUByte();
			if (scriptCount > 0) {
				widget.scripts = new int[scriptCount][];
				for (int script = 0; script < scriptCount; script++) {
					int instructionCount = buffer.readUShort();
					widget.scripts[script] = new int[instructionCount];
					for (int instruction = 0; instruction < instructionCount; instruction++) {
						widget.scripts[script][instruction] = buffer.readUShort();
					}
				}
			}

			if (widget.anInt262 == 0) {
				widget.anInt261 = buffer.readUShort();
				widget.aBoolean266 = buffer.readUByte() == 1;
				int i2 = buffer.readUShort();
				widget.children = new int[i2];
				widget.anIntArray241 = new int[i2];
				widget.anIntArray272 = new int[i2];

				for (int j3 = 0; j3 < i2; j3++) {
					widget.children[j3] = buffer.readUShort();
					widget.anIntArray241[j3] = buffer.readShort();
					widget.anIntArray272[j3] = buffer.readShort();
				}
			}

			if (widget.anInt262 == 1) {
				widget.anInt211 = buffer.readUShort();
				widget.aBoolean251 = buffer.readUByte() == 1;
			}

			if (widget.anInt262 == 2) {
				widget.inventoryIds = new int[widget.width * widget.height];
				widget.inventoryAmounts = new int[widget.width * widget.height];
				widget.aBoolean259 = buffer.readUByte() == 1;
				widget.aBoolean249 = buffer.readUByte() == 1;
				widget.aBoolean242 = buffer.readUByte() == 1;
				widget.aBoolean235 = buffer.readUByte() == 1;
				widget.anInt231 = buffer.readUByte();
				widget.anInt244 = buffer.readUByte();
				widget.anIntArray215 = new int[20];
				widget.anIntArray247 = new int[20];
				widget.wornIcons = new DirectSprite[20];

				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = buffer.readUByte();
					if (k3 == 1) {
						widget.anIntArray215[j2] = buffer.readShort();
						widget.anIntArray247[j2] = buffer.readShort();
						String name = buffer.readString();
						if (graphics != null && name.length() > 0) {
							int position = name.lastIndexOf(",");
							widget.wornIcons[j2] = getSprite(Integer.parseInt(name.substring(position + 1)), graphics,
									name.substring(0, position));
						}
					}
				}

				widget.menuActions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					widget.menuActions[l3] = buffer.readString();

					if (widget.menuActions[l3].length() == 0) {
						widget.menuActions[l3] = null;
					}
				}
			}

			if (widget.anInt262 == 3) {
				widget.filled = buffer.readUByte() == 1;
			}

			if (widget.anInt262 == 4 || widget.anInt262 == 1) {
				widget.aBoolean223 = buffer.readUByte() == 1;
				int font = buffer.readUByte();
				if (fonts != null) {
					widget.font = fonts[font];
				}

				widget.aBoolean268 = buffer.readUByte() == 1;
			}

			if (widget.anInt262 == 4) {
				widget.customisableText = buffer.readString();
				widget.aString228 = buffer.readString();
			}

			if (widget.anInt262 == 1 || widget.anInt262 == 3 || widget.anInt262 == 4) {
				widget.colour = buffer.readInt();
			}

			if (widget.anInt262 == 3 || widget.anInt262 == 4) {
				widget.anInt219 = buffer.readInt();
				widget.anInt216 = buffer.readInt();
				widget.anInt239 = buffer.readInt();
			} else if (widget.anInt262 == 5) {
				String s = buffer.readString();
				if (graphics != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					widget.aClass30_Sub2_Sub1_Sub1_207 = getSprite(Integer.parseInt(s.substring(i4 + 1)), graphics,
							s.substring(0, i4));
				}

				s = buffer.readString();
				if (graphics != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					widget.aClass30_Sub2_Sub1_Sub1_260 = getSprite(Integer.parseInt(s.substring(j4 + 1)), graphics,
							s.substring(0, j4));
				}
			} else if (widget.anInt262 == 6) {
				int content = buffer.readUByte();
				if (content != 0) {
					widget.mediaType = 1;
					widget.media = (content - 1 << 8) + buffer.readUByte();
				}

				content = buffer.readUByte();
				if (content != 0) {
					widget.anInt255 = 1;
					widget.anInt256 = (content - 1 << 8) + buffer.readUByte();
				}

				content = buffer.readUByte();
				if (content != 0) {
					widget.mediaAnimationId = (content - 1 << 8) + buffer.readUByte();
				} else {
					widget.mediaAnimationId = -1;
				}

				content = buffer.readUByte();
				if (content != 0) {
					widget.anInt258 = (content - 1 << 8) + buffer.readUByte();
				} else {
					widget.anInt258 = -1;
				}

				widget.spriteScale = buffer.readUShort();
				widget.spritePitch = buffer.readUShort();
				widget.spriteRoll = buffer.readUShort();
			} else if (widget.anInt262 == 7) {
				widget.inventoryIds = new int[widget.width * widget.height];
				widget.inventoryAmounts = new int[widget.width * widget.height];
				widget.aBoolean223 = buffer.readUByte() == 1;
				int font = buffer.readUByte();
				if (fonts != null) {
					widget.font = fonts[font];
				}

				widget.aBoolean268 = buffer.readUByte() == 1;
				widget.colour = buffer.readInt();
				widget.anInt231 = buffer.readShort();
				widget.anInt244 = buffer.readShort();
				widget.aBoolean249 = buffer.readUByte() == 1;
				widget.menuActions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					widget.menuActions[k4] = buffer.readString();
					if (widget.menuActions[k4].length() == 0) {
						widget.menuActions[k4] = null;
					}
				}
			}

			if (widget.anInt217 == 2 || widget.anInt262 == 2) {
				widget.aString222 = buffer.readString();
				widget.aString218 = buffer.readString();
				widget.anInt237 = buffer.readUShort();
			}

			if (widget.anInt217 == 1 || widget.anInt217 == 4 || widget.anInt217 == 5 || widget.anInt217 == 6) {
				widget.text = buffer.readString();
				if (widget.text.length() == 0) {
					if (widget.anInt217 == 1) {
						widget.text = "Ok";
					}
					if (widget.anInt217 == 4) {
						widget.text = "Select";
					}
					if (widget.anInt217 == 5) {
						widget.text = "Select";
					}
					if (widget.anInt217 == 6) {
						widget.text = "Continue";
					}
				}
			}

			if (widget.anInt217 == 2) {
				System.out.println("Widget: " + widget.id + ", " + (widget.children != null ? widget.children.length : -1));
			}
		}
	}

	private static DirectSprite getSprite(int position, Archive archive, String name) {
		long key = (StringUtils.hashSpriteName(name) << 8) + position;
		DirectSprite sprite = (DirectSprite) sprites.get(key);
		if (sprite != null) {
			return sprite;
		}

		try {
			sprite = new DirectSprite(archive, name, position);
			sprites.put(sprite, key);
		} catch (Exception ex) {
			return null;
		}
		return sprite;
	}

	public boolean aBoolean223;

	public boolean aBoolean235;
	public boolean aBoolean242;
	public boolean aBoolean249;
	public boolean aBoolean251;
	public boolean aBoolean259;
	public boolean aBoolean266;
	public boolean aBoolean268;
	public byte aByte254;
	public DirectSprite aClass30_Sub2_Sub1_Sub1_207;
	public DirectSprite aClass30_Sub2_Sub1_Sub1_260;
	public int anInt211;
	public int anInt214;
	public int anInt216;
	public int anInt217;
	public int anInt219;
	public int anInt230;
	public int anInt231;
	public int anInt236;
	public int anInt237;
	public int anInt239;
	public int anInt244;
	public int anInt255;
	public int anInt256;
	public int anInt258;
	public int anInt261;
	public int anInt262;
	public int[] anIntArray212;
	public int[] anIntArray215;
	public int[] anIntArray241;
	public int[] anIntArray245;
	public int[] anIntArray247;
	public int[] anIntArray272;
	public String aString218;
	public String aString222;
	public String aString228;
	public int[] children;
	public int colour;
	public String customisableText;
	public int displayedFrameCount;
	/**
	 * Indicates whether or not the widget should be drawn filled, or just as an outline.
	 */
	public boolean filled;
	public Font font;
	public int height;
	public int horizontalDrawOffset;
	public int id;
	public int[] inventoryAmounts;
	public int[] inventoryIds;
	public int lastFrameTime;
	public int media;
	public int mediaAnimationId;
	public int mediaType;
	public String[] menuActions;
	public int[][] scripts;
	public int scrollPosition;
	public int spritePitch;
	public int spriteRoll;
	public int spriteScale;
	public String text;
	public int verticalDrawOffset;
	public int width;
	public DirectSprite[] wornIcons; // FIXME nope

	public Model method209(int primaryFrame, int secondaryFrame, boolean flag) {
		Model model = flag ? getModel(anInt255, anInt256) : getModel(mediaType, media);

		if (model == null) {
			return null;
		}

		if (primaryFrame == -1 && secondaryFrame == -1 && model.triangleColours == null) {
			return model;
		}

		Model animatedModel = new Model(true, Frame.isInvalid(primaryFrame) & Frame.isInvalid(secondaryFrame), false, model);
		if (primaryFrame != -1 || secondaryFrame != -1) {
			animatedModel.skin();
		}

		if (primaryFrame != -1) {
			animatedModel.apply(primaryFrame);
		}

		if (secondaryFrame != -1) {
			animatedModel.apply(secondaryFrame);
		}

		animatedModel.light(64, 768, -50, -10, -50, true);
		return animatedModel;
	}

	public void swapInventoryItems(int first, int second) {
		int tmp = inventoryIds[first];
		inventoryIds[first] = inventoryIds[second];
		inventoryIds[second] = tmp;

		tmp = inventoryAmounts[first];
		inventoryAmounts[first] = inventoryAmounts[second];
		inventoryAmounts[second] = tmp;
	}

	private Model getModel(int type, int id) {
		Model model = (Model) models.get((type << 16) + id);
		if (model != null) {
			return model;
		}

		if (type == 1) {
			model = Model.lookup(id);
		} else if (type == 2) {
			model = NpcDefinition.lookup(id).model();
		} else if (type == 3) {
			model = Client.localPlayer.getBodyModel();
		} else if (type == 4) {
			model = ItemDefinition.lookup(id).asStack(50);
		}

		if (model != null) {
			models.put(model, (type << 16) + id);
		}

		return model;
	}

}