package kr.lesaautomaton.presentation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import kr.lesaautomaton.persistence.domain.Zipcode;
import kr.lesaautomaton.service.ZipcodeLB;
import kr.lesaautomaton.utils.CommonUtils;
import kr.lesaautomaton.utils.EscapeUtils;

public class ZipcodesAction extends BaseAction {
	
	private String keyword;
	private List<Zipcode> zipcodes;
	
	public String search(){
		
		if(StringUtils.equalsIgnoreCase(ServletActionContext.getRequest().getMethod(), "POST") && !StringUtils.isBlank(keyword)){
			
			//if(CommonUtils.isAjaxRequest(ServletActionContext.getRequest()))keyword = EscapeUtils.unescape(keyword);
			zipcodes = ZipcodeLB.getInstance().find(keyword);
			
		}
		
		return INPUT;
		
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = StringUtils.trim(keyword);
	}

	public List<Zipcode> getZipcodes() {
		return zipcodes;
	}
		

}
