package edu.cmu.cs.cs214.hw5b.plugin;

import java.util.ArrayList;
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
 * data objects and shows a pie chart which displays the top 10 posts in the
 * data list in terms of score
 * 
 * @author Dhruv Ranjan
 * 
 */
public class TopScoreAnalysisPlugin implements AnalysisPlugin {

    private static final int TEN = 10;

    /* A list of Data objects obtained from the framework */
    private List<Data> dataList;

    /* a DefaultPieDataset holding the data displays in the pie chart */
    private DefaultPieDataset chartData;

    /** {@inheritDoc} */
    public String getName() {
        return "Top Score Plugin";
    }

    /** {@inheritDoc} */
    public void setup() {

    }

    /*
     * returns a list of Data objects with the top 10 scores
     */
    private List<Data> findTopTen() {
        List<Data> newDataList = new ArrayList<Data>();
        for (int i = 0; i < this.dataList.size(); i++) {
            if (newDataList.size() < TEN) {
                newDataList.add(this.dataList.get(i));
            } else if (this.dataList.get(i).getScore() > findLowestScore(
                    newDataList).getScore()) {
                newDataList.remove(findLowestScore(newDataList));
                newDataList.add(this.dataList.get(i));
            }
        }
        return newDataList;
    }

    /**
     * Gets the lowest scoring data object from the dataList.
     */
    private Data findLowestScore(List<Data> dataList) {
        Data currentLowest = null;
        for (int i = 0; i < dataList.size(); i++) {
            if (currentLowest == null
                    || currentLowest.getScore() > dataList.get(i).getScore()) {
                currentLowest = dataList.get(i);
            }
        }
        return currentLowest;
    }

    /**
     * @return returns a JPanel containing the pie chart
     */
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
    public void update(List<Data> data, HashMap<Integer, List<Data>> sentiment) {

        this.dataList = data;
        DefaultPieDataset pieData = new DefaultPieDataset();
        List<Data> newDataList = findTopTen();
        for (int i = 0; i < TEN; i++) {
            if (newDataList.get(i) != null) {
                pieData.setValue(newDataList.get(i).getUsername(), newDataList
                        .get(i).getScore());
            }
        }
        this.chartData = pieData;
    }

}
