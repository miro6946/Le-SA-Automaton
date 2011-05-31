package kr.lesaautomaton.presentation;

import kr.lesaautomaton.exception.LoginIncorrectException;
import kr.lesaautomaton.persistence.domain.Member;
import kr.lesaautomaton.service.MemberLB;
import kr.lesaautomaton.utils.SessionUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class SessionsAction extends BaseAction {
	
	private static Logger LOG = Logger.getLogger(SessionsAction.class);
	
	private Member member;
	private String returnURI;
	
	public String create() throws Exception{
		
		try{
	
			account = MemberLB.getInstance().login(member.getMemberId(), member.getPass());
			SessionUtils.setAccount(ServletActionContext.getRequest().getSession(), account);
			
			/*
			returnURI = (String)session.getAttribute(CommonUtils.KEY_RETURN_URI);			
			if(StringUtils.isBlank(returnURI))
				returnURI = "/";
			else
				session.removeAttribute(CommonUtils.KEY_RETURN_URI);
			*/
			if(StringUtils.isBlank(returnURI))returnURI = "/";			

			return SUCCESS;
			
		}catch(LoginIncorrectException e){
		
			addActionError(getText("validation.login.incorrect"));
			return INPUT;
			
		}		
		
	}
	
	public String destroy() {
		// TODO Auto-generated method stub
		SessionUtils.initialize(ServletActionContext.getRequest().getSession());
		return SUCCESS;
	}	

	public String getReturnURI() {
		return returnURI;
	}

	public void setReturnURI(String returnURI) {
		this.returnURI = returnURI;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
