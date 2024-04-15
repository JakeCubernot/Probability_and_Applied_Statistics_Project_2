package regularPlotter;

import java.io.IOException;

public class TestRegularPlotter {
    public static void main(String[] args) throws IOException {
        RegularPlotter testPlotter = new RegularPlotter();
        testPlotter.runFunctionPlotter(-25, 25, 0.1);
        testPlotter.runSalter("User Function.csv");
        testPlotter.runSmoother("User Function with Salting.csv");
    }
}
