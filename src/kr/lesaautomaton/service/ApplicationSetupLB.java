package kr.lesaautomaton.service;

import kr.lesaautomaton.persistence.dao.ApplicationSetupDAO;
import kr.lesaautomaton.persistence.domain.ApplicationSetup;

public class ApplicationSetupLB {
	
	private static ApplicationSetupLB instance = null;
	
	private ApplicationSetupLB(){
		
	}
	
	public static synchronized ApplicationSetupLB getInstance(){
		if(instance == null)instance = new ApplicationSetupLB();
		return instance;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		throw new CloneNotSupportedException();
	}	
	
	public ApplicationSetup load(){
		return (new ApplicationSetupDAO()).getApplicationSetup();
	}
	
	public boolean save(ApplicationSetup applicationSetup){
		
		return (new ApplicationSetupDAO()).updateApplicationSetup(applicationSetup) > 0;
		
	}

}
