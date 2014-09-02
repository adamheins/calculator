package com.adamheins.expression;

import static org.junit.Assert.*;
import org.junit.Test;

import com.adamheins.expression.ExpressionException;
import com.adamheins.expression.ExpressionEvaluator;

/**
 * Tests for the <code>ExpressionEvaluator</code>.
 * 
 * @author Adam Heins
 * 
 */
public class ExpressionTests {

    @Test
    public void testEmpty() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate(""));
    }


    @Test
    public void testJustNumber() throws ExpressionException {
        assertEquals("3", ExpressionEvaluator.evaluate("3"));
    }


    @Test
    public void testAdditionWithIntegers() throws ExpressionException {
        assertEquals("17", ExpressionEvaluator.evaluate("12+5"));
    }


    @Test
    public void testAdditionWithDoubles() throws ExpressionException {
        assertEquals("17.8", ExpressionEvaluator.evaluate("12.6+5.2"));
    }


    @Test
    public void testSubtractionWithIntegers() throws ExpressionException {
        assertEquals("7", ExpressionEvaluator.evaluate("12-5"));
    }


    @Test
    public void testSubtractionWithDoubles() throws ExpressionException {
        assertEquals("7.4", ExpressionEvaluator.evaluate("12.6-5.2"));
    }


    @Test
    public void testMultiplicationWithIntegers() throws ExpressionException {
        assertEquals("60", ExpressionEvaluator.evaluate("12*5"));
    }


    @Test
    public void testMultiplicationWithDoubles() throws ExpressionException {
        assertEquals("65.52", ExpressionEvaluator.evaluate("12.6*5.2"));
    }


    @Test
    public void testDivisionEqualsInteger() throws ExpressionException {
        assertEquals("2", ExpressionEvaluator.evaluate("12/6"));
    }


    @Test
    public void testDivisionWithIntegersEqualsDouble()
            throws ExpressionException {
        assertEquals("1.5", ExpressionEvaluator.evaluate("15/10"));
    }


    @Test
    public void testDivisionWithDoublesEqualsDouble()
            throws ExpressionException {
        assertEquals("4.7", ExpressionEvaluator.evaluate("18.8/4"));
    }


    @Test
    public void testModuloWithIntegers() throws ExpressionException {
        assertEquals("5", ExpressionEvaluator.evaluate("12%7"));
    }


    @Test
    public void testModuloWithDoubles() throws ExpressionException {
        assertEquals("5.2", ExpressionEvaluator.evaluate("19.6%7.2"));
    }


    @Test
    public void testFactorial() throws ExpressionException {
        assertEquals("24", ExpressionEvaluator.evaluate("4!"));
    }


    @Test
    public void testScientificNotation() throws ExpressionException {
        assertEquals("200", ExpressionEvaluator.evaluate("2E2"));
    }


    @Test
    public void testExponentiation() throws ExpressionException {
        assertEquals("27", ExpressionEvaluator.evaluate("3^3"));
    }


    @Test
    public void testSqrt() throws ExpressionException {
        assertEquals("2", ExpressionEvaluator.evaluate("sqrt4"));
    }


    @Test
    public void testRoot() throws ExpressionException {
        assertEquals("3", ExpressionEvaluator.evaluate("3rt27"));
    }


    @Test
    public void testLn() throws ExpressionException {
        assertEquals("1", ExpressionEvaluator.evaluate("lne"));
    }


    @Test
    public void testLog() throws ExpressionException {
        assertEquals("4", ExpressionEvaluator.evaluate("log10000"));
    }


    @Test
    public void testSin() throws ExpressionException {
        assertEquals("1", ExpressionEvaluator.evaluate("sin(pi/2)"));
    }


    @Test
    public void testCos() throws ExpressionException {
        assertEquals("-1", ExpressionEvaluator.evaluate("cospi"));
    }


    @Test
    public void testTan() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate("tan0"));
    }


    @Test(expected = ExpressionException.class)
    public void testTanEqualToInfinity() throws ExpressionException {
        ExpressionEvaluator.evaluate("tan(pi/2)");
    }


    @Test
    public void testToDegrees() throws ExpressionException {
        assertEquals("180", ExpressionEvaluator.evaluate("dpi"));
    }
    
    
    @Test
    public void testToDegreesWithZero() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate("d0"));
    }


    @Test
    public void testToRadians() throws ExpressionException {
        assertEquals("1.5707963267948966192",
                ExpressionEvaluator.evaluate("r90"));
    }
    
    
    @Test
    public void testToRadiansWithZero() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate("r0"));
    }


    @Test
    public void testUnaryNegative() throws ExpressionException {
        assertEquals("-5", ExpressionEvaluator.evaluate("-5"));
    }


    @Test
    public void testUnaryNegativeWithBinaryOperator()
            throws ExpressionException {
        assertEquals("-12", ExpressionEvaluator.evaluate("4*-3"));
    }


    @Test
    public void testUnaryNegativeWithUnaryOperator() throws ExpressionException {
        assertEquals("-2", ExpressionEvaluator.evaluate("-log100"));
    }


    @Test
    public void testASin() throws ExpressionException {
        assertEquals("1.5707963267948966192",
                ExpressionEvaluator.evaluate("asin1"));
    }


    @Test(expected = ExpressionException.class)
    public void testASinNaN() throws ExpressionException {
        ExpressionEvaluator.evaluate("asin2");
    }


    @Test
    public void testACos() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate("acos0"));
    }


    @Test(expected = ExpressionException.class)
    public void testACosNaN() throws ExpressionException {
        ExpressionEvaluator.evaluate("acos2");
    }


    @Test
    public void testATan() throws ExpressionException {
        assertEquals("0.78539816339744830962",
                ExpressionEvaluator.evaluate("atan1"));
    }


    @Test
    public void testATanZero() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate("atan0"));
    }


    @Test
    public void testSinh() throws ExpressionException {
        assertEquals("1.1752011936438014569",
                ExpressionEvaluator.evaluate("sinh1"));
    }


    @Test
    public void testCosh() throws ExpressionException {
        assertEquals("1.5430806348152437785",
                ExpressionEvaluator.evaluate("cosh1"));
    }


    @Test
    public void testTanh() throws ExpressionException {
        assertEquals("0.76159415595576488812",
                ExpressionEvaluator.evaluate("tanh1"));
    }


    @Test
    public void testWithSpaces() throws ExpressionException {
        assertEquals("4", ExpressionEvaluator.evaluate("2 + 2"));
    }


    @Test
    public void testWithTabs() throws ExpressionException {
        assertEquals("4", ExpressionEvaluator.evaluate("2   +   2"));
    }


    @Test
    public void testWithMixedWhiteSpace() throws ExpressionException {
        assertEquals("4", ExpressionEvaluator.evaluate("    2 +   2  "));
    }


    @Test
    public void testHighPrecedenceLowPrecedence() throws ExpressionException {
        assertEquals("6", ExpressionEvaluator.evaluate("sqrt4*3"));
    }


    @Test
    public void testLowPrecedenceHighPrecedence() throws ExpressionException {
        assertEquals("6", ExpressionEvaluator.evaluate("3*sqrt4"));
    }


    @Test
    public void testSamePrecedence() throws ExpressionException {
        assertEquals("-1", ExpressionEvaluator.evaluate("3-2-2"));
    }


    @Test
    public void testResultWithScientificNotation() throws ExpressionException {
        assertEquals("1E102", ExpressionEvaluator.evaluate("100E100"));
    }


    @Test
    public void testResultHasNoScientificNotation() throws ExpressionException {
        assertEquals("314.15926535897932385", ExpressionEvaluator.evaluate("pi*100"));
    }
    
    
    @Test
    public void testBracketsNoOperators() throws ExpressionException {
        assertEquals("3", ExpressionEvaluator.evaluate("(3)"));
    }
    
    
    @Test
    public void testBracketsWithSimpleOperators() throws ExpressionException {
        assertEquals("18", ExpressionEvaluator.evaluate("(5+4)*2"));
    }
    
    
    @Test
    public void testBracketsWithUnaryNegative() throws ExpressionException {
        assertEquals("-4", ExpressionEvaluator.evaluate("-(2+2)"));
    }
    
    
    @Test
    public void testInversionDegreesToRadiansToDegrees() throws ExpressionException {
        assertEquals("90", ExpressionEvaluator.evaluate("dr90"));
    }
    
    
    @Test
    public void testInversionWithTrigOperators() throws ExpressionException {
        assertEquals("0", ExpressionEvaluator.evaluate("asinsinpi"));
    }
    
    
    @Test
    public void testImplicitMultiplication() throws ExpressionException {
        assertEquals("2", ExpressionEvaluator.evaluate("2sinr90"));
    }
    
    
    @Test
    public void testImplicitMultiplication2() throws ExpressionException {
        assertEquals("6", ExpressionEvaluator.evaluate("2log1000"));
    }
}
