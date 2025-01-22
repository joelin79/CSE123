import java.util.List;

// TODO: Write your implementation to MultiCipher here!
public class MultiCipher extends Cipher{

    private List<Cipher> ciphers;

    public MultiCipher(List<Cipher> ciphers){
        if(ciphers == null) throw new IllegalArgumentException();
        this.ciphers = ciphers;
    }

    @Override
    public String encrypt(String input) {
        if(input == null) throw new IllegalArgumentException();
        for (Cipher cipher : ciphers) {
            input = cipher.encrypt(input);
        }
        return input;
    }

    @Override
    public String decrypt(String input) {
        if(input == null) throw new IllegalArgumentException();
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            input = ciphers.get(i).decrypt(input);
        }
        return input;
    }
}