package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyAPI;
import me.theseems.tmoney.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

public class DepositBalanceSub implements SubCommand {

  public BigDecimal afterGet(BigDecimal amount, UUID player, Economy economy) {
    economy.deposit(player, amount);
    return amount;
  }

  @Override
  public void pass(CommandSender sender, String[] args) {
    String economy = "default";
    String playerName;
    String stringAmount;

    if (args.length >= 3) {
      economy = args[0];
      playerName = args[1];
      stringAmount = args[2];
    } else if (args.length == 2) {
      playerName = sender.getName();
      stringAmount = args[1];
      economy = args[0];
    } else if (args.length == 1) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("§cYou, as a console, should specify player's name, economy and amount");
        return;
      }
      playerName = sender.getName();
      stringAmount = args[0];
    } else {
      if (!(sender instanceof Player)) {
        sender.sendMessage("§cYou, as a console, should specify player's name, economy and amount");
      } else sender.sendMessage("§cPlease, specify at least an amount to deposit");
      return;
    }

    Optional<Economy> optional = TMoneyAPI.getEconomy(economy);
    if (!optional.isPresent()) {
      sender.sendMessage("§cEconomy §7'" + economy + "'§c is not found");
      return;
    }

    BigDecimal amount;
    try {
      amount = new BigDecimal(stringAmount);
    } catch (Exception e) {
      sender.sendMessage("§cCannot parse money amount from §7'" + stringAmount + "'");
      return;
    }

    UUID player;
    Player actual = Bukkit.getPlayer(playerName);
    if (actual == null) {
      sender.sendMessage(
          "§7Player §2'" + playerName + "'§7 is offline. §oTrying to use argument as UUID");
      try {
        player = UUID.fromString(playerName);
      } catch (Exception e) {
        sender.sendMessage("§cCannot use §7'" + playerName + "'§c as UUID");
        return;
      }
    } else {
      player = actual.getUniqueId();
    }

    amount = afterGet(amount, player, optional.get());
    sender.sendMessage(
        "§7Balance of player §2'"
            + playerName
            + "'§7 now is §2"
            + optional.get().getBalance(player).setScale(1, RoundingMode.HALF_DOWN)
            + " §7("
            + (amount.signum() >= 0 ? "§a+" : "§c-")
            + amount.abs()
            + "§7)");
  }

  @Override
  public String getPermission() {
    return "tmoney.deposit";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney deposit [economy] (player) [amount] §8- §2Deposit money on a player's balance in certain economy";
  }
}
