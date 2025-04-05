package ru.itmo.cs.kdot.lab2.util;

import lombok.extern.log4j.Log4j2;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Log4j2
public class FunctionGraph extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public FunctionGraph(String applicationTitle, String chartTitle, String csvFilePath) {
        super(applicationTitle);
        XYDataset dataset = retrieveDataset(csvFilePath);
        JFreeChart chart = createChart(dataset, chartTitle);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1260, 720));
        setContentPane(chartPanel);
    }

    private static XYDataset retrieveDataset(String csvFilePath) {
        XYSeries series = new XYSeries("Function");
        Path path = Paths.get(csvFilePath);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            Stream<String> lines = bufferedReader.lines().skip(1);
            lines.forEachOrdered(line -> {
                String[] values = line.split(",");
                if(values.length >= 2) {
                    try {
                        double x = Double.parseDouble(values[0].trim());
                        double y = Double.parseDouble(values[1].trim());
                        series.addOrUpdate(x, y);
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                }
            });
        }  catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при прочтении файла CSV: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            log.error(e);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private static JFreeChart createChart(XYDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        customizePlot(plot);
        return chart;
    }

    private static void customizePlot(XYPlot plot) {
        plot.setBackgroundPaint(Color.black);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.getRenderer().setSeriesPaint(0, Color.RED);
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
    }

    public static void displayChart(String chartTitle, String csvFilePath) {
        FunctionGraph graph = new FunctionGraph("Визуализация графики функции", chartTitle, csvFilePath);
        graph.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        graph.pack();
        graph.setLocationRelativeTo(null);
        graph.setVisible(true);
    }
}
