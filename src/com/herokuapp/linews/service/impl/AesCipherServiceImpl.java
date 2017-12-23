package com.herokuapp.linews.service.impl;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.DefaultBlockCipherService;
import org.apache.shiro.util.ByteSource;

import com.herokuapp.linews.service.AesCipherService;

public class AesCipherServiceImpl implements AesCipherService {
	private DefaultBlockCipherService defaultBlockCipherService = new DefaultBlockCipherService("AES");

	public String generateNewKey() {
		Key key = defaultBlockCipherService.generateNewKey();
		byte[] keyBytes = key.getEncoded();

		return Hex.encodeToString(keyBytes);
	}

	private String encrypt(byte[] plaintext, byte[] key) {
		ByteSource byteSource = defaultBlockCipherService.encrypt(plaintext, key);

		return byteSource.toBase64();
	}

	public String encrypt(String plaintext, String key) {
		String encrypt = encrypt(plaintext.getBytes(), Hex.decode(key));

		return encrypt;
	}

	private String decrypt(byte[] encrypt, byte[] key) {
		ByteSource byteSource = defaultBlockCipherService.decrypt(encrypt, key);
		String decrypt = CodecSupport.toString(byteSource.getBytes());

		return decrypt;
	}

	public String decrypt(String encrypt, String key) {
		String decrypt = decrypt(Base64.decode(encrypt), Hex.decode(key));

		return decrypt;
	}
}