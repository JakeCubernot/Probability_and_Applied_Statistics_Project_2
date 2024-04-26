package stockBot;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import regularPlotter.RegularPlotter;

public class StockDataPlotter extends RegularPlotter {

    private XYSeries stockSeries;
    private XYSeries rsiSeries;
    private XYSeries movingAverageSeries;
    private String[] stockDates;

    public StockDataPlotter() throws FileNotFoundException, IOException {
        stockSeries = new XYSeries("Microsoft Corp, MSFT, Stock (1Y)");
        rsiSeries = new XYSeries("RSI Calculation");
        movingAverageSeries = new XYSeries("Moving Average of MSFT");

        XYSeries overboughtRSISeries = new XYSeries("Overbought Line of the RSI"); 
        XYSeries oversoldRSISeries = new XYSeries("Oversold Line of the RSI"); 

        String[][] graphValues = openCSVFile("F:/Probability_and_Applied_Statistics_Project_2/Probability_and_Applied_Statistics_Project_2/src/stockBot/MSFT.csv");
        stockDates = new String[graphValues.length];
        for (int i = 0; i < graphValues.length; i++) {
            stockDates[i] = graphValues[i][0];
        }
        for (int i = 0; i < graphValues.length; i++) {
            stockSeries.add(i, Double.parseDouble(graphValues[i][1]));
        }

        for (int i = 0; i < 5; i++) {
            smoothGraph(graphValues); 
        }

        for (int i = 0; i < graphValues.length; i++) {
            movingAverageSeries.add(i, Double.parseDouble(graphValues[i][1]));
        }

        for (int i = 0; i < stockSeries.getItemCount(); i++) {
            if (i > 14) {
                rsiSeries.add(i, calculateRSI(stockSeries, i));
                overboughtRSISeries.add(i, 70);
                oversoldRSISeries.add(i, 30);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection(stockSeries);
        dataset.addSeries(rsiSeries);
        dataset.addSeries(overboughtRSISeries);
        dataset.addSeries(oversoldRSISeries);
        dataset.addSeries(movingAverageSeries); 

        JFreeChart chart = ChartFactory.createXYLineChart("Microsoft Corp, MSFT, Stock (1Y)", null, null,
            dataset, PlotOrientation.VERTICAL, true, true, true);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRangeAxis().setRange(0, 450);
        plot.getDomainAxis().setRange(0, 53);
        plot.getDomainAxis().setTickLabelsVisible(false);

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(false);
        chartpanel.setRangeZoomable(false);

        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(chartpanel, BorderLayout.NORTH);

        JFrame frame = new JFrame();
        frame.add(jPanel4);
        frame.pack();
        frame.setVisible(true);
    }

    public double calculateRSI(XYSeries series, int index) {
        double rs = calculateRS(series, index);
        return 100 - 100 / (1 + rs);
    }
    
    public double calculateRS(XYSeries series, int index) {
        int N = 14; // last 14 days
        double sumGain = 0;
        double sumLoss = 0;
    
        for (int i = 0; i < N; i++) {
            double change = series.getY(index - i).doubleValue() - series.getY(index - i - 1).doubleValue();
            if (change > 0) {
                sumGain += change;
            } else if (change < 0) {
                sumLoss -= change; 
            }
        }
    
        double avgGain = sumGain / N;
        double avgLoss = sumLoss / N;
    
        if (avgLoss == 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return avgGain / avgLoss;
        }
    }

    public XYSeries getStockSeries() {
        return stockSeries;
    }
    public XYSeries getRSISeries() {
        return rsiSeries;
    }
    public XYSeries getMovingAverageSeries() {
        return movingAverageSeries;
    }
    public String[] getStockDates() {
        return stockDates;
    }
}
