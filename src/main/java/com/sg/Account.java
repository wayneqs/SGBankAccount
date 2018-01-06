package com.sg;

import java.math.BigDecimal;

/**
 * This class is responsible for maintaining the list of accounting entries.
 */
public class Account {

    private String id;
    private BigDecimal balance;

    /**
     * Class cannot be constructed from the default constructor
     */
    private Account() {
    }

    /**
     * Class constructor specifying metadata for the account.
     * @param id the id of the account
     */
    public Account(String id) {
        this.id = id;
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void credit(Entry entry) {
        this.balance = this.balance.add(entry.getAmount());
    }

    public void debit(Entry entry) {
        this.balance = this.balance.subtract(entry.getAmount());
    }

    public Entry findByCounterPartyId(String counterpartyId) {
        return null;
    }
}
