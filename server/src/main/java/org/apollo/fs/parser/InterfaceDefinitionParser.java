package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.fs.Archive;
import org.apollo.fs.FileSystem;
import org.apollo.game.model.def.InterfaceDefinition;
import org.apollo.util.ByteBufferUtil;

/**
 * A class which parses interface definitions.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class InterfaceDefinitionParser {

	/**
	 * Parses and returns an array of interface definitions from the specified
	 * file system.
	 *
	 * @param fs The file system.
	 * @return An array of parsed interface definitions.
	 * @throws IOException If some I/O exception occurs.
	 */
	public static InterfaceDefinition[] parse(FileSystem fs) throws IOException {
		Archive archive = fs.getArchive(FileSystem.INTERFACE_ARCHIVE);
		ByteBuffer buffer = archive.getData("data");

		int count = buffer.getShort() & 0xFFFF;
		InterfaceDefinition[] defs = new InterfaceDefinition[count];

		while (buffer.hasRemaining()) {
			int index = buffer.getShort() & 0xFFFF;
			int parentId = -1;
			if (index == 65535) {
				parentId = buffer.getShort() & 0xFFFF;
				index = buffer.getShort() & 0xFFFF;
			}
			InterfaceDefinition def = new InterfaceDefinition(index);
			defs[index] = def;
			def.setParentId(parentId);
			def.setType(buffer.get() & 0xFF);
			def.setActionType(buffer.get() & 0xFF);
			def.setContentType(buffer.getShort() & 0xFFFF);
			def.setWidth(buffer.getShort() & 0xFFFF);
			def.setHeight(buffer.getShort() & 0xFFFF);
			def.setAlpha((byte) (buffer.get() & 0xFF));
			def.setHoveredPopup(buffer.get() & 0xFF);

			if (def.getHoveredPopup() != 0) {
				def.setHoveredPopup((def.getHoveredPopup() - 1 << 8) + (buffer.get() & 0xFF));
			} else {
				def.setHoveredPopup(-1);
			}

			int conditionCount = buffer.get() & 0xFF;
			if (conditionCount > 0) {
				def.setConditionTypes(new int[conditionCount]);
				def.setConditionValues(new int[conditionCount]);
				for (int condition = 0; condition < conditionCount; condition++) {
					def.getConditionTypes()[condition] = buffer.get() & 0xFF;
					def.getConditionValues()[condition] = buffer.getShort() & 0xFFFF;
				}
			}

			int opcodeCount = buffer.get() & 0xFF;
			if (opcodeCount > 0) {
				def.setOpcodes(new int[opcodeCount][]);
				for (int opcode = 0; opcode < opcodeCount; opcode++) {
					int subOpcodeCount = buffer.getShort() & 0xFFFF;
					def.getOpcodes()[opcode] = new int[subOpcodeCount];
					for (int subOpcode = 0; subOpcode < subOpcodeCount; subOpcode++) {
						def.getOpcodes()[opcode][subOpcode] = buffer.getShort() & 0xFFFF;
					}
				}
			}

			if (def.getType() == 0) {
				def.setScrollLimit(buffer.getShort() & 0xFFFF);
				def.setHiddenUntilHovered((buffer.get() & 0xFF) == 1);
				int childrenCount = buffer.getShort() & 0xFFFF;
				def.setChildren(new int[childrenCount]);
				def.setChildrenX(new int[childrenCount]);
				def.setChildrenY(new int[childrenCount]);
				for (int child = 0; child < childrenCount; child++) {
					def.getChildren()[child] = buffer.getShort() & 0xFFFF;
					def.getChildrenX()[child] = buffer.getShort();
					def.getChildrenY()[child] = buffer.getShort();
				}
			}

			if (def.getType() == 1) {
				buffer.getShort();
				buffer.get();
			}

			if (def.getType() == 2) {
				def.setItems(new int[def.getWidth() * def.getHeight()]);
				def.setItemAmounts(new int[def.getWidth() * def.getHeight()]);
				def.setItemSwapable((buffer.get() & 0xFF) == 1);
				def.setInventory((buffer.get() & 0xFF) == 1);
				def.setItemUsable((buffer.get() & 0xFF) == 1);
				def.setItemDeletesDraged((buffer.get() & 0xFF) == 1);
				def.setItemSpritePadsX(buffer.get() & 0xFF);
				def.setItemSpritePadsY(buffer.get() & 0xFF);
				def.setImageX(new int[20]);
				def.setImageY(new int[20]);
				for (int sprite = 0; sprite < 20; sprite++) {
					int hasSprite = buffer.get() & 0xFF;
					if (hasSprite == 1) {
						def.getImageX()[sprite] = buffer.getShort();
						def.getImageY()[sprite] = buffer.getShort();
						ByteBufferUtil.readString(buffer);
					}
				}
				def.setActions(new String[5]);
				for (int action = 0; action < 5; action++) {
					def.getActions()[action] = ByteBufferUtil.readString(buffer);
					if (def.getActions()[action].length() == 0) {
						def.getActions()[action] = null;
					}
				}
			}

			if (def.getType() == 3) {
				def.setFilled((buffer.get() & 0xFF) == 1);
			}

			if (def.getType() == 4 || def.getType() == 1) {
				def.setTypeFaceCentered((buffer.get() & 0xFF) == 1);
				buffer.get();
				def.setTypeFaceShadowed((buffer.get() & 0xFF) == 1);
			}

			if (def.getType() == 4) {
				def.setDisabledText(ByteBufferUtil.readString(buffer));
				def.setEnabledText(ByteBufferUtil.readString(buffer));
			}

			if (def.getType() == 1 || def.getType() == 3 || def.getType() == 4) {
				def.setDisabledColor(buffer.getInt());
			}

			if (def.getType() == 3 || def.getType() == 4) {
				def.setEnabledColor(buffer.getInt());
				def.setDisabledHoveredColor(buffer.getInt());
				def.setEnabledHoveredColor(buffer.getInt());
			}

			if (def.getType() == 5) {
				ByteBufferUtil.readString(buffer);
				ByteBufferUtil.readString(buffer);
			}

			if (def.getType() == 6) {
				index = buffer.get() & 0xFF;
				if (index != 0) {
					def.setModelType(1);
					def.setModelId((index - 1 << 8) + (buffer.get() & 0xFF));
				}
				index = buffer.get() & 0xFF;
				if (index != 0) {
					def.setEnabledModelType(1);
					def.setEnabledModelId((index - 1 << 8) + (buffer.get() & 0xFF));
				}
				index = buffer.get() & 0xFF;
				if (index != 0) {
					def.setDisabledAnimation((index - 1 << 8) + (buffer.get() & 0xFF));
				} else {
					def.setDisabledAnimation(-1);
				}
				index = buffer.get() & 0xFF;
				if (index != 0) {
					def.setEnabledAnimation((index - 1 << 8) + (buffer.get() & 0xFF));
				} else {
					def.setEnabledAnimation(-1);
				}
				def.setZoom(buffer.getShort() & 0xFFFF);
				def.setRotationX(buffer.getShort() & 0xFFFF);
				def.setRotationY(buffer.getShort() & 0xFFFF);
			}

			if (def.getType() == 7) {
				def.setItems(new int[def.getWidth() * def.getHeight()]);
				def.setItemAmounts(new int[def.getWidth() * def.getHeight()]);
				def.setTypeFaceCentered((buffer.get() & 0xFF) == 1);
				buffer.get();
				def.setTypeFaceShadowed((buffer.get() & 0xFF) == 1);
				def.setDisabledColor(buffer.getInt());
				def.setItemSpritePadsX(buffer.getShort());
				def.setItemSpritePadsY(buffer.getShort());
				def.setInventory((buffer.get() & 0xFF) == 1);
				def.setActions(new String[5]);
				for (int action = 0; action < 5; action++) {
					def.getActions()[action] = ByteBufferUtil.readString(buffer);
					if (def.getActions()[action].length() == 0) {
						def.getActions()[action] = null;
					}
				}
			}

			if (def.getActionType() == 2 || def.getType() == 2) {
				def.setSelectedActionName(ByteBufferUtil.readString(buffer));
				def.setSpellName(ByteBufferUtil.readString(buffer));
				def.setSpellUsableOn(buffer.getShort() & 0xFFFF);
			}

			if (def.getType() == 8) {
				def.setDisabledText(ByteBufferUtil.readString(buffer));
			}

			if (def.getActionType() == 1 || def.getActionType() == 4 || def.getActionType() == 5 || def.getActionType() == 6) {
				def.setTooltip(ByteBufferUtil.readString(buffer));
				if (def.getTooltip().length() == 0) {
					if (def.getActionType() == 1) {
						def.setTooltip("Ok");
					}
					if (def.getActionType() == 4) {
						def.setTooltip("Select");
					}
					if (def.getActionType() == 5) {
						def.setTooltip("Select");
					}
					if (def.getActionType() == 6) {
						def.setTooltip("Continue");
					}
				}
			}
		}

		return defs;
	}

}