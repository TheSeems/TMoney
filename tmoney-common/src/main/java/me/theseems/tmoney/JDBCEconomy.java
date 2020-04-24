package me.theseems.tmoney;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class JDBCEconomy implements Economy {
  private HikariDataSource source;
  private String name;
  private String prefix;

  private static final int SCALE = 30;

  public JDBCEconomy(String name, JDBCConfig jdbcConfig) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcConfig.getUrl());
    config.setUsername(jdbcConfig.getUser());
    config.setPassword(jdbcConfig.getPassword());
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.addDataSourceProperty("allowMultiQueries", "true");
    config.setMinimumIdle(0);
    config.setConnectionTimeout(30000);
    config.setIdleTimeout(35000);
    config.setMaxLifetime(45000);

    this.name = name;
    this.source = new HikariDataSource(config);
    this.prefix = jdbcConfig.getPrefix() + "TMoney_" + name;
    init();
  }

  @Override
  public String getName() {
    return name;
  }

  private void init() {
    try (Connection connection = source.getConnection();
         Statement statement = connection.createStatement()) {

      String createSql =
          "CREATE TABLE IF NOT EXISTS " + prefix + " (Player VARCHAR(100), Money NUMERIC)";
      statement.execute(createSql);
    } catch (SQLException e) {
      System.err.println("ERROR initializing table " + prefix + ": " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void initPlayer(UUID player) {
    if (exists(player)) return;
    try (Connection connection = source.getConnection();
         Statement statement = connection.createStatement()) {

      String insertSql = "INSERT INTO " + prefix + " VALUES ('" + player + "', 0)";
      statement.execute(insertSql);
    } catch (SQLException e) {
      System.err.println(
          "ERROR initializing player '"
              + player
              + "' economy"
              + getName()
              + "': "
              + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void deposit(UUID player, BigDecimal amount) {
    initPlayer(player);
    BigDecimal finalMoney = getBalance(player).add(amount).setScale(SCALE, RoundingMode.HALF_DOWN);
    changeMoney(player, finalMoney);
  }

  @Override
  public void withdraw(UUID player, BigDecimal amount) {
    initPlayer(player);
    BigDecimal finalMoney =
        getBalance(player).subtract(amount).setScale(SCALE, RoundingMode.HALF_DOWN);
    changeMoney(player, finalMoney);
  }

  private void changeMoney(UUID player, BigDecimal finalMoney) {
    try (Connection connection = source.getConnection();
         Statement statement = connection.createStatement()) {

      String updateSql =
          "UPDATE " + prefix + " SET Money=" + finalMoney + " WHERE Player='" + player + "'";
      statement.execute(updateSql);
    } catch (SQLException e) {
      System.err.println(
          "ERROR changing money of player '"
              + player
              + "' amount '"
              + finalMoney
              + "' economy '"
              + getName()
              + "': "
              + e.getMessage());
      e.printStackTrace();
    }
  }

  private boolean exists(UUID player) {
    try (Connection connection = source.getConnection();
         Statement statement = connection.createStatement()) {
      String selectSql = "SELECT Money FROM " + prefix + " WHERE Player='" + player + "'";
      ResultSet resultSet = statement.executeQuery(selectSql);
      return resultSet.next();
    } catch (SQLException e) {
      System.err.println(
          "ERROR checking if player exist '"
              + player
              + "' player '"
              + player
              + "' economy '"
              + getName()
              + "': "
              + e.getMessage());
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public BigDecimal getBalance(UUID player) {
    try (Connection connection = source.getConnection();
         Statement statement = connection.createStatement()) {
      String selectSql = "SELECT Money FROM " + prefix + " WHERE Player='" + player + "'";
      ResultSet resultSet = statement.executeQuery(selectSql);
      if (resultSet.next()) return resultSet.getBigDecimal("Money");
    } catch (SQLException e) {
      System.err.println(
          "ERROR getting balance for player '"
              + "' player '"
              + player
              + "' economy '"
              + getName()
              + "': "
              + e.getMessage());
      e.printStackTrace();
    }
    return BigDecimal.ZERO;
  }
}
