package com.herokuapp.linews.json;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JsonObjectMapper {
	private static JsonObjectMapper jsonObjectMapper;
	private ObjectMapper objectMapper;
	private XmlMapper xmlMapper;

	static {
		jsonObjectMapper = new JsonObjectMapper();
	}

	private JsonObjectMapper() {
		objectMapper = new ObjectMapper();
		xmlMapper = new XmlMapper();
	}

	public static JsonObjectMapper getInstance() {
		return jsonObjectMapper;
	}

	public String writeValueAsString(Object object) {
		String json = null;

		try {
			json = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException jsonProcessingException) {
			jsonProcessingException.printStackTrace();
		}

		return json;
	}

	public String writeValueAsStringByXmlMapper(String xml) {
		String json = null;

		try {
			List list = xmlMapper.readValue(xml, List.class);
			json = objectMapper.writeValueAsString(list);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return json;
	}

	public Map readValueMap(String json) {
		Map map = null;

		try {
			map = objectMapper.readValue(json, Map.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;
	}

	public List readValueList(String json) {
		List list = null;

		try {
			list = objectMapper.readValue(json, List.class);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return list;
	}
}