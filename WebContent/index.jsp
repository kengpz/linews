<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.herokuapp.linews.service.CryptoCurrencyService"%>
<%@page import="com.herokuapp.linews.service.RestTemplateService"%>
<%@page import="com.herokuapp.linews.factory.ApplicationContextFactory"%>
<% 
	request.setCharacterEncoding("UTF-8");
	String utk = (request.getParameter("utk") == null) ? "" : request.getParameter("utk");
	String site = (request.getParameter("ag") == null) ? "" : request.getParameter("ag");

	int result = 0;
	int length = 0;
	if("".equals(utk) == false && "".equals(site) == false) {
		RestTemplateService restTemplateService = (RestTemplateService) ApplicationContextFactory.getInstance().getBean("restTemplateService");
		CryptoCurrencyService cryptoCurrencyService = (CryptoCurrencyService) ApplicationContextFactory.getInstance().getBean("cryptoCurrencyService");
		Map currencyMap = restTemplateService.getForPrice(site);
		String message = cryptoCurrencyService.mappingMessage(site, currencyMap);
		result = restTemplateService.lineNotify(utk, message);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Line Message Agent</title>
</head>
<body>
Result : <%=result %><br/>
Length : <%=length %>
</body>
</html>