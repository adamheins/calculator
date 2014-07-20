package com.adamheins.function;


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Function {
    
	private final String[] OPERATORS = {"+","-","*","/","%","^","E","log","sin","cos","tan","asin","acos","atan"};
    private Map<String, int[]> OPERATOR_INFO = new HashMap<String, int[]>();
    
    
    
    private static final char[] PARENS = {'(', ')'};
    
    
    
    private final String[] CONSTANTS = {"e","pi"};
    private Map<String, Double> CONSTANT_VALUES = new HashMap<String, Double>();
    
    private static final int BINARY_LEFT = 0;
    private static final int BINARY_RIGHT = 1;
    private static final int UNARY_LEFT = 2;
    private static final int UNARY_RIGHT = 3;
    
    
    private String functionString;   
    private Queue<Token> postfixFunction;
    
    
    /**
     * Initialize the maps.
     */
    private void initMap() {
    	
    	// Populated with {precedence, associativity)
        OPERATOR_INFO.put("+", new int[]{0, BINARY_LEFT});
        OPERATOR_INFO.put("-", new int[]{0, BINARY_LEFT});
        OPERATOR_INFO.put("*", new int[]{1, BINARY_LEFT});
        OPERATOR_INFO.put("/", new int[]{1, BINARY_LEFT});
        OPERATOR_INFO.put("%", new int[]{1, BINARY_LEFT});
        OPERATOR_INFO.put("^", new int[]{2, BINARY_RIGHT});
        OPERATOR_INFO.put("E", new int[]{4, BINARY_RIGHT});
        OPERATOR_INFO.put("log", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("sin", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("cos", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("tan", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("asin", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("acos", new int[]{3, UNARY_RIGHT});
        OPERATOR_INFO.put("atan", new int[]{3, UNARY_RIGHT});
        
        CONSTANT_VALUES.put("e", Math.E);
        CONSTANT_VALUES.put("pi", Math.PI);
    }
    
    
    /**
     * Constructor.
     * @param functionString - A <code>String</code> representing the mathematical function.
     * @throws FunctionFormatException
     */
    public Function(String functionString) throws FunctionFormatException {
        initMap();
        
        this.functionString = functionString;
        
        postfixFunction = shuntingYard(parseFunction());
    }
    
    
    /**
     * Prints the elements of the postfix queue.
     * Mainly used for debugging purposes.
     */
    public void print() {
        while (postfixFunction.size() > 0)
            System.out.println(postfixFunction.remove().getValue());
    }
    
    
    /**
     * P
     * @return
     * @throws FunctionFormatException
     */
    private Queue<Token> parseFunction() throws FunctionFormatException {
    	Queue<Token> functionQueue = new LinkedList<Token>();
    	
    	int i = 0;
    	while (i < functionString.length()) {

            char ch = functionString.charAt(i);
            
            // Ignore whitespace.
            if (ch == ' ' || ch == '\t') {
                i++;
                continue;
            }            
            
            // Parse the token.
            Token token = parseToken(i);
            functionQueue.add(token);
            i += token.getValue().length();
    	}
    	
    	return functionQueue;
    }
    
      
    /**
     * Converts the queue of infix tokens to a queue of postfix tokens,
     * using the shunting-yard algorithm
     * @return The queue of tokens.
     * @throws FunctionFormatException
     */
    private Queue<Token> shuntingYard(Queue<Token> functionQueue) throws FunctionFormatException {
        
        Queue<Token> outQueue = new LinkedList<Token>();
        Deque<Token> opStack = new LinkedList<Token>();
               
        while (functionQueue.size() > 0) {
        	
        	Token token = functionQueue.remove();
            
        	// Token is a number or constant.
            if (token.getType() == Token.NUMBER || token.getType() == Token.CONSTANT) {
                outQueue.add(token);
                
                // Check if the next token in the queue is an operator with a unary right
                // associativity or a variable. If so, add an extra multiplication token
                // before it.
                if (functionQueue.size() > 0 && ((functionQueue.peek().getType() == Token.OPERATOR && OPERATOR_INFO.get(functionQueue.peek().getValue())[1] == UNARY_RIGHT) || functionQueue.peek().getType() == Token.VARIABLE)) {
                	Token multiplicationToken = new Token(Token.OPERATOR, "*");
                    pushOperator(multiplicationToken, outQueue, opStack);
                }
                
            // Token is a variable.
            } else if (token.getType() == Token.VARIABLE) {
                outQueue.add(token);
            
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
    
    
    
    private void pushOperator(Token token, Queue<Token> outQueue, Deque<Token> opStack) {
    	while (opStack.size() != 0 && !opStack.peek().getValue().equals("(") &&
                ((OPERATOR_INFO.get(token.getValue())[1] == BINARY_LEFT 
                && OPERATOR_INFO.get(token.getValue())[0] 
                		<= OPERATOR_INFO.get(opStack.peek().getValue())[0]) 
                || OPERATOR_INFO.get(token.getValue())[0] 
                		< OPERATOR_INFO.get(opStack.peek().getValue())[0]))
            outQueue.add(opStack.pop());
    	opStack.push(token);
    }
    
    
    /**
     * Evaluates this function for the given value of x.
     * Assumes there is only one variables in the function.
     * 
     * @param x - Value at which to evaluate the function.
     * 
     * @return The value of the function at the given value of x.
     * @throws FunctionFormatException
     */
    public double evaluate(double x) throws FunctionFormatException {
        Deque<Double> valueStack = new LinkedList<Double>();
        
        while (postfixFunction.size() > 0) {
            Token token = postfixFunction.remove();
            
            if (token.getType() == Token.NUMBER) {
                valueStack.push(Double.parseDouble(token.getValue()));
            } else if (token.getType() == Token.VARIABLE) {
                valueStack.push(x);
            } else if (token.getType() == Token.CONSTANT){
                valueStack.push(CONSTANT_VALUES.get(token.getValue()));
            } else {
                if (OPERATOR_INFO.get(token.getValue())[1] == UNARY_RIGHT) {
                    double num = valueStack.pop();
                    valueStack.push(doOperation(token, num));
                } else {
                    double rightNum = valueStack.pop();
                    double leftNum = valueStack.pop();
                    valueStack.push(doOperation(token, leftNum, rightNum));
                }
            }
        }

        return valueStack.pop();
    }
    
    
    
    /**
     * Evaluates this function for the given value of x.
     * @param x - Value at which to evaluate the function.
     * @return The value of the function at the given value of x.
     * @throws FunctionFormatException
     */
    public double evaluate(HashMap<Character, Double> variables) throws FunctionFormatException {
        Deque<Double> valueStack = new LinkedList<Double>();
        
        while (postfixFunction.size() > 0) {
            Token token = postfixFunction.remove();
            
            if (token.getType() == Token.NUMBER) {
                valueStack.push(Double.parseDouble(token.getValue()));
            } else if (token.getType() == Token.VARIABLE) {
                valueStack.push(variables.get(token.getValue().charAt(0)));
            } else if (token.getType() == Token.CONSTANT){
                valueStack.push(CONSTANT_VALUES.get(token.getValue()));
            } else {
                if (OPERATOR_INFO.get(token.getValue())[1] == UNARY_RIGHT) {
                    double num = valueStack.pop();
                    valueStack.push(doOperation(token, num));
                } else {
                    double rightNum = valueStack.pop();
                    double leftNum = valueStack.pop();
                    valueStack.push(doOperation(token, leftNum, rightNum));
                }
            }
        }

        return valueStack.pop();
    }
    
    
    /**
     * Performs a mathematical operation that requires a single numerical input.
     * @param operator - The mathematical operator.
     * @param num - The number being operated upon.
     * @return The result of the mathematical operation.
     * @throws FunctionFormatException
     */
    private double doOperation(Token operator, double num) throws FunctionFormatException {
        
        String operatorValue = operator.getValue();
        
        if (operatorValue.equals("log"))
            return Math.log(num);
        else if (operatorValue.equals("sin"))
            return Math.sin(num);
        else if (operatorValue.equals("cos"))
            return Math.cos(num);
        else if (operatorValue.equals("tan"))
            return Math.tan(num);
        else if (operatorValue.equals("asin"))
            return Math.asin(num);
        else if (operatorValue.equals("acos"))
            return Math.acos(num);
        else if (operatorValue.equals("atan"))
            return Math.atan(num);
        
        throw new FunctionFormatException("Something bad happened!");
    }
    
    
    /**
     * Performs a mathematical operation that requires two numerical inputs.
     * @param operator - The mathematical operator.
     * @param leftNum - The number to the left of the operator.
     * @param rightNum - The number to the right of the operator.
     * @return The result of the mathematical operation.
     * @throws FunctionFormatException
     */
    private double doOperation(Token operator, double leftNum, double rightNum) 
    		throws FunctionFormatException {
        
        String operatorValue = operator.getValue();
        
        if (operatorValue.equals("+"))
            return leftNum + rightNum;
        else if (operatorValue.equals("-"))
            return leftNum - rightNum;
        else if (operatorValue.equals("*"))
            return leftNum * rightNum;
        else if (operatorValue.equals("/"))
            return leftNum / rightNum;
        else if (operatorValue.equals("^"))
            return Math.pow(leftNum, rightNum);
        else if (operatorValue.equals("%"))
            return leftNum % rightNum;
        else if (operatorValue.equals("E"))
            return leftNum * Math.pow(10, rightNum);
        
        throw new FunctionFormatException("Something bad happened!");
    }
    
    
    
    /**
     * Parses the next token in the <code>String</code> representing the function.
     * @param i - The current index in the string.
     * @return - The next <code>Token</code>.
     * @throws FunctionFormatException
     */
    private Token parseToken(int i) throws FunctionFormatException {
        char ch = functionString.charAt(i);
        
        // Check for numbers.
        if (isNumber(ch)) {
            int start = i;
            
            // Find the end of the number.
            while (i < functionString.length() && isNumber(functionString.charAt(i)))
                i++;
            
            return new Token(Token.NUMBER, functionString.substring(start, i));
        }
               
        // Check for parentheses.
        for (int j = 0; j < PARENS.length; j++) {
            if (ch == PARENS[j])
                return new Token(Token.PAREN, String.valueOf(PARENS[j]));
        }
        
        // Check for a constant.
        for (int j = 0; j < CONSTANTS.length; j++) {
            if (functionString.indexOf(CONSTANTS[j], i) == i)
                return new Token(Token.CONSTANT, CONSTANTS[j]);
        }       
        
        // Finally, check for an operator.
        for (int j = 0; j < OPERATORS.length; j++) {
            if (functionString.indexOf(OPERATORS[j], i) == i)
                return new Token(Token.OPERATOR, OPERATORS[j]);
        }
                
        // Check for variables.
        // Assumes any letter that was not part of an operator is a variable in the function.
        if (isLetter(ch))
        	return new Token(Token.VARIABLE, String.valueOf(ch));
        
        // Throw an exception if this character matches nothing.
        throw new FunctionFormatException("Unknown symbol at " + functionString + "[" + i + "].");       
    }
    
    
    /**
     * Checks if a character is a number or radix point.
     * @param ch - Character to check.
     * @return True if the character is a number or radix, false otherwise.
     */
    private boolean isNumber(char ch) {
        if ((ch >= '0' && ch <= '9') || ch == '.')
            return true;
        return false;
    }
    
    
    /**
     * Checks if a character is a letter.
     * @param ch - Character to check.
     * @return True if the character is a letter, false otherwise.
     */
    private boolean isLetter(char ch) {
    	if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
    		return true;
    	return false;
    }
    
    
    /**
     * Main method.
     * @param args
     */
    public static void main(String args[]) {
        try {
            Function function = new Function("3sinpi");
            System.out.println(function.evaluate(0));
        } catch (FunctionFormatException e) {
            e.printStackTrace();
        }
        
    }  
}
