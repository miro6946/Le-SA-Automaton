package kr.lesaautomaton.interceptor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.lesaautomaton.persistence.domain.Member;
import kr.lesaautomaton.presentation.BaseAction;
import kr.lesaautomaton.utils.CommonUtils;
import kr.lesaautomaton.utils.SessionUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

public class SessionPreprocessInterceptor extends MethodFilterInterceptor {
	
	private static final Logger LOG = Logger.getLogger(SessionPreprocessInterceptor.class);
	
	private boolean onlyGuest = false;
	private boolean onlyMember = false;
	private boolean onlySupervise = false;
	private Set<String> groupCodes = Collections.EMPTY_SET;	
	private Set<String> typeCodes = Collections.EMPTY_SET;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		ActionSupport action = (ActionSupport)invocation.getAction();
		Member account = SessionUtils.getAccount(session);
		
//		BeanUtils.setProperty(action, "account", account);
		if(action instanceof BaseAction)((BaseAction)action).setAccount(account);
		
		if(groupCodes.isEmpty() && typeCodes.isEmpty()){
			
			if(onlyMember && account.getGroup().isGuest()){
				
				if(!CommonUtils.isAjaxRequest(request)){
					
					storeLocation(request);
					action.addActionError(action.getText("login.required"));
					return BaseAction.LOGIN_REQUIRED;
				
				}else{
					
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return Action.NONE;					
					
				}			
				
			}
			
			if(onlySupervise && !account.getGroup().isSupervise()){
					
				if(!CommonUtils.isAjaxRequest(request)){
					
					storeLocation(request);
					
					/*
					if(account.getGroup().isGuest())
						action.addActionError(action.getText("login.required"));
					else
						action.addActionError(action.getText("permission.denied"));
					*/
					if(!account.getGroup().isGuest())action.addActionError(action.getText("permission.denied"));
						
					return BaseAction.LOGIN_REQUIRED;
					
				}else{
					
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return Action.NONE;					
					
				}
					
			}
			
			if(onlyGuest && !account.getGroup().isGuest()){
				
				if(!CommonUtils.isAjaxRequest(request)){
					
					action.addActionError(action.getText("permission.denied"));
					return BaseAction.PERMISSION_ERROR;
					
				}else{
					
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return Action.NONE;
					
				}				
				
//				if((!inSupervise && SessionUtils.isLogin(session)) || (inSupervise && SessionUtils.isLogin(session) && SessionUtils.isSupervise(session))){
//					
//					if(!CommonUtils.isAjaxRequest(request)){
//						
//						action.addActionError(action.getText("permission.denied"));
//						return BaseAction.PERMISSION_ERROR;
//						
//					}else{
//						
//						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//						return Action.NONE;
//						
//					}
//					
//				}				
				
			}
		
		}else{
			
			if(account.getGroup().isGuest()){
				storeLocation(request);
				action.addActionError(action.getText("login.required"));
				return BaseAction.LOGIN_REQUIRED;							
			}			
			
			if(!groupCodes.isEmpty() && !groupCodes.contains(account.getGroupCode())){
				
				action.addActionError(action.getText("permission.denied"));
				return BaseAction.PERMISSION_ERROR;
				
			}
			
			if(!typeCodes.isEmpty() && !typeCodes.contains(account.getType())){
				
				String[] typeNames = new String[]{};
				
				for(String type : typeCodes){
					typeNames = ArrayUtils.add(typeNames, action.getText("member.type."+ type));
				}
				
				action.addActionError(action.getText("only.member.type", new String[]{StringUtils.join(typeNames, ", ")}));
				return BaseAction.PERMISSION_ERROR;
				
			}
			
		}
		
		return invocation.invoke();
	}
	
	private void storeLocation(HttpServletRequest request){
		
		String qstr = null, currentURI = null;//, svPrefix = "/supervise";
		//boolean inSupervise = StringUtils.startsWith(ServletActionContext.getActionMapping().getNamespace(), svPrefix);		
		
		if(request.getMethod().equals("GET")){
			qstr = request.getQueryString();
			currentURI = request.getServletPath();
			if(StringUtils.isNotBlank(qstr))currentURI += ("?"+ qstr);						
		}/*else{
			currentURI = (!inSupervise ? "/" : svPrefix +"/");
		}*/
		
		request.getSession().setAttribute(CommonUtils.KEY_RETURN_URI, currentURI);		
		
	}

	public void setOnlyGuest(boolean onlyGuest) {
		this.onlyGuest = onlyGuest;
	}
	
	public void setOnlyMember(boolean onlyMember){
		this.onlyMember = onlyMember;
	}	
	
	public void setOnlySupervise(boolean onlySupervise){
		this.onlySupervise = onlySupervise;
	}
	
    public void setGroupCodes(String groupCodes) {
        this.groupCodes = TextParseUtil.commaDelimitedStringToSet(groupCodes);
    }	
    
    public void setGroupCodes(String[] groupCodes) {
    	this.groupCodes = new HashSet<String>();
    	CollectionUtils.addAll(this.groupCodes, groupCodes);
    }
    
    public void setTypeCodes(String typeCodes) {
        this.typeCodes = TextParseUtil.commaDelimitedStringToSet( typeCodes);
    }	
    
    public void setTypeCodes(String[] typeCodes) {
    	this.typeCodes = new HashSet<String>();
    	CollectionUtils.addAll(this.typeCodes, typeCodes);
    }	     
	
}
