package me.theseems.tmoney;

import me.theseems.tmoney.command.TMoneyCommand;
import me.theseems.tmoney.config.TMoneyConfig;
import me.theseems.tmoney.support.TMoneyPlaceholderExpansion;
import me.theseems.tmoney.support.VaultEconomy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main extends JavaPlugin {
  private static Plugin plugin;
  private static TMoneyConfig moneyConfig;

  public static File loadFile(String name) throws IOException {
    if (!plugin.getDataFolder().exists()) {
      plugin.getDataFolder().mkdir();
    }
    File file = new File(plugin.getDataFolder(), name);
    if (!file.exists()) {
      file.createNewFile();
    }
    return file;
  }

  private void loadConfig() throws IOException {
    File file = loadFile("data.json");
    moneyConfig =
        TMoneyConfig.getBuilder().create().fromJson(new FileReader(file), TMoneyConfig.class);
  }

  @Override
  public void onEnable() {
    plugin = this;

    getLogger().info("Loading api...");
    TMoneyAPI.setManager(new SimpleEconomyManager());

    if (getServer().getPluginManager().getPlugin("Vault") != null) {
      getLogger().info("Trying to hook into Vault");
      try {
        VaultEconomy vaultEconomy = new VaultEconomy();
        TMoneyAPI.setDefaultEconomy(vaultEconomy);
        TMoneyAPI.getManager().addEconomy(vaultEconomy);
        getLogger().info("Setting default economy to a Vault");
      } catch (Exception e) {
        getLogger().info("Vault will not be supported by TMoney: " + e.getMessage());
      }
    }

    getLogger().info("Loading config..");
    try {
      loadConfig();
      for (Economy formEconomy : moneyConfig.formEconomies()) {
        getLogger().info("Loaded economy: '" + formEconomy.getName() + "'");
        TMoneyAPI.getManager().addEconomy(formEconomy);
      }
    } catch (IOException e) {
      getLogger().severe("Cannot load config with economies");
      e.printStackTrace();
    }

    Objects.requireNonNull(getCommand("tmoney")).setExecutor(new TMoneyCommand());

    if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
      getLogger().info("Hooking into Placeholder API...");
      new TMoneyPlaceholderExpansion().register();
      getLogger().info("Hooked!");
    }
  }

  public static Plugin getPlugin() {
    return plugin;
  }

  public static TMoneyConfig getMoneyConfig() {
    return moneyConfig;
  }
}
