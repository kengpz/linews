package com.herokuapp.linews.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Test_Publish {

	public static void main(String[] args) {
		/*try {
			getJsonRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		String strUrl = "https://api.line.me/v2/bot/message/push";
		String strAccessToken = "TRmL8E/pYjsx3FjlXSNsBfiSS9opDNiDZlkWdhMhzVivXYfx3lJq19vtvWDe6MF+pP21hy4AR7tqR71Atluh3MGViDX/B7X6H4aZtcubapd1ESnu8f8g38jy3x75INjre4cGjaXf6bxncLTqfru4hAdB04t89/1O/w1cDnyilFU=";

		/*SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress("proxy1.homepro.local", 8080);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		factory.setProxy(proxy);*/

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		//restTemplate.setRequestFactory(factory);

		HttpHeaders requestHeaders = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		requestHeaders.setContentType(mediaType);
		requestHeaders.set("Authorization", "Bearer " + strAccessToken);

		String json = "";
		try{
			json = getJsonRequest();
		}catch(Exception e) {
			
		}

		HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.exchange(strUrl, HttpMethod.POST, requestEntity, String.class);
		System.out.println("HttpStatus : " + responseEntity.getStatusCode());
		System.out.println("ResponseBody : " + responseEntity.getBody());

	}

	public static String getJsonRequest() throws JsonParseException, JsonMappingException, IOException {
		Map messages = new HashMap();
		String to = "U1095b0eb190532df137e4d45b70cd383";

		String json = "{    \"to\":\"" + to + "\",    \"messages\":[        {            \"type\":\"text\",            \"text\":\"Hello, user\"        },        {            \"type\":\"text\",            \"text\":\"May I help you?\"        }    ]}";
		messages = new ObjectMapper().readValue(json, Map.class);

		return json;
	}
}