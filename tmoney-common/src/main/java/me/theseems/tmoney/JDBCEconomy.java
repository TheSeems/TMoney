package me.theseems.tmoney;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class JDBCEconomy implements Economy {
  private HikariPool pool;
  private String name;
  private String prefix;

  public JDBCEconomy(String name, JDBCConfig jdbcConfig) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcConfig.getUrl());
    config.setUsername(jdbcConfig.getUser());
    config.setPassword(jdbcConfig.getPassword());
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.addDataSourceProperty("allowMultiQueries", "true");
    config.setMaximumPoolSize(200);
    this.name = name;
    this.pool = new HikariPool(config);
    this.prefix = "__ECO" + name + "_TMONEY";
    init();
  }

  private Connection getConnection() throws SQLException {
    return pool.getConnection();
  }

  @Override
  public String getName() {
    return name;
  }

  private void init() {
    try (Connection connection = getConnection()) {
      Statement statement = connection.createStatement();
      statement.execute(
          "CREATE TABLE IF NOT EXISTS " + prefix + " (Player VARCHAR(100), Money NUMERIC)");
    } catch (SQLException e) {
      System.err.println("ERROR initializing table " + prefix);
      e.printStackTrace();
    }
  }

  private void initPlayer(UUID player) {
    if (exists(player)) return;
    try (Connection connection = getConnection()) {
      Statement statement = connection.createStatement();
      statement.execute("INSERT INTO " + prefix + " VALUES ('" + player + "', 0)");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deposit(UUID player, BigDecimal amount) {
    try (Connection connection = getConnection()) {
      initPlayer(player);
      Statement statement = connection.createStatement();
      statement.execute(
          "UPDATE "
              + prefix
              + " SET Money="
              + getBalance(player).add(amount).toString()
              + " WHERE Player='"
              + player
              + "'");
    } catch (SQLException e) {
      System.err.println(
          "["
              + getName()
              + "] ERROR depositing to '"
              + player
              + "' amount '"
              + amount
              + "': "
              + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void withdraw(UUID player, BigDecimal amount) {
    try (Connection connection = getConnection()) {
      initPlayer(player);
      Statement statement = connection.createStatement();
      statement.execute(
          "UPDATE "
              + prefix
              + " SET Money="
              + getBalance(player).subtract(amount).toString()
              + " WHERE Player='"
              + player
              + "'");
    } catch (SQLException e) {
      System.err.println(
          "["
              + getName()
              + "] ERROR depositing to '"
              + player
              + "' amount '"
              + amount
              + "': "
              + e.getMessage());
      e.printStackTrace();
    }
  }

  private boolean exists(UUID player) {
    try (Connection connection = getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet resultSet =
              statement.executeQuery("SELECT Money FROM " + prefix + " WHERE Player='" + player + "'");
      return resultSet.next();
    } catch (SQLException e) {
      System.err.println("[" + getName() + "] ERROR getting balance for player '" + player + "'");
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public BigDecimal getBalance(UUID player) {
    try (Connection connection = getConnection()) {
      Statement statement = connection.createStatement();
      ResultSet resultSet =
          statement.executeQuery("SELECT Money FROM " + prefix + " WHERE Player='" + player + "'");
      if (!resultSet.next()) {
        return BigDecimal.ZERO;
      } else {
        return resultSet.getBigDecimal("Money");
      }
    } catch (SQLException e) {
      System.err.println("[" + getName() + "] ERROR getting balance for player '" + player + "'");
      e.printStackTrace();
    }
    return BigDecimal.ZERO;
  }
}
