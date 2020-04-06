package me.theseems.tmoney;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleEconomyManager implements EconomyManager {
  private Map<String, Economy> economyMap;

  public SimpleEconomyManager() {
    economyMap = new ConcurrentHashMap<>();
  }

  @Override
  public Optional<Economy> getEconomy(String name) {
    return Optional.ofNullable(economyMap.get(name));
  }

  @Override
  public void addEconomy(Economy economy) {
    if (economyMap.containsKey(economy.getName())) {
      throw new IllegalStateException(
          "Attempt to add economy with a name that already exists in manager");
    }
    economyMap.put(economy.getName(), economy);
  }

  @Override
  public void removeEconomy(String name) {
    economyMap.remove(name);
  }

  @Override
  public Collection<String> getEconomies() {
    return economyMap.keySet();
  }
}
