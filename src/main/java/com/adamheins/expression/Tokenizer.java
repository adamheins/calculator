package com.adamheins.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool for tokenizing a string of math.
 */
public class Tokenizer {

    
    /**
     * Parses the mathematical string into a list of math tokens.
     * 
     * @param expressionString The string of math to be parsed.
     * 
     * @return The list of tokens.
     * 
     * @throws ExpressionException Throws an expression if there is a syntax error in the 
     *     expression string.
     */
    public static List<Shuntable> parseExpression(String expressionString) 
            throws ExpressionException {
        
        List<Shuntable> tokens = new ArrayList<>();

        int i = 0;
        while (i < expressionString.length()) {

            char ch = expressionString.charAt(i);

            // Ignore whitespace.
            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            // Parse the token.
            Shuntable token = parseToken(expressionString, i);
            
            // Special case for negatives, as they can be either unary negatives or binary
            // subtraction signs. Subtraction is the default.
            if (token == Operator.MINUS) {
                if (tokens.size() == 0)
                    token = Operator.NEGATE;
                else {
                    Shuntable last = tokens.get(tokens.size() - 1);
                    if (last instanceof Operator || last == Parentheses.LEFT)
                        token = Operator.NEGATE;
                }
            }
            
            // Check for implicit multiplication sign. If it's there, add an explicit one.
            if (token instanceof Operator 
                    && ((Operator) token).getAssociativity() == Operator.Associativity.UNARY_RIGHT
                    && (tokens.size() > 0)
                    && (tokens.get(tokens.size() - 1) instanceof Number 
                            || tokens.get(tokens.size() - 1) instanceof Constant))
                tokens.add(Operator.MULTIPLY);
            
            // Add token to the queue.
            tokens.add(token);
            
            // Increment index by length of token.
            i += token.toString().length();
        }
        return tokens;
    }

    
    /**
     * Parses the next token in the String representing the function.
     * 
     * @param expressionString The string of math to be parsed.
     * @param i The current index in the string.
     * 
     * @return The next token.
     * 
     * @throws ExpressionException Throws an exception if an unrecognized token is encountered.
     * 
     */
    private static Shuntable parseToken(String expressionString, int i) throws ExpressionException {
        
        char ch = expressionString.charAt(i);

        
        // Check for numbers.
        if (isNumber(ch)) {
            int start = i;
            i++;
            while (i < expressionString.length() && isNumber(expressionString.charAt(i)))
                i++;
            return new Number(expressionString.substring(start, i));
        }

        // Check for parentheses.
        for (Shuntable paren : Parentheses.values()) {
            if (Character.toString(ch).equals(paren.toString()))
                return paren;
        }

        // Check for a constant.
        for (Shuntable constant : Constant.values()) {
            if (isSubstringAtIndex(expressionString, constant.toString(), i))
                return constant;
        }

        // Finally, check for an operator.
        for (Shuntable operator : Operator.values()) {
            if (isSubstringAtIndex(expressionString, operator.toString(), i)) {
                return operator;
            }
        }

        // Throw an exception if this character matches nothing.
        throw new ExpressionException("Syntax error at index [" + i + "].");
    }
    
    
    /**
     * Checks if a given substring starts at the specified index in the parent string.
     * 
     * @param original Parent string.
     * @param sub Substring of the parent string.
     * @param index Index to check.
     * 
     * @return True if the substring starts at this index, false otherwise.
     */
    public static boolean isSubstringAtIndex(String original, String sub, int index) {
        if (original.length() - index < sub.length())
            return false;
        
        for (int i = 0; i < sub.length(); i++) {
            if (original.charAt(i + index) != sub.charAt(i))
                return false;
        }
        return true;
    }

    
    /**
     * Checks if a character is a number or radix point.
     * 
     * @param ch Character to check.
     * 
     * @return True if the character is a number or radix, false otherwise.
     */
    private static boolean isNumber(char ch) {
        return (Character.isDigit(ch) || ch == '.');
    }
}
