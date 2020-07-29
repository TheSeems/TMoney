package me.theseems.tmoney;

import me.theseems.tmoney.config.EconomyConfigManager;

import java.util.Collection;
import java.util.Optional;

public class TMoneyAPI {
  private static EconomyManager manager;
  private static EconomyConfigManager configManager;
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

  public static EconomyConfigManager getConfigManager() {
    return configManager;
  }

  public static void setConfigManager(EconomyConfigManager configManager) {
    TMoneyAPI.configManager = configManager;
  }

  public static Collection<String> getEconomies() {
    return manager.getEconomies();
  }

  public static void setDefaultEconomy(Economy defaultEconomy) {
    TMoneyAPI.defaultEconomy = defaultEconomy;
  }
}
