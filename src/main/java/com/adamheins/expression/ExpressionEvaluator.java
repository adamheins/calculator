package main.java.com.adamheins.expression;


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ExpressionEvaluator {
    
	//TODO check for infinite and NaN
	//TODO simple formatting
	
	/** Constant indicating the operator is left associative and takes two arguments. */
	private static final int BINARY_LEFT = 0;
	
	/** Constant indicating the operator is right associative and takes two arguments. */
    private static final int BINARY_RIGHT = 1;
    
    /** Constant indicating the operator is left associative and takes one argument. */
    private static final int UNARY_LEFT = 2;
    
    /** Constant indicating the operator is right associative and takes one argument. */
    private static final int UNARY_RIGHT = 3;
	
    /**
     * Strings of all valid operators.
     * Operators in the string are checked against these in a linear order.
     * Therefore, operators which are a prefix of another (e.g. "sin" is a prefix of "sinh")
     * must come after the operator of which they are of a prefix, to avoid being falsely
     * identified.
     */
	private final static String[] OPERATORS = {"+", "-", "u-", "*", "/" ,"%", "^", "E", "!", "rt",
			"r", "d", "sqrt", "log", "ln", "sinh", "cosh", "tanh", "sin", "cos", "tan", "asin", 
			"acos", "atan"};
	
	/**
	 * Information for all operators.
	 * The integer array contains the precedence and associativity of the operator, in that order.
	 */
    private final static Map<String, int[]> OPERATOR_INFO = new HashMap<String, int[]>();
    static {
    	
    	// Basic arithmetic operators.
        OPERATOR_INFO.put("+", new int[]{0, BINARY_LEFT});
        OPERATOR_INFO.put("-", new int[]{0, BINARY_LEFT});
        OPERATOR_INFO.put("*", new int[]{1, BINARY_LEFT});
        OPERATOR_INFO.put("/", new int[]{1, BINARY_LEFT});
        OPERATOR_INFO.put("%", new int[]{1, BINARY_LEFT});
        
        // Unary negative operator.
        OPERATOR_INFO.put("u-", new int[]{6, UNARY_RIGHT});
        
        // Operators having to do with exponentiation.
        OPERATOR_INFO.put("^", new int[]{2, BINARY_RIGHT});
        OPERATOR_INFO.put("rt", new int[]{2, BINARY_RIGHT});
        OPERATOR_INFO.put("sqrt", new int[]{2, UNARY_RIGHT});
        OPERATOR_INFO.put("E", new int[]{5, BINARY_RIGHT});
        OPERATOR_INFO.put("!", new int[]{4, UNARY_LEFT});
        
        // Operators for converting between degrees and radians.
        OPERATOR_INFO.put("r", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("d", new int[]{3, UNARY_RIGHT});
        
        // Logarithmic operators.
        OPERATOR_INFO.put("log", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("ln", new int[]{3, UNARY_RIGHT});
        
        // Trigonometric operators.
        OPERATOR_INFO.put("sin", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("cos", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("tan", new int[]{3, UNARY_RIGHT});
        
        // Inverse trigonometric operators.
        OPERATOR_INFO.put("asin", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("acos", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("atan", new int[]{3, UNARY_RIGHT});
        
        // Hyperbolic trigonometric operators.
        OPERATOR_INFO.put("sinh", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("cosh", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("tanh", new int[]{3, UNARY_RIGHT});
    }
   
    
    /** Parentheses. */
    private final static char[] PARENS = {'(', ')'};  
    
    /** Strings of all supported constants. */
    private final static String[] CONSTANTS = {"e","pi"};
    
    /** Mapping of constants to their values. */
    private final static Map<String, Double> CONSTANT_VALUES = new HashMap<String, Double>();
    static {
    	CONSTANT_VALUES.put("e", Math.E);
        CONSTANT_VALUES.put("pi", Math.PI);
    }

    
    /**
     * Parses the mathematical <code>String</code> into a queue of math tokens.
     * @return The queue of tokens.
     * @throws ExpressionException
     */
    private static Queue<Token> parseExpression(String expressionString) 
    		throws ExpressionException {
    	Deque<Token> expressionQueue = new LinkedList<Token>();
    	
    	int i = 0;
    	while (i < expressionString.length()) {

            char ch = expressionString.charAt(i);
            
            // Ignore whitespace.
            if (ch == ' ' || ch == '\t') {
                i++;
                continue;
            }            
            
            // Parse the token.
            Token token = parseToken(expressionString, i);
            
            if (token.getValue().equals("-")) {
            	Token last = expressionQueue.peekLast();
            	if (last == null || last.getType() == Token.OPERATOR 
            			|| last.getValue().equals("(")) {
            		token = new Token(Token.OPERATOR, "u-");
            		expressionQueue.add(token);
                    i++;
                    continue;
            	}
            		
            }
            expressionQueue.add(token);
            i += token.getValue().length();
    	}

    	return expressionQueue;
    }
    
    
    /**
     * Parses the next token in the <code>String</code> representing the function.
     * @param expressionString - The string of math to be parsed.
     * @param i - The current index in the string.
     * @return - The next <code>Token</code>.
     * @throws ExpressionException
     */
    private static Token parseToken(String expressionString, int i) throws ExpressionException {
        char ch = expressionString.charAt(i);
        
        // Check for numbers.
        if (isNumber(ch)) {
            int start = i;
            
            // Find the end of the number.
            while (i < expressionString.length() && isNumber(expressionString.charAt(i)))
                i++;
            
            return new Token(Token.NUMBER, expressionString.substring(start, i));
        }
               
        // Check for parentheses.
        for (int j = 0; j < PARENS.length; j++) {
            if (ch == PARENS[j])
                return new Token(Token.PAREN, String.valueOf(PARENS[j]));
        }
        
        // Check for a constant.
        for (int j = 0; j < CONSTANTS.length; j++) {
            if (expressionString.indexOf(CONSTANTS[j], i) == i)
                return new Token(Token.CONSTANT, CONSTANTS[j]);
        }       

        // Finally, check for an operator.
        for (int j = 0; j < OPERATORS.length; j++) {
            if (expressionString.indexOf(OPERATORS[j], i) == i)
                return new Token(Token.OPERATOR, OPERATORS[j]);
        }
        
        // Throw an exception if this character matches nothing.
        throw new ExpressionException("Syntax error at index [" + i + "].");       
    }
    
    
    /**
     * Checks if a character is a number or radix point.
     * @param ch - Character to check.
     * @return True if the character is a number or radix, false otherwise.
     */
    private static boolean isNumber(char ch) {
        if ((ch >= '0' && ch <= '9') || ch == '.')
            return true;
        return false;
    }
    
      
    /**
     * Converts the queue of infix tokens to a queue of postfix tokens,
     * using the shunting-yard algorithm
     * @param expressionQueue - Queue of tokens representing the expression in infix form.
     * @return The queue of tokens in postfix form.
     * @throws ExpressionException
     */
    private static Queue<Token> shuntingYard(Queue<Token> expressionQueue) throws ExpressionException {
    	
        Queue<Token> outQueue = new LinkedList<Token>();
        Deque<Token> opStack = new LinkedList<Token>();
               
        while (expressionQueue.size() > 0) {
        	
        	Token token = expressionQueue.remove();
            
        	// Token is a number or constant.
            if (token.getType() == Token.NUMBER || token.getType() == Token.CONSTANT) {
                outQueue.add(token);
                
                // TODO move to queue parsing stage for consistency
                // Check if the next token in the queue is an operator with a unary right
                // associativity. If so, add an extra multiplication token before it.
                if (expressionQueue.size() > 0 && expressionQueue.peek().getType() == Token.OPERATOR 
                		&& OPERATOR_INFO.get(expressionQueue.peek().getValue())[1] == UNARY_RIGHT) {
                	Token multiplicationToken = new Token(Token.OPERATOR, "*");
                    pushOperator(multiplicationToken, outQueue, opStack);
                }
            
            // Token is a parenthesis.
            } else if (token.getType() == Token.PAREN) {
                if (token.getValue().equals("(")) {
                    opStack.push(token);
                } else {
                	Token poppedToken;
                    while (!(poppedToken = opStack.pop()).getValue().equals("("))
                        outQueue.add(poppedToken);
                }
                
            // Token is an operator.
            } else {
                pushOperator(token, outQueue, opStack);
            }
        }
        
        // Add everything remaining in the operator stack to the output queue.
        while (opStack.size() > 0)
            outQueue.add(opStack.pop());

        return outQueue;
    }
    
    
    /**
     * Pushes an operator into the operator stack in the shunting-yard algorithm.
     * One modification was made to the standard algorithm here. Operators cannot be popped
     * off the stack and added to the queue if they are unary.
     * 
     * @param token - Operator token to be pushed.
     * @param outQueue - Output queue.
     * @param opStack - Operator stack.
     */
    private static void pushOperator(Token token, Queue<Token> outQueue, Deque<Token> opStack) {
    	
    	while (opStack.size() != 0 && !opStack.peek().getValue().equals("(") && OPERATOR_INFO.get(opStack.peek().getValue())[1] < UNARY_LEFT &&
                ((OPERATOR_INFO.get(token.getValue())[1] == BINARY_LEFT 
                && OPERATOR_INFO.get(token.getValue())[0] 
                		<= OPERATOR_INFO.get(opStack.peek().getValue())[0]) 
                || OPERATOR_INFO.get(token.getValue())[0] 
                		< OPERATOR_INFO.get(opStack.peek().getValue())[0])) {
    		//System.out.println(opStack.peek().getValue());
            outQueue.add(opStack.pop());
    	}
    	opStack.push(token);
    }
    
    
    /**
     * Evaluates this mathematical expression.
     * 
     * @param expressionString - The <code>String</code> of math to be evaluated.
     * 
     * @return The value of the expression.
     * 
     * @throws ExpressionException
     */
    public static double evaluate(String expressionString) throws ExpressionException {
       
    	// Empty string evaluates to zero.
    	if (expressionString.isEmpty())
    		return 0;
    	
    	Queue<Token> postfixExpression = shuntingYard(parseExpression(expressionString));   	
    	Deque<Double> valueStack = new LinkedList<Double>();

        while (postfixExpression.size() > 0) {
            Token token = postfixExpression.remove();
            
            if (token.getType() == Token.NUMBER) {
                valueStack.push(Double.parseDouble(token.getValue()));
            } else if (token.getType() == Token.CONSTANT){
                valueStack.push(CONSTANT_VALUES.get(token.getValue()));
            } else {
            	if (OPERATOR_INFO.get(token.getValue())[1] == UNARY_RIGHT 
                		|| OPERATOR_INFO.get(token.getValue())[1] == UNARY_LEFT) {     		
                    double num = valueStack.pop();
                    valueStack.push(ExpressionMath.doOperation(token, num));
                } else {
                	
                    double rightNum = valueStack.pop();
                    double leftNum = valueStack.pop();
                    valueStack.push(ExpressionMath.doOperation(token, leftNum, rightNum));
                }
            }
        }

        return valueStack.pop();
    }
    
    
    public static String format(double number) {
    	
    	String numStr = String.valueOf(number);
    	System.out.println(numStr);
    	
    	int index = numStr.indexOf('E');
    	if (index != -1) {
    		String[] numParts = numStr.split("E");
    		
    		//System.out.println(numParts[0].indexOf('.'));
    		int length = numParts[0].length();
    		if (numParts[0].charAt(length - 1) == '0' && numParts[0].charAt(length - 2) == '.')
    			numParts[0] = numParts[0].substring(0, numParts[0].length() - 2);
    		numStr = numParts[0] + "E" + numParts[1];
    	} else {
    		int length = numStr.length();
    		if (numStr.charAt(length - 1) == '0' && numStr.charAt(length - 2) == '.')
    			numStr = numStr.substring(0, numStr.length() - 2);
    	}

    	return numStr;
    }

    
    /**
     * Main method.
     * @param args
     */
    public static void main(String args[]) {
        try {
            System.out.println(ExpressionEvaluator.evaluate("-(3*4)"));
        } catch (ExpressionException e) {
            System.out.println(e.getMessage());
        }
        
    }  
}