package stockBot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.jfree.data.xy.XYSeries;

/**
 * Sources:
 * https://docs.oracle.com/javase/8/docs/api/java/text/DecimalFormat.html
 */
public class StockBot {
    private StockDataPlotter stockData;

    private XYSeries stockSeries;
    private XYSeries movingAverageSeries;
    private XYSeries rsiSeries;

    private String[] stockDates;
    private double balance = 10000;
    private double transactionCost = 10; 

    public StockBot() throws FileNotFoundException, IOException {
        stockData = new StockDataPlotter();
        stockSeries = stockData.getStockSeries();
        movingAverageSeries = stockData.getMovingAverageSeries();
        rsiSeries = stockData.getRSISeries();
        stockDates = stockData.getStockDates();
    }

    private int tradeEvaluator(int index) {
        double stockPrice = stockSeries.getY(index).doubleValue();
        double movingAverage = movingAverageSeries.getY(index).doubleValue();
        double rsiValue;
    
        if (index >= 14 && index < rsiSeries.getItemCount() + 14) {
            rsiValue = rsiSeries.getY(index - 14).doubleValue();
        } else {
            rsiValue = 50;
        }
    
        if (stockPrice > movingAverage * 1.04 && rsiValue < 70) {
            return 1; // Buy 
        } else if (stockPrice < movingAverage * 0.98 && rsiValue > 30) {
            return -1; // Sell 
        } else {
            return 0; // Hold 
        }
    }

    private double calculateSharesToTrade(int decision, double price, double currentShares) {
        if (decision == 1) { 
            double maxShares = (balance - transactionCost) / price; 
            if (maxShares > 0) {
                return Math.floor(maxShares); // Buy
            } else {
                return 0; // Insufficient balance
            }
        } else if (decision == -1) { 
            return currentShares; // Sell
        } else {
            return 0; // Hold
        }
    }
    

    private void executeTrade(int decision, double sharesToTrade, double price) {
        double totalCost = price * sharesToTrade + transactionCost;
    
        if (decision == 1) {
            if (totalCost > balance) {
                System.out.println("Insufficient balance to buy shares.");
                return;
            } else {
                balance -= totalCost;
            }
        } else if (decision == -1) {
            double totalIncome = price * sharesToTrade - transactionCost;
            if (totalIncome < 0) {
                System.out.println("Error: Total income cannot be negative.");
                return;
            }
            balance += totalIncome;
        }
    }

    public void runStockBot() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        double currentShares = 0; 
        if (stockSeries != null && movingAverageSeries != null && stockSeries.getItemCount() == movingAverageSeries.getItemCount()) {
            for (int i = 0; i < stockSeries.getItemCount(); i++) {
                int decision = tradeEvaluator(i);
                double price = stockSeries.getY(i).doubleValue();
                double sharesToTrade = calculateSharesToTrade(decision, price, currentShares);
                executeTrade(decision, sharesToTrade, price);
    
                if (decision == 1) {
                    currentShares = sharesToTrade; 
                } else if (decision == -1) {
                    currentShares = 0; 
                }
    
                System.out.println("Trade Date: " + stockDates[i] + ", Decision: " + 
                decision + ", Shares: " + currentShares + ", Price: $" + df.format(price) + ", Balance: $" + df.format(balance));
            }
            System.out.println("Final balance: $" + df.format(balance));
        } else {
            System.out.println("Series is null.");
        }
    }
}
