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
    private double currentShares = 0; 

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
    
        if (rsiValue > 70) { // If RSI is above 70%, buy 100%
            return 1; // Buy 
        } else if (rsiValue < 30) { // If RSI is below 30%, sell 100%
            return -1; // Sell 
        } else if (stockPrice > movingAverage * 1.03) { // Compares the stock price with the 3% above the MA 
            return 1; // Buy 
        } else if (stockPrice < movingAverage * 0.97) { // Compares the stock price with the 3% below the MA 
            return -1; // Sell 
        } else {
            return 0; // Hold 
        }
    }    

    private double calculateSharesToTrade(int decision, double price) {
        if (decision == 1) { 
            double maxShares = balance * 1.00 / price; // Bot buys the amount of shares worth 100% of their balance
            if (maxShares > 0) {
                return Math.floor(maxShares); 
            } else {
                return 0; 
            }
        } else if (decision == -1) { 
            return currentShares; 
        } else {
            return 0; 
        }
    }  

    private void executeTrade(int decision, double sharesToTrade, double price) {
        double totalCost = price * sharesToTrade;
        if (decision == 1) { // Buy
            if (totalCost > balance) {
                System.out.println("Insufficient balance to buy shares.");
                return;
            } else {
                balance -= totalCost;
                currentShares += sharesToTrade;
            }
        } else if (decision == -1) { // Sell
            double totalIncome = price * sharesToTrade - transactionCost; 
            if (totalIncome < 0) {
                System.out.println("Error: Total income cannot be negative.");
                return;
            }
            balance += totalIncome;
            currentShares -= sharesToTrade;
        } else if (decision == 0 && sharesToTrade > 0) {
            balance -= transactionCost;
        }
    }  
    
    public void runStockBot() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        
        if (stockSeries != null && movingAverageSeries != null && 
        stockSeries.getItemCount() == movingAverageSeries.getItemCount()) {
            for (int i = 0; i < stockSeries.getItemCount(); i++) {
                int decision = tradeEvaluator(i);
                double price = stockSeries.getY(i).doubleValue();
                double sharesToTrade = calculateSharesToTrade(decision, price);
                executeTrade(decision, sharesToTrade, price);
    
                String decisionName;
                switch (decision) {
                    case 1:
                        decisionName = "Buy";
                        break;
                    case -1: 
                        decisionName = "Sell";
                        break;
                    default:
                        decisionName = "Hold";
                        break;
                } 
    
                System.out.println("Trade Date: " + stockDates[i] + ", Decision: " + 
                decisionName + ", Shares: " + currentShares + ", Price: $" + 
                df.format(price) + ", Balance: $" + df.format(balance));
    
                if (i == stockSeries.getItemCount() - 1 && currentShares > 0) {
                    executeTrade(-1, currentShares, price);
                    System.out.println("Final balance: $" + df.format(balance));
                }
            }
        } else {
            System.out.println("Series is null.");
        }
    }
}
