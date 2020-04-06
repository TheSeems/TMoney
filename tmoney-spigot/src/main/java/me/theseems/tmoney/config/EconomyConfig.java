package me.theseems.tmoney.config;

import me.theseems.tmoney.Economy;

public abstract class EconomyConfig {
    private String type;
    private String name;

    public abstract Economy getEconomy();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
