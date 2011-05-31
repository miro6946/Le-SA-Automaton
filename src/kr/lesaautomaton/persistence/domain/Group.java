package kr.lesaautomaton.persistence.domain;

import java.util.Date;

public class Group {
	
	public static final String GUEST_CODE = "00000";
	public static final String MEMBER_CODE = "00001";
	public static final String SUPERVISE_CODE = "99999";
	
	private String code;
	private String name;
	private String description;
	private boolean supervise;
	private Date disabledAt;
	private Date createdAt;
	private Date updatedAt;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isGuest(){
		return code == GUEST_CODE;
	}
	public boolean isSupervise() {
		return supervise;
	}
	public void setSupervise(boolean supervise) {
		this.supervise = supervise;
	}
	public boolean isDisable() {
		return disabledAt != null;
	}
	public void setDisable(boolean disable) {
		disabledAt = new Date();
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

}
