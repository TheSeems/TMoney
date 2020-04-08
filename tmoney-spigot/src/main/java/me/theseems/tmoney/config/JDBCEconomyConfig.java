package me.theseems.tmoney.config;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.JDBCConfig;
import me.theseems.tmoney.JDBCEconomy;

public class JDBCEconomyConfig extends EconomyConfig {
  private JDBCConfig config;

  public JDBCEconomyConfig(JDBCConfig config, String name) {
    this.config = config;
    setType("jdbc");
    setName(name);
  }

  @Override
  public Economy getEconomy() {
    return new JDBCEconomy(getName(), config);
  }

  public JDBCConfig getConfig() {
    return config;
  }

  public void setConfig(JDBCConfig config) {
    this.config = config;
  }
}
