package com.herokuapp.linews.request;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BufferedReaderRequest {
	public static String getRequest(HttpServletRequest httpServletRequest) {
		StringBuffer request = new StringBuffer();

		try {
			BufferedReader bufferedReader = httpServletRequest.getReader();

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				request.append(line);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return request.toString();
	}

	public static Map getRequestMap(HttpServletRequest httpServletRequest) {
		String request = getRequest(httpServletRequest);
		Map params = new HashMap();

		try {
			if (request.length() != 0) {
				params = new ObjectMapper().readValue(request, Map.class);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return params;
	}
}