package me.theseems.tmoney;

import java.math.BigDecimal;
import java.util.UUID;

public interface Economy {
  /**
   * Get name of economy: like currency name or something else
   *
   * @return name
   */
  String getName();

  /**
   * Deposit money to a player
   *
   * @param player to deposit for
   * @param amount to deposit
   */
  void deposit(UUID player, BigDecimal amount);

  /**
   * Withdraw money
   *
   * @param player to withdraw from
   * @param amount to withdraw
   */
  void withdraw(UUID player, BigDecimal amount);

  /**
   * Get balance of player
   *
   * @param player to get balance of
   * @return balance in the economy
   */
  BigDecimal getBalance(UUID player);
}
