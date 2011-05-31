package kr.lesaautomaton.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.lesaautomaton.exception.AttachmentFailureException;
import kr.lesaautomaton.exception.PostNotFoundException;
import kr.lesaautomaton.exception.RepliesFullException;
import kr.lesaautomaton.persistence.dao.AttachmentDAO;
import kr.lesaautomaton.persistence.dao.MessageDAO;
import kr.lesaautomaton.persistence.domain.Attachment;
import kr.lesaautomaton.persistence.domain.Board;
import kr.lesaautomaton.persistence.domain.Member;
import kr.lesaautomaton.persistence.domain.Message;
import kr.lesaautomaton.utils.AttachmentUtils;
import kr.lesaautomaton.utils.CommonUtils;
import kr.lesaautomaton.utils.ListChunk;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class MessageLB {
	
	private static MessageLB instance = null;
	
	private MessageLB(){
		
	}
	
	public static synchronized MessageLB getInstance(){
		if(instance == null)instance = new MessageLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}
	
	public ListChunk top(String boardId, Member member){
		return (new MessageDAO()).getMessages(boardId, null, null, null, true, !member.getGroup().isSupervise(), Integer.MAX_VALUE, 1);
	}
	
	public ListChunk find(String boardId, String column, String keyword, Member member, int pageSize, int page){
		
		String writer = null, title = null, contents = null;
		
		if(StringUtils.isNotBlank(keyword)){
			if(StringUtils.equals(column, "writer"))
				writer = ("%"+ keyword +"%");
			else if(StringUtils.equals(column, "title"))
				title = ("%"+ keyword +"%");
			else if(StringUtils.equals(column, "contents"))
				contents = keyword;			
		}
		
		return (new MessageDAO()).getMessages(boardId, writer, title, contents, false, !member.getGroup().isSupervise(), pageSize, page);
		
	}
	
	public void at(Message current, List<Attachment> images, List<Attachment> files, Member member) throws Exception{
		
		Message temp = null;
		MessageDAO messageDAO = new MessageDAO();
		AttachmentDAO attachmentDAO = null;
		
		if(member.getGroup().isSupervise()){
			temp = messageDAO.getMessage(current.getBoardId(), current.getSeq());
		}else{
			temp = messageDAO.getMessage(current.getBoardId(), current.getSeq(), member.getSeq(), true);
		}
		
		if(temp != null){
			
			PropertyUtils.copyProperties(current, temp);
			
			messageDAO.beginBatch();
			
			attachmentDAO = new AttachmentDAO(messageDAO.getSqlMap());
			if(current.getAttachedImageCount() > 0)images.addAll(attachmentDAO.getAttachments("messages", current.getSeq(), Attachment.UPLOAD_TYPE_IMAGE));
			if(current.getAttachedFileCount() > 0)files.addAll(attachmentDAO.getAttachments("messages", current.getSeq(), Attachment.UPLOAD_TYPE_FILE));
			
			messageDAO.endBatch();
			
		}else{
			
			throw new PostNotFoundException();
			
		}
		
	}
	
	public void at(Message current, boolean hits, Message previous, Message next, List<Attachment> images, List<Attachment> files, String column, String keyword, Member member) throws Exception{
		
		Message temp = null;
		MessageDAO messageDAO = new MessageDAO();
		AttachmentDAO attachmentDAO = null;
		
		String writer = null, title = null, contents = null;
		
		if(StringUtils.isNotBlank(keyword)){
			if(StringUtils.equals(column, "writer"))
				writer = ("%"+ keyword +"%");
			else if(StringUtils.equals(column, "title"))
				title = ("%"+ keyword +"%");
			else if(StringUtils.equals(column, "contents"))
				contents = ("%"+ keyword +"%");			
		}		
		
		temp = messageDAO.getMessage(current.getBoardId(), current.getSeq(), !member.getGroup().isSupervise());

		if(temp != null){
			
			PropertyUtils.copyProperties(current, temp);
			
			if(hits && (!member.getGroup().isSupervise() && member.getSeq() != current.getWriterSeq()) && messageDAO.increaseHits(current) > 0)current.setHits(current.getHits() + 1);
			
			messageDAO.beginBatch();
			
			temp = messageDAO.getMessage(true, false, current.getBoardId(), current.getSeq(), writer, title, contents, !member.getGroup().isSupervise());
			if(temp != null)PropertyUtils.copyProperties(previous, temp);
			temp = messageDAO.getMessage(false, true, current.getBoardId(), current.getSeq(), writer, title, contents, !member.getGroup().isSupervise());
			if(temp != null)PropertyUtils.copyProperties(next, temp);
			
			attachmentDAO = new AttachmentDAO(messageDAO.getSqlMap());
			if(current.getAttachedImageCount() > 0)images.addAll(attachmentDAO.getAttachments("messages", current.getSeq(), Attachment.UPLOAD_TYPE_IMAGE));
			if(current.getAttachedFileCount() > 0)files.addAll(attachmentDAO.getAttachments("messages", current.getSeq(), Attachment.UPLOAD_TYPE_FILE));
			
			messageDAO.endBatch();
		
		}else{
			
			throw new PostNotFoundException();
			
		}
		
	}
	
	public boolean save(Board board, Message message, Message original, List<Attachment> images, List<Attachment> files, Member member, HttpServletRequest request) throws Exception{
		
		Date now = new Date();
		Boolean retVal = false;
		int attachedImageCount = 0, attachedFileCount = 0;
		AttachmentDAO attachmentDAO = null;
		MessageDAO messageDAO = null;
		Map<String, Object> params = new HashMap<String, Object>();
		
//		message.setBoardId(board.getId());
//		message.setIp(request.getRemoteAddr());		
//		message.setContents(CommonUtils.stripTags(message.getContentsWithMarkup()));
//		message.setUpdatedAt(now);
		
		params.put("title", message.getTitle());
		params.put("contentsWithMarkup", message.getContentsWithMarkup());
		params.put("contents", CommonUtils.stripTags(message.getContentsWithMarkup()));
		
		params.put("boardId", board.getId());
		params.put("ip", request.getRemoteAddr());
		params.put("updatedAt", now);
		
		if(!member.getGroup().isSupervise()){
			params.put("writer", member.getName());
		}else{
			params.put("writer", message.getWriter());
			if(message.isDisable())params.put("disabledAt", now);
		}
				
		if(CollectionUtils.isNotEmpty(files))attachedFileCount = files.size();
		params.put("attachedFileCount", attachedFileCount);
		//message.setAttachedFileCount(attachedFileCount);
		
		messageDAO = new MessageDAO();
		
		try{
			
			messageDAO.beginTran();		
			
			if(message.getSeq() == 0){
					
				if(original != null && original.getSeq() > 0){
					
					original = messageDAO.getMessage(board.getId(), original.getSeq(), !member.getGroup().isSupervise());
					
					if(original != null){
					
						if(messageDAO.countReplyMessages(board.getId(), original.getBranch()) < 100){
						
						params.put("branch", original.getBranch());
						params.put("thread", original.getThread() - 1);
						params.put("depth", original.getDepth() + 1);
						
						messageDAO.decreaseMessageThread(board.getId(), original.getBranch(), original.getThread());
						
						}else{
							
							throw new RepliesFullException();
							
						}
					
					}else{
						
						throw new PostNotFoundException();
						
					}
					
				}
				
				params.put("writerSeq", member.getSeq());
				params.put("createdAt", now);
	//			message.setDepth((short)0);
	//			message.setHits(0);
	//			message.setCommentsCount(0);
				
				if(CollectionUtils.isNotEmpty(images)){
					AttachmentUtils.filtering(message.getContentsWithMarkup(), images);
					attachedImageCount = images.size();
				}
				
				params.put("onTop", board.isNoticable(member.getGroupCode()) && message.isOnTop());
				//message.setAttachedImageCount(attachedImageCount);
				//message.setCreatedAt(now);
				
				params.put("attachedImageCount", attachedImageCount);
				
				message.setSeq(messageDAO.insertMessage(params));
				
				retVal = message.getSeq() > 0;
				
				if(retVal){
					
					try{
					
						attachmentDAO = new AttachmentDAO(messageDAO.getSqlMap());
					
						attachmentDAO.beginBatch();
						
						if(attachedImageCount > 0){
						
							for(Attachment image : images){
								image.setAttachableType("messages");
								image.setAttachableSeq(message.getSeq());
								attachmentDAO.updateAttachable(image);
							}
						
						}
						
						if(attachedFileCount > 0){
						
							for(Attachment file : files){
								file.setAttachableType("messages");
								file.setAttachableSeq(message.getSeq());
								attachmentDAO.updateAttachable(file);
							}				
						
						}
						
						attachmentDAO.endBatch();
					
					}catch(RuntimeException e){
						
						e.printStackTrace();
						throw new AttachmentFailureException();
						
					}
					
				}			
				
			}else{		
				
				params.put("seq", message.getSeq());
				
				if(board.isNoticable(member.getGroupCode())){
					params.put("onTop", message.isOnTop());
				}						
				
				if(!member.getGroup().isSupervise()){
					//message.setWriterSeq(member.getSeq());
					params.put("writerSeq", member.getSeq());
				}
				
				List<Attachment> filtered = null;
				if(CollectionUtils.isNotEmpty(images)){
					filtered = AttachmentUtils.filtering(message.getContentsWithMarkup(), images);
					attachedImageCount = images.size();
				}
				
				//message.setAttachedImageCount(attachedImageCount);
				params.put("attachedImageCount", attachedImageCount);
				
				retVal = messageDAO.updateMessage(params) > 0;
				
				if(retVal){
				
					if(CollectionUtils.isNotEmpty(filtered)){
						
						try{
							
							attachmentDAO = new AttachmentDAO(messageDAO.getSqlMap());
						
							attachmentDAO.beginBatch();
							
							for (Attachment image : filtered) {
								image.setAttachableType("messages");
								image.setAttachableSeq(message.getSeq());
								attachmentDAO.deleteAttachable(PropertyUtils.describe(image));
							}
							
							attachmentDAO.endBatch();
						
						}catch(RuntimeException e){
							
							e.printStackTrace();
							throw new AttachmentFailureException();
							
						}
						
					}
				
				}else{
					
					throw new PostNotFoundException();
					
				}
				
			}
			
			messageDAO.commitTran();		
			
			BeanUtils.populate(message, params);
			
			return retVal;
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
			
		}finally{
			
			messageDAO.endTran();
			
		}		
		
	}
	
	public void destroy(Message message, Member member) throws Exception{
		
		MessageDAO messageDAO = new MessageDAO();
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("seq", message.getSeq());
		params.put("boardId", message.getBoardId());
		
		if(!member.getGroup().isSupervise())params.put("writerSeq", member.getSeq());
		
		try{
		
			messageDAO.beginTran();
			
			message = messageDAO.getMessage(params);
			
			if(message != null){
				
				messageDAO.deleteMessage(params);
				messageDAO.increaseMessageThread(message.getBoardId(), message.getBranch(), message.getThread());
				(new AttachmentDAO(messageDAO.getSqlMap())).deleteAttachable("messages", message.getSeq());
				
				messageDAO.commitTran();
				
			}else{
				
				throw new PostNotFoundException();
				
			}
		
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
			
		}finally{
			
			messageDAO.endTran();
			
		}
		
	}
	

}
