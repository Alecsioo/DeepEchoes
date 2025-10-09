package com.github.alecsio.deepechoes.capabilities;

import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EchoHandlerTests {

    private static final int MAX_ECHO = EchoHandler.MAX_ECHO;
    private EchoHandler handler;

    @BeforeEach
    void setUp() {
        handler = new EchoHandler();
    }

    // ---------- Helpers ----------
    private void fillExactly(int amount) {
        int remainder = handler.insertEcho(amount, false);
        assertEquals(0, remainder, "Should insert exactly with no remainder");
    }

    private void fillToCapacity() {
        fillExactly(MAX_ECHO);
        assertEquals(MAX_ECHO, handler.getStoredEcho(), "Handler should be full");
    }

    // ============================================================
    @Nested
    class InsertionTests {

        @Test
        void insertIntoEmpty_fitsCompletely_returnsZeroRemainder() {
            int toInsert = MAX_ECHO / 2;
            int remainder = handler.insertEcho(toInsert, false);
            assertEquals(0, remainder);
            assertEquals(toInsert, handler.getStoredEcho());
        }

        @Test
        void insertExactlyCapacity_fromEmpty_fillsAndReturnsZero() {
            int remainder = handler.insertEcho(MAX_ECHO, false);
            assertEquals(0, remainder);
            assertEquals(MAX_ECHO, handler.getStoredEcho());
        }

        @Test
        void insertWhenFull_returnsAllAsRemainder_andDoesNotChangeStored() {
            fillToCapacity();
            int remainder = handler.insertEcho(1234, false);
            assertEquals(1234, remainder);
            assertEquals(MAX_ECHO, handler.getStoredEcho());
        }

        @Test
        void overfill_returnsOnlyTheExcess_asRemainder_andCapsStorage() {
            int first = MAX_ECHO - 10; // leave a small headroom
            fillExactly(first);
            int remainder = handler.insertEcho(25, false); // can take 10; 15 remainder
            assertEquals(15, remainder);
            assertEquals(MAX_ECHO, handler.getStoredEcho());
        }

        @Test
        void simulateInsertion_doesNotMutate_andReportsRemainderCorrectly() {
            int remainder = handler.insertEcho(MAX_ECHO, true); // simulate inserting full cap into empty
            assertEquals(0, remainder, "Simulation should compute remainder as if inserting");
            assertEquals(0, handler.getStoredEcho(), "But simulation must not change stored amount");

            // After real insert, state should change
            int realRemainder = handler.insertEcho(MAX_ECHO, false);
            assertEquals(0, realRemainder);
            assertEquals(MAX_ECHO, handler.getStoredEcho());
        }

        @Test
        void zeroOrNegativeInsertion_returnsZero_andNoChange() {
            assertEquals(0, handler.insertEcho(0, false));
            assertEquals(0, handler.getStoredEcho());

            assertEquals(0, handler.insertEcho(-100, false));
            assertEquals(0, handler.getStoredEcho());
        }

        @Test
        void multipleInsertions_accumulateUntilCapacity_thenRemainderAfter() {
            int a = MAX_ECHO / 3;
            int b = MAX_ECHO / 3;
            int c = MAX_ECHO; // will overflow on third insert

            assertEquals(0, handler.insertEcho(a, false));
            assertEquals(a, handler.getStoredEcho());

            assertEquals(0, handler.insertEcho(b, false));
            assertEquals(a + b, handler.getStoredEcho());

            int remainder = handler.insertEcho(c, false);
            assertEquals((a + b + c) - MAX_ECHO, remainder);
            assertEquals(MAX_ECHO, handler.getStoredEcho());
        }
    }

    // ============================================================
    @Nested
    class ExtractionTests {

        @Test
        void extractFromEmpty_returnsZero_andDoesNotGoNegative() {
            assertEquals(0, handler.getStoredEcho());
            int extracted = handler.extractEcho(1234, false);
            assertEquals(0, extracted);
            assertEquals(0, handler.getStoredEcho());
        }

        @Test
        void extractLessThanStored_reducesStored_andReturnsRequested() {
            fillExactly(5000);
            int extracted = handler.extractEcho(1234, false);
            assertEquals(1234, extracted);
            assertEquals(5000 - 1234, handler.getStoredEcho());
        }

        @Test
        void extractExactlyAll_setsToZero_andReturnsAll() {
            fillExactly(7777);
            int extracted = handler.extractEcho(7777, false);
            assertEquals(7777, extracted);
            assertEquals(0, handler.getStoredEcho());
        }

        @Test
        void overExtract_returnsOnlyAvailable_andDrainsToZero() {
            fillExactly(2000);
            int extracted = handler.extractEcho(5000, false);
            assertEquals(2000, extracted);
            assertEquals(0, handler.getStoredEcho());
        }

        @Test
        void simulateExtraction_doesNotMutate_andReportsWhatWouldBeExtracted() {
            fillExactly(3000);

            int wouldExtract = handler.extractEcho(2500, true);
            assertEquals(2500, wouldExtract, "Simulation should report possible extraction");
            assertEquals(3000, handler.getStoredEcho(), "No mutation on simulate");

            int wouldExtractTooMuch = handler.extractEcho(9999, true);
            assertEquals(3000, wouldExtractTooMuch, "Simulation caps at available amount");
            assertEquals(3000, handler.getStoredEcho(), "Still no mutation");
        }

        @Test
        void zeroOrNegativeExtraction_returnsZero_andNoChange() {
            fillExactly(1000);
            assertEquals(0, handler.extractEcho(0, false));
            assertEquals(1000, handler.getStoredEcho());

            assertEquals(0, handler.extractEcho(-42, false));
            assertEquals(1000, handler.getStoredEcho());
        }

        @Test
        void extractAfterFillToCapacity_behavesCorrectly() {
            fillToCapacity();

            int first = handler.extractEcho(MAX_ECHO / 2, false);
            assertEquals(MAX_ECHO / 2, first);
            assertEquals(MAX_ECHO - (MAX_ECHO / 2), handler.getStoredEcho());

            int second = handler.extractEcho(MAX_ECHO, false); // over-extract the rest
            assertEquals(MAX_ECHO - (MAX_ECHO / 2), second);
            assertEquals(0, handler.getStoredEcho());
        }
    }
}

