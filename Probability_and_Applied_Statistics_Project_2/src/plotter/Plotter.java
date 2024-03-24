package plotter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Plotter {

    public String[][] graphSineFunction() {
        String[][] sineFunctionResult = new String[200 + 1][2];
        System.out.println(sineFunctionResult.length);
        for (int i = -100; i <= 100; i++) {
			sineFunctionResult[i + 100][0] = Integer.toString(i);
			sineFunctionResult[i + 100][1] = Double.toString(getYOfSineFunction(i));
        }
        return sineFunctionResult;
    }

	private double getYOfSineFunction(int x) {
		return Math.sin(x);
	}

    // Method from https://springhow.com/java-write-csv/.
    private void writeCSVFile(String[][] matrixData, String fileName) throws IOException {
    	
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

    public void run() throws IOException {
        String[][] userSineFunction = graphSineFunction();
		writeCSVFile(userSineFunction, "User Sine Function.csv");
    }
}
