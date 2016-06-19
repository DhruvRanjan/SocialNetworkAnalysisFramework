package edu.cmu.cs.cs214.hw5b.plugin;

import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw5b.core.Data;

/**
 * This is an interface describing the implementation for an analysis plugin.
 * 
 * @author Dhruv Ranjan
 * 
 */
public interface AnalysisPlugin {

    /**
     * Get the name of the analysis plugin.
     * 
     * @return A name describing the analysis plugin .
     */
    String getName();

    /**
     * Handles the setup process for the analysis plugin
     */
    void setup();

    /**
     * Gives the analysis plugin a list of the data as well as a grouped list of
     * data grouped by sentiment. Note that this function should be called
     * before the getPanel() function to insure correctness.
     * 
     * @param data
     *            the original list of data
     * @param sentiment
     *            the grouped list of data by sentiment.
     */
    void update(List<Data> data, HashMap<Integer, List<Data>> sentiment);

    /**
     * Returns the JPanel object to display the analysis.
     * 
     * @return the JPanel object
     */
    JPanel getPanel();

}
