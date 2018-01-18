package com.herokuapp.linews.service;

import java.util.Map;

public interface CryptoCurrencyService {
	public String getPairing(String id);
	public String getPrice(String site);
	public String mappingMessage(String site ,Map params);
}