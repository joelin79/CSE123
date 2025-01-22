import java.util.HashSet;
import java.util.Set;

// TODO: Write your implementation to CaesarKey here!
public class CaesarKey extends Substitution{
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