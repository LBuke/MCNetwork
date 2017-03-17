package me.lukebingham.core.command;

import me.lukebingham.core.util.rank.Rank;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public interface RankedCommand {

    /**
     * Only staff with the specified roll or higher can use the command.
     *
     * @return Roll value
     */
    Rank getRequiredRank();
}
