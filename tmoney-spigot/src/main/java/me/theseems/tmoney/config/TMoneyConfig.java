package me.theseems.tmoney.config;

import com.google.gson.GsonBuilder;
import me.theseems.tmoney.Economy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TMoneyConfig {
  private List<EconomyConfig> economies;
  private String libsUrl;
  private List<String> libs;
  private String defaultEconomy;

  public TMoneyConfig(List<EconomyConfig> economies, String libsUrl, List<String> libs) {
    this.economies = economies;
    this.libsUrl = libsUrl;
    this.libs = libs;
  }

  public String getDefaultEconomy() {
    return defaultEconomy;
  }

  public void setDefaultEconomy(String defaultEconomy) {
    this.defaultEconomy = defaultEconomy;
  }

  public TMoneyConfig(
      List<EconomyConfig> economies, String libsUrl, List<String> libs, String defaultEconomy) {
    this.economies = economies;
    this.libsUrl = libsUrl;
    this.libs = libs;
    this.defaultEconomy = defaultEconomy;
  }

  public List<EconomyConfig> getEconomies() {
    return economies;
  }

  public void setEconomies(List<EconomyConfig> economies) {
    this.economies = economies;
  }

  public Collection<Economy> formEconomies() {
    List<Economy> economies = new ArrayList<>();
    this.economies.forEach(
        economyConfig -> {
          Economy economy = economyConfig.getEconomy();
          if (economy != null) economies.add(economyConfig.getEconomy());
        });
    return economies;
  }

  public static GsonBuilder getBuilder() {
    return new GsonBuilder()
        .registerTypeAdapter(EconomyConfig.class, new EconomyConfigDeserializer());
  }

  public String getLibsUrl() {
    return libsUrl;
  }

  public void setLibsUrl(String libsUrl) {
    this.libsUrl = libsUrl;
  }

  public List<String> getLibs() {
    return libs;
  }

  public void setLibs(List<String> libs) {
    this.libs = libs;
  }
}
