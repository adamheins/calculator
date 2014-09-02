package com.adamheins.expression;

import java.math.RoundingMode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;


/**
 * Interprets and evaluates strings of math.
 */
public class ExpressionEvaluator {
    
    /** Internal precision of calculations. Presented value is one less than this. */
    public static final int PRECISION = 21;  

    
    /**
     * Converts a list of tokens from infix notation to postfix notation.
     * 
     * @param tokens
     * 
     * @return
     */
    private static Queue<Evaluatable> convertToPostfix(List<Shuntable> tokens) {

        Queue<Evaluatable> outputQueue = new LinkedList<>();
        Deque<Stackable> operatorStack = new LinkedList<>();

        // Shunt each token.
        for(Shuntable token : tokens)
            token.shunt(outputQueue, operatorStack);

        // Add everything remaining in the operator stack to the output queue.
        while (operatorStack.size() > 0)
            outputQueue.add((Operator)operatorStack.pop());

        return outputQueue;
    }

    
    /**
     * Evaluates this mathematical expression.
     * 
     * @param expressionString The <code>String</code> of math to be evaluated.
     * 
     * @return The value of the expression.
     * 
     * @throws ExpressionException Throws an exception if a syntax or math error is encountered.
     */
    public static String evaluate(String expressionString) throws ExpressionException {

        // Empty string evaluates to zero.
        if (expressionString.isEmpty())
            return "0";
        
        // Parse tokens from the string.
        List<Shuntable> tokens = Tokenizer.parseExpression(expressionString);

        // Convert to postfix notation.
        Queue<Evaluatable> postfixExpression = convertToPostfix(tokens);
        
        // Evaluate the postfix expression.
        Deque<Apfloat> valueStack = new LinkedList<Apfloat>();
        try {
            while (postfixExpression.size() > 0) {
                Evaluatable op = postfixExpression.remove();
                //System.out.println(op.toString());
                op.evaluate(valueStack);
            }
        } catch (ArithmeticException e) {
            throw new ExpressionException(e.getMessage());
        }

        // Format the result.
        return format(valueStack.pop());
    }
    
    
    /**
     * Format the numeric result.
     * 
     * @param result The result of evaluation.
     * 
     * @return The formatted result.
     */
    private static String format(Apfloat result) {
        
        // Scientific notation is used if the result won't otherwise fit into 20 places.
        Apfloat roundedResult = ApfloatMath.round(result, PRECISION - 1, RoundingMode.HALF_UP);
        String prettyString = roundedResult.toString(true);
        if (prettyString.length() > PRECISION + 3) {
            String uglyString = roundedResult.toString(false);
            return uglyString.replace('e', 'E');
        }
        return prettyString;
    }
}
