package com.adamheins.expression;

import java.util.Deque;
import java.util.Queue;

/**
 * Implemented by math tokens that can be converted to postfix notation using the shunting-yard
 * algorithm.
 */
public interface Shuntable {
    
    /**
     * Place this token in the correct postfix location using the shunting-yard algorithm.
     * 
     * @param outputQueue Queue of tokens that have been processed into postfix notation.
     * @param operatorStack Stack of operators waiting until they can be placed on the output queue.
     */
    public void shunt(Queue<Evaluatable> outputQueue, Deque<Stackable> operatorStack);
}
