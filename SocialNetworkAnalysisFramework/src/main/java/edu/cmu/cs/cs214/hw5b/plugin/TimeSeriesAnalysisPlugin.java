package edu.cmu.cs.cs214.hw5b.plugin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import edu.cmu.cs.cs214.hw5b.core.Data;

/**
 * An analysis plugin that looks at the relationship between the date of the
 * post and their score measures stratified by their sentiment value.
 * 
 * A TimeSeries Graph is used to display the information.
 * 
 * @author lxslarry
 * 
 */
public class TimeSeriesAnalysisPlugin implements AnalysisPlugin {

    /**
     * A map between the setiment score and a XYDataset for the corresponding
     * data.
     */
    private HashMap<Integer, XYDataset> mapping;

    /** {@inheritDoc} */
    public void setup() {
        mapping = new HashMap<Integer, XYDataset>();
    }

    /** {@inheritDoc} */
    public String getName() {
        return "Time Series Analysis Plugin";
    }

    /** {@inheritDoc} */
    public void update(List<Data> data, HashMap<Integer, List<Data>> sentiment) {
        mapping.clear();

        for (Integer value : sentiment.keySet()) {
            TimeSeries series = new TimeSeries("Random Data");

            for (Data d : sentiment.get(value)) {
                Second current = new Second(d.getDate());
                series.addOrUpdate(current, d.getScore());
            }
            mapping.put(value, new TimeSeriesCollection(series));
        }
    }

    /** {@inheritDoc} */
    public JPanel getPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout());

        for (Integer i : mapping.keySet()) {
            JFreeChart c = ChartFactory.createTimeSeriesChart(String.format(
                    "Post Scores Versus Date for Sentiment Value %d", i),
                    "Date", "Score of Post", mapping.get(i), false, false,
                    false);
            ChartPanel cp = new ChartPanel(c);
            p.add(cp, BorderLayout.CENTER);
        }
        return p;
    }
}
