// P0: Ciphers
// Name: Joe Lin
// Date: Jan 22, 2025
// CSE 123 BK
// TA: Benoit Le

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.util.*;

/**
 * This class is to test the CaesarKey, CaesarShift, MultiCipher,
 * and the Substitution class using JUnit
 */
public class Testing {

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'G' Spec Example")
    public void subAGTest() {
        // Remember that you can change MIN_CHAR AND MAX_CHAR 
        // in Cipher.java to make testing easier! For this 
        // example test, we are using MIN_CHAR = A and MAX_CHAR = G

        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('G'));

        Cipher testSubstitution = new Substitution("GCBEAFD");
        assertEquals("FGE", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("CGE"));

        // Per the spec, we should throw an IllegalArgumentException if 
        // the length of the encoder doesn't match the number of characters
        // within our Cipher's encodable range
        assertThrows(IllegalArgumentException.class, () -> {
            new Substitution("GCB");
        });
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'Z' encoder")
    public void subAZTest() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        // Reverse alphabetic
        Cipher testSubstitution = new Substitution(
                "ZYXWVUTSRQPONMLKJIHGFEDCBA"
        );
        assertEquals("UZW", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("YZW"));
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - ' '-'}' encoder")
    public void subComplexTest() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)(' ') && Cipher.MAX_CHAR == (int)('}'));

        // Swapping lowercase a<->b
        Cipher testSubstitution = new Substitution(
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`" +
                        "bacdefghijklmnopqrstuvwxyz{|}"
        );
        assertEquals("FAD", testSubstitution.encrypt("FAD"));
        assertEquals("fbd", testSubstitution.encrypt("fad"));
        assertEquals("BAD", testSubstitution.decrypt("BAD"));
        assertEquals("bad", testSubstitution.decrypt("abd"));
    }

    @Test
    @DisplayName("TODO: CaesarKey - 'A'-'Z'")
    public void keyAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        // TODO: Create a new CaesarKey("TIN"), encrypt the message "HELLO" and check the
        //       result's accurate. Then, take the encrypted message, decrypt it, and
        //       check the result's accurate
        Cipher caesarKey = new CaesarKey("TIN");
        String encrypted = caesarKey.encrypt("HELLO");
        assertEquals("EBJJM", encrypted);
        String decrypted = caesarKey.decrypt(encrypted);
        assertEquals("HELLO", decrypted);
    }

    @Test
    @DisplayName("TODO: CaesarShift - 'A'-'Z' encoder")
    public void shiftAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        // TODO: Create a new CaesarShift(6), encrypt the message "HELLO" and check the
        //       result's accurate. Then, take the encrypted message, decrypt it, and
        //       check the result's accurate
        Cipher caesarShift = new CaesarShift(6);
        String encrypted = caesarShift.encrypt("HELLO");
        assertEquals("NKRRU", encrypted);
        String decrypted = caesarShift.decrypt(encrypted);
        assertEquals("HELLO", decrypted);
    }

    @Test
    @DisplayName("TODO: MultiCipher - 'A'-'Z' encoder")
    public void multiAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int)('A') && Cipher.MAX_CHAR == (int)('Z'));

        // TODO: Create a new MultiCipher with ciphers CaesarKey("TIN") and CaesarShift(6)),
        //       encrypt the message "HELLO", and check the result's accurate. Then, take
        //       the encrypted message, decrypt it, and check the result's accurate
        Cipher caesarKey = new CaesarKey("TIN");
        Cipher caesarShift = new CaesarShift(6);
        MultiCipher multiCipher = new MultiCipher(Arrays.asList(caesarKey, caesarShift));
        String encrypted = multiCipher.encrypt("HELLO");
        assertEquals("KHPPS", encrypted);
        String decrypted = multiCipher.decrypt(encrypted);
        assertEquals("HELLO", decrypted);
    }
}
