package com.adamheins.expression;

import java.util.Deque;
import java.util.Queue;


/**
 * Parenthesis tokens.
 */
public enum Parentheses implements Stackable, Shuntable {
    LEFT("(") {
        @Override
        public void shunt(Queue<Evaluatable> outputQueue, Deque<Stackable> operatorStack) {
            operatorStack.push(this);
        }
    },
    RIGHT(")") {
        @Override
        public void shunt(Queue<Evaluatable> outputQueue, Deque<Stackable> operatorStack) {
            Stackable poppedToken;
            while ((poppedToken = operatorStack.pop()) != LEFT)
                outputQueue.add((Evaluatable)poppedToken);
        }
    };
    
    // Symbol representing this parenthesis.
    private final String symbol;
    
    
    private Parentheses(String symbol) {
        this.symbol = symbol;
    }
    
    
    @Override
    public String toString() {
        return symbol;
    }   
}
