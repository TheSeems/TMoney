package me.theseems.tmoney.command;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class HostCommand {
  protected Map<String, SubCommand> subs;

  public HostCommand() {
    subs = new HashMap<>();
  }

  public void attach(String name, SubCommand subCommand) {
    subs.put(name, subCommand);
  }

  public abstract void onNotFound(CommandSender sender);

  public abstract void onPermissionLack(CommandSender sender, String node);

  public void onError(CommandSender sender, Exception e) {
    sender
        .spigot()
        .sendMessage(
            new TextComponent(
                "§7Sorry.. we have encountered a problem executing this command. §8("
                    + e.getMessage()
                    + ")"));
    sender.spigot().sendMessage(new TextComponent("§7Please, try again later..."));
    e.printStackTrace();
  }

  public void propagate(CommandSender sender, String[] args) {
    if (args.length == 0 || !subs.containsKey(args[0] = args[0].toLowerCase())) {
      onNotFound(sender);
      return;
    }

    String requiredPermission = subs.get(args[0]).getPermission();
    if (!sender.hasPermission(requiredPermission)) {
      onPermissionLack(sender, requiredPermission);
      return;
    }

    String next = args[0];

    // Skip the first argument
    args = Arrays.copyOfRange(args, 1, args.length);

    try {
      SubCommand command = subs.get(next);
      if (!command.allowConsole() && !(sender instanceof Player)) {
        sender.sendMessage("§7Sorry, but command is only available for real players.");
        return;
      }

      command.pass(sender, args);
    } catch (Exception e) {
      onError(sender, e);
    }
  }
}
