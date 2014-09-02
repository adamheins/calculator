package com.adamheins.expression;

import java.util.Deque;
import org.apfloat.Apfloat;

/**
 * Implemented by math tokens that can be evaluated.
 */
public interface Evaluatable {
    
    /**
     * Evaluates this Evaluatable and pushes the result onto the value stack.
     * 
     * @param valueStack Stack of values.
     * 
     * @throws ExpressionException If the operation causes a math error.
     */
    public void evaluate (Deque<Apfloat> valueStack) throws ExpressionException;
}
