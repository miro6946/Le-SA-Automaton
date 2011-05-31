package kr.lesaautomaton.persistence.domain;

import java.io.File;
import java.util.Date;

import kr.lesaautomaton.utils.CommonUtils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author argentum
 *
 */
public class Attachment {
	
	public static final String UPLOAD_TYPE_IMAGE = "i";
	public static final String UPLOAD_TYPE_FILE = "f";
	
	private int seq;
	private int attachableSeq;
	private String attachableType;
	private boolean secure;
	private String filePath;
	private String fileName;
	private String fileExtension;
	private long fileSize;
	private String capacity;
	private String uploadType;
	private File file;
	private Date createdAt;
	private Date updatedAt;
	private String originalFileName;
	
	public boolean isImage(){
		return StringUtils.equals(uploadType, UPLOAD_TYPE_IMAGE);
	}
	
	public boolean isFile(){
		return StringUtils.equals(uploadType, UPLOAD_TYPE_FILE);
	}	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getAttachableSeq() {
		return attachableSeq;
	}
	public void setAttachableSeq(int attachableSeq) {
		this.attachableSeq = attachableSeq;
	}
	public String getAttachableType() {
		return attachableType;
	}
	public void setAttachableType(String attachableType) {
		this.attachableType = attachableType;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
		this.capacity = CommonUtils.capacity(fileSize);
	}
	public String getCapacity(){
		return capacity;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
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
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
		this.fileSize = file.length();
	}
	public String getOriginalFileName() {
		if(StringUtils.isEmpty(originalFileName)){
			originalFileName = fileName;
			if(StringUtils.isNotEmpty(fileExtension))originalFileName += ("."+ fileExtension);
		}
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
		
		String[] temp = StringUtils.split(originalFileName, '.');
		if(temp.length > 0){
			fileExtension = temp[temp.length - 1];
			fileName = originalFileName.replaceAll("\\."+ fileExtension +"$", "");
		}		
	}		
	
}
