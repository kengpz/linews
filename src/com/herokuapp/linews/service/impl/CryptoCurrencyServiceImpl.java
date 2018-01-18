package com.herokuapp.linews.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import com.herokuapp.linews.service.CryptoCurrencyService;
import com.herokuapp.linews.service.RestTemplateService;

public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
	private RestTemplateService restTemplateService;

	public void setRestTemplateService(RestTemplateService restTemplateService) {
		this.restTemplateService = restTemplateService;
	}

	public String getPairing(String id) {
		StringBuffer msg = new StringBuffer();
		String[] arrCurr = null;
		if(id.equalsIgnoreCase("all")) {
			arrCurr = new String[] {"1","14","21","25","26","28"};
		} else {
			arrCurr = new String[] {id};
		}

		Map currencyMap = restTemplateService.getForPrice("bx");
		msg.append(currencyMap.get("time") +"\n");
		msg.append("Exchange site : BX \n");
		msg.append("____________________________\n\n");
		for (int i = 0; i < arrCurr.length; i++) {
			Map currencyObject = (Map) currencyMap.get(arrCurr[i]);
			String currency = String.valueOf(currencyObject.get("secondary_currency")) + "/" + String.valueOf(currencyObject.get("primary_currency"));
			String change = (Double.parseDouble(String.valueOf(currencyObject.get("change"))) >= 0) ? "+" + String.valueOf(currencyObject.get("change")) : String.valueOf(currencyObject.get("change"));
			BigDecimal lastprice = new BigDecimal(String.valueOf(currencyObject.get("last_price")));

			DecimalFormat df = new DecimalFormat("#,###.00");
			if(arrCurr[i].equals("14")) df = new DecimalFormat("#0.00000000");

			msg.append("Currency : " + currency + " \n");
			msg.append("Last price : " + df.format(lastprice) + String.valueOf(currencyObject.get("primary_currency")) + "\n");
			msg.append("Change : " + change + "% \n");
			msg.append("____________________________\n\n");
		}

		return msg.toString();
	}
}