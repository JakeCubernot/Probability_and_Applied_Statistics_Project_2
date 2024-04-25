package stockBot;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.jfree.data.xy.XYSeries;

public class StockBot {

    private StockDataPlotter stockData;

    private XYSeries stockSeries;
    private XYSeries rsiSeries;
    private XYSeries movingAverageSeries;
    private String[] stockDates;
    private double balance = 10000;

    public StockBot() throws FileNotFoundException, IOException {
        stockData = new StockDataPlotter();
        stockSeries = stockData.getStockSeries();
        rsiSeries = stockData.getRSISeries();
        movingAverageSeries = stockData.getMovingAverageSeries();
        stockDates = stockData.getStockDates();
    }

    public void runStockBot() {
        if (stockSeries != null) {
            System.out.println(stockSeries.getItemCount());
            for (int i = 0; i < stockSeries.getItemCount(); i++) {
                // Your logic here
            }
        } else {
            System.out.println("Stock series is null.");
        }
    }
}
