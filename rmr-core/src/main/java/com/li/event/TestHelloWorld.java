package com.li.event;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHelloWorld {
	public static void main(String[] args) {
		  @SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
	                new String[] { "applicationContext-dubbo.xml" });  
		  context.start();  
		HelloWorld bean = (HelloWorld) context.getBean("helloWorld");
		bean.say("Hi");
	}
}
