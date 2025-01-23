// P0: Ciphers
// Name: Joe Lin
// Date: Jan 22, 2025
// CSE 123 BK
// TA: Benoit Le

import java.util.HashSet;
import java.util.Set;

/**
 * CaesarKey class extends the Substitution class and represents a Caesar cipher key.
 * The key must be a non-empty string consisting of unique characters from a valid character range.
 * The constructor validates the key and generates the corresponding substitution encoding.
 *
 * This class assumes the key contains no duplicate characters and falls within a valid
 * range of characters as defined by the parent class (Substitution).
 */
public class CaesarKey extends Substitution{

    /**
     * Constructor for the CaesarKey class.
     *
     * Validates the key to ensure it's not null or empty, contains no duplicates, and
     * only includes characters within the allowed range. It then generates the encoding
     * by appending any missing characters from the valid range.
     *
     * @param key The key for the Caesar cipher substitution.
     * @throws IllegalArgumentException If the key is invalid
     * (null, empty, duplicate, or out of range).
     */
    public CaesarKey(String key){
        if(key == null || key.isEmpty()){
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < key.length(); i++){
            char c = key.charAt(i);
            if(c < MIN_CHAR || c > MAX_CHAR){
                throw new IllegalArgumentException();
            }
        }

        String encoding = "";
        Set<Character> used = new HashSet<>();
        for(int i = 0; i < key.length(); i++){
            char c = key.charAt(i);
            if(used.contains(c)){
                throw new IllegalArgumentException();
            }
            encoding += c;
            used.add(c);
        }

        for (int i = MIN_CHAR; i <= MAX_CHAR; i++) {
            if (!used.contains((char) i)) {
                encoding += (char) i;
            }
        }

        setEncoding(encoding);
    }
}