package me.theseems.tmoney.config;

import com.google.gson.*;
import me.theseems.tmoney.JDBCConfig;

import java.lang.reflect.Type;

public class EconomyConfigDeserializer implements JsonDeserializer<EconomyConfig> {
    @Override
    public EconomyConfig deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        switch (object.get("type").getAsString()) {
            case "memory":
                return new MemoryEconomyConfig(object.get("name").getAsString());
            case "jdbc":
                JDBCConfig jdbcConfig = jsonDeserializationContext.deserialize(object.get("config"), JDBCConfig.class);
                return new JDBCEconomyConfig(jdbcConfig, object.get("name").getAsString());
        }
        return null;
    }
}
