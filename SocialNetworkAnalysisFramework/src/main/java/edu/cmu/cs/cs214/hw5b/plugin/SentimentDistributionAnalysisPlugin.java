package edu.cmu.cs.cs214.hw5b.plugin;

import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import edu.cmu.cs.cs214.hw5b.core.Data;

/**
 * 
 * Implements the Analysis plugin interface with a plugin which takes a List of
 * data objects and shows a pie chart which displays the percentage of posts
 * with each sentiment score (scores are from 0 to 4)
 * 
 * @author Dhruv Ranjan
 * 
 */
public class SentimentDistributionAnalysisPlugin implements AnalysisPlugin {

    /**
     * A DefaultPieDataset holding the data displays in the pie chart
     */
    private DefaultPieDataset chartData;

    /** {@inheritDoc} */
    public String getName() {
        return "Sentiment Distribution Analysis Plugin";
    }

    /** {@inheritDoc} */
    public void setup() {

    }

    /** {@inheritDoc} */
    public JPanel getPanel() {

        JFreeChart pieChart = ChartFactory.createPieChart(getName(),
                this.chartData);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        return pieChartPanel;

    }

    /**
     * accepts a list of Data objects and a HashMap mapping scores from the
     * sentiment analysis to Lists of data objects whose contents exhibit that
     * score, and uses this data to update the display of the analysis plugin.
     */
    /** {@inheritDoc} */
    public void update(List<Data> data, HashMap<Integer, List<Data>> sentiment) {

        DefaultPieDataset pieData = new DefaultPieDataset();
        for (Integer i : sentiment.keySet()) {
            pieData.setValue("Sentiment: " + i, sentiment.get(i).size());
        }

        this.chartData = pieData;
    }

}
