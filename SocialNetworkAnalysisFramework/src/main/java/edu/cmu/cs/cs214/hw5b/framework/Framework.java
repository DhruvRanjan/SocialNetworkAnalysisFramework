package edu.cmu.cs.cs214.hw5b.framework;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw5b.plugin.AnalysisPlugin;
import edu.cmu.cs.cs214.hw5b.plugin.DataPlugin;

/**
 * This is an interface for the framework.
 * 
 * @author lxslarry
 * 
 */
public interface Framework {

    /**
     * Registers a data plugin with the framework.
     * 
     * @param data
     *            DataPlugin to register.
     */
    void registerDataPlugin(DataPlugin data);

    /**
     * Registers an analysis plugin with the framework.
     * 
     * @param analysis
     *            AnalysisPlugin to register.
     */
    void registerAnalysisPlugin(AnalysisPlugin analysis);

    /**
     * Calls on data to get information about the query.
     * 
     * @param data
     *            the DataPlugin to get the information from.
     * @param query
     *            the string to look up
     */
    void requestData(DataPlugin data, String query);

    /**
     * Notifies the current analysis plugin that new data has arrived.
     * 
     * @param analysis
     *            the current analysis plugin.f
     */
    void notifyAnalysisPlugin(AnalysisPlugin analysis);

    /**
     * Returns all data plugins that have been registered with the framework.
     * 
     * @return list of data plugin objects
     */
    ArrayList<DataPlugin> getDataPlugins();

    /**
     * Returns all analysis plugins that have been registered with the
     * framework.
     * 
     * @return list of analysis plugin objects
     */
    ArrayList<AnalysisPlugin> getAnalysisPlugins();

}
