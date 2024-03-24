package statsLibrary;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * The StatsLibrary class contains multiple functions associated with statistics.
 * 
 * @author Jake Cubernot
 */
public class StatsLibrary {
	// Probability_and_Applied_Statistics_Project_1 Section


	/** 
	 * Converts an int[] to ArrayList<Integer>.
	 * 
	 * @param userList The list that the user wants to convert to an Arraylist<Integer>.
	 * @return The converted int[] to ArrayList<Integer>.
	 */
	private ArrayList<Integer> listToArrayList(int[] userList) {
		ArrayList<Integer> newArrayList = new ArrayList<Integer>();
		for(int i = 0; i < userList.length; i++) {
			newArrayList.add(userList[i]);
		}
		return newArrayList;
	}

	
	/** 
	 * Converts an int[] to a String that shows a visual representation of the list, int[].
	 * 
	 * @param userList The int[] that the user wants to convert to a String.
	 * @return The String that shows a visual representation of the list, int[].
	 */
	public String getList(int[] userList) {
		String userListToString = "{";
		for (int i = 0; i < userList.length; i++) {
			if (i == userList.length - 1) {
				userListToString += (String.valueOf(userList[i]) + "}");
			} else {
				userListToString += (String.valueOf(userList[i]) + ", ");
			}
		}
		return userListToString;
	}

    
	/** 
	 * Finds the mean of a set or a list of integers.
	 * (Used https://www.cuemath.com/mean-median-mode-formula/ to get mathamatical formulas
	 * and to help me get a better understanding of the functions.)
	 * 
	 * @param userList The list that the user wants to find the mean result.
	 * @return The result of the mean as a double.
	 */
    public double findMean(int[] userList) {
		double sum = 0;
		for(int i = 0; i < userList.length; i++) {
			sum = userList[i] + sum;
		}
		
		double result = sum / userList.length;
		
		return result;
	}


	/** 
	 * Finds the median of a set or a list of integers.
	 * (Used https://www.cuemath.com/mean-median-mode-formula/ to get mathamatical formulas 
	 * and to help me get a better understanding of the functions.)
	 * 
	 * @param userList The list that the user wants to find the median result.
	 * @return The result of the median as a double.
	 */
	public double findMedian(int[] userList) {
		ArrayList<Integer> orderedList = listToArrayList(userList);
		orderedList.sort(null);

		if(orderedList.size() % 2 == 0) {
			return ((orderedList.get((orderedList.size() / 2))) + 
			orderedList.get((orderedList.size() / 2) - 1)) / 2.0;
		} else {
			return orderedList.get(orderedList.size() / 2);
		}
	}


	/** 
	 * Finds the mode of a set or a list of integers.
	 * (Used https://www.cuemath.com/mean-median-mode-formula/ to get mathamatical formulas 
	 * and to help me get a better understanding of the functions.)
	 * 
	 * @param userList The list that the user wants to find the mode result.
	 * @return The result of the mode as a double.
	 */
	public double findMode(int[] userList) {

		int maxRepeatCount; // Stores the number of repeats that occurs on each index i.
		int maxRepeatIndex = 0; // Stores the index which had the highest count of repeats. 
		int maxTopRepeatCount = 0; // Stores the top repeat count of the current index with most repeats.

		for(int i = 0; i < userList.length; i++) {
			maxRepeatCount = 0;
			for(int j = 0; j < userList.length; j++) {
				if(userList[i] == userList[j]) {
					maxRepeatCount += 1;
				}
			}
			if(maxRepeatCount > maxTopRepeatCount) {
				maxRepeatIndex = i;
				maxTopRepeatCount = maxRepeatCount;
			}
		}
		return userList[maxRepeatIndex];
	}
	
	
	/** 
	 * Finds the standard deviation of a set or a list of integers.
	 * 
	 * @param userList The list that the user wants to find the standard deviation result.
	 * @return The result of the standard deviation as a double.
	 */
	public double findStandardDeviation(int[] userList) {
		double listMean = findMean(userList);
		double standardDeviation = 0;
		for(int i = 0; i < userList.length; i++) {
			standardDeviation += Math.pow(userList[i] - listMean, 2);
		}
		standardDeviation = Math.sqrt(standardDeviation / userList.length);
		return standardDeviation;
	}


	/** 
	 * Finds the variance of a set or a list of integers.
	 * 
	 * @param userList The list that the user wants to find the variance result.
	 * @return The result of the variance as a double.
	 */
	public double findVariance(int[] userList) {
		double listMean = findMean(userList);
		double variance = 0;
		for(int i = 0; i < userList.length; i++) {
			variance += Math.pow(userList[i] - listMean, 2);
		}
		return variance / userList.length;
	}

	
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
	 * Finds the permutation of n choose r.
	 * 
	 * @param n The integer value of the set's size.
	 * @param r The integer value of the total number of choosen values in the set.
	 * @return Permutation result as BigInteger.
	 */
	public BigInteger findPermutation(int n, int r) {
		BigInteger permutationResult = BigInteger.ONE;
		permutationResult = permutationResult.multiply(findFactorial(n).divide(findFactorial(n - r)));
		return permutationResult;
	}
	

	/** 
	 * Finds the combination of n choose r.
	 * 
	 * @param n The integer value of the set's size.
	 * @param r The integer value of the total number of choosen values in the set.
	 * @return Combination result as BigInteger.
	 */
	public BigInteger findCombination(int n, int r) {
		BigInteger combinationResult = BigInteger.ONE;
		combinationResult = combinationResult.multiply(findFactorial(n).divide(findFactorial(r)
		.multiply(findFactorial(n - r))));
		return combinationResult;
	}


	/** 
	 * Finds the binomial distribution of n choose r.
	 * 
	 * @param n The integer value of the number of trials.
	 * @param r The integer value of the number of successes desired.
	 * @param p The double value of one trial's probability of success, i.e, 80% would be inputted as 0.80.
	 * @return Binomial distribution result as a double.
	 */
	public double findBinomialDistribution(int n, int x, double p) {
		return (findCombination(n, x).intValue() * Math.pow(p, x) * Math.pow(1 - p, n - x));
	}


	/**
	 * Finds the conditional probability of A given B.
	 * 
	 * @param pAIntersectsB Probability of A and B
	 * @param pB Probability of B
	 * @return Conditional probability result of the probability of A given B.
	 */
	public double findConditionalProbability(double pAIntersectsB, double pB) {
		return pAIntersectsB/pB;
	}


	/**
	 * Finds the geometric distribution of a given set of variables.
	 * 
	 * @param k The first occurrence of success within a trial. 
	 * @param p The probability of success on each trial.
	 * @return The double result of the geometric distribution.
	 */
	public double findGeometricDistribution(int k, double p) {
		return Math.pow(1 - p, k - 1) * p;
	}


	/**
	 * Finds the hypergeometric distribution of a given set of variables.
	 * 
	 * @param N Number of the population
	 * @param n Number of the sample
	 * @param k Number of successes in the population
	 * @param x Number of successes in the sample
	 * @return The BigInteger result of the hypergeometric distribution.
	 */
	public BigInteger findHypergeometricDistribution(int N, int n, int k, int x) {
		BigInteger numerator = findCombination(k, x).multiply(findCombination(N - k, n - x));
		return (numerator.divide(findCombination(N, n)));
	}


	/**
	 * Finds the negative binomial distribution of a given set of variables.
	 * 
	 * @param n Number of trials 
	 * @param k Number of successes
	 * @param p Probability of success
	 * @return The BigInteger result of the negative binomial distribution.
	 */
	public BigInteger findNegativeBinomialDistribution(int n, int k, double p) {
		BigInteger negativeBinomialDistribution = BigInteger.ONE;
		negativeBinomialDistribution = negativeBinomialDistribution.multiply(BigInteger.valueOf((long)(Math.pow(p, k) * Math.pow(1 - p, n - k))));
		return (negativeBinomialDistribution.multiply(findCombination(n - 1, k - 1)));
	}


	// Probability_and_Applied_Statistics_Project_2 Section


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

}
