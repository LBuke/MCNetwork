package me.lukebingham.core.command;

import me.lukebingham.core.util.rank.Role;

/**
 * Created by LukeBingham on 16/03/2017.
 */
public interface StaffCommand {

    /**
     * Only staff with the specified role or higher can use the command.
     *
     * @return Role value
     */
    Role getRequiredRole();
}
