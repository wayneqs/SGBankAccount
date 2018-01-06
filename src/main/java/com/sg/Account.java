package com.sg;

/**
 * This class is responsible for maintaining the list of accounting entries.
 */
public class Account {

    private String id;
    private double balance;

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
    }

    public double getBalance() {
        return balance;
    }
}
