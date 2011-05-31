package kr.lesaautomaton.interceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kr.lesaautomaton.utils.EscapeUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

public class UnEscapeStringInterceptor implements Interceptor {
	
	private static final Logger LOG = Logger.getLogger(UnEscapeStringInterceptor.class); 
	
	private Set<String> paramNames = Collections.EMPTY_SET;
	private boolean unescapeAll = false;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		// TODO Auto-generated method stub
		
		//if(CommonUtils.isAjaxRequest(ServletActionContext.getRequest())){
		if(unescapeAll || !paramNames.isEmpty()){
		
	        Map parameters = ai.getInvocationContext().getParameters();
	        Map<String, String[]> newParams = new HashMap<String, String[]>();
	        Set<Map.Entry> entries = parameters.entrySet();
	        
	        for (Iterator<Map.Entry> iterator = entries.iterator(); iterator.hasNext();) {
	        	
	            Map.Entry entry = iterator.next();
	            String key = (String)entry.getKey();
	            
	            if(unescapeAll || paramNames.contains(key)){

		            String[] values = (String[])entry.getValue();
		            for (int i = 0; i < values.length; i++) {
						values[i] = EscapeUtils.unescape(values[i]);
					}
		            
		            newParams.put(key, values);

	            	LOG.debug("Unescaped ["+key+"] values : "+ ArrayUtils.toString(values));
		            
	            }
	            
	        }
	        
	        parameters.putAll(newParams);
        
		}
		
		
		return ai.invoke();
	}
	
    /**
     * Splits a string into a List
     */
//    protected Set<String> stringToList(String val) {
//        if (val != null) {
//            String[] list = val.split("[ ]*,[ ]*");
//            return Arrays.asList(list);
//        } else {
//            return Collections.EMPTY_LIST;
//        }
//    }	

    public void setParamNames(String names){
    	if(!StringUtils.equals(names, "*")){
    		this.paramNames = TextParseUtil.commaDelimitedStringToSet(names);
    	}else{
    		unescapeAll = true;
    	}
    	
    }

}
