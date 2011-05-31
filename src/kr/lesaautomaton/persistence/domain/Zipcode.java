package kr.lesaautomaton.persistence.domain;

import org.apache.commons.lang3.StringUtils;

public class Zipcode {
	
	private String zipcodePrimary;
	private String zipcodeSecondary;
	private String sido;
	private String gugun;
	private String dong;
	private String etc;
	private String address;
	
	public String getZipcode(){
		String zipcode = "";
		if(!StringUtils.isEmpty(zipcodePrimary) && !StringUtils.isEmpty(zipcodeSecondary))zipcode = zipcodePrimary +"-"+ zipcodeSecondary;
		return zipcode;
	}
	public String getZipcodePrimary() {
		return zipcodePrimary;
	}
	public void setZipcodePrimary(String zipcodePrimary) {
		this.zipcodePrimary = zipcodePrimary;
	}
	public String getZipcodeSecondary() {
		return zipcodeSecondary;
	}
	public void setZipcodeSecondary(String zipcodeSecondary) {
		this.zipcodeSecondary = zipcodeSecondary;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}	

}
