package kr.lesaautomaton.service;

import java.util.HashMap;
import java.util.Map;

import kr.lesaautomaton.persistence.dao.GroupDAO;
import kr.lesaautomaton.persistence.domain.Group;

public class GroupLB {
	
	private static GroupLB instance = null;
	
	private GroupLB(){
		
	}
	
	public static synchronized GroupLB getInstance(){
		if(instance == null)instance = new GroupLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}
	
	public Map<String, String> codeMap(){
		
		Map<String, String> codes = new HashMap<String, String>();
		
		for(Group group : (new GroupDAO()).getGroups())codes.put(group.getCode(), group.getName());
			
		return codes;
		
	}

}
