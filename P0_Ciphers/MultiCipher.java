import java.util.List;

/**
 * MultiCipher class extends Cipher and applies multiple ciphers in sequence
 * for encryption and in reverse order for decryption.
 *
 * It requires a list of Cipher objects during initialization.
 */
public class MultiCipher extends Cipher{

    private List<Cipher> ciphers;

    /**
     * Constructor for the MultiCipher class.
     *
     * Initializes the MultiCipher with a list of Cipher objects. The provided list of ciphers
     * will be applied sequentially during encryption and in reverse order during decryption.
     *
     * @param ciphers A list of Cipher objects to be used in the multi-step encryption
     *                and decryption process.
     * @throws IllegalArgumentException If the provided list of ciphers is null.
     */
    public MultiCipher(List<Cipher> ciphers){
        if(ciphers == null) throw new IllegalArgumentException();
        this.ciphers = ciphers;
    }

    /**
     * Encrypts the given input string by applying each cipher in the list in sequence.
     *
     * Each cipher in the list is used to transform the input string. The output of one cipher
     * becomes the input for the next cipher in the list.
     *
     * @param input The string to be encrypted.
     * @return The encrypted string after all ciphers have been applied.
     * @throws IllegalArgumentException If the input string is null.
     */
    @Override
    public String encrypt(String input) {
        if(input == null) throw new IllegalArgumentException();
        for (Cipher cipher : ciphers) {
            input = cipher.encrypt(input);
        }
        return input;
    }

    /**
     * Decrypts the given input string by applying each cipher in the list in reverse order.
     *
     * Each cipher in the list is used to reverse the encryption applied earlier. The output of
     * one cipher becomes the input for the previous cipher in the list, effectively undoing
     * the transformations applied during encryption.
     *
     * @param input The string to be decrypted.
     * @return The decrypted string after all ciphers have been reversed.
     * @throws IllegalArgumentException If the input string is null.
     */
    @Override
    public String decrypt(String input) {
        if(input == null) throw new IllegalArgumentException();
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            input = ciphers.get(i).decrypt(input);
        }
        return input;
    }
}