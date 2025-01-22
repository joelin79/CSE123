import java.util.HashSet;
import java.util.Set;

// TODO: Write your implementation to Subsitution here!
public class Substitution extends Cipher{

    private String encoding;


    public Substitution(){
        encoding = null;
    }

    public Substitution(String encoding){
        setEncoding(encoding);
    }

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