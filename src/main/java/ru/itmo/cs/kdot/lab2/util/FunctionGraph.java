package ru.itmo.cs.kdot.lab2.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;

public class FunctionGraph extends JFrame {
    private static final long serialVersionUID = 1L;

    public FunctionGraph(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        // This will create the dataset
        XYDataset dataset = createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }

    private static XYDataset retrieveDataset() {
        TimeSeries series = new TimeSeries("Function");
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        try (FileReader fileReader = new FileReader()) {

        }
        return result;

    }

    private static JFreeChart createChart(XYDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        return chart;
    }

    public static JPanel createDemoPanel(String title) {
        JFreeChart jFreeChart = createChart(retrieveDataset(), title);
        return new ChartPanel(jFreeChart);
    }
}
