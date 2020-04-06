package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyAPI;
import me.theseems.tmoney.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class GetBalanceSub implements SubCommand {
    @Override
    public void pass(CommandSender sender, String[] args) {
        String economy = "";
        String playerName;
        if (args.length >= 2) {
            economy = args[0];
            playerName = args[1];
        } else if (args.length == 1) {
            playerName = args[0];
            economy = TMoneyAPI.getDefault().getName();
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§7You, as a console, should specify at least player's name");
                return;
            }
            playerName = sender.getName();
        }

        Optional<Economy> optional = TMoneyAPI.getEconomy(economy);
        if (!optional.isPresent()) {
            sender.sendMessage("§7Economy §6'" + economy + "'§7 is not found");
            return;
        }

        UUID player = null;
        Player actual = Bukkit.getPlayer(playerName);
        if (actual == null) {
            sender.sendMessage("§7Player §6'" + playerName + "'§7 is offline. §oTrying to use argument as UUID");
            try {
                player = UUID.fromString(playerName);
            } catch (Exception e) {
                sender.sendMessage("§7Cannot use §6'" + args[1] + "'§7 as UUID");
            }
        } else {
            player = actual.getUniqueId();
        }

        BigDecimal amount = optional.get().getBalance(player);
        sender.sendMessage("§7Balance of player §6'" + playerName + "'§7 is §6'" + amount + "'");
    }

    @Override
    public String getPermission() {
        return "tmoney.balance";
    }
}
