package me.theseems.tmoney;

import me.theseems.tmoney.utils.ConfigGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class H2EconomyTest {
    private String generateDigitString(int size) {
        StringBuilder builder = new StringBuilder();
        final String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < size; i++) {
            builder.append(digits[new Random().nextInt(digits.length)]);
        }
        return builder.toString();
    }

    @Test
    public void precisionTest() {
        ConfigGenerator.createSampleConfig()
                .formEconomies()
                .forEach(
                        economy -> {
                            UUID player = UUID.randomUUID();
                            BigDecimal bigDecimal = new BigDecimal(generateDigitString(25));
                            economy.deposit(player, bigDecimal);
                            assertTrue(
                                    economy
                                            .getBalance(player)
                                            .subtract(bigDecimal)
                                            .abs()
                                            .compareTo(BigDecimal.valueOf(1e-20))
                                            < 0);
                        });
    }
}
