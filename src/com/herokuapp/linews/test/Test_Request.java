package com.herokuapp.linews.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herokuapp.linews.factory.ApplicationContextFactory;
import com.herokuapp.linews.json.JsonObjectMapper;
import com.herokuapp.linews.service.RestTemplateService;

public class Test_Request {

	public static void main(String[] args) throws UnsupportedEncodingException {
		RestTemplateService restTemplateService = (RestTemplateService) ApplicationContextFactory.getInstance().getBean("restTemplateService");
		String url = "https://bx.in.th/api/";
		String url1 = "https://bx.in.th/api/trade/?" + UriUtils.encode("pairing=26", "UTF-8");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));


		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Content-Type", "application/json");
		requestHeaders.set("User-Agent", "Mozilla/5.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		Date date = new Date(responseEntity.getHeaders().getDate());
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateFormatted = formatter.format(date);

		StringBuffer msg = new StringBuffer();
		msg.append("Crypto price at : " + dateFormatted +"\n");
		msg.append("____________________________\n\n");
		//System.out.println("Crypto price at : " + dateFormatted);
		//System.out.println("____________________________");
		//System.out.println();

		Map currencyMap = JsonObjectMapper.getInstance().readValueMap(responseEntity.getBody());
		String[] arrCurr = new String[] {"1","21","25","26","28"};
		for(int i=0; i<arrCurr.length; i++) {
			Map currencyObject = (Map) currencyMap.get(arrCurr[i]);
			String currency = String.valueOf(currencyObject.get("secondary_currency")) + "/" + String.valueOf(currencyObject.get("primary_currency"));
			String change = (Double.parseDouble(String.valueOf(currencyObject.get("change"))) >= 0) ? "+" + String.valueOf(currencyObject.get("change")) : String.valueOf(currencyObject.get("change"));
			BigDecimal lastprice = new BigDecimal(String.valueOf(currencyObject.get("last_price")));
	
			msg.append(currency + " --> https://bx.in.th/\n");
			//System.out.println(currency + " --> https://bx.in.th/");
			msg.append("Last price : " + lastprice + "\n");
			//System.out.println("Last price : " + lastprice);
			msg.append("Change : " + change + "\n");
			//System.out.println("Change : " + change);
			msg.append("____________________________\n\n");
			//System.out.println();
		}

		msg.append("(star)(star)(star)(star)(star)\n");
		System.out.println(msg.toString());
		Map params = new HashMap();
		params.put("msg",msg.toString());
		String response = restTemplateService.getForResponse("", params);
		System.out.println("Response : " + response);
	}
}