package me.theseems.tmoney.config;

import com.google.gson.*;
import me.theseems.tmoney.Economy;
import me.theseems.tmoney.JDBCConfig;
import me.theseems.tmoney.TMoneyAPI;

import java.lang.reflect.Type;
import java.util.Optional;

public class EconomyConfigDeserializer implements JsonDeserializer<EconomyConfig> {
  @Override
  public EconomyConfig deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject object = jsonElement.getAsJsonObject();
    String configType = object.get("type").getAsString();

    switch (configType) {
      case "memory":
        return new MemoryEconomyConfig(object.get("name").getAsString());

      case "jdbc":
        JDBCConfig jdbcConfig =
            jsonDeserializationContext.deserialize(object.get("config"), JDBCConfig.class);
        return new JDBCEconomyConfig(jdbcConfig, object.get("name").getAsString());

      default:
        CustomEconomyConfig config =
            jsonDeserializationContext.deserialize(object, CustomEconomyConfig.class);
        if (config == null) {
          System.err.println("Error loading one of the economies! Check the config!");
          return null;
        }

        return new EconomyConfig() {
          @Override
          public Economy getEconomy() {
            Optional<Economy> economy = TMoneyAPI.getConfigManager().bake(config.getType(), config);
            if (!economy.isPresent()) {
              System.out.println(
                  "Cannot find economy provider for type '"
                      + config.getType()
                      + "' (economy '"
                      + config.getName()
                      + "'). Check config or make sure provider for this economy type persist!");
              return null;
            }
            return economy.get();
          }
        };
    }
  }
}
