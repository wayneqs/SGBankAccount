package com.sg;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * This immutable class encapsulates an accounting charge.
 */
public class Entry {
    private final BigDecimal amount;
    private final String counterpartyId;
    private Instant timestamp;

    public Entry(BigDecimal amount, String counterpartyId, Instant timestamp) {

        this.amount = amount;
        this.counterpartyId = counterpartyId;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCounterparty() {
        return counterpartyId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
