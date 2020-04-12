package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.Economy;
import me.theseems.tmoney.TMoneyAPI;
import me.theseems.tmoney.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

public class WithdrawBalanceSub extends DepositBalanceSub {

  @Override
  public BigDecimal afterGet(BigDecimal amount, UUID player, Economy economy) {
    economy.withdraw(player, amount);
    return amount.negate();
  }

  @Override
  public String getPermission() {
    return "tmoney.withdraw";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney withdraw [economy] (player) [amount] §8- §2Withdraw money from a player's balance in certain economy";
  }
}
