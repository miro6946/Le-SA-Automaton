package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.lesaautomaton.persistence.domain.Group;

import org.apache.log4j.Logger;

public class GroupDAO extends BaseDAO{
	
	private static final Logger LOG = Logger.getLogger(GroupDAO.class);
	
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
	
	public Group getGroup(Map<String, Object> params) throws RuntimeException{
		
		try{
		
			return (Group)sqlMap.queryForObject("getGroup", params);
		
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public Group getGroup(String code){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		
		return getGroup(params);
		
	}
	
	public List<Group> getGroups() throws RuntimeException{
	
		try{
			
			return sqlMap.queryForList("getGroups");
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
	}

}
