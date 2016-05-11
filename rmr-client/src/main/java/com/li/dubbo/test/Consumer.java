package com.li.dubbo.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.li.dubbo.service.ClientService;

public class Consumer {  
	  
    public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "applicationContext.xml" });  
        context.start();  
//  
//        DemoService demoService = (DemoService) context.getBean("demoService"); //  
//        String hello = demoService.sayHello("uuuuuuuuuuuuuu");
          ClientService demoService = (ClientService) context.getBean("clientService"); //  
          String hello = demoService.execute("uuuuuuuuuuuuuu");
          System.out.println(hello); //   
          System.in.read();
        // System.out.println(demoService.hehe());  
    }  
  
}  