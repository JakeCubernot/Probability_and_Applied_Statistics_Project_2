package statsLibrary;

public class TestStatsLibrary {
    public static void main(String[] args) {
        StatsLibrary test = new StatsLibrary();

        double testResult = test.findPoissonDistribution(1, 3);
        System.out.println("Poisson Distribution where x=1 and lambda=3: " + (testResult * 100) + "%"); 

        testResult = test.findChebyshevsTheorem(2);
        System.out.println("\nChebyshev's Theorem where k=2: " + (testResult * 100) + "%"); 

        testResult = test.findUniformDistribution(2, 5); 
        System.out.println("\nUniform Distribution where a=2 and b=5: " + (testResult * 100) + "%"); 
    }
}
