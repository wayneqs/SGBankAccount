package com.sg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

/**
 * This class is responsible for maintaining the list of accounting entries.
 */
public class Account {

    private String id;
    private BigDecimal balance;
    private List<Entry> credits = new ArrayList<>();
    private List<Entry> debits = new ArrayList<>();

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
     * Add a credit entry to the account
     * @param entry the entry to credit to the account
     * @see Entry
     */
    public void credit(Entry entry) {
        this.balance = this.balance.add(entry.getAmount());
        this.credits.add(entry);
    }

    /**
     * Add a debit entry to the account
     * @param entry the entry to debit from the account
     * @see Entry
     */
    public void debit(Entry entry) {
        this.balance = this.balance.subtract(entry.getAmount());
        this.debits.add(entry);
    }

    /**
     * Find all entries for a given counterparty. This could be a long list so is returned in a {@link Stream}.
     * @param counterpartyId
     * @return the result of the search as a{@link Stream}
     * @see Stream the result
     * @see Entry
     */
    public Stream<Entry> findByCounterPartyId(String counterpartyId) {
        return concat(
                findCreditsByCounterPartyId(counterpartyId),
                findDebitsByCounterPartyId(counterpartyId));
    }

    /**
     * Find credit entries for a given counterparty. This could be a long list so is returned in a {@link Stream}.
     * @param counterpartyId
     * @return the result of the search as a{@link Stream}
     * @see Stream the result
     * @see Entry
     */
    public Stream<Entry> findCreditsByCounterPartyId(String counterpartyId) {
        return credits.stream()
                .filter(entry -> entry.getCounterparty().equals(counterpartyId));
    }

    /**
     * Find debit entries for a given counterparty. This could be a long list so is returned in a {@link Stream}.
     * @param counterpartyId
     * @return the result of the search as a{@link Stream}
     * @see Stream the result
     * @see Entry
     */
    public Stream<Entry> findDebitsByCounterPartyId(String counterpartyId) {
        return debits.stream()
                .filter(entry -> entry.getCounterparty().equals(counterpartyId));
    }
}
