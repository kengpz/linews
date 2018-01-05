package com.herokuapp.linews.test;

import java.util.HashMap;
import java.util.Map;

import com.herokuapp.linews.factory.ApplicationContextFactory;
import com.herokuapp.linews.service.RestTemplateService;

public class Test_Sender {

	public static void main(String[] args) {
		RestTemplateService restTemplateService = (RestTemplateService) ApplicationContextFactory.getInstance().getBean("restTemplateService");
		String msg = "xx";
		Map response = new HashMap();
		response.put("msg", msg);
		System.out.println("Status : " + restTemplateService.getForResponse("", response));
	}

}