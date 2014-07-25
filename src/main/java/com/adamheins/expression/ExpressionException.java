package com.adamheins.expression;


public class ExpressionException extends Exception {
	
	// Serial Version UID
	private static final long serialVersionUID = 243733144165974081L;

	
	public ExpressionException() {
		super();
	}
	
	
	public ExpressionException(String message) {
		super(message);
	}
	
	
	public ExpressionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public ExpressionException(Throwable cause) {
		super(cause);
	}
}


