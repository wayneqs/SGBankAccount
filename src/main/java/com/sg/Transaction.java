package com.sg;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * This class is responsible for explicitly linking a withdrawal from one account with the deposit in another.
 */
public class Transaction {

    private Instant timestamp;
    private final BigDecimal amount;
    private final String payeeId;
    private final String payerId;

    private Transaction(Instant timestamp, BigDecimal amount, String payeeId, String payerId) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.payeeId = payeeId;
        this.payerId = payerId;
    }

    /**
     * Create a new Transaction. The amount will be credited to the payee's account and debited from the payer's account.
     * @param amount the amount to be transferred
     * @param payee  the recipient of the funds
     * @param payer  the provider of the funds
     * @return the created Transaction
     * @see Account
     * @see Entry
     * @see BigDecimal
     */
    public static Transaction create(BigDecimal amount, Account payee, Account payer) {
        Instant timestamp = Instant.now();
        Entry creditEntry = new Entry(amount, payee.getId(), timestamp);
        Entry debitEntry = new Entry(amount.negate(), payer.getId(), timestamp);
        payee.addEntry(creditEntry);
        payer.addEntry(debitEntry);
        return new Transaction(timestamp, amount, payee.getId(), payer.getId());
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * The amount that was transferred
     * @return
     * @see BigDecimal
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * The recipient account's identifier of the transferred funds
     * @return
     */
    public String getPayeeId() {
        return payeeId;
    }

    /**
     * The provider account's identifier of the transferred funds
     * @return
     */
    public String getPayerId() {
        return payerId;
    }
}
