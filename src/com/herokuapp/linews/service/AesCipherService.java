package com.herokuapp.linews.service;

public interface AesCipherService {
	public String generateNewKey();
	public String encrypt(String plaintext, String key);
	public String decrypt(String encrypt, String key);
}