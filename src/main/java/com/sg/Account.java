package com.sg;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for maintaining the list of accounting entries.
 */
public class Account {

    private String id;
    private BigDecimal balance;
    private List<Entry> entries = new ArrayList<>();

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
        this.entries.add(entry);
    }

    public void debit(Entry entry) {
        this.balance = this.balance.subtract(entry.getAmount());
        this.entries.add(entry);
    }

    public List<Entry> findByCounterPartyId(String counterpartyId) {
        return entries.stream()
                .filter(entry -> entry.getCounterparty().equals(counterpartyId))
                .collect(Collectors.toList());
    }
}
