package me.theseems.tmoney;

import me.theseems.tmoney.papi.TMoneyPlaceholderExpansion;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceholderTest {
    @Test
    public void formatTest() {
        assertEquals(TMoneyPlaceholderExpansion.format(new BigDecimal("10101010")), "10.1M");
        assertEquals(TMoneyPlaceholderExpansion.format(new BigDecimal("123456")), "123.45K");
        assertEquals(TMoneyPlaceholderExpansion.format(new BigDecimal("123466")), "123.46K");
        assertEquals(TMoneyPlaceholderExpansion.format(new BigDecimal("-0.1")), "-0.1");
    }
}
