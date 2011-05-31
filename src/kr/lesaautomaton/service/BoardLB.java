package kr.lesaautomaton.service;

import java.util.HashMap;
import java.util.Map;

import kr.lesaautomaton.persistence.dao.BoardDAO;
import kr.lesaautomaton.persistence.domain.Board;

public class BoardLB {
	
	private static BoardLB instance = null;
	
	private BoardLB(){
		
	}
	
	public static synchronized BoardLB getInstance(){
		if(instance == null)instance = new BoardLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}
	
	public Map<String, Board> idMap(){
		
		Map<String, Board> idMap = new HashMap<String, Board>();
		
		for(Board board : (new BoardDAO()).getBoards())idMap.put(board.getId(), board);
			
		return idMap;
		
	}

}
