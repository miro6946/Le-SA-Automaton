package kr.lesaautomaton.persistence.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kr.lesaautomaton.persistence.domain.Message;
import kr.lesaautomaton.utils.ListChunk;

public class MessageDAO extends BaseDAO {
	
	public ListChunk getMessages(String boardId, String writer, String title, String contents, boolean onlyTop, boolean onlyEnable, int pageSize, int page) throws RuntimeException{
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("boardId", boardId);
		params.put("writer", writer);
		params.put("title", title);
		params.put("contents", contents);
		params.put("onTop", onlyTop);
		if(onlyEnable)params.put("disable", false);		
			
		return paginate(params, "countMessages", "getMessages", pageSize, page);
		
	}	
	
	public Message getMessage(Map<String, Object> params) throws RuntimeException{
		
		try {
			
			return (Message)sqlMap.queryForObject("getMessage", params);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
		
	}
	
	public Message getMessage(String boardId, int messageSeq, boolean onlyEnable) throws RuntimeException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("boardId", boardId);
		params.put("seq", messageSeq);
		params.put("disable", !onlyEnable);
		
		return getMessage(params);
		
	}	
	
	public Message getMessage(String boardId, int messageSeq, int writerSeq, boolean onlyEnable) throws RuntimeException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("boardId", boardId);
		params.put("seq", messageSeq);
		params.put("writerSeq", writerSeq);
		params.put("disable", !onlyEnable);
		
		return getMessage(params);
		
	}
	
	public Message getMessage(String boardId, int messageSeq) throws RuntimeException{
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("boardId", boardId);
		params.put("seq", messageSeq);
		
		return getMessage(params);
		
	}			
	
	public Message getMessage(boolean previous, boolean next, String boardId, int messageSeq, String writer, String title, String contents, boolean onlyEnable){
		
		String selectSqlId = "";
		if(previous)
			selectSqlId = "getPreviousMessage";
		else if(next)
			selectSqlId = "getNextMessage";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("boardId", boardId);
		params.put("seq", messageSeq);
		params.put("writer", writer);
		params.put("title", title);
		params.put("contents", contents);		
		if(onlyEnable)params.put("disable", false);	
		
		try {
			
			return (Message)sqlMap.queryForObject(selectSqlId, params);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
		
	}
	
	public int insertMessage(Map params) throws RuntimeException{
		
		try{
			return (Integer)sqlMap.insert(!params.containsKey("branch") ? "insertMessage" : "insertReplyMessage", params);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	private int updateMessageThread(String boardId, int branch, int thread, String sqlId) throws RuntimeException{
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("boardId", boardId);
		params.put("branch", branch);
		params.put("thread", thread);
		
		try{
			return (Integer)sqlMap.update(sqlId, params);
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int decreaseMessageThread(String boardId, int branch, int thread){
		
		return updateMessageThread(boardId, branch, thread, "decreaseMessageThread");
		
	}
	
	public int increaseMessageThread(String boardId, int branch, int thread){
		
		return updateMessageThread(boardId, branch, thread, "increaseMessageThread");
		
	}		
	
	public int countReplyMessages(String boardId, int branch){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("boardId", boardId);
		params.put("branch", branch);
		
		try{
		
			return (Integer)sqlMap.queryForObject("countReplyMessages", params);
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int updateMessage(Map params) throws RuntimeException{
		
		try {
			params.put("boardId", ((String)params.get("boardId")).replaceAll("'", "''"));
			return sqlMap.update("updateMessage", params);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}		
	
	public int deleteMessage(Map params) throws RuntimeException{
		
		try {
		
			return sqlMap.delete("deleteMessage", params);
		
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public int increaseHits(Message message) throws RuntimeException{
		
		try{
	
			return sqlMap.update("increaseMessageHits", message);
		
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
	}

}
