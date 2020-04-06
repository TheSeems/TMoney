package me.theseems.tmoney.support;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.theseems.tmoney.TMoneyAPI;
import org.bukkit.entity.Player;

public class TMoneyPlaceholderExpansion extends PlaceholderExpansion {
  @Override
  public String getIdentifier() {
    return "tmoney";
  }

  @Override
  public String getAuthor() {
    return "TheSeems<me@theseems.ru>";
  }

  @Override
  public String getVersion() {
    return "0.1D";
  }

  @Override
  public String onPlaceholderRequest(Player p, String params) {
    final String[] balance = {"0"};
    TMoneyAPI.getManager()
            .getEconomy(params)
            .ifPresent(economy -> balance[0] = economy.getBalance(p.getUniqueId()).toString());

    return balance[0];
  }
}
