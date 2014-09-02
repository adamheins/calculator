package com.adamheins.expression;

import java.util.Deque;
import java.util.Queue;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.ApintMath;

/**
 * All operation tokens.
 */
public enum Operator implements Stackable, Evaluatable {
    
    PLUS("+", Associativity.BINARY_LEFT, Precedence.ADDITION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(left.add(right));
        }
    }, MINUS("-", Associativity.BINARY_LEFT, Precedence.ADDITION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(left.subtract(right));
        }        
    }, NEGATE("-", Associativity.UNARY_RIGHT, Precedence.EXPONENTIATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(valueStack.pop().negate());
        }        
    }, MULTIPLY("*", Associativity.BINARY_LEFT, Precedence.MULTIPLICATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(left.multiply(right));
        }
    }, DIVIDE("/", Associativity.BINARY_LEFT, Precedence.MULTIPLICATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(left.divide(right));
        }
    }, MODULO("%", Associativity.BINARY_LEFT, Precedence.MULTIPLICATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(left.mod(right));
        }
    }, POW("^", Associativity.BINARY_RIGHT, Precedence.EXPONENTIATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(ApfloatMath.pow(left, right));
        }
    }, SCI_NOTATION("E", Associativity.BINARY_RIGHT, Precedence.SCI_NOT) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(ApfloatMath.scale(left, right.longValue()));
        }
    }, ROOT("rt", Associativity.BINARY_RIGHT, Precedence.EXPONENTIATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat right = valueStack.pop();
            Apfloat left = valueStack.pop();
            valueStack.push(ApfloatMath.root(right, left.longValue()));
        }
    }, LOG("log", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.log(valueStack.pop(), new Apfloat("10")));
        }
    }, LN("ln", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.log(valueStack.pop()));
        }
    }, SINH("sinh", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.sinh(valueStack.pop()));
        }
    }, COSH("cosh", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.cosh(valueStack.pop()));
        }
    }, TANH("tanh", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.tanh(valueStack.pop()));
        }
    }, SIN("sin", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.sin(valueStack.pop()));
        }
    }, COS("cos", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.cos(valueStack.pop()));
        }
    }, TAN("tan", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.tan(valueStack.pop()));
        }
    }, ASIN("asin", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.asin(valueStack.pop()));
        }
    }, ACOS("acos", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat num = valueStack.pop();
            if (num.intValue() == 0)
                valueStack.push(num);
            else
                valueStack.push(ApfloatMath.acos(num));
        }
    }, ATAN("atan", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.atan(valueStack.pop()));
        }
    }, FACTORIAL("!", Associativity.UNARY_LEFT, Precedence.FACTORIAL) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) throws ExpressionException {
            String numStr = valueStack.pop().toString();
            if (numStr.contains("."))
                throw new ExpressionException("Math error.");
            valueStack.push(new Apfloat(ApintMath.factorial(Long.parseLong(numStr)).toString()));
        }
    }, SQRT("sqrt", Associativity.UNARY_RIGHT, Precedence.EXPONENTIATION) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            valueStack.push(ApfloatMath.sqrt(valueStack.pop()));
        }
    }, TO_RADIANS("r", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat num = valueStack.pop();
            if (num.intValue() == 0)
                valueStack.push(num);
            else
                valueStack.push(ApfloatMath.toRadians(num));
        }
    }, TO_DEGREES("d", Associativity.UNARY_RIGHT, Precedence.TRIG) {
        @Override
        public void evaluate(Deque<Apfloat> valueStack) {
            Apfloat num = valueStack.pop();
            if (num.intValue() == 0)
                valueStack.push(num);
            else
                valueStack.push(ApfloatMath.toDegrees(num));
        }
    };

    
    // Symbol representing the Operator.
    private final String symbol;
    
    // Associativity of the Operator.
    private final Associativity associativity;
    
    // Precedence of the Operator.
    private final Precedence precedence;
    
    private Operator(String symbol, Associativity associativity, Precedence precedence) {
        this.symbol = symbol;
        this.associativity = associativity;
        this.precedence = precedence;
    }
    
    
    /**
     * Get the associativity of the Operator.
     * 
     * @return Associativity of the Operator.
     */
    public Associativity getAssociativity() {
        return associativity;
    }

    
    @Override
    public void shunt(Queue<Evaluatable> outputQueue, Deque<Stackable> operatorStack) {
        while (operatorStack.size() != 0) {
            Stackable prev = operatorStack.peek();
            if (prev == Parentheses.LEFT)
                break;
            Operator prevOp = (Operator) prev;
            if (!((associativity == Associativity.BINARY_LEFT 
                    && precedence.compareTo(prevOp.precedence) <= 0) 
                    || precedence.compareTo(prevOp.precedence) < 0))
                break;
            outputQueue.add((Operator)operatorStack.pop());
        }
        operatorStack.push(this);        
    }

    
    @Override
    public String toString() {
        return symbol;
    }
    
    
    /**
     * Associativity of an Operator.
     */
    enum Associativity {
        BINARY_LEFT, BINARY_RIGHT, UNARY_LEFT, UNARY_RIGHT
    }
    
    
    /**
     * Precedence of an Operator. Higher precedence is on the right.
     */
    enum Precedence {
        ADDITION, MULTIPLICATION, EXPONENTIATION, TRIG, FACTORIAL, SCI_NOT;
    }
}
