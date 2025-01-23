/**
 * CaesarShift class extends Substitution and implements a Caesar cipher with a shift.
 * The shift value determines how many positions characters are moved, with cyclic wrapping
 * around the character range.
 */
public class CaesarShift extends Substitution {
    private int shift;

    /**
     * Constructor for the CaesarShift class.
     *
     * Validates the shift value to ensure it is positive, and then calculates the encoding
     * based on the shift. The characters are cycled through starting from the character at the
     * position of the shift, wrapping around if necessary.
     *
     * @param shift The number of positions to shift each character in the alphabet.
     * @throws IllegalArgumentException If the shift value is zero or negative.
     */
    public CaesarShift(int shift) {
        if (shift <= 0) throw new IllegalArgumentException();
        this.shift = shift;

        String encoding = "";
        for(int i = MIN_CHAR + shift % TOTAL_CHARS; i <= MAX_CHAR; i++) {
            encoding += (char)i;
        }
        for(int i = MIN_CHAR; i < MIN_CHAR + shift % TOTAL_CHARS; i++) {
            encoding += (char)i;
        }
        setEncoding(encoding);
    }

}