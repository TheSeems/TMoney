package me.theseems.tmoney;

import com.google.gson.GsonBuilder;
import me.theseems.tmoney.config.EconomyConfig;
import me.theseems.tmoney.config.JDBCEconomyConfig;
import me.theseems.tmoney.config.MemoryEconomyConfig;
import me.theseems.tmoney.config.TMoneyConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {

  @Test
  public void test() {
    List<EconomyConfig> configList = new ArrayList<>();
    configList.add(new MemoryEconomyConfig("simple-first"));
    configList.add(new MemoryEconomyConfig("simple-second"));
    configList.add(new JDBCEconomyConfig(new JDBCConfig("url", "user", "password"), "jdbc-first"));
    configList.add(new JDBCEconomyConfig(new JDBCConfig("url", "user", "password"), "jdbc-second"));
    TMoneyConfig config = new TMoneyConfig(configList);

    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(config));
    String string = new GsonBuilder().create().toJson(config);

    TMoneyConfig tMoneyConfig = TMoneyConfig.getBuilder().create().fromJson(string, TMoneyConfig.class);
    String other = new GsonBuilder().create().toJson(tMoneyConfig);

    Assert.assertEquals(other, string);
  }
}
