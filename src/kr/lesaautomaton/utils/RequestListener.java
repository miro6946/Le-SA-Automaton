package kr.lesaautomaton.utils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import kr.lesaautomaton.persistence.domain.Member;

public class RequestListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)event.getServletRequest();
		
		request.setAttribute("MEMBER_TYPE_PRIVATE", Member.MEMBER_TYPE_PRIVATE);
		request.setAttribute("MEMBER_TYPE_PUBLISHER", Member.MEMBER_TYPE_PUBLISHER);
		request.setAttribute("MEMBER_TYPE_ORGANIZATION", Member.MEMBER_TYPE_ORGANIZATION);
		
	}

}
