package kr.lesaautomaton.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateTypeConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass){
		// TODO Auto-generated method stub
		try {
			
			return DateUtils.parseDate(values[0], "yyyy-MM-dd");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
	}

	@Override
	public String convertToString(Map context, Object o) {
		// TODO Auto-generated method stub
		return (new SimpleDateFormat("yyyy-MM-dd")).format(o);
	}

}
