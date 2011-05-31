package kr.lesaautomaton.exception;

public class AttachmentFailureException extends RuntimeException {
	
	public AttachmentFailureException(){
		super("Some problems occured at attaching files.");
	}	

}
