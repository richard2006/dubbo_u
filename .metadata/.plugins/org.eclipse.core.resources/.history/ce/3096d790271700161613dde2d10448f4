package com.ruixue.dubbo.service.impl;

import com.ruixue.dubbo.service.ClientService;
import com.ruixue.dubbo.service.DemoService;

public class ClientServiceImpl implements ClientService {
	
	private DemoService demoService;

	@Override
	public String execute(String content) {
		// TODO Auto-generated method stub
		return demoService.sayHello(content);
	}


	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

}
