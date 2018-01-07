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

    private Account account = new Account("accountId");

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @Test
        @DisplayName("should have a zero balance")
        void shouldInitialiseWithZeroBalance() {
            assertEquals(BigDecimal.ZERO, account.getBalance());
        }

        @Test
        @DisplayName("should have an identifier")
        void shouldInitialiseWithIdentifier() {
            assertEquals("accountId", account.getId());
        }
    }

    @Nested
    @DisplayName("after adding a positive entry")
    class AfterPositiveAccountEntry {

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
            assertAll("account",
                    () -> assertEquals(amount, account.getBalance()),
                    () -> assertTrue(account.hasPositiveBalance()),
                    () -> assertFalse(account.hasNegativeBalance()));
        }
    }

    @Nested
    @DisplayName("after adding a negative entry")
    class AfterNegativeAccountEntry {

        Entry entry;
        BigDecimal amount = BigDecimal.valueOf(-22.56);

        @BeforeEach
        void addAnEntry() {
            entry = mock(Entry.class);
            when(entry.getAmount()).thenReturn(amount);
            account.addEntry(entry);
        }

        @Test
        @DisplayName("the balance should equal the amount of the entry")
        void shouldEqualTheEntry() {
            assertAll("account",
                    () -> assertEquals(amount, account.getBalance()),
                    () -> assertFalse(account.hasPositiveBalance()),
                    () -> assertTrue(account.hasNegativeBalance()));
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
        @DisplayName("will report a balance equal to the sum of its entries")
        void shouldEqualTheEntry() {
            assertEquals(BigDecimal.valueOf(1.2), account.getBalance());
        }

        @Test
        @DisplayName("should not find credit/debit entries for unknown counterparty")
        void shouldReturnEmptyForEntryMatchMisses() {
            assertTrue(account.findByCounterPartyId("no-entries-for-this-counterparty").count() == 0);
        }

        @Test
        @DisplayName("should not find credit entries for unknown counterparty")
        void shouldReturnEmptyForCreditEntryMatchMisses() {
            assertTrue(account.findCreditsByCounterPartyId("no-entries-for-this-counterparty").count() == 0);
        }

        @Test
        @DisplayName("should not find debit entries for unknown counterparty")
        void shouldReturnEmptyForDebitEntryMatchMisses() {
            assertTrue(account.findDebitsByCounterPartyId("no-entries-for-this-counterparty").count() == 0);
        }

        @Test
        @DisplayName("should find credit/debit entries by counterparty")
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
        @DisplayName("should find credit entries by counterparty")
        void shouldReturnMatchesForCreditEntryMatchHits() {
            List<Entry> matches = account.findCreditsByCounterPartyId(entry3.getCounterparty())
                                           .collect(Collectors.toList());
            assertAll("found credit entries",
                    () -> assertTrue(matches.size() == 1),
                    () -> assertTrue(matches.contains(entry3)));
        }

        @Test
        @DisplayName("should find debit entries by counterparty")
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
