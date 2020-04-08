package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyAPI;
import me.theseems.tmoney.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

public class WithdrawBalanceSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    String economy = "default";
    String playerName = "";
    String stringAmount = "0";

    if (args.length >= 3) {
      economy = args[0];
      playerName = args[1];
      stringAmount = args[2];
    } else if (args.length == 2) {
      playerName = args[0];
      stringAmount = args[1];
      economy = TMoneyAPI.getDefault().getName();
    } else if (args.length == 1) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("§7You, as a console, should specify at least player's name");
        return;
      }
      playerName = sender.getName();
      stringAmount = args[0];
    } else {
      sender.sendMessage("§7Please, at least specify an amount to deposit");
    }

    Optional<Economy> optional = TMoneyAPI.getEconomy(economy);
    if (!optional.isPresent()) {
      sender.sendMessage("§7Economy §2'" + economy + "'§7 is not found");
      return;
    }

    BigDecimal amount;
    try {
      amount = new BigDecimal(stringAmount);
    } catch (Exception e) {
      sender.sendMessage("§7Cannot parse money amount from §2'" + stringAmount + "'");
      return;
    }

    UUID player = null;
    Player actual = Bukkit.getPlayer(playerName);
    if (actual == null) {
      sender.sendMessage(
              "§7Player §2'" + playerName + "'§7 is offline. §oTrying to use argument as UUID");
      try {
        player = UUID.fromString(playerName);
      } catch (Exception e) {
        sender.sendMessage("§7Cannot use §2'" + args[1] + "'§7 as UUID");
      }
    } else {
      player = actual.getUniqueId();
    }

    optional.get().withdraw(player, amount);
    sender.sendMessage(
            "§7Balance of player §2'" + playerName + "'§7 now is " + optional.get().getBalance(player).setScale(1, RoundingMode.HALF_DOWN) + " (-" + amount + ")");
  }

  @Override
  public String getPermission() {
    return "tmoney.withdraw";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney withdraw (economy) [player] [amount] §8- §2Withdraw money from a player's balance in certain economy";
  }
}
