package com.herokuapp.linews.service;

import java.util.Map;

public interface RestTemplateService {
	public Map getForMap(String action, String json);
	public Map getForMap(String action, Map params);
	public Map getForPrice(String agent);
	public String getForResponse(String action, Map response);
}