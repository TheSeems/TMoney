package me.theseems.tmoney;

import me.theseems.tmoney.config.CustomEconomyConfig;
import me.theseems.tmoney.config.EconomyConfigManager;
import me.theseems.tmoney.config.EconomyConfigProvider;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleEconomyConfigManager implements EconomyConfigManager {
  private Map<String, EconomyConfigProvider> providerMap;

  public SimpleEconomyConfigManager() {
    providerMap = new ConcurrentHashMap<>();
  }

  /**
   * Register provider to a manager
   *
   * @param provider to register
   */
  @Override
  public void register(EconomyConfigProvider provider) {
    providerMap.put(provider.getType(), provider);
  }

  /**
   * Unregister economy provider from a manager by type
   *
   * @param type of economy prover to unregister
   */
  @Override
  public void unregister(String type) {
    providerMap.remove(type);
  }

  /**
   * Bake economy
   *
   * @param type of economy to bake
   * @param config of economy to bake
   * @return economy if provider is found (otherwise Optional.empty())
   */
  @Override
  public Optional<Economy> bake(String type, CustomEconomyConfig config) {
    return getProvider(type).flatMap(provider -> Optional.ofNullable(provider.bake(config)));
  }

  /**
   * Get economy config provider for certain type
   *
   * @param type to get provider for
   * @return provider
   */
  @Override
  public Optional<EconomyConfigProvider> getProvider(String type) {
    return Optional.ofNullable(providerMap.get(type));
  }

  /**
   * Get types of economies there are in a manager
   *
   * @return types
   */
  @Override
  public Collection<String> getTypes() {
    return providerMap.keySet();
  }
}
