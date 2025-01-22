// TODO: Write your implementation to CaesarShift here!
public class CaesarShift extends Substitution {
    private int shift;

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