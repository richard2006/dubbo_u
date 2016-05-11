package com.li.dubbo.service.impl;

import com.li.dubbo.service.DemoService;

public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String content) {
		// TODO Auto-generated method stub
		System.out.println("Hello, Dubbo!" + content);
		return "Got it";
	}

}
