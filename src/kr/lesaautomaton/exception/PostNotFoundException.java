package kr.lesaautomaton.exception;

public class PostNotFoundException extends RuntimeException {
	
	public PostNotFoundException(){
		super("Cannot find post.");
	}	

}
