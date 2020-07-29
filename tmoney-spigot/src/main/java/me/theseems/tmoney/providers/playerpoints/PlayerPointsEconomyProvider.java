package me.theseems.tmoney.providers.playerpoints;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.JDBCConfig;
import me.theseems.tmoney.config.CustomEconomyConfig;
import me.theseems.tmoney.config.EconomyConfigProvider;

import java.util.Map;

public class PlayerPointsEconomyProvider implements EconomyConfigProvider {
  /**
   * Bake economy out of it's config
   *
   * @param customEconomyConfig to bake from
   * @return economy
   */
  @Override
  public Economy bake(CustomEconomyConfig customEconomyConfig) {
    Map<String, Object> meta = customEconomyConfig.getConfig();
    String url = (String) meta.getOrDefault("url", "jdbc:mysql://localhost:3306");
    String user = (String) meta.getOrDefault("user", "user_does_not_specified");
    String password = (String) meta.getOrDefault("password", "password_does_not_specified");
    String prefix = (String) meta.getOrDefault("prefix", "");

    JDBCConfig config =
        new JDBCConfig(url, user, password, prefix);

    return new PlayerPointsEconomy(customEconomyConfig.getName(), config);
  }

  /**
   * Get economy provider type
   *
   * @return type
   */
  @Override
  public String getType() {
    return "playerpoints";
  }
}
