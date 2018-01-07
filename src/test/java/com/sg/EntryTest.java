package com.sg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("An Entry")
class EntryTest {

    @Test
    @DisplayName("should have an amount, counterparty and timestamp")
    void getAmount() {
        Instant timestamp = Instant.now();
        Entry entry = new Entry(BigDecimal.TEN, "counterparty", timestamp);
        assertAll("entry",
                () -> assertTrue(entry.getAmount().equals(BigDecimal.TEN)),
                () -> assertTrue(entry.getCounterparty().equals("counterparty")),
                () -> assertTrue(entry.getTimestamp().equals(timestamp)));
    }
}