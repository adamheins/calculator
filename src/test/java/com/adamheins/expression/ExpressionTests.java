package test.java.com.adamheins.expression;


import static org.junit.Assert.*;

import org.junit.Test;

import main.java.com.adamheins.expression.ExpressionEvaluator;
import main.java.com.adamheins.expression.ExpressionException;


/**
 * 
 * @author Adam
 * Cases:
 * every operation
 * testing precedence against same
 * testing precedence against higher/lower
 *
 */


public class ExpressionTests {
	
    /** Results are given a small error bound to account for floating point inaccuracies. */
	private final double ERROR = 0.000000000000001;
	
	
	@Test
	public void testEmpty() throws ExpressionException {
		assertEquals(0, ExpressionEvaluator.evaluate(""), 0);
	}
	
	
	@Test
	public void testJustNumber() throws ExpressionException {
		assertEquals(3, ExpressionEvaluator.evaluate("3"), 0);
	}
	
	
	@Test
	public void testAdditionWithIntegers() throws ExpressionException {
		assertEquals(17, ExpressionEvaluator.evaluate("12+5"), 0);
	}
	
	
	@Test
	public void testAdditionWithDoubles() throws ExpressionException {
		assertEquals(17.8, ExpressionEvaluator.evaluate("12.6+5.2"), ERROR);
	}
	
	
	@Test
	public void testSubtractionWithIntegers() throws ExpressionException {
		assertEquals(7, ExpressionEvaluator.evaluate("12-5"), 0);
	}
	
	
	@Test
	public void testSubtractionWithDoubles() throws ExpressionException {
		assertEquals(7.4, ExpressionEvaluator.evaluate("12.6-5.2"), ERROR);
	}
	
	
	@Test
	public void testMultiplicationWithIntegers() throws ExpressionException {
		assertEquals(60, ExpressionEvaluator.evaluate("12*5"), 0);
	}
	
	
	@Test
	public void testMultiplicationWithDoubles() throws ExpressionException {
		assertEquals(65.52, ExpressionEvaluator.evaluate("12.6*5.2"), ERROR);
	}
	
	
	@Test
	public void testDivisionEqualsInteger() throws ExpressionException {
		assertEquals(2, ExpressionEvaluator.evaluate("12/6"), 0);
	}
	
	
	@Test
	public void testDivisionWithIntegersEqualsDouble() throws ExpressionException {
		assertEquals(1.5, ExpressionEvaluator.evaluate("15/10"), ERROR);
	}
	
	
	@Test
	public void testDivisionWithDoublesEqualsDouble() throws ExpressionException {
		assertEquals(4.7, ExpressionEvaluator.evaluate("18.8/4"), ERROR);
	}
	
	
	@Test
	public void testModuloWithIntegers() throws ExpressionException {
		assertEquals(5, ExpressionEvaluator.evaluate("12%7"), 0);
	}
	
	
	@Test
	public void testModuloWithDoubles() throws ExpressionException {
		assertEquals(5, ExpressionEvaluator.evaluate("12%7"), 0);
	}
	
	
	@Test
	public void testFactorial() throws ExpressionException {
		assertEquals(24, ExpressionEvaluator.evaluate("4!"), 0);
	}
	
	
	@Test
	public void testScientificNotation() throws ExpressionException {
		assertEquals(200, ExpressionEvaluator.evaluate("2E2"), 0);
	}
	
	
	@Test
	public void testExponentiation() throws ExpressionException {
		assertEquals(27, ExpressionEvaluator.evaluate("3^3"), 0);
	}
	
	
	@Test
	public void testSqrt() throws ExpressionException {
		assertEquals(2, ExpressionEvaluator.evaluate("sqrt4"), ERROR);
	}
	
	
	@Test
	public void testRoot() throws ExpressionException {
		assertEquals(3, ExpressionEvaluator.evaluate("3rt27"), ERROR);
	}
	
	
	@Test
	public void testLn() throws ExpressionException {
		assertEquals(1, ExpressionEvaluator.evaluate("lne"), ERROR);
	}
	
	
	@Test
	public void testLog() throws ExpressionException {
		assertEquals(4, ExpressionEvaluator.evaluate("log10000"), 0);
	}
	
	
	@Test
	public void testSin() throws ExpressionException {
		assertEquals(1, ExpressionEvaluator.evaluate("sin(pi/2)"), 0);
	}
	
	
	@Test
	public void testCos() throws ExpressionException {
		assertEquals(-1, ExpressionEvaluator.evaluate("cospi"), 0);
	}
	
	
	@Test
	public void testTan() throws ExpressionException {
		assertEquals(0, ExpressionEvaluator.evaluate("tan0"), 0);
	}
	
	
	@Test
	public void testTanEqualToInfinity() throws ExpressionException {
		assertEquals(Double.POSITIVE_INFINITY, ExpressionEvaluator.evaluate("tan(pi/2)"), 0);
	}
	
	
	@Test
	public void testToDegrees() throws ExpressionException {
		assertEquals(180, ExpressionEvaluator.evaluate("dpi"), 0);
	}
	
	
	@Test
	public void testToRadians() throws ExpressionException {
		assertEquals(Math.PI / 2, ExpressionEvaluator.evaluate("r90"), 0);
	}
	
	
	@Test
	public void testUnaryNegative() throws ExpressionException {
		assertEquals(-5, ExpressionEvaluator.evaluate("-5"), 0);
	}
	
	
	@Test
	public void testUnaryNegativeWithBinaryOperator() throws ExpressionException {
		assertEquals(-12, ExpressionEvaluator.evaluate("4*-3"), 0);
	}
	
	
	@Test
	public void testUnaryNegativeWithUnaryOperator() throws ExpressionException {
		assertEquals(-2, ExpressionEvaluator.evaluate("-log100"), 0);
	}
	
	
	@Test
	public void testASin() throws ExpressionException {
		assertEquals(Math.PI / 2, ExpressionEvaluator.evaluate("asin1"), 0);
	}
	
	
	@Test
	public void testASinNaN() throws ExpressionException {
		assertEquals(Double.NaN, ExpressionEvaluator.evaluate("asin2"), 0);
	}
	
	
	@Test
	public void testACos() throws ExpressionException {
		assertEquals(0, ExpressionEvaluator.evaluate("acos0"), 0);
	}
	
	
	@Test
	public void testACosNaN() throws ExpressionException {
		assertEquals(Double.POSITIVE_INFINITY, ExpressionEvaluator.evaluate("acos2"), 0);
	}
	

	@Test
	public void testATan() throws ExpressionException {
		assertEquals(Math.PI / 4, ExpressionEvaluator.evaluate("atan1"), 0);
	}
	

	@Test
	public void testATanZero() throws ExpressionException {
		assertEquals(0, ExpressionEvaluator.evaluate("atan0"), 0);
	}
	
}
