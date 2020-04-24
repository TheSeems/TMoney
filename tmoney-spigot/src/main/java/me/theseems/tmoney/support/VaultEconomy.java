package me.theseems.tmoney.support;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.BigDecimal;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class VaultEconomy implements Economy {
  private net.milkbowl.vault.economy.Economy vault;

  public VaultEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      throw new IllegalStateException("Vault plugin is absent");
    }
    RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp =
        Bukkit.getServer()
            .getServicesManager()
            .getRegistration(net.milkbowl.vault.economy.Economy.class);
    if (rsp == null) {
      throw new IllegalStateException("Vault plugin's provider is absent");
    }
    vault = rsp.getProvider();
  }

  @Override
  public String getName() {
    return "vault";
  }

  @Override
  public void deposit(UUID player, BigDecimal amount) {
    vault.depositPlayer(Bukkit.getOfflinePlayer(player), amount.doubleValue());
  }

  @Override
  public void withdraw(UUID player, BigDecimal amount) {
    vault.withdrawPlayer(Bukkit.getOfflinePlayer(player), amount.doubleValue());
  }

  @Override
  public BigDecimal getBalance(UUID player) {
    return BigDecimal.valueOf(vault.getBalance(Bukkit.getOfflinePlayer(player)));
  }
}
