package me.theseems.tmoney.command;

import me.theseems.tmoney.Main;
import me.theseems.tmoney.command.subs.DepositBalanceSub;
import me.theseems.tmoney.command.subs.GetBalanceSub;
import me.theseems.tmoney.command.subs.WithdrawBalanceSub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TMoneyCommand extends SubHost implements CommandExecutor {
  public TMoneyCommand() {
    attach("deposit", new DepositBalanceSub());
    attach("withdraw", new WithdrawBalanceSub());
    attach("balance", new GetBalanceSub());
  }

  @Override
  public void onNotFound(CommandSender sender) {
    sender.sendMessage(
        "§2§lTMoney §fby TheSeems<me@theseems.ru> §7v"
            + Main.getPlugin().getDescription().getVersion());
  }

  @Override
  public void onPermissionLack(CommandSender sender, String node) {
    onNotFound(sender);
  }

  @Override
  public boolean onCommand(
      CommandSender commandSender, Command command, String s, String[] strings) {
    propagate(commandSender, strings);
    return true;
  }
}
