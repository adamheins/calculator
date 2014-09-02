package com.adamheins.expression;

import java.util.Deque;
import java.util.Queue;

import org.apfloat.Apfloat;

/**
 * A number token.
 */
public class Number implements Shuntable, Evaluatable {
    
    // Value of the number.
    private String value;
    
    
    /**
     * Create a new Number.
     * 
     * @param value The value of the number.
     */
    public Number(String value) {
        this.value = value;
    }

    @Override
    public void evaluate(Deque<Apfloat> valueStack) {
        valueStack.push(new Apfloat(value, ExpressionEvaluator.PRECISION));
    }

    @Override
    public void shunt(Queue<Evaluatable> outputQueue, Deque<Stackable> operatorStack) {
        outputQueue.add(this);       
    }
    
    @Override
    public String toString() {
        return value;
    }
}
