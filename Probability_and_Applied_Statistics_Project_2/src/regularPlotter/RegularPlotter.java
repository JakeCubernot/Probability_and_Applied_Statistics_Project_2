package regularPlotter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The RegularPlotter class provides the basic functions to plot and alter mathematical equations. 
 * 
 * @author Jake Cubernot 
 */
public class RegularPlotter {

	
	/**
	 * Creates the graph using a given function. 
	 * 
	 * @param minValue minimum x value of function.
	 * @param maxValue maximum x value of function.
	 * @param incrementValue the distance between each x value.
	 * @return plots to the function in a String matrix. 
	 */
    protected String[][] graphFunction(double minValue, double maxValue, double incrementValue) {
		int lengthOfX = (int) Math.ceil((maxValue - minValue) / incrementValue) + 1;
        String[][] sineFunctionResult = new String[lengthOfX][2];
        for (int i = 0; i < lengthOfX; i++) {
			double valueOfX = minValue + i * incrementValue;
			sineFunctionResult[i][0] = Double.toString(valueOfX);
			sineFunctionResult[i][1] = Double.toString(getYOfFunction(valueOfX));
        }
        return sineFunctionResult;
    }


	/**
	 * Gets the y of the given fuction at x.
	 * 
	 * @param x x value of function.
	 * @return the result y of the function as a double.
	 */
	private double getYOfFunction(double x) {
		return Math.sin(x / 2);
	}


	/**
	 * Randomly salts the given function.
	 * 
	 * @param userGraph user's function to salt.
	 * @return the salted function as a String matrix.  
	 */
	protected String[][] saltGraph(String[][] userGraph) {
		Random rng = new Random();
		for (int i = 0; i < userGraph.length; i++) {
			double oldYValue = Double.parseDouble(userGraph[i][1]);
			double newYValue = oldYValue + rng.nextDouble(-3, 3);
			userGraph[i][1] = String.valueOf(newYValue);
		}
		return userGraph;
	}


	/**
	 * Smooths the given function using the moving mean average.
	 * 
	 * @param userGraph user's function to smooth.
	 * @return the smoothed function as a String matrix. 
	 */
	protected String[][] smoothGraph(String[][] userGraph) {
		for (int i = 1; i < userGraph.length - 1; i++) {
			double previousYValue = Double.parseDouble(userGraph[i - 1][1]);
			double currentYValue = Double.parseDouble(userGraph[i][1]);
			double nextYValue = Double.parseDouble(userGraph[i + 1][1]);
			userGraph[i][1] = String.valueOf(getYAverage(previousYValue, currentYValue, nextYValue));
		}
		return userGraph;
	}


	/**
	 * Finds the mean of the Y differece between three neighboring x values. 
	 * 
	 * @param previousYValue previous index.
	 * @param currentYValue current index.
	 * @param nextYValue next index.
	 * @return the mean result as a double. 
	 */
	private double getYAverage(double previousYValue, double currentYValue, double nextYValue) {
		return (previousYValue + currentYValue + nextYValue) / 3;
	}


	/**
	 * Automatically repeats smoothing process of the function until satisfied . 
	 * 
	 * @param userGraph user's function to automatically smooth.
	 * @return
	 */
	protected String[][] autoSmoother(String[][] userGraph) {
		int smoothCounter = 0;
		while (!isSmoothed(userGraph)) {
			userGraph = smoothGraph(userGraph);
			smoothCounter++;
		}
		System.out.println(smoothCounter);
		return userGraph;
	}
	

	/**
	 * Determines when the function is smoothed enough. 
	 * 
	 * @param userGraph user's function to be tested.
	 * @return the boolean of if the function is smoothed. 
	 */
	private boolean isSmoothed(String[][] userGraph) {
		for (int i = 1; i < userGraph.length - 5; i++) {
			double previousYValue = Double.parseDouble(userGraph[i - 1][1]);
			double currentYValue = Double.parseDouble(userGraph[i][1]);
			double nextYValue = Double.parseDouble(userGraph[i + 1][1]);
	
			double yAverage = getYAverage(previousYValue, currentYValue, nextYValue);
	
			double percentage = 0.01; // Evaluates each Y difference within a range 2% below and above the average
			double yAverageMax = yAverage * (1 + percentage); 
        	double yAverageMin = yAverage - (yAverage * percentage); 
	
			boolean isSmoothed = true; 
			for (int j = 0; j < 3; j++) {
				double yDifference = Double.parseDouble(userGraph[i + 2 + j][1]) - 
				Double.parseDouble(userGraph[i + 1 + j][1]);
				if (yDifference < yAverageMin || yDifference > yAverageMax) {
					isSmoothed = false; 
					break; 
				}
			}
	
			if (isSmoothed) {
				return true; 
			}
		}
		return false; 
	} 


    // Method from https://springhow.com/java-write-csv/.
    protected void writeCSVFile(String[][] matrixData, String fileName) throws IOException {
    	
    	File csvFile = new File(fileName);
    	FileWriter fileWriter = new FileWriter(csvFile);
    	
    	for (String[] data : matrixData) {
    	    StringBuilder line = new StringBuilder();
    	    for (int i = 0; i < data.length; i++) {
    	        line.append(data[i]);
    	        if (i != data.length - 1) {
    	            line.append(',');
    	        }
    	    }
    	    line.append("\n");
    	    fileWriter.write(line.toString());
    	}
    	fileWriter.flush();
    	fileWriter.close();
    } 

	
	// Method inspired from https://www.baeldung.com/java-csv-file-array.
	protected String[][] openCSVFile(String fileName) throws FileNotFoundException, IOException {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records.add(Arrays.asList(values));
			}
		}
		String[][] stringArray = new String[records.size()][];
		for (int i = 0; i < records.size(); i++) {
			List<String> innerList = records.get(i);
			stringArray[i] = innerList.toArray(new String[0]);
		}
		return stringArray;
	}

    public void runFunctionPlotter(double minValue, double maxValue, double incrementValue) throws IOException {
        String[][] userSineFunction = graphFunction(minValue, maxValue, incrementValue);
		writeCSVFile(userSineFunction, "User Function.csv");
    }

	public void runSalter(String fileName) throws FileNotFoundException, IOException {
		String[][] userSineFunction = saltGraph(openCSVFile(fileName));
		writeCSVFile(userSineFunction, "User Function with Salting.csv");
	}

	public void runSmoother(String fileName) throws IOException {
		String[][] userSineFunction = smoothGraph(openCSVFile(fileName));
		for (int i = 1; i < 200; i++) {
			userSineFunction = smoothGraph(userSineFunction);
		}
		writeCSVFile(userSineFunction, "User Function with Smoother.csv");
	}
}
