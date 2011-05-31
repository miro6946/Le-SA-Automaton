package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import kr.lesaautomaton.persistence.domain.Board;

import org.apache.log4j.Logger;

public class BoardDAO extends BaseDAO{
	
	private static final Logger LOG = Logger.getLogger(BoardDAO.class);
	
	public List<Board> getBoards() throws RuntimeException{
	
		try{
			
			return sqlMap.queryForList("getBoards");
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
	}

}
