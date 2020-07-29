package me.theseems.tmoney.command.subs;

import me.theseems.tmoney.Economy;

import java.math.BigDecimal;
import java.util.UUID;

public class SetBalanceSub extends DepositBalanceSub {
  @Override
  public BigDecimal afterGet(BigDecimal amount, UUID player, Economy economy) {
    BigDecimal were = economy.getBalance(player);
    economy.withdraw(player, were);
    economy.deposit(player, amount);
    return amount.subtract(were);
  }

  @Override
  public String getPermission() {
    return "tmoney.set";
  }

  @Override
  public String getDescription() {
    return "§7/tmoney set [economy] (player) [amount] §8- §2Set money for a player in certain economy";
  }
}
