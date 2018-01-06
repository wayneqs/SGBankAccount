package com.sg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("An Account")
class AccountTests {

    private Account account = new Account("Harry");

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @Test
        @DisplayName("the account should have a zero balance")
        void shouldInitialiseWithZeroBalance() {
            assertEquals(BigDecimal.ZERO, account.getBalance());
        }
    }

    @Nested
    @DisplayName("after adding a credit entry")
    class AfterAccountCredit {

        Entry entry;
        BigDecimal amount = BigDecimal.valueOf(22.56);

        @BeforeEach
        void addAnEntry() {
            entry = mock(Entry.class);
            when(entry.getAmount()).thenReturn(amount);
            account.credit(entry);
        }

        @Test
        @DisplayName("the balance should equal the amount of the entry")
        void shouldEqualTheEntry() {
            assertEquals(amount, account.getBalance());
        }
    }

    @Nested
    @DisplayName("after adding a debit entry")
    class AfterAccountDebit {

        Entry entry;
        BigDecimal amount = BigDecimal.valueOf(22.56);

        @BeforeEach
        void addAnEntry() {
            entry = mock(Entry.class);
            when(entry.getAmount()).thenReturn(amount);
            account.debit(entry);
        }

        @Test
        @DisplayName("the balance should equal the negated amount of the entry")
        void shouldEqualTheEntry() {
            assertEquals(amount.negate(), account.getBalance());
        }
    }

    @Nested
    @DisplayName("after adding several entries")
    class AfterSeveralEntries {

        Entry entry1;
        Entry entry2;
        Entry entry3;
        BigDecimal amount1 = BigDecimal.valueOf(1.1);
        BigDecimal amount2 = BigDecimal.valueOf(2.2);
        BigDecimal amount3 = BigDecimal.valueOf(3.3);

        @BeforeEach
        void addSeveralEntries() {
            entry1 = mock(Entry.class);
            entry2 = mock(Entry.class);
            entry3 = mock(Entry.class);
            when(entry1.getAmount()).thenReturn(amount1);
            when(entry2.getAmount()).thenReturn(amount2);
            when(entry3.getAmount()).thenReturn(amount3);
            account.credit(entry1);
            account.debit(entry2);
            account.credit(entry3);
        }

        @Test
        @DisplayName("the balance should equal the amount of the entry")
        void shouldEqualTheEntry() {
            assertEquals(amount1.subtract(amount2).add(amount3), account.getBalance());
        }
    }
}
