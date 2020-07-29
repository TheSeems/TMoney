package me.theseems.tmoney.providers.vault;

import me.theseems.tmoney.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.BigDecimal;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class VaultEconomy implements Economy {
  private final net.milkbowl.vault.economy.Economy vaultEconomy;
  private final String name;

  public VaultEconomy(String name) {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      throw new IllegalStateException("Vault plugin is not found");
    }

    RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> provider =
        Bukkit.getServer()
            .getServicesManager()
            .getRegistration(net.milkbowl.vault.economy.Economy.class);

    if (provider == null) {
      throw new IllegalStateException("Vault plugin's provider is not found");
    }

    this.vaultEconomy = provider.getProvider();
    this.name = "vault";
  }

  public VaultEconomy() {
    this("vault");
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void deposit(UUID player, BigDecimal amount) {
    vaultEconomy.depositPlayer(Bukkit.getOfflinePlayer(player), amount.doubleValue());
  }

  @Override
  public void withdraw(UUID player, BigDecimal amount) {
    vaultEconomy.withdrawPlayer(Bukkit.getOfflinePlayer(player), amount.doubleValue());
  }

  @Override
  public BigDecimal getBalance(UUID player) {
    return BigDecimal.valueOf(vaultEconomy.getBalance(Bukkit.getOfflinePlayer(player)));
  }
}
