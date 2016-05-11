package com.ruixue.dubbo.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ruixue.dubbo.service.ClientService;



@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Resource(name = "clientService")
	private ClientService clientService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(){
		// TODO Auto-generated method stub
		
		String message = clientService.execute("Success");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		return new ModelAndView("index.jsp", model);
	}
	public ClientService getClientService() {
		return clientService;
	}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
}
