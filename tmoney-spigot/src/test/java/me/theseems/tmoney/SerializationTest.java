package me.theseems.tmoney;

import com.google.gson.GsonBuilder;
import me.theseems.tmoney.config.TMoneyConfig;
import me.theseems.tmoney.utils.ConfigGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

  @Test
  public void test() {
      TMoneyConfig config = ConfigGenerator.createSampleConfig();
      String string = new GsonBuilder().create().toJson(config);

      TMoneyConfig tMoneyConfig =
              TMoneyConfig.getBuilder().create().fromJson(string, TMoneyConfig.class);
      String other = new GsonBuilder().create().toJson(tMoneyConfig);

      assertEquals(other, string);
  }
}
