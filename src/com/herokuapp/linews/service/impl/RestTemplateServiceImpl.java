package com.herokuapp.linews.service.impl;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herokuapp.linews.service.RestTemplateService;

public class RestTemplateServiceImpl implements RestTemplateService {
	private static Logger logger = LoggerFactory.getLogger(RestTemplateServiceImpl.class);
	private ObjectMapper objectMapper;
	private RestTemplate restTemplate;
	private HttpHeaders httpHeaders;
	private HttpHeaders httpHtmlHeaders;
	private HttpHeaders httpUrlencodedHeaders;
	private String url;
	public static final long HOUR = 3600*1000;

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
	}

	public void setHttpHeaders(HttpHeaders httpHeaders) {
		MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
		this.httpHeaders = httpHeaders;
		this.httpHeaders.setContentType(mediaType);
		this.httpHeaders.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
	}

	public void setHttpUrlencodedHeaders(HttpHeaders httpUrlencodedHeaders) {
		MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
		this.httpUrlencodedHeaders = httpUrlencodedHeaders;
		this.httpUrlencodedHeaders.setContentType(mediaType);
	}

	public void setHttpHtmlHeaders(HttpHeaders httpHtmlHeaders) {
		MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
		this.httpHtmlHeaders = httpHtmlHeaders;
		this.httpHtmlHeaders.setContentType(mediaType);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map getForMap(String action, String json) {
		logger.info("GetForMap : Action = " + action + ", Json = " + json);

		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>(json, httpHeaders);
			ResponseEntity<String> responseEntity = restTemplate.exchange(url + action, HttpMethod.POST, requestEntity, String.class);
			Map response = objectMapper.readValue(responseEntity.getBody(), Map.class);

			return response;
		} catch (Exception exception) {
			logger.info("GetForMap : Action = " + url + action + ", Json = " + json + ", Exception = " + exception);
			return null;
		}
	}

	public Map getForMap(String action, Map params) {
		logger.info("GetForMap : Action = " + action + ", Params = " + params);

		try {
			String json = objectMapper.writeValueAsString(params);
			HttpEntity<String> requestEntity = new HttpEntity<String>(json, httpHeaders);
			ResponseEntity<String> responseEntity = restTemplate.exchange(url + action, HttpMethod.POST, requestEntity, String.class);
			Map response = objectMapper.readValue(responseEntity.getBody(), Map.class);

			return response;
		} catch (Exception exception) {
			logger.info("GetForMap : Action = " + url + action + ", Params = " + params + ", Exception = " + exception);
			return null;
		}
	}

	public String getForResponse(String action, Map response) {
		try {
			MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
			multiValueMap.add("msg", String.valueOf(response.get("msg")));
			multiValueMap.add("to", "C32eb44a444128e6b10c54365d3bc521c");
			multiValueMap.add("cat", "TRmL8E/pYjsx3FjlXSNsBfiSS9opDNiDZlkWdhMhzVivXYfx3lJq19vtvWDe6MF+pP21hy4AR7tqR71Atluh3MGViDX/B7X6H4aZtcubapd1ESnu8f8g38jy3x75INjre4cGjaXf6bxncLTqfru4hAdB04t89/1O/w1cDnyilFU=");
			ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://muaythaijklsx.herokuapp.com/sender.php", multiValueMap, String.class);

			return responseEntity.getBody().toString();
		} catch (Exception exception) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getForEntity("http://muaythaijklsx.herokuapp.com/sender.php?to=U1095b0eb190532df137e4d45b70cd383&cat=TRmL8E/pYjsx3FjlXSNsBfiSS9opDNiDZlkWdhMhzVivXYfx3lJq19vtvWDe6MF+pP21hy4AR7tqR71Atluh3MGViDX/B7X6H4aZtcubapd1ESnu8f8g38jy3x75INjre4cGjaXf6bxncLTqfru4hAdB04t89/1O/w1cDnyilFU=&msg=" + response.toString(), String.class);

			return exception.getMessage();
		}
	}

	public Map getForPrice(String agent) {
		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>("", httpHeaders);
			ResponseEntity<String> responseEntity = restTemplate.exchange("https://bx.in.th/api/", HttpMethod.GET, requestEntity, String.class);
			Map response = objectMapper.readValue(responseEntity.getBody(), Map.class);

			Date date = new Date(responseEntity.getHeaders().getDate() + 7 * HOUR);
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			String dateFormatted = formatter.format(date);
			response.put("time", dateFormatted);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int lineNotify(String token, String message) {
		httpUrlencodedHeaders.set("Authorization", "Bearer " + token);
		try {
			HttpEntity<String> requestEntity = new HttpEntity<String>("", httpUrlencodedHeaders);
			ResponseEntity<String> responseEntity = restTemplate.exchange("https://notify-api.line.me/api/notify", HttpMethod.POST, requestEntity, String.class);
			return responseEntity.getStatusCode().value();
		} catch(Exception e) {
			return 555;
		}
	}
}