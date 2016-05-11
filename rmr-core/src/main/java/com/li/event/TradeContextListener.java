package com.li.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class TradeContextListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent e) {

		System.out.println(e.getClass().toString());
		// TODO Auto-generated method stub
		if (e instanceof ContextStartedEvent) {
			System.out.println("it was contextStartedEvent");
		}

		if (e instanceof TradeEvent) {
			System.out.println(e.getSource());
		}

	}

}
