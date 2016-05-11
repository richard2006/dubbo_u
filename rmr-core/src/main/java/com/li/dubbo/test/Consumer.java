package com.li.dubbo.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.li.dubbo.service.DemoService;

public class Consumer {  
	  
    public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "applicationContext-dubbo.xml" });  
        context.start();  
  
        DemoService demoService = (DemoService) context.getBean("demoService"); //  
        String hello = demoService.sayHello("welcome"); //
        System.out.println(hello); //   
  
        // System.out.println(demoService.hehe());  
    }  
  
}  