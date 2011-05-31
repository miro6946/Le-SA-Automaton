package kr.lesaautomaton.exception;

public class RepliesFullException extends RuntimeException{
	
	public RepliesFullException(){
		super("Quautity of repies had been reached limit(100).");
	}

}
