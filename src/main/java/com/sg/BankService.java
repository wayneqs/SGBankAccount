package com.sg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for maintaining the transaction log for account transfers.
 */
public class BankService {

    private List<Account> accountRepository = new ArrayList<>();
    private List<Transaction> transactionLog = new ArrayList<>();

    /**
     * Transfers an amount between two accounts
     * @param amount the amount to transfer
     * @param payeeId the identifier of the payee account
     * @param payerId the identifier of the payer account
     * @return a transaction to record the transfer
     */
    public Transaction transfer(BigDecimal amount, String payeeId, String payerId) {
        Account payee = findAccount(payeeId);
        Account payer = findAccount(payerId);
        if( payee == null && payer == null ) {
            throw new RuntimeException(String.format("Can't find accounts with identifiers=%s,%s", payeeId, payerId));
        }
        if( payee == null ) {
            throw new RuntimeException("Can't find payee account with identifier="+payeeId);
        }
        if( payer == null ) {
            throw new RuntimeException("Can't find payer account with identifier="+payerId);
        }
        Transaction transaction = Transaction.create(amount, payee, payer);
        transactionLog.add(transaction);
        return transaction;
    }

    /**
     * Creates a new account within the bank service
     * @param identifier identifier for the new account (assumed to be unique)
     * @return the created account
     * @see Account
     */
    public Account createAccount(String identifier) {
        Account account = new Account(identifier);
        accountRepository.add(account);
        return account;
    }

    Account findAccount(String identifier) {
        return accountRepository.stream()
                .filter(account -> account.getId().equals(identifier))
                .findFirst().orElse(null);
    }
}
