package com.adamheins.function;


public class FunctionFormatException extends Exception {
	
	// Serial Version UID
	private static final long serialVersionUID = 243733144165974081L;

	public FunctionFormatException() {
		super();
	}
	
	public FunctionFormatException(String message) {
		super(message);
	}
	
	public FunctionFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FunctionFormatException(Throwable cause) {
		super(cause);
	}
}


