package kr.lesaautomaton.persistence.domain;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Member {
	
	public static final String MEMBER_TYPE_PRIVATE = "privates";
	public static final String MEMBER_TYPE_PUBLISHER = "publishers";
	public static final String MEMBER_TYPE_ORGANIZATION = "organizations";
	
	private int seq;
	private String groupCode;
	private Group group;
	
	private int privateSeq;
	private int publisherSeq;
	private int organizationSeq;
	
	private String memberId;
	private String pass;	
	private String encryptedPass;
	private String name;
	private String type;
	private String emailId;
	private String emailHost;
	private String tel1 = "";
	private String tel2 = "";
	private String tel3 = "";
	private String hp1 = "";
	private String hp2 = "";
	private String hp3 = "";
	private String fax1 = "";
	private String fax2 = "";
	private String fax3 = "";
	private String zipcodePrimary = "";
	private String zipcodeSecondary = "";
	private String address = "";
	private String addressDetail = "";
	private boolean usable = true;
	private Date createdAt;
	private Date updatedAt;
	
	//Private member's
	private String ssnPrimary;
	private String ssnSecondary;
	private String encryptedSsnSecondary;	
	
	//Publisher member's
	private String registrationNo;
	private String publisherName = "";	
	private Date publisherRegdate;
	
	//Organization member's
	private String businessLicenseNo;
	private String representative = "";
	
	//Publisher & Organization member's
	private String personInCharge = "";
	
	public boolean isPrivate(){
		return StringUtils.equals(type, MEMBER_TYPE_PRIVATE);
	}
	
	public boolean isPublisher(){
		return StringUtils.equals(type, MEMBER_TYPE_PUBLISHER);
	}
	
	public boolean isOrganization(){
		return StringUtils.equals(type, MEMBER_TYPE_ORGANIZATION);
	}		
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEncryptedPass() {
		return encryptedPass;
	}
	public void setEncryptedPass(String encryptedPass) {
		this.encryptedPass = encryptedPass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail(){
		
		String email = "";
		if(!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(emailHost))email = emailId +"@"+ emailHost;
		
		return email;
		
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = StringUtils.trimToEmpty(emailId);
	}
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = StringUtils.trimToEmpty(emailHost);
	}
	public String getTel(){
		
		String[] tel = new String[]{};
		
		if(!StringUtils.isEmpty(tel1))tel = ArrayUtils.add(tel, tel1);
		if(!StringUtils.isEmpty(tel2))tel = ArrayUtils.add(tel, tel2);
		if(!StringUtils.isEmpty(tel3))tel = ArrayUtils.add(tel, tel3);
		
		return StringUtils.join(tel, "-");
		
	}	
	public String getTel1() {
		return tel1;
	}	
	public void setTel1(String tel1) {
		this.tel1 = StringUtils.trimToEmpty(tel1);
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = StringUtils.trimToEmpty(tel2);
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = StringUtils.trimToEmpty(tel3);
	}
	public String getHp(){
		
		String[] hp = new String[]{};
		
		if(!StringUtils.isEmpty(hp1))hp = ArrayUtils.add(hp, hp1);
		if(!StringUtils.isEmpty(hp2))hp = ArrayUtils.add(hp, hp2);
		if(!StringUtils.isEmpty(hp3))hp = ArrayUtils.add(hp, hp3);
		
		return StringUtils.join(hp, "-");
		
	}	
	public String getHp1() {
		return hp1;
	}
	public void setHp1(String hp1) {
		this.hp1 = StringUtils.trimToEmpty(hp1);
	}
	public String getHp2() {
		return hp2;
	}
	public void setHp2(String hp2) {
		this.hp2 = StringUtils.trimToEmpty(hp2);
	}
	public String getHp3() {
		return hp3;
	}
	public void setHp3(String hp3) {
		this.hp3 = StringUtils.trimToEmpty(hp3);
	}
	public String getFax(){
		
		String[] fax = new String[]{};
		
		if(!StringUtils.isEmpty(fax1))fax = ArrayUtils.add(fax, fax1);
		if(!StringUtils.isEmpty(fax2))fax = ArrayUtils.add(fax, fax2);
		if(!StringUtils.isEmpty(fax3))fax = ArrayUtils.add(fax, fax3);
		
		return StringUtils.join(fax, "-");
		
	}		
	public String getFax1() {
		return fax1;
	}
	public void setFax1(String fax1) {
		this.fax1 = StringUtils.trimToEmpty(fax1);
	}
	public String getFax2() {
		return fax2;
	}
	public void setFax2(String fax2) {
		this.fax2 = StringUtils.trimToEmpty(fax2);
	}
	public String getFax3() {
		return fax3;
	}
	public void setFax3(String fax3) {
		this.fax3 = StringUtils.trimToEmpty(fax3);
	}
	public String getZipcodePrimary() {
		return zipcodePrimary;
	}	
	public String getZipcode() {
		
		String[] zipcode = new String[]{};
		
		if(!StringUtils.isEmpty(zipcodePrimary))zipcode = ArrayUtils.add(zipcode, zipcodePrimary);
		if(!StringUtils.isEmpty(zipcodeSecondary))zipcode = ArrayUtils.add(zipcode, zipcodeSecondary);
		
		return StringUtils.join(zipcode, "-");
		
	}
	public void setZipcodePrimary(String zipcodePrimary) {
		this.zipcodePrimary = StringUtils.trimToEmpty(zipcodePrimary);
	}
	public String getZipcodeSecondary() {
		return zipcodeSecondary;
	}
	public void setZipcodeSecondary(String zipcodeSecondary) {
		this.zipcodeSecondary = StringUtils.trimToEmpty(zipcodeSecondary);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = StringUtils.trimToEmpty(address);
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = StringUtils.trimToEmpty(addressDetail);
	}
	public boolean isUsable() {
		return usable;
	}
	public void setUsable(boolean usable) {
		this.usable = usable;
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
	public String getSsn(){
		
		String ssn = "";
		if(!StringUtils.isEmpty(ssnPrimary) && !StringUtils.isEmpty(ssnSecondary))ssn = ssnPrimary +"-"+ ssnSecondary;
		
		return ssn;
		
	}	
	public String getSsnPrimary() {
		return ssnPrimary;
	}
	public void setSsnPrimary(String ssnPrimary) {
		this.ssnPrimary = ssnPrimary;
	}
	public String getSsnSecondary() {
		return ssnSecondary;
	}
	public void setSsnSecondary(String ssnSecondary) {
		this.ssnSecondary = ssnSecondary;
	}
	public String getEncryptedSsnSecondary() {
		return encryptedSsnSecondary;
	}

	public void setEncryptedSsnSecondary(String encryptedSsnSecondary) {
		this.encryptedSsnSecondary = encryptedSsnSecondary;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}
	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getPersonInCharge() {
		return personInCharge;
	}
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public int getPrivateSeq() {
		return privateSeq;
	}

	public void setPrivateSeq(int privateSeq) {
		this.privateSeq = privateSeq;
	}

	public int getPublisherSeq() {
		return publisherSeq;
	}

	public void setPublisherSeq(int publisherSeq) {
		this.publisherSeq = publisherSeq;
	}

	public int getOrganizationSeq() {
		return organizationSeq;
	}

	public void setOrganizationSeq(int organizationSeq) {
		this.organizationSeq = organizationSeq;
	}

	public Date getPublisherRegdate() {
		return publisherRegdate;
	}

	public void setPublisherRegdate(Date publisherRegdate) {
		this.publisherRegdate = publisherRegdate;
	}	
	
}
