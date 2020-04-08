package me.theseems.tmoney.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {
  /**
   * Pass the sub command
   *
   * @param sender of sub command
   * @param args   of sub command
   */
  void pass(CommandSender sender, String[] args);

  /**
   * Get permission to use the sub
   *
   * @return permission
   */
  default String getPermission() {
    return "tmoney.use";
  }

  /**
   * Get sub description
   *
   * @return description of sub
   */
  String getDescription();

  /**
   * Do a sub command allows console to use ut
   *
   * @return allow console to use a sub
   */
  default boolean allowConsole() {
    return true;
  }
}
