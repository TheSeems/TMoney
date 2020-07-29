package me.theseems.tmoney.config;

import me.theseems.tmoney.Economy;

public interface EconomyConfigProvider {
  /**
   * Bake economy out of it's config
   *
   * @param customEconomyConfig to bake from
   * @return economy
   */
  Economy bake(CustomEconomyConfig customEconomyConfig);

  /**
   * Get economy provider type
   *
   * @return type
   */
  String getType();
}
