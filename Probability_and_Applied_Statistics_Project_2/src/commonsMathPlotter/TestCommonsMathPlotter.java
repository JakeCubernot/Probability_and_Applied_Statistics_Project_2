package commonsMathPlotter;

import java.io.IOException;

public class TestCommonsMathPlotter {
    public static void main(String[] args) throws IOException {
        CommonsMathPlotter testCommonsMathPlotter = new CommonsMathPlotter();
        testCommonsMathPlotter.runFunctionPlotter(-25, 25, 0.1);
        testCommonsMathPlotter.runSalter("User Function with Commons Math.csv");
        testCommonsMathPlotter.runSmoother("User Function with Salting with Commons Math.csv");
    }
}
