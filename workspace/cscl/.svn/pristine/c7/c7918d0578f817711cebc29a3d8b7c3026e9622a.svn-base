package com.pc.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSA {
	/**
	 * 公钥加密
	 * 
	 * @param content 要加密的文本
	 * @param pubkey 公钥
	 * @return 密文
	 */
	public static String encrypt(String content, String pubkey) {
		byte[] cipherText = null;
		String text = "";
		try {
			// get an RSA cipher object and print the provider
			Cipher cipher = Cipher.getInstance("RSA");
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(pubkey));
			cipherText = cipher.doFinal(content.getBytes());
			text = Base64.encode(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

//	/**
//	 * 解密
//	 * 
//	 * @param content 密文
//	 * @param private_key 商户私钥
//	 * @param input_charset 编码格式
//	 * @return 解密后的字符串
//	 */
//	public static String decrypt(String content, String private_key,
//			String input_charset) throws Exception {
//		//获取私钥               getPrivateKeyPKCS8(private_key)
//		PrivateKey prikey = getPrivateKeyPKCS1(private_key);
//
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.DECRYPT_MODE, prikey);
//
//		InputStream ins = new ByteArrayInputStream(Base64.decode(content));
//		ByteArrayOutputStream writer = new ByteArrayOutputStream();
//		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
//		byte[] buf = new byte[128];
//		int bufl;
//
//		while ((bufl = ins.read(buf)) != -1) {
//			byte[] block = null;
//
//			if (buf.length == bufl) {
//				block = buf;
//			} else {
//				block = new byte[bufl];
//				for (int i = 0; i < bufl; i++) {
//					block[i] = buf[i];
//				}
//			}
//			writer.write(cipher.doFinal(block));
//		}
//		return new String(writer.toByteArray(), input_charset);
//	}

//	/**
//	 * 得到私钥 PKCS1
//	 * 
//	 * @param key 密钥字符串（经过base64编码）
//	 * @throws Exception
//	 */
//	public static PrivateKey getPrivateKeyPKCS1(String key) throws Exception {
//		byte[] keyBytes;
//		keyBytes = Base64.decode(key);
//
//    	RSAPrivateKey privatekey = RSAPrivateKey.getInstance(ASN1Sequence.fromByteArray(keyBytes));
//		RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(
//				privatekey.getModulus(), privatekey.getPrivateExponent());
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivKeySpec);
//
//		return privateKey;
//	}
	
	/**
	 * 得到私钥 PKCS8
	 * 
	 * @param key 密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyPKCS8(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}

	/**
	 * 得到公钥
	 * 
	 * @param key 密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		return publicKey;
	}

}
