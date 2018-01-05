package com.herokuapp.linews.test;

import java.io.IOException;

import com.herokuapp.linews.factory.ApplicationContextFactory;
import com.herokuapp.linews.service.CryptoCurrencyService;

public class URLConnectionReader {

	public static void main(String[] args) throws IOException {
		CryptoCurrencyService cryptoCurrencyService = (CryptoCurrencyService) ApplicationContextFactory.getInstance().getBean("cryptoCurrencyService");
		String ppp = cryptoCurrencyService.getPairing("all");
		System.out.println(ppp);
	}

}
