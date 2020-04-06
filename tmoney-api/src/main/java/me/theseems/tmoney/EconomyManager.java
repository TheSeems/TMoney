package me.theseems.tmoney;

import java.util.Collection;
import java.util.Optional;

public interface EconomyManager {
  /**
   * Get economy by name
   *
   * @param name of economy
   * @return optional of found economy
   */
  Optional<Economy> getEconomy(String name);

  /**
   * Add economy to manager
   * @param economy to add
   */
  void addEconomy(Economy economy);

  /**
   * Remove economy from manager
   * @param name to remove
   */
  void removeEconomy(String name);

  /**
   * Get all economies there are
   *
   * @return economies
   */
  Collection<String> getEconomies();
}
