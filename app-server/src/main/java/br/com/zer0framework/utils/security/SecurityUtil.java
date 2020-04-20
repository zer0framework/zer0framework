package br.com.zer0framework.utils.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtil {

	private static String secret = "iasdgfladsflakjsdfhalk";
	private static byte[] key;
	private static SecretKeySpec secretKey;

	public static void setSecretKey() {
		String myKey = secret;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			key = myKey.getBytes("UTF-8");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);

			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encryptor(Integer toEncrypt) {
		try {
			setSecretKey();

			long expiration = new Date(System.currentTimeMillis() + 7200000).getTime();

			String str = toEncrypt +":"+ expiration;

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String decryptor(String strToDecrypt) {
		try {
			setSecretKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean validateToken(String token) {

		String decrypted = null;
		try {
			decrypted = decryptor(token);
		}catch (Exception e){
			return false;
		}

		String[] y = decrypted.split(":");

		Date expirationDate =  new Date(Long.parseLong(y[1]));
		Date now = new Date();

        return now.before(expirationDate);
    }

    public static Integer getUserIdFromToken(String token){
		String decrypted = decryptor(token);

		String[] y = decrypted.split(":");
		return Integer.parseInt(y[0]);
	}
}