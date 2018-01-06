package com.sg;

import java.math.BigDecimal;

/**
 * This immutable class encapsulates an accounting charge.
 */
public class Entry {
    private final BigDecimal amount;
    private final String counterpartyId;

    public Entry(BigDecimal amount, String counterpartyId) {

        this.amount = amount;
        this.counterpartyId = counterpartyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCounterparty() {
        return counterpartyId;
    }
}
