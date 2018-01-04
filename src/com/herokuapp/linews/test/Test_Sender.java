package com.herokuapp.linews.test;

import java.util.HashMap;
import java.util.Map;

import com.herokuapp.linews.factory.ApplicationContextFactory;
import com.herokuapp.linews.service.RestTemplateService;

public class Test_Sender {

	public static void main(String[] args) {
		RestTemplateService restTemplateService = (RestTemplateService) ApplicationContextFactory.getInstance().getBean("restTemplateService");
		String sat = "TRmL8E/pYjsx3FjlXSNsBfiSS9opDNiDZlkWdhMhzVivXYfx3lJq19vtvWDe6MF+pP21hy4AR7tqR71Atluh3MGViDX/B7X6H4aZtcubapd1ESnu8f8g38jy3x75INjre4cGjaXf6bxncLTqfru4hAdB04t89/1O/w1cDnyilFU=";
		String to = "U1095b0eb190532df137e4d45b70cd383";
		String msg = "xx";
		Map response = new HashMap();
		response.put("cat", sat);
		response.put("to", to);
		response.put("msg", msg);
		System.out.println("Status : " + restTemplateService.getForResponse("", response));
	}

}