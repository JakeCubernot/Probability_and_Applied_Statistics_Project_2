package stockBot;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestStockBot {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        StockBot stockBot = new StockBot();
        stockBot.runStockBot();
    }
}
