package kr.lesaautomaton.presentation;

import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.lesaautomaton.exception.DuplicateMemberIdException;
import kr.lesaautomaton.exception.LoginIncorrectException;
import kr.lesaautomaton.exception.NoSuchMemberException;
import kr.lesaautomaton.persistence.domain.Group;
import kr.lesaautomaton.persistence.domain.Member;
import kr.lesaautomaton.service.MemberLB;
import kr.lesaautomaton.utils.SessionUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author argentum
 *
 */
public class MembersAction extends BaseAction {
	
	public static final String AGREEMENT_REQUIRED = "agreement_required";
	public static final String SELECT_MEMBER_TYPE = "select_member_type";
	
	public static final String INVALID_MEMBER_ID = "sex|tkpf|fuck|fuckyou|fucku|asshole|admin|supervise|supervisor|administrator|test|bitch|shit|www|root|adm|sa|guest|publisher|organization|private|public";
	
	private static final Logger LOG = Logger.getLogger(MembersAction.class);
	
	private Member member;
	private String pass;
	private String newPass;
	private String type;
	private boolean termsOfUse = false;
	private String checkFieldName;
	private String findFieldName;
	private boolean changePass = false;
	private boolean changeSSN = false;
	
	public String agreement(){
		
		if(StringUtils.isBlank(type)){
			addActionError(getText("member.type.must.be.selected"));
			return SELECT_MEMBER_TYPE;
		}			
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("type", type);
		
		return AGREEMENT_REQUIRED;
	}
	
	public String input() throws Exception{
		
		String actionName = ActionContext.getContext().getName();
		
		if(StringUtils.equals(actionName, "new")){
		
			if(!termsOfUse){
				addActionError(getText("agree.terms.of.use"));
				return AGREEMENT_REQUIRED;
			}
			
			type = String.valueOf(ActionContext.getContext().getSession().get("type"));
			
			if(StringUtils.isBlank(type)){
				addActionError(getText("member.type.must.be.selected"));
				return SELECT_MEMBER_TYPE;
			}
			
			member = new Member();
			member.setType(type);
		
		}else if(StringUtils.equals(actionName, "edit")){
			
			MemberLB memberLB = MemberLB.getInstance();
			
			try{
			
				memberLB.login(account.getMemberId(), pass);
				memberLB.loadDetail(account);
				
				member = account;
				
				return INPUT;
				
			}catch (LoginIncorrectException e) {
				// TODO: handle exception
				
				addActionError(getText("validation.pass.incorrect"));
				return "wrong_password";
				
			}
			
			
		}
		
		return INPUT;
	}
	
	
	public String check() throws Exception{
		
		//Map<String, Object> session = ActionContext.getContext().getSession();
		MemberLB memberLB = MemberLB.getInstance();
		
		member.setSeq(account.getSeq());
		
		if(StringUtils.equals(checkFieldName, "memberId")){
					
			if((new RegexValidator("^("+ INVALID_MEMBER_ID +")$", false)).isValid(member.getMemberId())){
				addActionError(getText("validation.member.memberId.banned"));
			}else{
			
				if(memberLB.isDuplicatedMember(member)){
					addActionError(getText("validation.member.memberId.duplicated"));
				}else{
					addActionMessage(getText("validation.member.memberId.valid"));
				}
			
			}
			
		}else if(StringUtils.equals(checkFieldName, "SSN")){
			
			if(memberLB.isDuplicatedPrivate(member)){
				addActionError(getText("validation.member.ssn.duplicated"));
			}else{
				addActionMessage(getText("validation.member.ssn.valid"));
			}
			
		}else if(StringUtils.equals(checkFieldName, "businessLicenseNo")){
			
			if(memberLB.isDuplicatedPublisher(member)){
				addActionError(getText("validation.member.businessLicenseNo.duplicated"));
			}else{
				addActionMessage(getText("validation.member.businessLicenseNo.valid"));
			}	
			
		}else if(StringUtils.equals(checkFieldName, "name")){
			
			if(memberLB.isDuplicatedOrganization(member)){
				addActionError(getText("validation.member.name.duplicated"));
			}else{
				addActionMessage(getText("validation.member.name.valid"));
			}				
			
		}else{
			
			throw new RuntimeException("Invalid checkFieldName.");
			
		}
		
		return INPUT;
		
	}	
	
	
	public String create() throws Exception{
		
		if(!termsOfUse){
			addActionError(getText("agree.terms.of.use"));
			return AGREEMENT_REQUIRED;
		}	
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		type = String.valueOf(session.get("type"));
		
		if(StringUtils.isBlank(type)){
			addActionError(getText("member.type.must.be.selected"));
			return SELECT_MEMBER_TYPE;
		}				
		
		try {

			member.setType(type);
			member.setUsable(true);
			member.setGroupCode(Group.MEMBER_CODE);
			
			if(MemberLB.getInstance().save(member)){
				//addActionMessage("회원 가입이 완료되었습니다.");
				session.remove("type");
				return SUCCESS;
			}else{
				return ERROR;
			}
			
		} catch (DuplicateMemberIdException e) {
			// TODO: handle exception
			addActionError(getText("validation.member.memberId.duplicated"));
			return INPUT;		
			
		} 
		
	}
	
	public String update() throws Exception {
		
		MemberLB memberLB = MemberLB.getInstance();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		try{
			
			memberLB.login(account.getMemberId(), pass);
			
			member.setSeq(account.getSeq());
			member.setName(account.getName());
			member.setType(account.getType());
			member.setUsable(account.isUsable());
			member.setGroupCode(account.getGroupCode());//그룹 수정 불가
			
			if(member.isPublisher()){
				member.setBusinessLicenseNo("");//사업자 번호 수정 불가
			}else if(member.isOrganization()){
				member.setName("");//단체명 수정 불가
			}
			
			if(changePass)member.setPass(newPass);
			
			if(memberLB.save(member)){
				
				if(member != null){
					
					account = memberLB.at(account.getSeq());
					SessionUtils.setAccount(session, account);
					return SUCCESS;				
					
				}else{
					
					addActionError(getText("no.such.member"));
					SessionUtils.initialize(session);
					
					return LOGIN_REQUIRED;
					
				}

			}
			
			return ERROR;
			
		}catch (LoginIncorrectException e) {
			// TODO: handle exception
			
			addActionError(getText("validation.pass.incorrect"));
			return INPUT;
			
		}		
				
		
	}
	
	public String destroy() throws Exception{
		
		MemberLB memberLB = MemberLB.getInstance();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		try{
			
			memberLB.login(account.getMemberId(), pass);
			
			if(memberLB.destroy(account)){
				SessionUtils.initialize(session);
				return SUCCESS;
			}
			
			return ERROR;
			
		}catch (LoginIncorrectException e) {
			// TODO: handle exception
			
			addActionError(getText("validation.pass.incorrect"));
			return INPUT;
			
		}			
		
	}
	
	public String find() throws Exception {
		
		MemberLB memberLB = MemberLB.getInstance();
		
		try{
			
			//if(CommonUtils.isAjaxRequest(ServletActionContext.getRequest()))member.setName(EscapeUtils.unescape(member.getName()));
		
			if(StringUtils.equals(findFieldName, "memberId")){
				
				memberLB.findMemberId(member);
				addActionMessage(getText("find.memberId", new String[]{member.getName(), member.getMemberId()}));
				
			}else if(StringUtils.equals(findFieldName, "pass")){
				
				memberLB.findPass(member);
				addActionMessage(getText("find.pass", new String[]{member.getName()}));
				
			}else{
				
				throw new RuntimeException("Invalid findFieldName.");
				
			}
		
		}catch(NoSuchMemberException e){
			
			addActionError(getText("no.such.member"));
			
		}
		
		return INPUT;
		
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public boolean isTermsOfUse() {
		return termsOfUse;
	}

	public void setTermsOfUse(boolean termsOfUse) {
		this.termsOfUse = termsOfUse;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public boolean isPrivate(){
		
		if(account.getGroup().isGuest()){
			if(StringUtils.isBlank(type))type = String.valueOf(ActionContext.getContext().getSession().get("type"));
			return StringUtils.equals(type, Member.MEMBER_TYPE_PRIVATE);
		}else{
			return account.isPrivate();
		}
		
	}	
	
	public boolean isPublisher(){
		
		if(account.getGroup().isGuest()){
			if(StringUtils.isBlank(type))type = String.valueOf(ActionContext.getContext().getSession().get("type"));
			return StringUtils.equals(type, Member.MEMBER_TYPE_PUBLISHER);
		}else{
			return account.isPublisher();
		}
	}
	
	public boolean isOrganization() {
		
		if(account.getGroup().isGuest()){
			if(StringUtils.isBlank(type))type = String.valueOf(ActionContext.getContext().getSession().get("type"));
			return StringUtils.equals(type, Member.MEMBER_TYPE_ORGANIZATION);
		}else{
			return account.isOrganization();
		}
	}

	public String getCheckFieldName() {
		return checkFieldName;
	}

	public void setCheckFieldName(String checkFieldName) {
		this.checkFieldName = checkFieldName;
	}

	public String getFindFieldName() {
		return findFieldName;
	}

	public void setFindFieldName(String findFieldName) {
		this.findFieldName = findFieldName;
	}

	public boolean isChangePass() {
		return changePass;
	}

	public void setChangePass(boolean changePass) {
		this.changePass = changePass;
	}

	public boolean isChangeSSN() {
		return changeSSN;
	}

	public void setChangeSSN(boolean changeSSN) {
		this.changeSSN = changeSSN;
	}	
	
}