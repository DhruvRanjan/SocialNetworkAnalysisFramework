package edu.cmu.cs.cs214.hw5b.gui;

import edu.cmu.cs.cs214.hw5b.framework.SentimentFramework;
import edu.cmu.cs.cs214.hw5b.plugin.SentimentDistributionAnalysisPlugin;
import edu.cmu.cs.cs214.hw5b.plugin.TimeSeriesAnalysisPlugin;
import edu.cmu.cs.cs214.hw5b.plugin.TopScoreAnalysisPlugin;
import edu.cmu.cs.cs214.hw5b.plugin.TwitterDataPlugin;

/**
 * Runs the GUI object.
 * 
 * @author lxslarry
 * 
 */
public class Main {

    /**
     * Starts the GUI.
     */
    public static void main(String[] args) {
        SentimentFramework framework = new SentimentFramework();
        framework.registerDataPlugin(new TwitterDataPlugin());
        framework.registerAnalysisPlugin(new TimeSeriesAnalysisPlugin());
        framework.registerAnalysisPlugin(new TopScoreAnalysisPlugin());
        framework .registerAnalysisPlugin(new SentimentDistributionAnalysisPlugin());
        new GUI(framework);
    }
}