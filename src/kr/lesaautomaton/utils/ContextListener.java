package kr.lesaautomaton.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kr.lesaautomaton.service.ApplicationSetupLB;
import kr.lesaautomaton.service.BoardLB;
import kr.lesaautomaton.service.GroupLB;

public class ContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// TODO Auto-generated method stub
		ServletContext context = e.getServletContext();
		
		context.setAttribute("groupCodeMap", GroupLB.getInstance().codeMap());
		context.setAttribute("boardIdMap", BoardLB.getInstance().idMap());
		context.setAttribute("applicationSetup", ApplicationSetupLB.getInstance().load());
		
	}

}
