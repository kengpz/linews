package com.herokuapp.linews.test;

import java.util.HashMap;
import java.util.Map;

import com.herokuapp.linews.factory.ApplicationContextFactory;
import com.herokuapp.linews.service.RestTemplateService;

public class Test_Sender {

	public static void main(String[] args) {
		final String USER_TOKEN = "7uvzfuT7JTx18KSalT7bRFEXtoZSm1ndB4SfBtu3q2p";
		RestTemplateService restTemplateService = (RestTemplateService) ApplicationContextFactory.getInstance().getBean("restTemplateService");
		String msg = "xx";
		Map response = new HashMap();
		response.put("msg", msg);
		System.out.println("Status : " + restTemplateService.getForResponse("", response));
	}
}