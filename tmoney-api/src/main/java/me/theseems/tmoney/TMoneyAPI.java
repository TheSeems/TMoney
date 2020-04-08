package me.theseems.tmoney;

import java.util.Collection;
import java.util.Optional;

public class TMoneyAPI {
  private static EconomyManager manager;
  private static Economy defaultEconomy;

  public static EconomyManager getManager() {
    return manager;
  }

  public static void setManager(EconomyManager manager) {
    TMoneyAPI.manager = manager;
  }

  public static Economy getDefault() {
    return defaultEconomy;
  }

  public static Optional<Economy> getEconomy(String name) {
    return manager.getEconomy(name);
  }

  public static Collection<String> getEconomies() {
    return manager.getEconomies();
  }

  public static void setDefaultEconomy(Economy defaultEconomy) {
    TMoneyAPI.defaultEconomy = defaultEconomy;
  }
}
