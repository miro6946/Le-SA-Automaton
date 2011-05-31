package kr.lesaautomaton.utils;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import uk.ltd.getahead.dwr.util.Logger;

public class EscapeStringConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		// TODO Auto-generated method stub
		Logger.getLogger(EscapeStringConverter.class).debug(EscapeUtils.unescape(values[0]));
		return EscapeUtils.unescape(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		// TODO Auto-generated method stub
		Logger.getLogger(EscapeStringConverter.class).debug("String.valueOf(o):"+ EscapeUtils.unescape(String.valueOf(o).toString()));
		System.out.println(o.getClass().getName());
		return String.valueOf(o);
	}

}
