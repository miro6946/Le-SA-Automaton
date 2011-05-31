package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import kr.lesaautomaton.utils.ListChunk;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseDAO {

	protected static final Logger LOG = Logger.getLogger(BaseDAO.class);
	protected SqlMapClient sqlMap = null;
	
	public BaseDAO(){
		sqlMap = SqlConfig.getSqlMapInstance();
	}
	
	public BaseDAO(SqlMapClient sqlMap){
		this.sqlMap = sqlMap;
	}	
	
	public void beginTran() throws SQLException{
		sqlMap.startTransaction();
	}
	
	public void beginTran(int isolationLevel) throws SQLException{
		sqlMap.startTransaction(isolationLevel);
	}	
	
	public void commitTran() throws SQLException{
		sqlMap.commitTransaction();
	}
	
	public void endTran(){
//		try {
//			sqlMap.endTransaction();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw (SQLException)e.fillInStackTrace();
//		}
		try {
			sqlMap.endTransaction();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public void beginBatch() throws SQLException{
		sqlMap.startBatch();
	}
	
	public int endBatch() throws SQLException{
		return sqlMap.executeBatch();
	}
	
	public ListChunk paginate(Map<String, Object> params, String countSqlId, String selectSqld, int pageSize, int page){
		return paginate(params, countSqlId, selectSqld, pageSize, page, true);
	}
	
	public ListChunk paginate(Map<String, Object> params, String countSqlId, String selectSqld, int pageSize, int page, boolean desc){
		
		ListChunk chunk = null;
		int start = 0, end = 0, count = 0;
		
		try{
			
			if(pageSize < Integer.MAX_VALUE){
			
				count = (Integer)sqlMap.queryForObject(countSqlId, params);
				
				start = pageSize * (page - 1) + 1;
				end = pageSize * page;					
				
				if(count > 0){
					params.put("start", start);
					params.put("end", end);			
					chunk = new ListChunk(count, (ArrayList)sqlMap.queryForList(selectSqld, params));
				}else{
					chunk = new ListChunk(0, new ArrayList());
				}
			
			}else{
				
				start = 1;
				end = pageSize;
				
				params.put("start", start);
				params.put("end", end);		
				
				chunk = new ListChunk();
				chunk.setDataList((ArrayList)sqlMap.queryForList(selectSqld, params));
				chunk.setTotalCount(count = chunk.getDataCount());
				
			}
			
			if(desc){
				chunk.setRownum((pageSize < count) ? (count - ((page - 1) * pageSize)) : count, true);
			}else{
				chunk.setRownum((pageSize < count) ? (page - 1) * pageSize + 1 : 1, false);
			}
			chunk.setPageSize(pageSize);
			
			return chunk;		
		
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
	}

	public SqlMapClient getSqlMap() {
		return sqlMap;
	}

	public void setSqlMap(SqlMapClient sqlMap) {
		this.sqlMap = sqlMap;
	}
		
	
}