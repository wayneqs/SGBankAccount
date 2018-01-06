package com.sg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTests {

    @Test
    @DisplayName("should initialise with a zero balance")
    void shouldInitialiseWithZeroBalance() {
        assertEquals(0, new Account("Harry").getBalance());
    }
}
