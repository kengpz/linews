package com.herokuapp.linews.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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
import com.herokuapp.linews.json.JsonObjectMapper;

public class Test_Request {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = "https://bx.in.th/api/";
		String url1 = "https://bx.in.th/api/trade/?" + UriUtils.encode("pairing=26", "UTF-8");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));


		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Content-Type", "application/json");
		requestHeaders.set("User-Agent", "Mozilla/5.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		System.out.println(responseEntity.getHeaders().getDate());
		System.out.println(JsonObjectMapper.getInstance().readValueMap(responseEntity.getBody()).get("1")); //btc
		System.out.println(JsonObjectMapper.getInstance().readValueMap(responseEntity.getBody()).get("21")); //eth
		System.out.println(JsonObjectMapper.getInstance().readValueMap(responseEntity.getBody()).get("25")); //xrp
		System.out.println(JsonObjectMapper.getInstance().readValueMap(responseEntity.getBody()).get("26")); //omg
		System.out.println(JsonObjectMapper.getInstance().readValueMap(responseEntity.getBody()).get("28")); //evx
		//System.out.println(responseEntity.getBody());

	}
}