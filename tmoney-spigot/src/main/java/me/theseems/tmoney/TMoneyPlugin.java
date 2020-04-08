package me.theseems.tmoney;

import me.theseems.tmoney.command.TMoneyCommand;
import me.theseems.tmoney.config.TMoneyConfig;
import me.theseems.tmoney.support.TMoneyPlaceholderExpansion;
import me.theseems.tmoney.support.VaultEconomy;
import me.theseems.tmoney.utils.ClassLoader;
import me.theseems.tmoney.utils.ConfigGenerator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class TMoneyPlugin extends JavaPlugin {
  private static Plugin plugin;
  private static TMoneyConfig moneyConfig;

  public static void loadDrivers() throws ClassNotFoundException {
    File file = new File(plugin.getDataFolder(), "drivers");
    if (file.listFiles() == null) {
      return;
    }

    ClassLoader classLoader = new ClassLoader();
    for (File listFile : Objects.requireNonNull(file.listFiles())) {
      if (!listFile.getName().endsWith(".jar")) continue;
      classLoader.put(listFile);
      getPlugin().getLogger().info("Loading " + listFile + " as a driver");
      Class.forName(listFile.getName().substring(0, listFile.getName().length() - 4));
    }
  }

  public static void loadConfig() {
    File file;
    try {
      file = ConfigGenerator.loadFile("data.json");
    } catch (IOException e) {
      plugin.getLogger().warning("Error loading config file: " + e.getMessage());
      e.printStackTrace();
      return;
    }

    try {
      moneyConfig =
              TMoneyConfig.getBuilder().create().fromJson(new FileReader(file), TMoneyConfig.class);
      if (moneyConfig == null) throw new NullPointerException("Config is empty");
    } catch (Exception e) {
      plugin
              .getLogger()
              .warning(
                      "Cannot load a valid config with economies ("
                              + e.getMessage()
                              + "). Using sample one");
      try {
        moneyConfig =
                ConfigGenerator.createAndWriteSampleConfig(
                        new File(plugin.getDataFolder(), "data.json"));
      } catch (IOException ex) {
        plugin.getLogger().warning("Error writing sample config: " + ex.getMessage());
        plugin.getServer().getPluginManager().disablePlugin(plugin);
        ex.printStackTrace();
      }
    }
  }

  private static void loadEconomies() {
    plugin.getLogger().info("Loading economies...");
    for (Economy formEconomy : moneyConfig.formEconomies()) {
      plugin.getLogger().info("Loaded economy: '" + formEconomy.getName() + "'");
      TMoneyAPI.getManager().addEconomy(formEconomy);
    }

    if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
      plugin.getLogger().info("Hooking into Vault...");
      try {
        VaultEconomy vaultEconomy = new VaultEconomy();
        TMoneyAPI.setDefaultEconomy(vaultEconomy);
        TMoneyAPI.getManager().addEconomy(vaultEconomy);
        plugin.getLogger().info("Setting default economy to a Vault");
      } catch (Exception e) {
        plugin.getLogger().info("Vault will not be supported by TMoney: " + e.getMessage());
      }
    }
  }

  private static void downloadLibs() {
    for (String lib : moneyConfig.getLibs()) {
      File file = new File(plugin.getDataFolder(), "drivers/" + lib + ".jar");
      if (file.exists()) continue;
      else {
        try {
          file.createNewFile();
        } catch (IOException e) {
          plugin.getLogger().warning("Cannot create file for lib " + lib + ": " + e.getMessage());
          e.printStackTrace();
          return;
        }
      }

      URL url;
      URLConnection con;
      DataInputStream dis;
      FileOutputStream fos;
      byte[] fileData;
      try {
        url = new URL(moneyConfig.getLibsUrl() + "/" + lib + ".jar"); // File Location goes here
        con = url.openConnection(); // open the url connection.
        dis = new DataInputStream(con.getInputStream());
        fileData = new byte[con.getContentLength()];
        for (int q = 0; q < fileData.length; q++) {
          fileData[q] = dis.readByte();
        }
        dis.close(); // close the data input stream
        fos = new FileOutputStream(file); // FILE Save Location goes here
        fos.write(fileData); // write out the file we want to save.
        fos.close(); // close the output stream writer

        plugin.getLogger().info("Downloaded lib: " + lib);
      } catch (Exception m) {
        plugin.getLogger().warning("Cannot download lib " + lib + ": " + m.getMessage());
        m.printStackTrace();
      }
    }
  }

  public static void setup() {
    if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
    File file = new File(plugin.getDataFolder(), "drivers");
    if (!file.exists()) {
      file.mkdir();
    }

    loadConfig();
    downloadLibs();

    try {
      loadDrivers();
    } catch (Exception e) {
      plugin.getLogger().warning("Cannot load drivers: " + e.getMessage());
      e.printStackTrace();
    }

    loadEconomies();
  }

  public static Plugin getPlugin() {
    return plugin;
  }

  public static TMoneyConfig getMoneyConfig() {
    return moneyConfig;
  }

  public static void setMoneyConfig(TMoneyConfig moneyConfig) {
    TMoneyPlugin.moneyConfig = moneyConfig;
  }

  @Override
  public void onLoad() {
    plugin = this;
    TMoneyAPI.setManager(new SimpleEconomyManager());
    setup();
  }

  @Override
  public void onEnable() {
    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
      getLogger().info("Hooking into Placeholder API...");
      new TMoneyPlaceholderExpansion().register();
      getLogger().info("Hooked!");
    }
    Objects.requireNonNull(getCommand("tmoney")).setExecutor(new TMoneyCommand());
  }
}
