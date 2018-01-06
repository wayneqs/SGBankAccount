package com.sg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("An Entry")
class EntryTest {

    @Test
    @DisplayName("should have an amount and a counterparty")
    void getAmount() {
        Entry entry = new Entry(BigDecimal.TEN, "counterparty");
        assertAll("entry",
                () -> assertTrue(entry.getAmount().equals(BigDecimal.TEN)),
                () -> assertTrue(entry.getCounterparty().equals("counterparty")));
    }
}