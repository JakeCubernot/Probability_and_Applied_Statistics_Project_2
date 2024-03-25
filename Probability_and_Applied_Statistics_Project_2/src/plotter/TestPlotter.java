package plotter;

import java.io.IOException;

public class TestPlotter {
    public static void main(String[] args) throws IOException {
        Plotter testPlotter = new Plotter();
        testPlotter.runFunctionPlotter(-25, 25, 0.1);
        testPlotter.runSalter("User Function.csv");
        testPlotter.runSmoother("User Function with Salting.csv");
    }
}
