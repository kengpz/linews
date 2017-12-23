<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.herokuapp.linews.service.RestTemplateService"%>
<%@page import="com.herokuapp.linews.factory.ApplicationContextFactory"%>
<%
	RestTemplateService restTemplateService = (RestTemplateService) ApplicationContextFactory.getInstance().getBean("restTemplateService");
	Map params = new HashMap();
	Enumeration enumeration = request.getParameterNames();
	while (enumeration.hasMoreElements()) {
		String key = (String) enumeration.nextElement();
		params.put(key, String.valueOf(request.getParameter(key)));
	}

	String result = "200";
	if(params.isEmpty() == false) {
		result = restTemplateService.getForResponse("", params);
	}
%><%out.clear();%><%=result%>