package com.sg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Entry entry4;

        @BeforeEach
        void addSeveralEntries() {
            entry1 = createMockEntry("counterparty1", BigDecimal.valueOf(1.1));
            entry2 = createMockEntry("counterparty2", BigDecimal.valueOf(2.2));
            entry3 = createMockEntry("counterparty3", BigDecimal.valueOf(3.3));
            entry4 = createMockEntry("counterparty3", BigDecimal.valueOf(1));
            account.credit(entry1);
            account.debit(entry2);
            account.credit(entry3);
            account.credit(entry4);
        }

        @Test
        @DisplayName("the balance should equal the accounting sum of the entries")
        void shouldEqualTheEntry() {
            assertEquals(BigDecimal.valueOf(3.2), account.getBalance());
        }

        @Test
        @DisplayName("the account should return null for counterparty search misses")
        void shouldReturnEmptyForEntryMatchMisses() {
            assertTrue(account.findByCounterPartyId("no-entries-for-this-counterparty").isEmpty());
        }

        @Test
        @DisplayName("the account should return all matches for counterparty search")
        void shouldReturnMatchesForEntryMatchHits() {
            List<Entry> matches = account.findByCounterPartyId(entry3.getCounterparty());
            assertAll("found entries",
                        () -> assertTrue(matches.size() == 2),
                        () -> assertTrue(matches.contains(entry3)),
                        () -> assertTrue(matches.contains(entry4)));
        }
    }

    private Entry createMockEntry(String counterpartyId, BigDecimal amount) {
        Entry entry = mock(Entry.class);
        when(entry.getCounterparty()).thenReturn(counterpartyId);
        when(entry.getAmount()).thenReturn(amount);
        return entry;
    }
}
