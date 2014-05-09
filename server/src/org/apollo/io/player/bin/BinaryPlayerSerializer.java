package org.apollo.io.player.bin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.Position;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.io.player.PlayerSerializer;
import org.apollo.io.player.PlayerSerializerResponse;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.security.PlayerCredentials;
import org.apollo.util.StreamUtil;

public class BinaryPlayerSerializer implements PlayerSerializer {

    @Override
    public void savePlayer(Player player) throws IOException {
	File f = BinaryPlayerUtil.getFile(player.getName());

	try (DataOutputStream out = new DataOutputStream(new FileOutputStream(f))) {
	    // write credentials and privileges
	    StreamUtil.writeString(out, player.getName());
	    StreamUtil.writeString(out, player.getCredentials().getPassword());
	    out.writeByte(player.getPrivilegeLevel().toInteger());
	    out.writeBoolean(player.isMembers());

	    // write position
	    Position position = player.getPosition();
	    out.writeShort(position.getX());
	    out.writeShort(position.getY());
	    out.writeByte(position.getHeight());

	    // write appearance
	    out.writeBoolean(player.hasDesignedCharacter());
	    Appearance appearance = player.getAppearance();
	    out.writeByte(appearance.getGender().toInteger());
	    int[] style = appearance.getStyle();
	    for (int i = 0; i < style.length; i++) {
		out.writeByte(style[i]);
	    }
	    int[] colors = appearance.getColors();
	    for (int i = 0; i < colors.length; i++) {
		out.writeByte(colors[i]);
	    }
	    out.flush();

	    // write inventories
	    writeInventory(out, player.getInventory());
	    writeInventory(out, player.getEquipment());
	    writeInventory(out, player.getBank());

	    // write skills
	    SkillSet skills = player.getSkillSet();
	    out.writeByte(skills.size());
	    for (int i = 0; i < skills.size(); i++) {
		Skill skill = skills.getSkill(i);
		out.writeByte(skill.getCurrentLevel());
		out.writeDouble(skill.getExperience());
	    }
	}
    }

    /**
     * Writes an inventory to the specified output stream.
     * 
     * @param out The output stream.
     * @param inventory The inventory.
     * @throws IOException if an I/O error occurs.
     */
    private void writeInventory(DataOutputStream out, Inventory inventory) throws IOException {
	int capacity = inventory.capacity();
	out.writeShort(capacity);

	for (int slot = 0; slot < capacity; slot++) {
	    Item item = inventory.get(slot);
	    if (item != null) {
		out.writeShort(item.getId() + 1);
		out.writeInt(item.getAmount());
	    } else {
		out.writeShort(0);
		out.writeInt(0);
	    }
	}
    }

    /**
     * The default spawn position.
     */
    private static final Position SPAWN_POSITION = new Position(3222, 3222);

    @Override
    public PlayerSerializerResponse loadPlayer(PlayerCredentials credentials) throws IOException {
	File f = BinaryPlayerUtil.getFile(credentials.getUsername());
	if (!f.exists()) {
	    return new PlayerSerializerResponse(LoginConstants.STATUS_OK, new Player(credentials, SPAWN_POSITION));
	}

	try (DataInputStream in = new DataInputStream(new FileInputStream(f))) {
	    // read credentials and privileges
	    String name = StreamUtil.readString(in);
	    String pass = StreamUtil.readString(in);

	    if (!name.equalsIgnoreCase(credentials.getUsername()) || !pass.equals(credentials.getPassword())) {
		return new PlayerSerializerResponse(LoginConstants.STATUS_INVALID_CREDENTIALS);
	    }

	    PrivilegeLevel privilegeLevel = PrivilegeLevel.valueOf(in.readByte());
	    boolean members = in.readBoolean();

	    // read position
	    int x = in.readUnsignedShort();
	    int y = in.readUnsignedShort();
	    int height = in.readUnsignedByte();

	    // read appearance
	    boolean designedCharacter = in.readBoolean();

	    int genderIntValue = in.readUnsignedByte();
	    Gender gender = genderIntValue == Gender.MALE.toInteger() ? Gender.MALE : Gender.FEMALE;
	    int[] style = new int[7];
	    for (int i = 0; i < style.length; i++) {
		style[i] = in.readUnsignedByte();
	    }
	    int[] colors = new int[5];
	    for (int i = 0; i < colors.length; i++) {
		colors[i] = in.readUnsignedByte();
	    }

	    Player p = new Player(credentials, new Position(x, y, height));
	    p.setPrivilegeLevel(privilegeLevel);
	    p.setMembers(members);
	    p.setDesignedCharacter(designedCharacter);
	    p.setAppearance(new Appearance(gender, style, colors));

	    // read inventories
	    readInventory(in, p.getInventory());
	    readInventory(in, p.getEquipment());
	    readInventory(in, p.getBank());

	    // read skills
	    int size = in.readUnsignedByte();
	    SkillSet skills = p.getSkillSet();
	    skills.stopFiringEvents();
	    try {
		for (int i = 0; i < size; i++) {
		    int level = in.readUnsignedByte();
		    double experience = in.readDouble();
		    skills.setSkill(i, new Skill(experience, level, SkillSet.getLevelForExperience(experience)));
		}
	    } finally {
		skills.startFiringEvents();
	    }

	    return new PlayerSerializerResponse(LoginConstants.STATUS_OK, p);
	}
    }

    /**
     * Reads an inventory from the input stream.
     * 
     * @param in The input stream.
     * @param inventory The inventory.
     * @throws IOException if an I/O error occurs.
     */
    private void readInventory(DataInputStream in, Inventory inventory) throws IOException {
	int capacity = in.readUnsignedShort();

	inventory.stopFiringEvents();
	try {
	    for (int slot = 0; slot < capacity; slot++) {
		int id = in.readUnsignedShort();
		int amount = in.readInt();
		if (id != 0) {
		    inventory.set(slot, new Item(id - 1, amount));
		} else {
		    inventory.reset(slot);
		}
	    }
	} finally {
	    inventory.startFiringEvents();
	}
    }

}
