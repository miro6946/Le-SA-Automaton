package kr.lesaautomaton.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import kr.lesaautomaton.persistence.domain.Attachment;
import kr.lesaautomaton.persistence.domain.Member;

import org.apache.commons.io.FileUtils;

public class AttachmentUtils {
	
	public static final String ALLOW_IMAGE_EXTENSIONS = "jpg|gif|bmp|png|raw|jpeg|pcx|tif";
	public static final String IMAGE_TAG_SEARCH_PATTERN = "<img [^<>]*src=[\'|\"]{1,}[^= \']*\\/attachments\\/show\\.(action\\?attachment\\.seq=%d)[\'|\"]{1,}[^<>]*>";
	public static final String IMAGE_TAG_SEARCH_PATTERN_PUBLIC = "<img [^<>]*src=[\'|\"]{1,}[^= \']*\\/uploaded\\/[0-9]{4}\\/[0-9]{1,2}\\/%d[\'|\"]{1,}[^<>]*>";
	//public static final String IMAGE_TAG_SEARCH_PATTERN_PUBLIC = "<img [^<>]*src=[\'|\"]{1,}[^= \']*%s[\'|\"]{1,}[^<>]*>";
	
	public static String allowedImageExtenstionCommaDelimeter(){
		return ALLOW_IMAGE_EXTENSIONS.replaceAll("\\|", ", ");
	}
	
	public static synchronized List<Attachment> filtering(String contents, List<Attachment> images){
		
		List<Attachment> temp = new ArrayList<Attachment>(images);
		List<Attachment> removed = new ArrayList<Attachment>();
			
		for(Attachment image : temp){
			
			if(image != null){
				if(!Pattern.compile(String.format(image.isSecure() ? IMAGE_TAG_SEARCH_PATTERN : IMAGE_TAG_SEARCH_PATTERN_PUBLIC, image.getSeq())).matcher(contents).find()){
					if(images.remove(image))removed.add(image);
				}
			}else{
				images.remove(image);
			}
			
		}
		
		return removed;
		
	}
	
	public static long getMaxUploadSize(Member member){
		
		return member.getGroup().isSupervise() ? FileUtils.ONE_MB * 100 : FileUtils.ONE_MB * 10;
		
	}
	

}
