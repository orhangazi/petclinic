import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void generateEncodedPasswords(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("{bcrypt}"+bCryptPasswordEncoder.encode("secret1"));
        System.out.println("{bcrypt}"+bCryptPasswordEncoder.encode("secret2"));
        System.out.println("{bcrypt}"+bCryptPasswordEncoder.encode("secret3"));
    }
}