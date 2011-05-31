package kr.lesaautomaton.exception;

public class NoSuchMemberException extends RuntimeException {
	
	public NoSuchMemberException(){
		super("Cannot find member.");
	}

}
