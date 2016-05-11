package com.li.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class HelloWorld implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher tradeEventPublisher;

	public void say(String message) {
		System.out.println("say : " + message);
		// construct a TradeEvent instance and publish it
		TradeEvent tradeEvent = new TradeEvent(new String("tradeEvent"));
		this.tradeEventPublisher.publishEvent(tradeEvent);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub
		this.tradeEventPublisher = applicationEventPublisher;
	}
}