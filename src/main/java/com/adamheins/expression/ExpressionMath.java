package main.java.com.adamheins.expression;

import java.util.HashMap;
import java.util.Map;


/**
 * Handles math operators for the <code>ExpressionEvaluator</code> class.
 * 
 * @author Adam Heins
 *
 */
public class ExpressionMath {

    
    /**
     * Due to the nature of floating point number representations, Java
     * incorrectly evaluates certain "round" values of trigonometric functions and a
     * small error is introduced. This look-up table corrects that behaviour.
     */
    private final static Map<Double, double[]> TRIG_MAPPINGS = new HashMap<Double, double[]>();
    static {
        TRIG_MAPPINGS.put(0d, new double[] { 0, 1, 0 });
        TRIG_MAPPINGS.put(Math.PI / 2, new double[] { 1, 0, Double.POSITIVE_INFINITY });
        TRIG_MAPPINGS.put(Math.PI, new double[] { 0, -1, 0 });
        TRIG_MAPPINGS.put(3 * Math.PI / 2, new double[] { -1, 0, Double.NEGATIVE_INFINITY });
    }

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
    public static double doOperation(Token operator, double num) throws ExpressionException {

        String operatorValue = operator.getValue();

        if (operatorValue.equals("ln"))
            return Math.log(num);
        else if (operatorValue.equals("log"))
            return Math.log(num) / Math.log(10);
        else if (operatorValue.equals("sin"))
            return TRIG_MAPPINGS.containsKey(num) ? TRIG_MAPPINGS.get(num)[0] : Math.sin(num);
        else if (operatorValue.equals("cos"))
            return TRIG_MAPPINGS.containsKey(num) ? TRIG_MAPPINGS.get(num)[1] : Math.cos(num);
        else if (operatorValue.equals("tan"))
            return TRIG_MAPPINGS.containsKey(num) ? TRIG_MAPPINGS.get(num)[2] : Math.tan(num);
        else if (operatorValue.equals("asin"))
            return Math.asin(num);
        else if (operatorValue.equals("acos"))
            return Math.acos(num);
        else if (operatorValue.equals("atan"))
            return Math.atan(num);
        else if (operatorValue.equals("!"))
            return fact(num);
        else if (operatorValue.equals("sqrt"))
            return Math.sqrt(num);
        else if (operatorValue.equals("r"))
            return Math.toRadians(num);
        else if (operatorValue.equals("d"))
            return Math.toDegrees(num);
        else if (operatorValue.equals("sinh"))
            return Math.sinh(num);
        else if (operatorValue.equals("cosh"))
            return Math.cosh(num);
        else if (operatorValue.equals("tanh"))
            return Math.tanh(num);
        else if (operatorValue.equals("u-"))
            return -num;

        throw new ExpressionException("Math error.");
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
     * @throws ExpressionException
     */
    public static double doOperation(Token operator, double leftNum, double rightNum)
            throws ExpressionException {

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
        else if (operatorValue.equals("E"))
            return leftNum * Math.pow(10, rightNum);
        else if (operatorValue.equals("rt"))
            return Math.pow(rightNum, 1.0 / leftNum);

        throw new ExpressionException("Math error.");
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
    private static double fact(double num) throws ExpressionException {

        // Ensure that the number is an integer.
        if ((int) num != num)
            throw new ExpressionException("Math error.");

        // Calculate factorial.
        double result = 1;
        for (int i = 2; i <= num; i++)
            result *= i;
        return result;
    }
}
