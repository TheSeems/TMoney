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

  public TMoneyConfig(List<EconomyConfig> economies, String libsUrl, List<String> libs) {
    this.economies = economies;
    this.libsUrl = libsUrl;
    this.libs = libs;
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
