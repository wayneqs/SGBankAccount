package com.sg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A BankService")
class BankServiceTest {

    BankService bank;
    Account payer;
    Account payee;

    @BeforeEach
    void createTheBank() {
        bank = new BankService();
        bank.createAccount("payee");
        bank.createAccount("payer");
        payer = bank.findAccount("payer");
        payee = bank.findAccount("payee");
    }

    @Test
    @DisplayName("should fail to transfer amount when payee account not found")
    void shouldFailTransferWhenPayeeAndPayerAccountNotFound() {
        assertThrows(RuntimeException.class,
                () -> bank.transfer(BigDecimal.ONE, "payee-not-found", "payer-not-found"));
    }

    @Test
    @DisplayName("should fail to transfer amount when payee account not found")
    void shouldFailTransferWhenPayeeAccountNotFound() {
        BigDecimal before = payer.getBalance();
        assertThrows(RuntimeException.class,
                () -> bank.transfer(BigDecimal.ONE, "payee-not-found", "payer"));
        assertEquals(before, payer.getBalance(), "Balance should be same after failure");
    }

    @Test
    @DisplayName("should fail to transfer amount when payer account not found")
    void shouldFailTransferWhenPayerAccountNotFound() {
        BigDecimal before = payee.getBalance();
        assertThrows(RuntimeException.class,
                () -> bank.transfer(BigDecimal.ONE, "payee", "payer-not-found"));
        assertEquals(before, payee.getBalance(), "Balance should be same after failure");
    }

    @Test
    @DisplayName("should record a transaction when a transfer completes")
    void transactionShouldBeRecordedOnTransfer() {
        BigDecimal payeeBefore = payee.getBalance();
        BigDecimal payerBefore = payer.getBalance();

        Transaction record = bank.transfer(BigDecimal.TEN, "payee", "payer");
        assertAll("record",
                () -> assertTrue(record.getAmount().equals(BigDecimal.TEN)),
                () -> assertTrue(record.getPayeeId().equals("payee")),
                () -> assertTrue(record.getPayerId().equals("payer")),
                () -> assertNotNull(record.getTimestamp()));

        assertAll("balances",
                () -> assertNotEquals(payeeBefore, payee.getBalance()),
                () -> assertNotEquals(payerBefore, payer.getBalance()));
    }

    // more tests...
}