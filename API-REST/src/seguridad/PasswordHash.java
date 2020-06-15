package seguridad;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Hex;

public class PasswordHash {
	
	private String hashedString;
	
	public void generatePassword(String contrasena, String salt) {
		char[] passwordChars = contrasena.toCharArray();
        byte[] saltBytes = salt.getBytes(); 
        byte[] hashedBytes = hashPassword(passwordChars, saltBytes);
        this.hashedString = Hex.encodeHexString(hashedBytes);
	}
	
	private byte[] hashPassword(final char[] password, final byte[] salt) {
		
		final int iterations = 10000;
	    final int keyLength = 512; 
	    
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512"); 
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
	
	public String generateSalt() {
		byte[] b = new byte[16];
	    Random RANDOM = new SecureRandom();
	    RANDOM.nextBytes(b);
	    return new String(Base64.getEncoder().encode(b));
    }
	
	public String getHash() {
		return this.hashedString;
	}
}
