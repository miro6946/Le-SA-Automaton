package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.lesaautomaton.persistence.domain.Member;
import kr.lesaautomaton.utils.ListChunk;

import org.apache.log4j.Logger;

public class MemberDAO extends BaseDAO{
	
	private static final Logger LOG = Logger.getLogger(MemberDAO.class);
	
//	public int countWords(String name, String memberId){
//		
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		
//		params.put("name", name);
//		params.put("memberId", memberId);
//		
//		try {
//			
//			return (Integer)sqlMap.queryForObject("countMembers", params);
//			
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		
//	}
	
	public ListChunk getMembers(String name, String memberId, String[] type, int pageSize, int page) throws RuntimeException{
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("name", name);
		params.put("memberId", memberId);
		params.put("type", type);
			
		return paginate(params, "countMembers", "getMembers", pageSize, page);
		
	}
	
	public Member getMember(Map<String, Object> params) throws RuntimeException{
	
		try {
			
			return (Member)sqlMap.queryForObject("getMember", params);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public Member getMember(int memberSeq) throws RuntimeException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("seq", memberSeq);
		
		return getMember(params);
		
	}	
	
	public Member getMember(String memberId, String encryptedPass, boolean isUsable) throws RuntimeException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("encryptedPass", encryptedPass);
		params.put("usable", isUsable);
		
		return getMember(params);
		
	}	
	
	public Member getPrivate(int memberSeq) throws RuntimeException{
		
		try {
			
			return (Member)sqlMap.queryForObject("getPrivate", memberSeq);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public Member getPublisher(int memberSeq) throws RuntimeException{
		
		try {
			
			return (Member)sqlMap.queryForObject("getPublisher", memberSeq);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public Member findMember(Member member) throws RuntimeException{
		
		try {

			return (Member)sqlMap.queryForObject("findMember", member);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int updatePass(Member member) throws RuntimeException{
	
		try {
			
			return (Integer)sqlMap.update("updatePass", member);

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	
	public Member getOrganization(int memberSeq) throws RuntimeException{
		
		try {
			
			return (Member)sqlMap.queryForObject("getOrganization", memberSeq);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}	
	
	public int insertMember(Member member) throws RuntimeException{
		
		Date now = new Date();
		member.setCreatedAt(now);
		member.setUpdatedAt(now);
		
		try{
			return (Integer)sqlMap.insert("insertMember", member);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int insertPrivate(Member member) throws RuntimeException{
		
		try{
			return (Integer)sqlMap.insert("insertPrivate", member);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public int insertPublisher(Member member) throws RuntimeException{
		
		try{
			return (Integer)sqlMap.insert("insertPublisher", member);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public int insertOrganization(Member member) throws RuntimeException{
		
		try{
			return (Integer)sqlMap.insert("insertOrganization", member);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}	
	
	public int updateMember(Member member) throws RuntimeException{
		
		member.setUpdatedAt(new Date());
		
		try {
			return sqlMap.update("updateMember", member);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}		
	
	public int updatePublisher(Member member) throws RuntimeException{
		
		try {
			return sqlMap.update("updatePublisher", member);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int updateOrganization(Member member) throws RuntimeException{
		
		try {
			return sqlMap.update("updateOrganization", member);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int deleteMember(int memberSeq) throws RuntimeException {
		
		try {
			return sqlMap.delete("deleteMember", memberSeq);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int existsMemberId(String memberId) throws RuntimeException{
		
		Object ret = null;
		
		try {
			
			ret = sqlMap.queryForObject("existsMemberId", memberId);
			
			if(ret == null)
				return 0;
			else
				return (Integer)ret;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int existsSsn(int seq, String ssnPrimary, String encryptedSsnSecondary) throws RuntimeException{
		
		Object ret = null;
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ssnPrimary", ssnPrimary);
		params.put("encryptedSsnSecondary", encryptedSsnSecondary);		
		params.put("seq", seq);
		
		try {
			
			ret = sqlMap.queryForObject("existsSsn", params);
			
			if(ret == null)
				return 0;
			else
				return (Integer)ret;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int existsBusinessLicenseNo(int seq, String businessLicenseNo) throws RuntimeException{
		
		Object ret = null;
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("businessLicenseNo", businessLicenseNo);		
		params.put("seq", seq);		
		
		try {
			
			ret = sqlMap.queryForObject("existsBusinessLicenseNo", params);
			
			if(ret == null)
				return 0;
			else
				return (Integer)ret;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}	
	
	public int existsName(int seq, String name) throws RuntimeException{
		
		Object ret = null;
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);		
		params.put("seq", seq);		
		
		try {
			
			ret = sqlMap.queryForObject("existsName", params);
			
			if(ret == null)
				return 0;
			else
				return (Integer)ret;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}	


}
