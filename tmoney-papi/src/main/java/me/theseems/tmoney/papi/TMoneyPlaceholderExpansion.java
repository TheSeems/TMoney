package me.theseems.tmoney.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
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
    return "0.4D";
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

  private BigDecimal getBalance(Player p, String economy, int digits) {
    Optional<Economy> economyOptional = TMoneyAPI.getManager().getEconomy(economy);
    if (!economyOptional.isPresent()) economyOptional = Optional.ofNullable(TMoneyAPI.getDefault());

    if (economyOptional.isPresent())
      return economyOptional
          .get()
          .getBalance(p.getUniqueId())
          .setScale(digits, RoundingMode.FLOOR)
          .stripTrailingZeros();
    else return BigDecimal.ZERO;
  }

  public static String format(BigDecimal decimal) {
    final List<String> names = Arrays.asList("", "K", "M", "B", "T");
    int digits = decimal.signum() == 0 ? 1 : decimal.precision() - decimal.scale();

    if (digits > names.size() * 3) {
      return ">999" + names.get(names.size() - 1);
    }

    BigDecimal actual =
        decimal.divide(
            new BigDecimal(10).pow(Math.max(0, (digits - 1) / 3 * 3)), 2, RoundingMode.FLOOR);
    actual = actual.stripTrailingZeros();
    return actual.toString() + names.get(Math.max(0, (digits - 1) / 3));
  }

  @Override
  public String onPlaceholderRequest(Player p, String params) {
    String[] arguments = params.split("&&");

    String economy;
    String mode = "default";
    int digits = 2;

    if (arguments.length == 0) return "";

    economy = arguments[0];
    if (arguments.length > 1) {
      try {
        digits = Integer.parseInt(arguments[1]);
      } catch (NumberFormatException ignored) {
        mode = arguments[1];
      }
    }

    BigDecimal balance = getBalance(p, economy, digits);
    if ("formatted".equals(mode)) {
      return format(balance);
    }
    return balance.toPlainString();
  }
}
