package me.theseems.tmoney.config;

import me.theseems.tmoney.Economy;

import java.util.Collection;
import java.util.Optional;

public interface EconomyConfigManager {
  /**
   * Register provider to a manager
   *
   * @param provider to register
   */
  void register(EconomyConfigProvider provider);

  /**
   * Unregister economy provider from a manager by type
   *
   * @param type of economy prover to unregister
   */
  void unregister(String type);

  /**
   * Bake economy
   *
   * @param type of economy to bake
   * @param config of economy to bake
   * @return economy if provider is found (otherwise Optional.empty())
   */
  Optional<Economy> bake(String type, CustomEconomyConfig config);

  /**
   * Get economy config provider for certain type
   *
   * @param type to get provider for
   * @return provider
   */
  Optional<EconomyConfigProvider> getProvider(String type);

  /**
   * Get types of economies there are in a manager
   *
   * @return types
   */
  Collection<String> getTypes();
}
