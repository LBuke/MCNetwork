package me.lukebingham.game.type;

/**
 * Created by LukeBingham on 29/03/2017.
 */
public interface TeamMode extends PlayMode {

    /**
     * @return Name of the current {@link me.lukebingham.game.type.PlayMode}
     */
    @Override default String getName() {
        return "Teams";
    }

    /**
     * Get the amount of players that each {@link me.lukebingham.game.team.Team} can handle.
     * @return amount of players per {@link me.lukebingham.game.team.Team}
     */
    default int getPlayersPerTeam() {
        return 2;
    }

    /**
     * Get the amount of {@link me.lukebingham.game.team.Team}s allowed in the current {@link me.lukebingham.game.Game}.
     * @return amount of {@link me.lukebingham.game.team.Team}s
     */
    default int getTeamsAmount() {
        return getMaxPlayers() / getPlayersPerTeam();
    }
}
