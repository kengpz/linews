package com.herokuapp.linews.service.impl;

import java.nio.charset.Charset;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herokuapp.linews.service.RestTemplateService;

public class RestTemplateServiceImpl implements RestTemplateService {
	private static Logger logger = LoggerFactory.getLogger(RestTemplateServiceImpl.class);
	private ObjectMapper objectMapper;
	private RestTemplate restTemplate;
	private HttpHeaders httpHeaders;
	private HttpHeaders httpHtmlHeaders;
	private String url;

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
			String json = objectMapper.writeValueAsString(response);
			HttpEntity<String> requestEntity = new HttpEntity<String>(json, httpHeaders);
			ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://muaythaijklsx.herokuapp.com/sender.php?to=U1095b0eb190532df137e4d45b70cd383&sat=TRmL8E/pYjsx3FjlXSNsBfiSS9opDNiDZlkWdhMhzVivXYfx3lJq19vtvWDe6MF+pP21hy4AR7tqR71Atluh3MGViDX/B7X6H4aZtcubapd1ESnu8f8g38jy3x75INjre4cGjaXf6bxncLTqfru4hAdB04t89/1O/w1cDnyilFU=&msg="+json, String.class);
			//ResponseEntity<String> responseEntity = restTemplate.exchange("http://muaythaijklsx.herokuapp.com/sender.php", HttpMethod.POST, requestEntity, String.class);

			return responseEntity.getBody().toString();
		} catch (Exception exception) {
			return "404";
		}
	}
}