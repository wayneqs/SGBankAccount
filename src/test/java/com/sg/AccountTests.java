package com.sg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("An Account")
public class AccountTests {

    @Nested
    @DisplayName("when new")
    class WhenNew {

        Account account;

        @BeforeEach
        void createNewAccount() {
            account = new Account("Harry");
        }

        @Test
        @DisplayName("should initialise with a zero balance")
        void shouldInitialiseWithZeroBalance() {
            assertEquals(0, account.getBalance());
        }
    }
}
