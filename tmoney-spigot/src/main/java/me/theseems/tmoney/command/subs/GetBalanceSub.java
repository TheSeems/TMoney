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

public class GetBalanceSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    String economy = "default";
    String playerName;
    if (args.length >= 2) {
      economy = args[0];
      playerName = args[1];
    } else if (args.length == 1) {
      playerName = sender.getName();
      economy = args[0];
    } else {
      if (!(sender instanceof Player)) {
        sender.sendMessage("§cYou, as a console, should specify economy and player's name");
        return;
      }
      playerName = sender.getName();
    }

    Optional<Economy> optional = TMoneyAPI.getEconomy(economy);
    if (!optional.isPresent()) {
      sender.sendMessage("§cEconomy §7'" + economy + "'§c is not found");
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

    BigDecimal amount = optional.get().getBalance(player);
    sender.sendMessage(
        "§7Balance of player §2'"
            + playerName
            + "'§7 in §2'"
            + economy
            + "'§7 is §2'"
            + amount.setScale(3, RoundingMode.HALF_DOWN)
            + "'");
  }

  @Override
  public String getPermission() {
    return "tmoney.balance";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney balance (economy) [player] §8- §2Get balance of player in certain economy";
  }
}
