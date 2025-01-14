/**
 * This is the test suite used to evaluate your solution
 * to this problem when you hit "Mark"!
 * Consider viewing this code to find out how the tests work,
 * or even editing it (the "Mark" button will use the original
 * tests, but "Check" should update to reflect any changes you make.)
 */
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class LocationTest {
    /**
     * TA Note - oftentimes when you have tests that run more than once,
     *           it can be useful to create "helper methods" in your test
     *           file, rather than to copy-paste code in every testing method.
     *           Of course, you should be careful when doing this - if there's a bug
     *           in your regular program, your tests should catch it. If there's a bug
     *           in your tests, on the other hand, those can be very difficult to find
     *           (because the first thought is usually never "there's a problem in
    *            my tests...")!
     */
    // A helper method which helps run a test of Location's
    // compareTo.
    //
    // Implementer's note:
    // Note that this method just looks at the sign of expected - e.g.,
    // if loc1.compareTo(loc2) should return -2, this method will pass if compareTo returns -1.
    // (This is because compareTo returns an int where the SIGN is what matters; -1 and -2 are essentially equivalent)
    //
    // Location loc1 - the first Location object
    // Location loc2 - the second Location object
    // Location expected - the expected value of loc1.compareTo(loc2)
    private void helper(Location loc1, Location loc2, int expected) {
        int actual = loc1.compareTo(loc2);
        if (expected < 0) {
            assertTrue(actual < 0, "expected: < 0, actual: " + actual);
        } else if (expected == 0) {
            assertEquals(expected, actual);
        } else { // expected > 0
            assertTrue(actual > 0, "expected: > 0, actual: " + actual);
        }
    }

    @Test
    @DisplayName("Seattle 47.65N 122.3W vs. Sydney 34.1S 151.1E - Sydney should come first")
    public void test0() {
        Location l1 = new Location("Seattle", 47.65, 122.3);
        Location l2 = new Location("Sydney", 32.1, 151.1);
        helper(l1, l2, 1);
    }

    @Test
    @DisplayName("Yakima 42.65N 115.3W vs. Tacoma 42.65N 122.3W - Yakima should come first")
    public void test1() {
        Location l1 = new Location("Yakima", 42.65, 115.3);
        Location l2 = new Location("Tacoma", 42.65, 122.3);
        helper(l1, l2, -1);
    }

    @Test
    @DisplayName("Tacoma 42.65N 122.3W vs. Hickville 42.65N 122.3W - They should be equal")
    public void test2() {
        Location l1 = new Location("Tacoma", 42.65, 122.3);
        Location l2 = new Location("Hickville", 42.65, 122.3);
        helper(l1, l2, 0);
    }
}
