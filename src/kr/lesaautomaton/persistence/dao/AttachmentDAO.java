package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.lesaautomaton.persistence.domain.Attachment;

import com.ibatis.sqlmap.client.SqlMapClient;

public class AttachmentDAO extends BaseDAO {
	
	public AttachmentDAO(){
		super();
	}
	
	public AttachmentDAO(SqlMapClient sqlMap){
		super(sqlMap);
	}
	
	public List<Attachment> getAttachments(Map<String, Object> params){
		
		try{
			return (List<Attachment>)sqlMap.queryForList("getAttachments", params);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}			
		
	}
	
	public List<Attachment> getAttachments(int[] attachmentSeq){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("seq", attachmentSeq);
		
		return getAttachments(params);
		
	}
	
	public List<Attachment> getAttachments(String attachableType, int attachableSeq, String uploadType){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("attachableType", attachableType);
		params.put("attachableSeq", attachableSeq);
		params.put("uploadType", uploadType);
		
		return getAttachments(params);
		
	}	
	
	public Attachment getAttachment(Map<String, Object> params) throws RuntimeException{
		
		try{
			return (Attachment)sqlMap.queryForObject("getAttachment", params);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public Attachment getAttachment(int attachmentSeq, boolean enableOrphanFilter) throws RuntimeException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("seq", attachmentSeq);
		params.put("orphanFilter", enableOrphanFilter);
		
		return getAttachment(params);
		
	}				
	
	public int insertAttachment(Attachment attachment) {
		
		try{
			return (Integer)sqlMap.insert("insertAttachment", attachment);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public int deleteAttachment(Attachment attachment){
		
		try {
			return sqlMap.delete("deleteAttachment", attachment);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int updateAttachable(Attachment attachment){
		
		try {
			
			return sqlMap.update("updateAttachable", attachment);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int deleteAttachable(Map<String, Object> params){
		
		try {
			
			return sqlMap.update("deleteAttachable", params);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}	
	
	public int deleteAttachable(String attachableType, int attachableSeq){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("attachableType", attachableType);
		params.put("attachableSeq", attachableSeq);
		
		return deleteAttachable(params);		
		
	}

}
