package kr.lesaautomaton.interceptor;

import kr.lesaautomaton.presentation.BaseAction;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class SetPageIdInterceptor implements Interceptor {
	
	private boolean enable = true;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		
		if(enable){
		
			ActionMapping map = ServletActionContext.getActionMapping();
			
			((BaseAction)invocation.getAction()).setPageId((map.getNamespace() +"/"+ map.getName()).replaceAll("([\\/]{2,})", "/"));
		
		}
		
		return invocation.invoke();
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
