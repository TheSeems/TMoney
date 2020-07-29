package me.theseems.tmoney.utils;

import com.google.gson.GsonBuilder;
import me.theseems.tmoney.JDBCConfig;
import me.theseems.tmoney.TMoneyPlugin;
import me.theseems.tmoney.config.JDBCEconomyConfig;
import me.theseems.tmoney.config.TMoneyConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

public class ConfigGenerator {
  public static TMoneyConfig createSampleConfig() {
    String path = "<path-to-db>";
    try {
      File file =
          new File(
              new File(
                      TMoneyPlugin.class
                          .getProtectionDomain()
                          .getCodeSource()
                          .getLocation()
                          .toURI())
                  .getParentFile(),
              "/TMoney/Economy.db");

      if (!file.exists()) {
        file.createNewFile();
      }
      path = file.toPath().toString();
    } catch (URISyntaxException | IOException e) {
      try {
        path = File.createTempFile("tmoney-temp", "h2.db").getPath();
        System.err.println("Using the temp file: " + path);
        System.err.println("Please, consider specifying non-temp file (DB) path in config!");
      } catch (Exception ex) {
        System.err.println("Cannot even use temp file for config");
        System.err.println("Please, fill in file (DB) path in config!");
      }
    }

    JDBCConfig sample = new JDBCConfig("jdbc:h2:file:" + path, "minecraft", "password", "mc");
    JDBCEconomyConfig economyConfig = new JDBCEconomyConfig(sample, "donate");
    return new TMoneyConfig(
        Collections.singletonList(economyConfig),
        "https://theseems.ru/tmoney/libs",
        Arrays.asList("org.h2.Driver", "org.postgresql.Driver"));
  }

  public static void writeConfig(TMoneyConfig config, File file) throws IOException {
    FileWriter writer = new FileWriter(file);
    new GsonBuilder().setPrettyPrinting().create().toJson(config, writer);
    writer.flush();
  }

  public static TMoneyConfig createAndWriteSampleConfig(File file) throws IOException {
    TMoneyConfig config = createSampleConfig();
    writeConfig(config, file);
    return config;
  }

  public static File loadFile(String name) throws IOException {
    File dataFolder = TMoneyPlugin.getPlugin().getDataFolder();
    if (!dataFolder.exists()) {
      dataFolder.mkdir();
    }

    File file = new File(dataFolder, name);
    if (!file.exists()) {
      file.createNewFile();
    }

    return file;
  }
}
