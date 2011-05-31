package kr.lesaautomaton.presentation;

import java.util.ArrayList;
import java.util.List;

import kr.lesaautomaton.exception.AttachmentFailureException;
import kr.lesaautomaton.exception.RepliesFullException;
import kr.lesaautomaton.persistence.domain.Attachment;
import kr.lesaautomaton.persistence.domain.Board;
import kr.lesaautomaton.persistence.domain.Message;
import kr.lesaautomaton.service.AttachmentLB;
import kr.lesaautomaton.service.MessageLB;
import kr.lesaautomaton.utils.ListChunk;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

public class CommunityAction extends BaseAction implements Preparable{
	
	private static final Logger LOG = Logger.getLogger(CommunityAction.class);
	
//	private String boardId;
	private Board board;
	private Message message;
	private Message previous;
	private Message next;
	private Message original;
	private String column;
	private String keyword;
	private ListChunk tops;
	private ListChunk messages;
	private int pageSize = 10;
	private int page = 1;
	private List<Attachment> images;
	private List<Attachment> files;
	private Attachment attachment;
	
	public String index(){
		
		MessageLB messageLB = MessageLB.getInstance();
		
		tops = messageLB.top(board.getId(), account);
		messages = messageLB.find(board.getId(), column, keyword, account, pageSize, page);
		
		return SUCCESS;
		
	}
	
	public String show() throws Exception{
		
		previous = new Message();
		next = new Message();
		
		message.setBoardId(board.getId());
			
		MessageLB.getInstance().at(message, true, previous, next, images, files, column, keyword, account);
		
		return SUCCESS;
		
	}
	
	public String write(){

		
		message = new Message();
		
		if(original != null && original.getSeq() > 0){
			//답글 권한 확인
			if(!board.isRepliable(account.getGroup().getCode())){
				addActionError(getText("permission.denied"));
				return PERMISSION_ERROR;
			}
			message.setTitle("Re : "+ original.getTitle());
			message.setContentsWithMarkup(original.getContentsWithMarkup());
		}
		
		message.setWriter(account.getName());		
		
		return SUCCESS;
		
	}

	
	public String save() throws Exception{		
		
		AttachmentLB attachmentLB = null;
		
		if(!hasErrors()){
	
			try{
			
				if(MessageLB.getInstance().save(board, message, original, images, files, account, ServletActionContext.getRequest())){
					return SUCCESS;
				}	
				
			}catch (RepliesFullException e) {
				// TODO: handle exception
				addActionError(getText("replies.full.exception"));
				return INPUT;
			
			}catch(AttachmentFailureException e){
				
				addActionError(getText("attachment.failure.exception"));
				return SUCCESS;
				
			}
			
			return ERROR;
		
		}else{
			
			attachmentLB = AttachmentLB.getInstance();
			
			images = attachmentLB.find(images);			
			files = attachmentLB.find(files);
			
			attachment.setAttachableSeq(message.getSeq());
			
			return INPUT;
			
		}
		
	}
	
	public String modify() throws Exception{
		
		message.setBoardId(board.getId());
		
		MessageLB.getInstance().at(message, images, files, account);
		
		attachment.setAttachableSeq(message.getSeq());
		
		return SUCCESS;		
		
	}	
	
	public String destroy() throws Exception{
		
		message.setBoardId(board.getId());
		MessageLB.getInstance().destroy(message, account);
		
		page = 1;
		
		return SUCCESS;

	}
	
	
//	@Override
//	public void validate() {
//		// TODO Auto-generated method stub
//		System.out.println("-0-----------------------------------------");
//		System.out.println(hasErrors());
//	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		images = new ArrayList<Attachment>();
		files = new ArrayList<Attachment>();
		
		attachment = new Attachment();
		attachment.setSecure(true);
		attachment.setAttachableType("messages");
	
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {		
		this.message = message;
	}

	public Message getPrevious() {
		return previous;
	}

	public void setPrevious(Message previous) {
		this.previous = previous;
	}

	public Message getNext() {
		return next;
	}

	public void setNext(Message next) {
		this.next = next;
	}

	public ListChunk getTops() {
		return tops;
	}

	public void setTops(ListChunk tops) {
		this.tops = tops;
	}

	public String getColumn() {
		return column;
	}	

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public List<Attachment> getImages() {
		return images;
	}

	public void setImages(List<Attachment> images) {
		this.images = images;
	}

	public List<Attachment> getFiles() {
		return files;
	}

	public void setFiles(List<Attachment> files) {
		this.files = files;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ListChunk getMessages() {
		return messages;
	}

	public void setMessages(ListChunk messages) {
		this.messages = messages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}		

//	public String getBoardId() {
//		return boardId;
//	}
//
//	public void setBoardId(String boardId) {
//		this.boardId = boardId;
//	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Message getOriginal() {
		return original;
	}

	public void setOriginal(Message original) {
		this.original = original;
	}
	
}
