package com.sg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        @Test
        @DisplayName("the account should have an identifier")
        void shouldInitialiseWithIdentifier() {
            assertEquals("Harry", account.getId());
        }
    }

    @Nested
    @DisplayName("after adding an entry")
    class AfterAccountEntry {

        Entry entry;
        BigDecimal amount = BigDecimal.valueOf(22.56);

        @BeforeEach
        void addAnEntry() {
            entry = mock(Entry.class);
            when(entry.getAmount()).thenReturn(amount);
            account.addEntry(entry);
        }

        @Test
        @DisplayName("the balance should equal the amount of the entry")
        void shouldEqualTheEntry() {
            assertEquals(amount, account.getBalance());
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
            entry2 = createMockEntry("counterparty2", BigDecimal.valueOf(-2.2));
            entry3 = createMockEntry("counterparty3", BigDecimal.valueOf(3.3));
            entry4 = createMockEntry("counterparty3", BigDecimal.valueOf(-1));
            account.addEntry(entry1);
            account.addEntry(entry2);
            account.addEntry(entry3);
            account.addEntry(entry4);
        }

        @Test
        @DisplayName("the balance should equal the accounting sum of the entries")
        void shouldEqualTheEntry() {
            assertEquals(BigDecimal.valueOf(1.2), account.getBalance());
        }

        @Test
        @DisplayName("search miss for counterparty's entries should return no results")
        void shouldReturnEmptyForEntryMatchMisses() {
            assertTrue(account.findByCounterPartyId("no-entries-for-this-counterparty").count() == 0);
        }

        @Test
        @DisplayName("search miss for counterparty's credit entries should return no results")
        void shouldReturnEmptyForCreditEntryMatchMisses() {
            assertTrue(account.findCreditsByCounterPartyId("no-entries-for-this-counterparty").count() == 0);
        }

        @Test
        @DisplayName("search miss for counterparty's debit entries should return no results")
        void shouldReturnEmptyForDebitEntryMatchMisses() {
            assertTrue(account.findDebitsByCounterPartyId("no-entries-for-this-counterparty").count() == 0);
        }

        @Test
        @DisplayName("should be able to search for combined entries for a counterparty")
        void shouldReturnMatchesForEntryMatchHits() {
            // this will return an unsorted list of debits and credits
            List<Entry> matches = account.findByCounterPartyId(entry3.getCounterparty())
                                         .collect(Collectors.toList());
            assertAll("found credit/debit entries",
                        () -> assertTrue(matches.size() == 2),
                        () -> assertTrue(matches.contains(entry3)),
                        () -> assertTrue(matches.contains(entry4)));
        }

        @Test
        @DisplayName("should be able to search for credit entries by counterparty")
        void shouldReturnMatchesForCreditEntryMatchHits() {
            List<Entry> matches = account.findCreditsByCounterPartyId(entry3.getCounterparty())
                                           .collect(Collectors.toList());
            assertAll("found credit entries",
                    () -> assertTrue(matches.size() == 1),
                    () -> assertTrue(matches.contains(entry3)));
        }

        @Test
        @DisplayName("should be able to search for debit entries by counterparty")
        void shouldReturnMatchesForDebitEntryMatchHits() {
            List<Entry> matches = account.findDebitsByCounterPartyId(entry4.getCounterparty())
                                         .collect(Collectors.toList());
            assertAll("found debit entries",
                        () -> assertTrue(matches.size() == 1),
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
