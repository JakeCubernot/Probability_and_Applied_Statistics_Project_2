package commonsMathPlotter;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.math3.analysis.function.Sin;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import regularPlotter.RegularPlotter;

/**
 * CommonsMathPlotter
 * 
 * Sources: 
 * https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/analysis/function/Sin.html
 * https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/stat/descriptive/moment/Mean.html
 * https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/random/RandomDataGenerator.html
 * 
 * @author Jake Cubernot
 */
public class CommonsMathPlotter extends RegularPlotter {

    protected String[][] graphFunction(double minValue, double maxValue, double incrementValue) {
        int lengthOfX = (int) Math.ceil((maxValue - minValue) / incrementValue) + 1;
        String[][] sineFunctionResult = new String[lengthOfX][2];
        Sin sinFunction = new Sin();
        
        for (int i = 0; i < lengthOfX; i++) {
            double valueOfX = minValue + i * incrementValue;
            sineFunctionResult[i][0] = Double.toString(valueOfX);
            sineFunctionResult[i][1] = Double.toString(sinFunction.value(valueOfX / 2)); 
        }
        return sineFunctionResult;
    }

    protected String[][] saltGraph(String[][] userGraph) {
		RandomDataGenerator rdg = new RandomDataGenerator();
		for (int i = 0; i < userGraph.length; i++) {
			double oldYValue = Double.parseDouble(userGraph[i][1]);
			double newYValue = oldYValue + rdg.nextUniform(-3, 3);
			userGraph[i][1] = String.valueOf(newYValue);
		}
		return userGraph;
	}

    protected String[][] smoothGraph(String[][] userGraph) {

        Mean mean = new Mean();
		for (int i = 1; i < userGraph.length - 1; i++) {
            double[] yValues = new double[3];
			yValues[0] = Double.parseDouble(userGraph[i - 1][1]);
			yValues[1] = Double.parseDouble(userGraph[i][1]);
			yValues[2] = Double.parseDouble(userGraph[i + 1][1]);
			userGraph[i][1] = String.valueOf(mean.evaluate(yValues));
		}
		return userGraph;
	}

    public void runFunctionPlotter(double minValue, double maxValue, double incrementValue) throws IOException {
        String[][] userSineFunction = graphFunction(minValue, maxValue, incrementValue);
        writeCSVFile(userSineFunction, "User Function with Commons Math.csv");
    }

	public void runSalter(String fileName) throws FileNotFoundException, IOException {
		String[][] userSineFunction = saltGraph(openCSVFile(fileName));
		writeCSVFile(userSineFunction, "User Function with Salting with Commons Math.csv");
	}

	public void runSmoother(String fileName) throws IOException {
		String[][] userSineFunction = smoothGraph(openCSVFile(fileName));
		for (int i = 1; i < 200; i++) {
			userSineFunction = smoothGraph(userSineFunction);
		}
		writeCSVFile(userSineFunction, "User Function with Smoother with Commons Math.csv");
	}
}
