
package kr.lesaautomaton.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import kr.lesaautomaton.exception.DuplicateMemberIdException;
import kr.lesaautomaton.exception.LoginIncorrectException;
import kr.lesaautomaton.exception.NoSuchMemberException;
import kr.lesaautomaton.persistence.dao.MemberDAO;
import kr.lesaautomaton.persistence.domain.Member;
import kr.lesaautomaton.utils.EncrytionUtils;
import kr.lesaautomaton.utils.ListChunk;
import kr.lesaautomaton.utils.Mailer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MemberLB {
	
	private static MemberLB instance = null;
	
	private MemberLB(){
		
	}
	
	public static synchronized MemberLB getInstance(){
		if(instance == null)instance = new MemberLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}
	
	public Member at(int memberSeq){
		
		return (new MemberDAO()).getMember(memberSeq);
		
	}
	
	public void loadDetail(Member member){
		
		MemberDAO memberDAO = new MemberDAO();
		Member detail = null;
		
		if(member.isPrivate()){
			
			detail = memberDAO.getPrivate(member.getSeq());
			
			member.setSsnPrimary(detail.getSsnPrimary());
			
		}else if(member.isPublisher()){
			
			detail = memberDAO.getPublisher(member.getSeq());
			
			member.setRegistrationNo(detail.getRegistrationNo());
			member.setPublisherName(detail.getPublisherName());
			member.setPersonInCharge(detail.getPersonInCharge());
			member.setBusinessLicenseNo(detail.getBusinessLicenseNo());
			member.setSsnPrimary(detail.getSsnPrimary());
			member.setPublisherRegdate(detail.getPublisherRegdate());
			
		}else if(member.isOrganization()){
			
			detail = memberDAO.getOrganization(member.getSeq());
			
			member.setBusinessLicenseNo(detail.getBusinessLicenseNo());
			member.setRepresentative(detail.getRepresentative());
			member.setPersonInCharge(detail.getPersonInCharge());		
			
		}else{
			
			throw new RuntimeException("Invalid member type.");
			
		}
		
	}	
	
	public ListChunk find(String column, String keyword, String[] type, int pageSize, int page){
		
		String name = null, memberId = null;
		
		if(StringUtils.isNotBlank(keyword)){
			if(StringUtils.equals(column, "name"))
				name = ("%"+ keyword +"%");
			else if(StringUtils.equals(column, "memberId"))
				memberId = ("%"+ keyword +"%");
		}
		
		return (new MemberDAO()).getMembers(name, memberId, type, pageSize, page);
		
	}

	public Member login(String memberId, String pass) throws Exception{
		
		Member member = (new MemberDAO()).getMember(memberId, EncrytionUtils.password(pass), true);
		
		if(member != null){
			return member;
		}else{
			throw new LoginIncorrectException();
		}
	}
	
	public boolean isDuplicatedMember(Member member){
		return (new MemberDAO()).existsMemberId(member.getMemberId()) > 0;
	}
	
	public boolean isDuplicatedPrivate(Member member) throws Exception{
		return (new MemberDAO()).existsSsn(member.getSeq(), member.getSsnPrimary(), EncrytionUtils.password(member.getSsnSecondary())) > 0;
	}
	
	public boolean isDuplicatedPublisher(Member member){
		return (new MemberDAO()).existsBusinessLicenseNo(member.getSeq(), member.getBusinessLicenseNo()) > 0;
	}
	
	public boolean isDuplicatedOrganization(Member member) {
		member.setName(StringUtils.deleteWhitespace(member.getName()));
		return (new MemberDAO()).existsName(member.getSeq(), member.getName()) > 0;
	}
	
	public boolean save(Member member) throws Exception{
		
		boolean result = false;
		MemberDAO memberDAO = new MemberDAO();
		
		try{
			
			memberDAO.beginTran();
			
			if(member.getSeq() == 0){
				
				if(memberDAO.existsMemberId(member.getMemberId()) == 0){
					
					member.setEncryptedPass(EncrytionUtils.password(member.getPass()));
					memberDAO.insertMember(member);
					
				}else{
					throw new DuplicateMemberIdException();
				}
				
			}else{
				
				if(StringUtils.isNotBlank(member.getPass()))member.setEncryptedPass(EncrytionUtils.password(member.getPass()));
				
				if(memberDAO.updateMember(member) > 0){
					
					if(!member.isPrivate()){
						
						if(member.isPublisher()){
							if(StringUtils.isNotEmpty(member.getSsn()))member.setEncryptedSsnSecondary(EncrytionUtils.password(member.getSsnSecondary()));
							result = memberDAO.updatePublisher(member) > 0;
						}else if (member.isOrganization()) {
							result = memberDAO.updateOrganization(member) > 0;
						}else{
							throw new RuntimeException("Invalid member type.");
						}
						
						if(!result)throw new RuntimeException("No member's type data.");
						
					}
					
				}else{
					member = null;
				}
				
			}
			
			result = true;
			
			memberDAO.commitTran();
			
		}catch(DuplicateMemberIdException e){
			
			throw e;		
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new Exception(e);
			
		}finally{
			
			memberDAO.endTran();
			
		}
		
		return result;
		
	}	
	
	public void findMemberId(Member member) throws Exception {
		
		MemberDAO memberDAO = new MemberDAO();
		Member findResult = null;
		
		if(member.isPrivate()){
			member.setEncryptedSsnSecondary(EncrytionUtils.password(member.getSsnSecondary()));
		}else if(!member.isPublisher() && !member.isOrganization()){
			throw new RuntimeException("Invalid member type.");
		}
		
		findResult = memberDAO.findMember(member);
		
		if(findResult != null){
			
			member.setMemberId(findResult.getMemberId());
			
		}else{
			
			throw new NoSuchMemberException();
			
		}
		
	}
	
	public void findPass(Member member) throws Exception {
		
		Member findResult = null;
		MemberDAO memberDAO = new MemberDAO();
		
		if(member.isPrivate()){
			member.setEncryptedSsnSecondary(EncrytionUtils.password(member.getSsnSecondary()));	
		}else if(!member.isPublisher() && !member.isOrganization()){
			throw new RuntimeException("Invalid member type.");
		}
		
		try{
			
			memberDAO.beginTran();
			
			findResult = memberDAO.findMember(member);
			
			if(findResult != null){
				
				member.setSeq(findResult.getSeq());
				member.setMemberId(findResult.getMemberId());				
				member.setEmailId(findResult.getEmailId());
				member.setEmailHost(findResult.getEmailHost());
				member.setPass(RandomStringUtils.randomAlphanumeric(6));
				member.setEncryptedPass(EncrytionUtils.password(member.getPass()));
				
				memberDAO.updatePass(member);
				
			}else{
				
				throw new NoSuchMemberException();
				
			}
			
			memberDAO.commitTran();
			
		}catch(NoSuchMemberException e){
			
			throw e;
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
			
		}finally{
			memberDAO.endTran();
		}
			
		Mailer mailer = new Mailer();
		
		ActionSupport action = ((ActionSupport)ActionContext.getContext().getActionInvocation().getAction());
		
		mailer.setMailFrom(action.getText("webmaster.email"));
		mailer.setRcptTO(String.format("%s <%s>", member.getName(), member.getEmail()));
		mailer.setSubject(action.getText("email.title.password.information"));
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(MemberLB.class.getResourceAsStream("/kr/lesaautomaton/template/find_password.htm"), "UTF-8"));
		
		StringBuffer text = new StringBuffer();
		String line = null, contents = null;
		
		while ((line = reader.readLine()) != null) {
			text.append(line);
		}
		
		URL requestUrl = new URL(ServletActionContext.getRequest().getRequestURL().toString());
		
		contents = text.toString();
		contents = StringUtils.replace(contents, "{{host}}", requestUrl.toString().replace(requestUrl.getProtocol()+"://", "").replace(requestUrl.getPath(), ""));
		contents = StringUtils.replace(contents, "{{name}}", member.getName());
		contents = StringUtils.replace(contents, "{{password}}", member.getPass());
		
		mailer.setContents(contents);
		
		new Thread(mailer).run();
		
	}
	
	public boolean destroy(Member member){
		
		return (new MemberDAO()).deleteMember(member.getSeq()) > 0;
		
	}

}
