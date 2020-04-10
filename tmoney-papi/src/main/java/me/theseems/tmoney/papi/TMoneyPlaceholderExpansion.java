package me.theseems.tmoney.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.RoundingMode;
import java.util.Optional;

public class TMoneyPlaceholderExpansion extends PlaceholderExpansion {
  @Override
  public String getIdentifier() {
    return "TMoney";
  }

  @Override
  public String getAuthor() {
    return "TheSeems<me@theseems.ru>";
  }

  @Override
  public String getVersion() {
    return "0.2D";
  }

  @Override
  public boolean canRegister() {
    return TMoneyAPI.getManager() != null && Bukkit.getPluginManager().getPlugin("TMoney") != null;
  }

  @Override
  public boolean register() {
    if (!canRegister()) return false;
    return super.register();
  }

  @Override
  public String getRequiredPlugin() {
    return "TMoney";
  }

  @Override
  public String onPlaceholderRequest(Player p, String params) {
    final String[] balance = {"0"};
    Optional<Economy> economyOptional = TMoneyAPI.getManager().getEconomy(params);
    if (!economyOptional.isPresent()) economyOptional = Optional.ofNullable(TMoneyAPI.getDefault());

    economyOptional.ifPresent(
        economy ->
            balance[0] =
                economy.getBalance(p.getUniqueId()).setScale(0, RoundingMode.HALF_DOWN).toString());

    return balance[0];
  }
}
