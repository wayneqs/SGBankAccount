package com.sg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class is responsible for maintaining the list of accounting entries.
 */
public class Account {

    private String id;
    private BigDecimal balance;
    private List<Entry> entries = new ArrayList<>();

    /**
     * Class constructor specifying metadata for the account.
     * @param id the id of the account
     */
    public Account(String id) {
        this.id = id;
        this.balance = BigDecimal.ZERO;
    }

    /**
     * The current balance of the account
     * @return the balance
     * @see BigDecimal
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * The account identifier
     * @return the identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Find all entries for a given counterparty. This could be a long list so is returned in a {@link Stream}.
     * @param counterpartyId
     * @return the result of the search as a{@link Stream}
     * @see Stream the result
     * @see Entry
     */
    public Stream<Entry> findByCounterPartyId(String counterpartyId) {
        return entries.stream()
                .filter(entry -> entry.getCounterparty().equals(counterpartyId));
    }

    /**
     * Find credit entries for a given counterparty. This could be a long list so is returned in a {@link Stream}.
     * @param counterpartyId
     * @return the result of the search as a{@link Stream}
     * @see Stream the result
     * @see Entry
     */
    public Stream<Entry> findCreditsByCounterPartyId(String counterpartyId) {
        return findByCounterPartyId(counterpartyId)
                // credits are positive
                .filter(entry -> isPositive(entry.getAmount()));
    }

    /**
     * Find debit entries for a given counterparty. This could be a long list so is returned in a {@link Stream}.
     * @param counterpartyId
     * @return the result of the search as a{@link Stream}
     * @see Stream the result
     * @see Entry
     */
    public Stream<Entry> findDebitsByCounterPartyId(String counterpartyId) {
        return findByCounterPartyId(counterpartyId)
                // debits are negative
                .filter(entry -> isNegative(entry.getAmount()));
    }

    /**
     * Add an accounting entry to the account.
     * @param entry the entry to add
     * @see Entry
     */
    public void addEntry(Entry entry) {
        this.balance = this.balance.add(entry.getAmount());
        this.entries.add(entry);
    }

    /**
     * Checks if debits exceed credits
     * @return
     */
    public boolean hasNegativeBalance() {
        return isNegative(this.getBalance());
    }

    /**
     * Checks if credits exceed debits
     * @return
     */
    public boolean hasPositiveBalance() {
        return isPositive(this.getBalance());
    }

    private boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == -1;
    }

    private boolean isPositive(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 1;
    }
}
