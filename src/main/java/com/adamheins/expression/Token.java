/**
 * Represents a mathematical token, one of:
 * <ul>
 * <li>Number</li>
 * <li>Variable</li>
 * <li>Operator</li>
 * <li>Parenthesis</li>
 * <li>Constant</li>
 * </ul>
 * 
 * @author Adam Heins
 */

package com.adamheins.expression;


/**
 * A math token.
 * 
 * @author Adam
 *
 */
public class Token {
    
	// Constants for different types of Tokens.
    public static final int NUMBER = 0;
    public static final int VARIABLE = 1;
    public static final int OPERATOR = 2;
    public static final int PAREN = 3;
    public static final int CONSTANT = 4;
    
    /** The type of token. */
    private int type;
    
    /** The value of the operator. */
    private String value;
    
    
    /**
     * Constructor.
     * 
     * @param type - The type of the token.
     * @param value - The value of the token.
     */
    public Token(int type, String value) {
        this.type = type;
        this.value = value;
    }
    
    
    /**
     * Get the type of token.
     * 
     * @return The type of this token.
     */
    public int getType() {
        return type;
    }
    
    
    /**
     * Get the value of the token.
     * 
     * @return The value of this token.
     */
    public String getValue() {
        return value;
    }
}