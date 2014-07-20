package com.adamheins.function;


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Function {
    
    private Map<String, int[]> OPERATOR_INFO = new HashMap<String, int[]>();
    private final String[] OPERATORS = {"+","-","*","/","%","^","E","log","sin","cos","tan","asin","acos","atan"};
    private final char[] PARENS = {'(', ')'};
    private final char[] VARIABLES = {'x'};
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
        OPERATOR_INFO.put("^", new int[]{4, BINARY_RIGHT});
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
        
        postfixFunction = parseSymbolQueue();
    }
    
    
    /**
     * Constructor with an option to specify a list of variables being used in the function.
     * 
     * TODO: Implement multiple variables in the function.
     * 
     * @param functionString - A <code>String</code> representing the mathematical function.
     * @param variables - The list of variables in the function.
     */
    public Function(String functionString, char[] variables) {
        
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
     * Parses the infix mathematical string into a queue of tokens in postfix notation.
     * @return The queue of tokens.
     * @throws FunctionFormatException
     */
    private Queue<Token> parseSymbolQueue() throws FunctionFormatException {
        
        Queue<Token> outQueue = new LinkedList<Token>();
        Deque<Token> opStack = new LinkedList<Token>();
        
        
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
            i += token.getValue().length();
            
            // Shunting-yard algorithm.
            if (token.getType() == Token.NUMBER || token.getType() == Token.CONSTANT) {
                outQueue.add(token);
            } else if (token.getType() == Token.VARIABLE) {
               /* if (outQueue.size() > 0 && outQueue.peek().getType() == Token.NUMBER) {
                    // Create a new multiplication symbol.
                    Token multiplicationToken = new Token(Token.OPERATOR, "*");
                    while (opStack.size() != 0 && !opStack.peek().getValue().equals("(") &&
                            ((OPERATOR_INFO.get(multiplicationToken.getValue())[1] == BINARY_LEFT && OPERATOR_INFO.get(multiplicationToken.getValue())[0] <= OPERATOR_INFO.get(opStack.peek().getValue())[0]) 
                            || OPERATOR_INFO.get(multiplicationToken.getValue())[0] < OPERATOR_INFO.get(opStack.peek().getValue())[0]))
                        outQueue.add(opStack.pop());
                    opStack.push(multiplicationToken);
                }*/
                outQueue.add(token);
            } else if (token.getType() == Token.PAREN) {
                if (token.getValue().equals("(")) {
                    opStack.push(token);
                } else {
                    while (true) {
                        Token poppedToken = opStack.pop();
                        if (poppedToken.getValue().equals("("))
                            break;
                        else
                            outQueue.add(poppedToken);
                    }
                }   
            } else {
                while (opStack.size() != 0 && !opStack.peek().getValue().equals("(") &&
                        ((OPERATOR_INFO.get(token.getValue())[1] == BINARY_LEFT && OPERATOR_INFO.get(token.getValue())[0] <= OPERATOR_INFO.get(opStack.peek().getValue())[0]) 
                        || OPERATOR_INFO.get(token.getValue())[0] < OPERATOR_INFO.get(opStack.peek().getValue())[0]))
                    outQueue.add(opStack.pop());
                opStack.push(token);
            }
        }
        
        // Add everything remaining in the operator stack to the output queue.
        while (opStack.size() > 0)
            outQueue.add(opStack.pop());
          
        return outQueue;
    }
    
    
    /**
     * Evaluates this function for the given value of x.
     * @param x - Value at which to evaluate the function.
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
            
            String numberStr = functionString.substring(start, i);
            return new Token(Token.NUMBER, numberStr);
        }
        
        // Check for variables.
        for (int j = 0; j < VARIABLES.length; j++) {
            if (ch == VARIABLES[j])
                return new Token(Token.VARIABLE, String.valueOf(VARIABLES[j]));
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
     * Represents a mathematical token, one of:
     * <ul>
     * <li>Number</li>
     * <li>Variable</li>
     * <li>Operator</li>
     * <li>Parenthesis</li>
     * <li>Constant</li>
     * </ul>
     * @author Adam Heins
     *
     */
    class Token {
        
    	/**
    	 * Constants for the different types of tokens.
    	 */
        public static final int NUMBER = 0;
        public static final int VARIABLE = 1;
        public static final int OPERATOR = 2;
        public static final int PAREN = 3;
        public static final int CONSTANT = 4;
        
        /**
         * The type of token.
         */
        private int type;
        
        /**
         * The value of the operator.
         */
        private String value;
        
        
        /**
         * Constructor.
         * @param type - The type of the token.
         * @param value - The value of the token.
         */
        public Token(int type, String value) {
            this.type = type;
            this.value = value;
        }
        
        
        /**
         * Get the type of token.
         * @return The type of this token.
         */
        public int getType() {
            return type;
        }
        
        
        /**
         * Get the value of the token.
         * @return The value of this token.
         */
        public String getValue() {
            return value;
        }
    }
    
    
    /**
     * Main method.
     * @param args
     */
    public static void main(String args[]) {
        try {
            Function function = new Function("e^6");
            System.out.println(function.evaluate(2));
        } catch (FunctionFormatException e) {
            e.printStackTrace();
        }
        
    }  
}
