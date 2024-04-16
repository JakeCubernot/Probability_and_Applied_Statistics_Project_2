package jFreeChartPlotter;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import regularPlotter.RegularPlotter;

/**
 * Plotter using JFreeChart
 * 
 * Sources: 
 * https://stackoverflow.com/a/12318835
 */
public class JFreeChartPlotter extends RegularPlotter {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        XYSeries normalFunction = new XYSeries("User Function");
        XYSeries saltedFunction = new XYSeries("Salted Function");
        XYSeries smoothedFunction = new XYSeries("Smoothed Function");
        
        JFreeChartPlotter plotter = new JFreeChartPlotter();

        String[][] csvValues = plotter.openCSVFile("F:/Probability_and_Applied_Statistics_Project_2/Probability_and_Applied_Statistics_Project_2/documentation/Plotter Work/Regular Plotter Work/Step 1, User Function.csv");
        for (int i = 0; i < csvValues.length; i++) {
            normalFunction.add(Double.parseDouble(csvValues[i][0]), Double.parseDouble(csvValues[i][1]));
        }
        csvValues = plotter.openCSVFile("F:/Probability_and_Applied_Statistics_Project_2/Probability_and_Applied_Statistics_Project_2/documentation/Plotter Work/Regular Plotter Work/Step 2, User Function with Salting.csv");
        for (int i = 0; i < csvValues.length; i++) {
            saltedFunction.add(Double.parseDouble(csvValues[i][0]), Double.parseDouble(csvValues[i][1]));
        }
        csvValues = plotter.openCSVFile("F:/Probability_and_Applied_Statistics_Project_2/Probability_and_Applied_Statistics_Project_2/documentation/Plotter Work/Regular Plotter Work/Step 3, User Function with Smoother.csv");
        for (int i = 0; i < csvValues.length; i++) {
            smoothedFunction.add(Double.parseDouble(csvValues[i][0]), Double.parseDouble(csvValues[i][1]));
        }
              
        XYSeriesCollection dataset = new XYSeriesCollection(normalFunction);
        dataset.addSeries(smoothedFunction);
        dataset.addSeries(saltedFunction);

        JFreeChart chart = ChartFactory.createXYLineChart(null, null, null, 
        dataset, PlotOrientation.VERTICAL, true, true, true);
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);

        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new BorderLayout());
        jPanel4.add(chartpanel, BorderLayout.NORTH);

        JFrame frame = new JFrame();
        frame.add(jPanel4);
        frame.pack();
        frame.setVisible(true);
    }
}
