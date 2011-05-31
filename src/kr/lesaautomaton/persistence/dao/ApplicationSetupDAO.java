package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;

import kr.lesaautomaton.persistence.domain.ApplicationSetup;

public class ApplicationSetupDAO extends BaseDAO {
	
	public ApplicationSetup getApplicationSetup() throws RuntimeException{
		
		try {
			
			return (ApplicationSetup)sqlMap.queryForObject("getApplicationSetup");
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public int updateApplicationSetup(ApplicationSetup applicationSetup) throws RuntimeException{
		
		try{
		
			return sqlMap.update("updateApplicationSetup", applicationSetup);
		
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
}
