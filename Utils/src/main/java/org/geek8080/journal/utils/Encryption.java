package org.geek8080.journal.utils;

import org.jasypt.util.text.AES256TextEncryptor;

public class Encryption {

	private static final AES256TextEncryptor encryptor;

	static {
		encryptor = new AES256TextEncryptor();
		// This sets the password to be used to encrypt the text passed.
		encryptor.setPassword("TstP2ButEtTP.");
	}

	public static String encryptText(String text){
		String encryptedText = encryptor.encrypt(text);
		return encryptedText;
	}

	public static String decryptText(String text){
		return encryptor.decrypt(text);
	}
}
