package kr.lesaautomaton.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class ClientCacheInterceptor extends MethodFilterInterceptor {
	
	private int second = 0;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//response.setHeader("Expires", String.valueOf(second));
		
		if(second > -1){
			
			if(second == 0)
				response.setHeader("Cache-Control", "private, max-age=0, must-revalidate");
			else
				response.setHeader("Cache-Control", "max-age="+ second);
			
		}else{
			
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			//response.setHeader(arg0, arg1)
			
		}
		
		return invocation.invoke();
		
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

}
