package kr.lesaautomaton.utils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("------------------session created-----------------------");
		//SessionUtils.initialize(e.getSession());
		//e.getSession().setMaxInactiveInterval(10);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("------------------session destroied-----------------------");
	}
	

}
