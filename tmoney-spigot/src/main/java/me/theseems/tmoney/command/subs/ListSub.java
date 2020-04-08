package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.TMoneyAPI;
import me.theseems.tmoney.command.SubCommand;
import org.bukkit.command.CommandSender;

public class ListSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    StringBuilder builder = new StringBuilder();
    for (String economy : TMoneyAPI.getEconomies()) {
      builder.append(economy).append("§7, §2");
    }
    if (builder.length() > 6) {
      builder.delete(builder.length() - 6, builder.length());
    }

    sender.sendMessage(
            "§7Economies (" + TMoneyAPI.getEconomies().size() + ") §2" + builder.toString());
  }

  @Override
  public String getPermission() {
    return "tmoney.list";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney list §8- §2Get list of all economies there are";
  }
}
