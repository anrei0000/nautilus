package com.anrei0000.robot_industrial;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * Class to be used for encription / decription of communication Source modified
 * from
 * http://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt
 * -example
 * 
 * @author Ciuc
 *
 */
public class AdvancedEncryptionStandard {
	private static final int FLAG = 0;
	private String encryptionKey;

	public AdvancedEncryptionStandard(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String encrypt(String plainText) throws Exception {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

		// return Base64.encodeBase64String(encryptedBytes);
		return Base64.encodeToString(encryptedBytes, FLAG);
	}

	public String decrypt(String encrypted) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
		byte[] plainBytes = cipher.doFinal(Base64.decode(encrypted, FLAG));

		return new String(plainBytes);
	}

	private Cipher getCipher(int cipherMode) throws Exception {
		String encryptionAlgorithm = "AES";
		SecretKeySpec keySpecification = new SecretKeySpec(
				encryptionKey.getBytes("UTF-8"), encryptionAlgorithm);
		Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
		cipher.init(cipherMode, keySpecification);

		return cipher;
	}
}