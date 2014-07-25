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
	
	
	@Test
	public void testAddition() throws ExpressionException {
		assertEquals(17, ExpressionEvaluator.evaluate("12+5"), 0);
	}
	
	
	@Test
	public void testSubtraction() throws ExpressionException {
		assertEquals(7, ExpressionEvaluator.evaluate("12-5"), 0);
	}
	
	
	@Test
	public void testMultiplication() throws ExpressionException {
		assertEquals(60, ExpressionEvaluator.evaluate("12*5"), 0);
	}
	
	
	@Test
	public void testDivisionEven() throws ExpressionException {
		assertEquals(2, ExpressionEvaluator.evaluate("12/6"), 0);
	}
	
	
	@Test
	public void testDivisionOdd() throws ExpressionException {
		assertEquals(1.5, ExpressionEvaluator.evaluate("15/10"), 0);
	}
	
	
	@Test
	public void testModulo() throws ExpressionException {
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
		assertEquals(2, ExpressionEvaluator.evaluate("sqrt4"), 0);
	}
	
	
	@Test
	public void testRoot() throws ExpressionException {
		assertEquals(3, ExpressionEvaluator.evaluate("3rt27"), 0);
	}
	
	
	@Test
	public void testLn() throws ExpressionException {
		assertEquals(1, ExpressionEvaluator.evaluate("lne"), 0);
	}
	
	
	@Test
	public void testLog() throws ExpressionException {
		assertEquals(4, ExpressionEvaluator.evaluate("log10000"), 0);
	}
	
	
	@Test
	public void testSin() throws ExpressionException {
		assertEquals(1, ExpressionEvaluator.evaluate("sin(pi/2)"), 0);
	}
	
}
