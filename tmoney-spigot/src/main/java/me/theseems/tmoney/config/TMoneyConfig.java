package me.theseems.tmoney.config;

import com.google.gson.GsonBuilder;
import me.theseems.tmoney.Economy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TMoneyConfig {
  private List<EconomyConfig> economies;

  public TMoneyConfig(List<EconomyConfig> economies) {
    this.economies = economies;
  }

  public List<EconomyConfig> getEconomies() {
    return economies;
  }

  public void setEconomies(List<EconomyConfig> economies) {
    this.economies = economies;
  }

  public Collection<Economy> formEconomies() {
    List<Economy> economies = new ArrayList<>();
    this.economies.forEach(economyConfig -> economies.add(economyConfig.getEconomy()));
    return economies;
  }

  public static GsonBuilder getBuilder() {
    return new GsonBuilder().registerTypeAdapter(EconomyConfig.class, new EconomyConfigDeserializer());
  }
}
