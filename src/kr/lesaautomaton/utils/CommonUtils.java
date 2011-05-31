package kr.lesaautomaton.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class CommonUtils {
	
	public static String KEY_RETURN_URI = "return_uri";
	public static String AJAX_HEADER_NAME = "XMLHttpRequest";
	public static String IMAGE_FILE_PATTERN = "<img [^<>]*src=[\'|\"]{1,}[^= \']*\\/attachments\\/show\\.(action\\?attachment.\\seq=[0-9]*)[\'|\"]{1,}[^<>]*>";
	
	public static boolean isAjaxRequest(HttpServletRequest request){
		
		return StringUtils.equals(request.getHeader("X-Requested-With"), AJAX_HEADER_NAME);
		
	}
	
	public static String stripTags(String str){
		try{
			return str.replaceAll("<[^>]+>", "");
		}catch (NullPointerException e) {
			// TODO: handle exception
			return "";
		}
	}
	
	public static String stripJavascript(String str){
		try{
			return str.replaceAll("(?i)<script.*>.*<\\/script>", "");
		}catch (NullPointerException e) {
			// TODO: handle exception
			return "";
		}		
	}
	
	public static String simpleFormat(String str){
		
		String ret = str;
		
		if(StringUtils.isNotEmpty(ret)){
		
			ret = StringEscapeUtils.escapeXml(ret);
			ret = "<p>"+ ret.replaceAll("\r\n?", "\n").replaceAll("\n[\n]+", "</p><p>").replaceAll("\n", "<br>") +"</p>";
		
		}
		
		return ret;
		
	}
	
	public static String capacity(long cap){

		if(cap > 1073741824)
			return Math.round(cap/1073741824) + " GB";
		else if(cap > 1048576)
			return Math.round(cap/1048576) + " MB";
		else if(cap > 1024)
			return Math.round(cap/1024) + " KB";
		else
			return Math.round(cap) + " B";
		
	}

}
