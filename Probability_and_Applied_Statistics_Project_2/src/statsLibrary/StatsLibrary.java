package statsLibrary;

import java.math.BigInteger;

/**
 * The StatsLibrary class contains multiple functions associated with statistics.
 * 
 * @author Jake Cubernot
 */
public class StatsLibrary {

	
	/** 
	 * Finds the factorial of a value n.
	 * (Used https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html to better understand the BigInteger class)
	 * 
	 * @param n The integer value that the user wants to find the factorial result.
	 * @return Factorial result as BigInteger.
	 */
	private BigInteger findFactorial(int n) {
		BigInteger factorialResult = BigInteger.ONE;
		for(int i = n; i > 0; i--) {
			factorialResult = factorialResult.multiply(BigInteger.valueOf(i));
		}
		return factorialResult;
	}


	/**
	 * Finds the poisson distribution of a given set of variables.
	 * 
	 * @param x Number of successes
	 * @param lambda Rate of success
	 * @return The double result of the poisson distribution.
	 */
	public double findPoissonDistribution(int x, int lambda) {
		double poissonDistributionNumerator = Math.pow(lambda, x) * Math.pow(Math.E, -1 * lambda);
		return poissonDistributionNumerator/findFactorial(x).intValue();
	}

	/**
	 * Finds the Chebyshev's theorem of a given set of variable.
	 * 
	 * @param k for any number k greater than 1
	 * @return The double result of the Chebyshev's theorem.
	 */
	public double findChebyshevsTheorem(int k) {
		return (1 - (1 / Math.pow(k, 2)));
	}
	
	/**
	 * Finds the uniform distribution of a given set of variables.
	 * 
	 * @param a Lowest value of x 
	 * @param b Highest value of x
	 * @return The double result of the uniform distribution.
	 */
	public double findUniformDistribution(double a, double b) {
		return (1/(b - a));
	}
}
