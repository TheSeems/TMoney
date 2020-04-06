package me.theseems.tmoney.config;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.MemoryEconomy;

public class MemoryEconomyConfig extends EconomyConfig {

    public MemoryEconomyConfig(String name) {
        setName(name);
        setType("memory");
    }

    @Override
    public Economy getEconomy() {
        return new MemoryEconomy(getName());
    }
}
