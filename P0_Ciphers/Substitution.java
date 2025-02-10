// P0: Ciphers
// Name: Joe Lin
// Date: Jan 22, 2025
// CSE 123 BK
// TA: Benoit Le

import java.util.HashSet;
import java.util.Set;

/**
 * Substitution class extends Cipher and implements a basic substitution cipher.
 *
 * It allows defining a custom encoding where each input character is substituted
 * based on the encoding string. The encoding must be a permutation of all valid
 * characters, with no duplicates.
 */
public class Substitution extends Cipher{

    private String encoding;

    /**
     * Default constructor for the Substitution class.
     *
     * Initializes the Substitution object without a predefined encoding. The encoding
     * can later be set using the setEncoding method.
     */
    public Substitution(){
        encoding = null;
    }

    /**
     * Constructor for the Substitution class with a predefined encoding string.
     *
     * Sets the encoding for the substitution cipher. The encoding string must contain
     * a permutation of all characters in the valid range and have the correct length.
     *
     * @param encoding The encoding string used for substitution.
     * @throws IllegalArgumentException If the encoding string is invalid (null, incorrect length,
     *                                  or contains duplicate/invalid characters).
     */
    public Substitution(String encoding){
        setEncoding(encoding);
    }

    /**
     * Sets the encoding string for the substitution cipher.
     *
     * The encoding string defines the substitution pattern, where each character in the input
     * is mapped to a character in the encoding string. The encoding must contain all
     * valid characters without duplication and must match the required character set.
     *
     * @param encoding The encoding string for the substitution cipher.
     * @throws IllegalArgumentException If the encoding is null, has an incorrect length,
     *                                  or contains invalid or duplicate characters.
     */
    public void setEncoding(String encoding){
        if(encoding == null || encoding.length() != TOTAL_CHARS){
            throw new IllegalArgumentException();
        }
        Set<Character> set = new HashSet<>();
        for(int i = 0; i < encoding.length(); i++){
            char c = encoding.charAt(i);
            if(c < MIN_CHAR || c > MAX_CHAR || set.contains(c)) {
                throw new IllegalArgumentException();
            }
            set.add(c);
        }

        this.encoding = encoding;
    }

    /**
     * Encrypts the given input string using the defined substitution encoding.
     *
     * Each character in the input string is substituted according to the encoding string.
     *
     * Special cases to consider:
     * - The valid range of characters for input is from MIN_CHAR to MAX_CHAR.
     * - If an input character falls outside this range (e.g., 'X' when the range is 'A'-'G'),
     *   the behavior is undefined and may result in an exception or incorrect encryption.
     * - Ensure that input only contains characters within the defined range to prevent errors.
     *
     * @param input The string to be encrypted.
     * @return The encrypted string, where each character has been substituted according
     * to the encoding.
     * @throws IllegalStateException If the encoding has not been set.
     * @throws IllegalArgumentException If the input string is null.
     */
    @Override
    public String encrypt(String input) {
        if(encoding == null){
            throw new IllegalStateException();
        }
        if(input == null){
            throw new IllegalArgumentException();
        }

        String plainText = "";
        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            plainText += encoding.charAt(c - MIN_CHAR);
        }

        return plainText;
    }

    /**
     * Decrypts the given input string using the defined substitution encoding.
     *
     * Each character in the encrypted string is reversed using the encoding string
     * to recover the original text.
     *
     * Special cases to consider:
     * - The valid range of characters for input is from MIN_CHAR to MAX_CHAR.
     * - If an input character falls outside this range (e.g., 'X' when the range is 'A'-'G'),
     *   the behavior is undefined and may result in an exception or incorrect decryption.
     * - Ensure that input only contains characters within the defined range to prevent errors.
     *
     * @param input The string to be decrypted.
     * @return The decrypted string, where each character has been reverted to its original form.
     * @throws IllegalStateException If the encoding has not been set.
     * @throws IllegalArgumentException If the input string is null.
     */
    @Override
    public String decrypt(String input) {
        if(encoding == null){
            throw new IllegalStateException();
        }
        if(input == null){
            throw new IllegalArgumentException();
        }

        String cipherText = "";
        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            cipherText += (char) (encoding.indexOf(c) + MIN_CHAR);
        }
        return cipherText;
    }
}