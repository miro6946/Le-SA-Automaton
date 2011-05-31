package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import kr.lesaautomaton.persistence.domain.Zipcode;

public class ZipcodeDAO extends BaseDAO {
	
	public List<Zipcode> getZipcodes(String keyword) throws RuntimeException{
		try {
			
			return (List<Zipcode>)sqlMap.queryForList("getZipcodes", keyword);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
