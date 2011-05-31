package kr.lesaautomaton.service;

import java.util.List;

import kr.lesaautomaton.persistence.dao.ZipcodeDAO;
import kr.lesaautomaton.persistence.domain.Zipcode;

public class ZipcodeLB {
	
	private static ZipcodeLB instance = null;
	
	private ZipcodeLB(){
	}
	
	public static synchronized ZipcodeLB getInstance(){
		if(instance == null)instance = new ZipcodeLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}		
	
	public List<Zipcode> find(String keyword){
		return (new ZipcodeDAO()).getZipcodes(keyword +"%");
	}

}
