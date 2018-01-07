package com.sg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Transaction")
class TransactionTest {

    Transaction transaction;
    Account payee;
    Account payer;

    @BeforeEach
    void createTheTransaction() {
        payee = new Account("payee");
        payer = new Account("payer");
        assertAll("accounts have zero balance",
                () -> assertTrue(payee.getBalance().equals(BigDecimal.ZERO)),
                () -> assertTrue(payer.getBalance().equals(BigDecimal.ZERO)));
        transaction = Transaction.create(BigDecimal.TEN, payee, payer);
    }

    @Test
    @DisplayName("will debit the payer's account with the amount")
    void shouldDebitThePayer() {
        assertTrue(payer.hasNegativeBalance());
    }

    @Test
    @DisplayName("will credit the payee's account with the amount")
    void shouldCreditThePayee() {
        assertFalse(payer.hasPositiveBalance());
    }

    @Test
    @DisplayName("will create an immutable record of the transfer")
    void createsATransaction() {
        assertAll("transaction",
                () -> assertTrue(transaction.getAmount().equals(BigDecimal.TEN)),
                () -> assertTrue(transaction.getPayeeId().equals(payee.getId())),
                () -> assertTrue(transaction.getPayerId().equals(payer.getId())),
                () -> assertNotNull(transaction.getTimestamp()));
    }
}