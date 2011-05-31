package kr.lesaautomaton.exception;

public class DuplicateMemberIdException extends RuntimeException {
	
	public DuplicateMemberIdException(){
		super("Duplicated Member's id");
	}

}
