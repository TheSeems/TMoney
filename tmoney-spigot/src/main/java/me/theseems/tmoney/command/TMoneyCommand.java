package me.theseems.tmoney.command;

import me.theseems.tmoney.TMoneyPlugin;
import me.theseems.tmoney.command.subs.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TMoneyCommand extends SubHost implements CommandExecutor {
  public TMoneyCommand() {
    attach("deposit", new DepositBalanceSub());
    attach("withdraw", new WithdrawBalanceSub());
    attach("balance", new GetBalanceSub());
    attach("reload", new ReloadSub());
    attach("list", new ListSub());
  }

  @Override
  public void onNotFound(CommandSender sender) {
    sender.sendMessage(
        "§2§lTMoney §fby TheSeems<me@theseems.ru> §7v"
            + TMoneyPlugin.getPlugin().getDescription().getVersion());
  }

  @Override
  public void onPermissionLack(CommandSender sender, String node) {
    onNotFound(sender);
  }

  @Override
  public boolean onCommand(
      CommandSender commandSender, Command command, String s, String[] strings) {
    if (strings.length > 0 && strings[0].equals("help")) {
      onNotFound(commandSender);
      subs.forEach(
          (s1, subCommand) -> {
            if (subCommand.getDescription() != null
                && commandSender.hasPermission(subCommand.getPermission()))
              commandSender.sendMessage(subCommand.getDescription());
          });
      return true;
    }

    propagate(commandSender, strings);
    return true;
  }
}
