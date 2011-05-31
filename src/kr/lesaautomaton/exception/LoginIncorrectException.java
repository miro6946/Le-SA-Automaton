package kr.lesaautomaton.exception;

public class LoginIncorrectException extends RuntimeException {
	
	public LoginIncorrectException(){
		super("Wrong id or password.");
	}

}
