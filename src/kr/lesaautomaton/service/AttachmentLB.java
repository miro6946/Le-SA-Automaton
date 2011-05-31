package kr.lesaautomaton.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import kr.lesaautomaton.persistence.dao.AttachmentDAO;
import kr.lesaautomaton.persistence.domain.Attachment;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

public class AttachmentLB {
	
	private static final Logger LOG = Logger.getLogger(AttachmentLB.class);
	
	private static AttachmentLB instance = null;
	
	private AttachmentLB(){
		
	}
	
	public static synchronized AttachmentLB getInstance(){
		if(instance == null)instance = new AttachmentLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}
	
	public Attachment at(int seq){
		
		return (new AttachmentDAO()).getAttachment(seq, false);
		
	}
	
	public List<Attachment> find(List<Attachment> attachments){
		
		int[] attachmentSeq = new int[]{};
		for (Attachment attachment : attachments)attachmentSeq = ArrayUtils.add(attachmentSeq, attachment.getSeq());
		
		if(attachmentSeq.length > 0)attachments = (new AttachmentDAO()).getAttachments(attachmentSeq);
		
		return attachments;
		
	}
	
	public List<Attachment> find(String attachableType, int attachableSeq, String uploadType){
		
		return (new AttachmentDAO()).getAttachments(attachableType, attachableSeq, uploadType);
		
	}
	
	public boolean save(Attachment attachment, ServletContext context) throws Exception{
		
		boolean result = false;
		
		Date now = new Date();
		attachment.setCreatedAt(now);
		attachment.setUpdatedAt(now);		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);		
		attachment.setFilePath(String.format("uploaded/%d/%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1));
		
		AttachmentDAO attachmentDAO = new AttachmentDAO();
		
		try{
			
			attachmentDAO.beginTran();
			
			attachmentDAO.insertAttachment(attachment);
			
			if(attachment.getSeq() > 0){
				
				FileUtils.copyFile(attachment.getFile(), new File(context.getRealPath("/") +(attachment.isSecure() ? "../" : "/")+ attachment.getFilePath() +"/"+ attachment.getSeq()));
			
//				if(attachment.isSecure())
//					FileUtils.copyFile(attachment.getFile(), new File(context.getRealPath("/") +"../"+ attachment.getFilePath() +"/"+ attachment.getSeq()));
//				else
//					FileUtils.copyFile(attachment.getFile(), new File(context.getRealPath("/") + attachment.getFilePath() +"/"+ URLEncoder.encode(attachment.getOriginalFileName(), "UTF-8")));
				
				attachmentDAO.commitTran();
				
				result = true;
				
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
			
		}finally{
			
			attachmentDAO.endTran();
			
		}
		
		return result;
		
	}
	
	public boolean destroy(Attachment attachment, String rootPath) throws Exception{
		
		boolean result = false;
		AttachmentDAO attachmentDAO = new AttachmentDAO();
		
		try {
			
			attachmentDAO.beginTran();
			
			attachment = attachmentDAO.getAttachment(PropertyUtils.describe(attachment));
			
			if(attachment != null && attachmentDAO.deleteAttachment(attachment) > 0){
				
				FileUtils.forceDelete(new File(rootPath +(attachment.isSecure() ? "../" : "/")+ attachment.getFilePath() +"/"+ attachment.getSeq()));
				
				attachmentDAO.commitTran();
				
				result = true;
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
			
		} finally{
		
			attachmentDAO.endTran();
			
		}
		
		return result;
		
	}

}
