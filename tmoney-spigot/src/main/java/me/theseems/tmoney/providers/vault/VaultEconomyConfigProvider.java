package me.theseems.tmoney.providers.vault;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.config.CustomEconomyConfig;
import me.theseems.tmoney.config.EconomyConfigProvider;

public class VaultEconomyConfigProvider implements EconomyConfigProvider {
    /**
     * Bake economy out of it's config
     *
     * @param customEconomyConfig to bake from
     * @return economy
     */
    @Override
    public Economy bake(CustomEconomyConfig customEconomyConfig) {
        return new VaultEconomy(customEconomyConfig.getName() == null ? "vault" : customEconomyConfig.getName());
    }

    /**
     * Get economy provider type
     *
     * @return type
     */
    @Override
    public String getType() {
        return "vault";
    }
}
