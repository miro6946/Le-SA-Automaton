package kr.lesaautomaton.utils;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import kr.lesaautomaton.persistence.domain.Group;
import kr.lesaautomaton.persistence.domain.Member;

public class SessionUtils {
	
	private static final Logger LOG = Logger.getLogger(SessionUtils.class);
	
	public static Member getGuestAccount(){
		
		Member member = new Member();
		
		Group group = new Group();
		group.setCode(Group.GUEST_CODE);
		
		member.setGroup(group);
		member.setGroupCode(group.getCode());
		
		return member;
		
	}
	
	public static Member initialize(HttpSession session){
		
		if(LOG.isDebugEnabled()){
			
			LOG.debug("------------------------------------------------");
			try{
				LOG.debug("SessionUtils.initialize("+ BeanUtils.describe(session).toString() +")");
			}catch (Exception e) {
				// TODO: handle exception
				LOG.debug("Exception("+ e.getMessage() +") occured where logging SessionUtils.initialize()....");
			}
			LOG.debug("------------------------------------------------");
			
		}
		
		Member member = getGuestAccount();

		setAccount(session, member);
		
		return member;
		
	}
	
	public static Member getAccount(HttpSession session){
		
		Member member = (Member)session.getAttribute("member");
		
		if(member == null)member = initialize(session);
		
		return member;
		
	}
	
	public static void setAccount(HttpSession session, Member member){
		
		session.setAttribute("member", member);
		
	}

}
