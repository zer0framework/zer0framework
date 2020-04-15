package br.com.zer0framework.utils.security;

import java.io.UnsupportedEncodingException;
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
			System.out.println("Cryptographic algorithm not available: " + e.toString());
		} catch (UnsupportedEncodingException e) {
			System.out.println("Character encoding not supported: " + e.toString());
		}
	}

	public static String encryptor(String strToEncrypt) {
		try {
			setSecretKey();

			String currentToken = new Date(System.currentTimeMillis()).toString();
			currentToken = Base64.getEncoder().encodeToString(currentToken.getBytes("utf-8"));

			String str = strToEncrypt + currentToken;

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decryptor(String strToDecrypt) {
		try {
			setSecretKey();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			String str = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
			return str.substring(0, str.length() - 40);
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
	
	//TODO Concrete validation of the token
	public static boolean validateToken() {
		return false;
	}
}