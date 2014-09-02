package com.adamheins.expression;

import java.util.Deque;
import java.util.Queue;

import org.apfloat.Apfloat;


/**
 * All constant tokens.
 */
public enum Constant implements Shuntable, Evaluatable {
    
    /** Pi constant. */
    PI("pi", "3.141592653589793238462643383279"),
    
    /** Euler's constant. */
    E("e", "2.718281828459045235360287471352");
    
    
    // Symbol representing the constant.
    private String symbol;
    
    // The value of the constant.
    private Apfloat value;
    
    
    private Constant(String symbol, String value) {
        this.symbol = symbol;
        this.value = new Apfloat(value);
    }
    
    
    @Override
    public void evaluate(Deque<Apfloat> valueStack) {
        valueStack.push(value);
    }
    
    
    @Override
    public void shunt(Queue<Evaluatable> outputQueue, Deque<Stackable> operatorStack) {
        outputQueue.add(this);
    }
    
    
    @Override
    public String toString() {
        return symbol;
    }
}
