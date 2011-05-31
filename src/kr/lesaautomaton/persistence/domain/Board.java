package kr.lesaautomaton.persistence.domain;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Board {
	
	public static final String GROUP_CODE_SEPARATOR = "|";

	private String id;
	private String name;
	private String layout;
	private String description;
	private Date disabledAt;
	private Date createdAt;
	private Date updatedAt;
	private String[] accessable;
	private String[] writable;
	private String[] readable;
	private String[] repliable;
	private String[] commentable;
	private String[] fileAttachable;
	private String[] imgAttachable;
	private String[] noticable;
	private String[] secretable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDisabledAt() {
		return disabledAt;
	}
	public void setDisabledAt(Date disabledAt) {
		this.disabledAt = disabledAt;
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
	
	public boolean isAccessable(String groupCode){
		return ArrayUtils.contains(accessable, groupCode);
	}
	public String getAccessableString() {
		return StringUtils.join(accessable, GROUP_CODE_SEPARATOR);
	}
	public String[] getAccessable(){
		return accessable;
	}
	public void setAccessable(String accessable) {
		this.accessable = StringUtils.split(accessable, GROUP_CODE_SEPARATOR);
	}
	
	public boolean isWritable(String groupCode){
		return ArrayUtils.contains(writable, groupCode);
	}
	public String getWritableString() {
		return StringUtils.join(writable, GROUP_CODE_SEPARATOR);
	}
	public String[] getWritable(){
		return writable;
	}
	public void setWritable(String writable) {
		this.writable = StringUtils.split(writable, GROUP_CODE_SEPARATOR);
	}
	
	public boolean isReadable(String groupCode){
		return ArrayUtils.contains(readable, groupCode);
	}
	public String getReadableString() {
		return StringUtils.join(readable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getReadable(){
		return readable;
	}
	public void setReadable(String readable) {
		this.readable = StringUtils.split(readable, GROUP_CODE_SEPARATOR);
	}
		
	public boolean isRepliable(String groupCode){
		return ArrayUtils.contains(repliable, groupCode);
	}
	public String getRepliableString() {
		return StringUtils.join(repliable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getRepliable(){
		return repliable;
	}
	public void setRepliable(String repliable) {
		this.repliable = StringUtils.split(repliable, GROUP_CODE_SEPARATOR);
	}
	
	public boolean isCommentable(String groupCode){
		return ArrayUtils.contains(commentable, groupCode);
	}
	public String getCommentableString() {
		return StringUtils.join(commentable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getCommentable(){
		return commentable;
	}
	public void setCommentable(String commentable) {
		this.commentable = StringUtils.split(commentable, GROUP_CODE_SEPARATOR);
	}	
	
	public boolean isFileAttachable(String groupCode){
		return ArrayUtils.contains(fileAttachable, groupCode);
	}
	public String getFileAttachableString() {
		return StringUtils.join(fileAttachable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getFileAttachable(){
		return fileAttachable;
	}
	public void setFileAttachable(String fileAttachable) {
		this.fileAttachable = StringUtils.split(fileAttachable, GROUP_CODE_SEPARATOR);
	}	
	
	public boolean isImgAttachable(String groupCode){
		return ArrayUtils.contains(imgAttachable, groupCode);
	}
	public String getImgAttachableString() {
		return StringUtils.join(imgAttachable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getImgAttachable(){
		return imgAttachable;
	}
	public void setImgAttachable(String imgAttachable) {
		this.imgAttachable = StringUtils.split(imgAttachable, GROUP_CODE_SEPARATOR);
	}	
	
	public boolean isNoticable(String groupCode){
		return ArrayUtils.contains(noticable, groupCode);
	}
	public String getNoticableString() {
		return StringUtils.join(noticable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getNoticable(){
		return noticable;
	}
	public void setNoticable(String noticable) {
		this.noticable = StringUtils.split(noticable, GROUP_CODE_SEPARATOR);
	}	
	
	public boolean isSecretable(String groupCode){
		return ArrayUtils.contains(secretable, groupCode);
	}
	public String getSecretableString() {
		return StringUtils.join(secretable, GROUP_CODE_SEPARATOR) + GROUP_CODE_SEPARATOR;
	}
	public String[] getSecretable(){
		return secretable;
	}
	public void setSecretable(String secretable) {
		this.secretable = StringUtils.split(secretable, GROUP_CODE_SEPARATOR);
	}	
	
}
