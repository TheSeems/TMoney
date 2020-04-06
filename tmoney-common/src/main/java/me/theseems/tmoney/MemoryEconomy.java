package me.theseems.tmoney;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class MemoryEconomy implements Economy {
    private Map<UUID, BigDecimal> money;
    private String name;

    public MemoryEconomy(String name) {
        this.name = name;
        this.money = new ConcurrentHashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void deposit(UUID player, BigDecimal amount) {
        money.putIfAbsent(player, BigDecimal.ZERO);
        money.computeIfPresent(player, (uuid, bigDecimal) -> bigDecimal.add(amount));
    }

    @Override
    public void withdraw(UUID player, BigDecimal amount) {
        money.putIfAbsent(player, BigDecimal.ZERO);
        money.computeIfPresent(player, (uuid, bigDecimal) -> bigDecimal.subtract(amount));
    }

    @Override
    public BigDecimal getBalance(UUID player) {
        return money.getOrDefault(player, BigDecimal.ZERO);
    }
}
