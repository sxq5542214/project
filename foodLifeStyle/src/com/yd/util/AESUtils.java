package com.yd.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESUtils {
	public static String encrypt(String input, String key, String vi) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(vi.getBytes()));
		byte[] encrypted = cipher.doFinal(input.getBytes("utf-8"));
		// 此处使用 BASE64做转码。
		return DatatypeConverter.printBase64Binary(encrypted);
	}

	public static String decrypt(String input, String key, String vi) throws Exception {
		byte[] bytes = DatatypeConverter.parseBase64Binary(input);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), new IvParameterSpec(vi.getBytes()));
		byte[] decrypted = cipher.doFinal(bytes);
		// 此处使用BASE64做转码。
		return new String(decrypted, "UTF-8");
	}
}
