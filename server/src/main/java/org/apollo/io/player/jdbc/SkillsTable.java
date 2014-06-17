package org.apollo.io.player.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;

public final class SkillsTable extends Table {

    private final PreparedStatement loadStatement;
    private final PreparedStatement saveStatement;

    public SkillsTable(Connection connection) throws SQLException {
	loadStatement = connection.prepareStatement("SELECT * FROM skills WHERE player_id = ?;");
	saveStatement = connection.prepareStatement("INSERT INTO skills (player_id, current_level, experience) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE current_level = VALUES(current_level), experience = VALUES(experience);");
    }

    @Override
    public void load(Player player) throws SQLException, IOException {
	loadStatement.setInt(1, player.getDatabaseId());

	SkillSet skills = player.getSkillSet();
	skills.stopFiringEvents();
	try {
	    try (ResultSet set = loadStatement.executeQuery()) {
		while (set.next()) {
		    for (int i = 0; i < SkillSet.SKILL_COUNT; i++) {
			int level = set.getInt("current_level");
			double experience = set.getDouble("experience");
			skills.setSkill(i, new Skill(experience, level, SkillSet.getLevelForExperience(experience)));
		    }
		}
	    }
	} finally {
	    skills.startFiringEvents();
	}
    }

    @Override
    public void save(Player player) throws SQLException, IOException {
	saveStatement.setInt(1, player.getDatabaseId());

	SkillSet skills = player.getSkillSet();

	for (int i = 0; i < skills.size(); i++) {
	    Skill skill = skills.getSkill(i);
	    saveStatement.setInt(2, skill.getCurrentLevel());
	    saveStatement.setDouble(3, skill.getExperience());

	    saveStatement.addBatch();
	}

	saveStatement.executeBatch();
    }

}
