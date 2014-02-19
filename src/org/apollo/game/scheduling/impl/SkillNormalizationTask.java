package org.apollo.game.scheduling.impl;

import org.apollo.game.model.GameCharacter;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * A {@link ScheduledTask} which normalizes the skills of a player: gradually
 * brings them back to their normal value as specified by the experience.
 * @author Graham
 */
public final class SkillNormalizationTask extends ScheduledTask {

    /**
     * The gameCharacter.
     */
    private final GameCharacter gameCharacter;

    /**
     * Creates the skill normalization task.
     * @param gameCharacter The gameCharacter.
     */
    public SkillNormalizationTask(GameCharacter gameCharacter) {
        super(100, false);
        this.gameCharacter = gameCharacter;
    }

    @Override
    public void execute() {
        if (!gameCharacter.isActive() && !gameCharacter.isMob()) {
            stop();
        } else {
            gameCharacter.getSkillSet().normalize();
        }
    }

}
