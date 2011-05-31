package kr.lesaautomaton.presentation;

import org.apache.struts2.ServletActionContext;

import kr.lesaautomaton.persistence.domain.Member;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	public static String LOGIN_REQUIRED = "login.required";
	public static String PERMISSION_ERROR = "permission.error";
	
	protected String pageId;
	protected Member account;
	
	public BaseAction(){
		
		super();
		ServletActionContext.getRequest().getSession().setMaxInactiveInterval(1800);
		
	}	

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Member getAccount() {
		//if(account == null)account = SessionUtils.getAccount(ServletActionContext.getRequest().getSession());
		return account;
	}

	public void setAccount(Member account) {
		this.account = account;
	}	

}
