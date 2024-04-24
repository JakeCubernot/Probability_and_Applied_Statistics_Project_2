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

public class StockBot extends RegularPlotter {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        XYSeries stockFunction = new XYSeries("Microsoft Corp, MSFT, Stock (1Y)"); 
        XYSeries rsiFunction = new XYSeries("RSI Calculation"); 
        
        StockBot plotter = new StockBot();

        String[][] graphValues = plotter.openCSVFile("F:/Probability_and_Applied_Statistics_Project_2/Probability_and_Applied_Statistics_Project_2/src/stockBot/MSFT.csv");
        for (int i = 0; i < graphValues.length; i++) {
            stockFunction.add(i, Double.parseDouble(graphValues[i][2])); 
        } 
              
        XYSeriesCollection dataset = new XYSeriesCollection(stockFunction); 
        dataset.addSeries(rsiFunction); 

        JFreeChart chart = ChartFactory.createXYLineChart("Microsoft Corp, MSFT, Stock (1Y)", null, null, 
        dataset, PlotOrientation.VERTICAL, true, true, true);

        XYPlot plot = (XYPlot) chart.getPlot(); 
        plot.getRangeAxis().setRange(250, 450); 
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
}
