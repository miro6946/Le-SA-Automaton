package kr.lesaautomaton.persistence.domain;

import java.util.Date;

/**
 * @author argentum
 * 
 */
public class Message {

	private int seq;
	private String boardId;
	private int branch;
	private int thread;
	private short depth;
	private String title;
	private String writer;
	private int writerSeq;
	private String ip;
	private String contents;
	private String contentsWithMarkup;
	private int hits;
	private int commentsCount;
	private boolean onTop;
	private boolean disable;
	private int attachedImageCount;
	private int attachedFileCount;
	private Date disabledAt;
	private Date createdAt;
	private Date updatedAt;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		thread = seq * 100;
		this.seq = seq;
	}	
	
	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public short getDepth() {
		return depth;
	}

	public void setDepth(short depth) {
		this.depth = depth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getWriterSeq() {
		return writerSeq;
	}

	public void setWriterSeq(int writerSeq) {
		this.writerSeq = writerSeq;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getContentsWithMarkup() {
		return contentsWithMarkup;
	}

	public void setContentsWithMarkup(String contentsWithMarkup) {
		this.contentsWithMarkup = contentsWithMarkup;
	}	

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public boolean isOnTop() {
		return onTop;
	}

	public void setOnTop(boolean onTop) {
		this.onTop = onTop;
	}

	public Date getDisabledAt() {
		return disabledAt;
	}

	public void setDisabledAt(Date disabledAt) {
		disable = disabledAt != null;
		this.disabledAt = disabledAt;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}	

	public int getAttachedImageCount() {
		return attachedImageCount;
	}

	public void setAttachedImageCount(int attachedImageCount) {
		this.attachedImageCount = attachedImageCount;
	}

	public int getAttachedFileCount() {
		return attachedFileCount;
	}

	public void setAttachedFileCount(int attachedFileCount) {
		this.attachedFileCount = attachedFileCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}	

}
