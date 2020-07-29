package me.theseems.tmoney.config;

import java.util.Map;

public class CustomEconomyConfig {
  // Type of economy
  private final String type;

  // Metadata. For example: jdbc url.
  private final Map<String, Object> config;

  // Name of economy
  private final String name;

  public CustomEconomyConfig(String type, Map<String, Object> config, String name) {
    this.type = type;
    this.config = config;
    this.name = name;
  }

  public Map<String, Object> getConfig() {
    return config;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
