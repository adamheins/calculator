package com.adamheins.expression;

import com.adamheins.expression.Token;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.ApfloatRuntimeException;
import org.apfloat.ApintMath;


/**
 * Handles math operators for the <code>ExpressionEvaluator</code> class.
 * 
 * @author Adam Heins
 *
 */
public class ExpressionMath {


    /**
     * Performs a mathematical operation that requires a single numerical input.
     * 
     * @param operator The mathematical operator.
     * @param num The number being operated upon.
     * 
     * @return The result of the mathematical operation.
     * 
     * @throws ExpressionException Throws an exception is a math error occurs.
     */
    public static Apfloat doOperation(Token operator, Apfloat num) throws ExpressionException {

        String operatorValue = operator.getValue();

        try {
            if (operatorValue.equals("ln"))
                return ApfloatMath.log(num);
            else if (operatorValue.equals("log"))
                return ApfloatMath.log(num, new Apfloat("10"));
            else if (operatorValue.equals("sin"))
                return ApfloatMath.sin(num);
            else if (operatorValue.equals("cos"))
                return ApfloatMath.cos(num);
            else if (operatorValue.equals("tan"))
                return ApfloatMath.tan(num);
            else if (operatorValue.equals("asin"))
                return ApfloatMath.asin(num);
            else if (operatorValue.equals("acos"))
                return acos(num);
            else if (operatorValue.equals("atan"))
                return ApfloatMath.atan(num);
            else if (operatorValue.equals("!"))
                return factorial(num);
            else if (operatorValue.equals("sqrt"))
                return ApfloatMath.sqrt(num);
            else if (operatorValue.equals("r"))
                return ApfloatMath.toRadians(num);
            else if (operatorValue.equals("d"))
                return ApfloatMath.toDegrees(num);
            else if (operatorValue.equals("sinh"))
                return ApfloatMath.sinh(num);
            else if (operatorValue.equals("cosh"))
                return ApfloatMath.cosh(num);
            else if (operatorValue.equals("tanh"))
                return ApfloatMath.tanh(num);
            else if (operatorValue.equals("u-"))
                return num.negate();
            
            throw new ExpressionException("Math error.");
        
        } catch (ArithmeticException ae) {
            throw new ExpressionException("Math error.");
        } catch (ApfloatRuntimeException are) {
            throw new ExpressionException("Math error.");
        }
        
    }

    
    /**
     * Performs a mathematical operation that requires two numerical inputs.
     * 
     * @param operator The mathematical operator.
     * @param leftNum The number to the left of the operator.
     * @param rightNum The number to the right of the operator.
     * 
     * @return The result of the mathematical operation.
     * 
     * @throws ExpressionException Throws an exception if a math error occurs.
     */
    public static Apfloat doOperation(Token operator, Apfloat leftNum, Apfloat rightNum)
            throws ExpressionException {

        String operatorValue = operator.getValue();

        try {
            if (operatorValue.equals("+"))
                return leftNum.add(rightNum);
            else if (operatorValue.equals("-"))
                return leftNum.subtract(rightNum);
            else if (operatorValue.equals("*"))
                return leftNum.multiply(rightNum);
            else if (operatorValue.equals("/"))
                return leftNum.divide(rightNum);
            else if (operatorValue.equals("%"))
                return leftNum.mod(rightNum);
            else if (operatorValue.equals("^"))
                return ApfloatMath.pow(leftNum, rightNum);    
            else if (operatorValue.equals("E"))
                return leftNum.multiply(ApfloatMath.pow(new Apfloat("10", ExpressionEvaluator.PRECISION), rightNum));
            else if (operatorValue.equals("rt"))
                return ApfloatMath.root(rightNum, leftNum.longValue());
            
            throw new ExpressionException("Math error.");
        
        } catch (ArithmeticException ae) {
            throw new ExpressionException("Math error.");
        } catch (ApfloatRuntimeException are) {
            throw new ExpressionException("Math error.");
        }
    }

    
    /**
     * Computes the factorial of a number.
     * 
     * @param num Number to raise to a factorial.
     * 
     * @return The result of the factorial operation.
     * 
     * @throws ExpressionException Throws an exception if the number is not an integer.
     */
    private static Apfloat factorial(Apfloat num) throws ExpressionException {

        String numStr = num.toString();
        
        // Check if the number is an integer.
        if (numStr.contains("."))
            throw new ExpressionException("Math error.");

        return new Apfloat(ApintMath.factorial(Long.parseLong(numStr)).toString());
        
    }
    
    
    /**
     * Computes the inverse cosine of a number.
     * There is an error is the Apfloat library where the acos function throws an error when
     * taking an input of 0. This function corrects that behaviour to return the correct result
     * of 0.
     * 
     * @param num Number to which to apply inverse cosine function.
     * 
     * @return Inverse cosine of <code>num</code>.
     */
    private static Apfloat acos(Apfloat num) {
        if (num.intValue() == 0)
            return num;
        return ApfloatMath.acos(num);
    }
}
