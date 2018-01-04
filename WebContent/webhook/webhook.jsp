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
		params.put("type", "Parameter");
		result = restTemplateService.getForResponse("", params);
	} else {
		Enumeration attributes = request.getAttributeNames();
		params.put("type", "Attributes");
		while(attributes.hasMoreElements()) {
			String attr = (String) attributes.nextElement();
			params.put(attr, String.valueOf(request.getAttribute(attr)));
		}
		result = restTemplateService.getForResponse("", params);
	}
%><%out.clear();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hooker</title>
</head>
<body>
<%=result%>
</body>
</html>