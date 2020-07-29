package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.TMoneyAPI;
import me.theseems.tmoney.TMoneyPlugin;
import me.theseems.tmoney.command.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    sender.sendMessage("§7Unloading existing economies...");
    for (String economy : TMoneyAPI.getManager().getEconomies()) {
      TMoneyAPI.getManager().removeEconomy(economy);
    }

    try {
      TMoneyPlugin.setup();
      TMoneyPlugin.loadVault();
      sender.sendMessage(
          "§aPlugin reloaded with "
              + TMoneyAPI.getManager().getEconomies().size()
              + " econom" + (TMoneyAPI.getManager().getEconomies().size() == 1 ? "y" : "ies"));
    } catch (Exception e) {
      sender.sendMessage("§cError reloading config: " + e.getMessage());
    }
  }

  @Override
  public String getPermission() {
    return "tmoney.reload";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney reload §8- §2Reload config";
  }
}
