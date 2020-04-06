package me.theseems.tmoney.command;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class SubHost {
  protected Map<String, SubCommand> subs;

  public SubHost() {
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
                "§7Sorry.. we have a problem executing this command: §c" + e.getMessage()));
    sender.spigot().sendMessage(new TextComponent("§7Try again later..."));
    e.printStackTrace();
  }

  public void propagate(CommandSender sender, String[] args) {
    if (args.length == 0 || !subs.containsKey(args[0])) {
      onNotFound(sender);
      return;
    }

    String requiredPermission = subs.get(args[0]).getPermission();
    if (sender instanceof Player && !sender.hasPermission(requiredPermission)) {
      onPermissionLack(sender, requiredPermission);
    }

    String next = args[0];
    args = Arrays.copyOfRange(args, 1, args.length);
    try {
      SubCommand command = subs.get(next);
      if (!command.allowConsole() && !(sender instanceof Player)) {
        sender
            .spigot()
            .sendMessage(
                new TextComponent("§7Sorry.. but§c command is unavailable for you. (Not Player)"));
        return;
      }

      command.pass(sender, args);
    } catch (Exception e) {
      onError(sender, e);
    }
  }
}
