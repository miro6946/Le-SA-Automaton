package kr.lesaautomaton.persistence.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlConfig {
	
	private static SqlMapClient sqlMap;
	
	static{
		
		try{
		
			String resource = "kr/lesaautomaton/persistence/dao/xml/sql-map-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		}catch(Exception e){
			
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
	}
	
	public static SqlMapClient getSqlMapInstance(){
		return sqlMap;
	}
	
	public static boolean ping(){
		
		boolean retVal = false;
		
		try{
			
			sqlMap.startTransaction();
			
			Connection conn = sqlMap.getCurrentConnection();
			PreparedStatement pstmt = conn.prepareStatement("select 1 from dual");
			
			retVal = pstmt.execute();
			
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}finally{
			try{
				sqlMap.endTransaction();
			}catch(SQLException e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		return retVal;
		
	}	

}
