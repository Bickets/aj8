package org.apollo.io.player.bin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.appearance.Appearance;
import org.apollo.game.model.appearance.Gender;
import org.apollo.game.model.inv.Inventory;
import org.apollo.game.model.skill.Skill;
import org.apollo.game.model.skill.SkillSet;
import org.apollo.io.player.PlayerSerializer;
import org.apollo.io.player.PlayerSerializerResponse;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.security.PlayerCredentials;
import org.apollo.util.StreamUtil;

/**
 * An implementation of {@link PlayerSerializer} used to save
 * <code>Player</code>s as binary objects.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 * @author Graham Edgecombe
 */
public final class BinaryPlayerSerializer extends PlayerSerializer {

	/**
	 * Constructs a new {@link BinaryPlayerSerializer} with the specified world.
	 *
	 * @param world The world this player is in.
	 */
	public BinaryPlayerSerializer(World world) {
		super(world);
	}

	@Override
	protected void savePlayer(Player player) throws IOException {
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
			for (int element : style) {
				out.writeByte(element);
			}
			int[] colors = appearance.getColors();
			for (int color : colors) {
				out.writeByte(color);
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
		} finally {
			appendToCache(player);
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

	@Override
	protected PlayerSerializerResponse loadPlayer(PlayerCredentials credentials) throws IOException {
		File f = BinaryPlayerUtil.getFile(credentials.getUsername());
		if (!f.exists()) {
			return new PlayerSerializerResponse(LoginConstants.STATUS_OK, new Player(credentials, Player.DEFAULT_SPAWN_POSITION, world));
		}

		try {
			/* Attempt to grab the cached player, if it exists. */
			Player player = getFromCache(credentials.getEncodedUsername());

			if (player != null) {
				/* It exists, validate the caches credentials to the requested. */
				if (!player.getName().equalsIgnoreCase(credentials.getUsername()) || !player.getPassword().equals(credentials.getPassword())) {
					return new PlayerSerializerResponse(LoginConstants.STATUS_INVALID_CREDENTIALS);
				}

				return new PlayerSerializerResponse(LoginConstants.STATUS_OK, player);
			}

		} finally {
			/*
			 * Invalidate this cache entry, it will be added again when the
			 * player is saved.
			 */
			removeFromCache(credentials.getEncodedUsername());
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

			Player player = new Player(credentials, new Position(x, y, height), world);
			player.setPrivilegeLevel(privilegeLevel);
			player.setMembers(members);
			player.setDesignedCharacter(designedCharacter);
			player.setAppearance(new Appearance(gender, style, colors));

			// read inventories
			readInventory(in, player.getInventory());
			readInventory(in, player.getEquipment());
			readInventory(in, player.getBank());

			// read skills
			int size = in.readUnsignedByte();
			SkillSet skills = player.getSkillSet();
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

			return new PlayerSerializerResponse(LoginConstants.STATUS_OK, player);
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