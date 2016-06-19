package edu.cmu.cs.cs214.hw5b.plugin;

import java.util.List;

import edu.cmu.cs.cs214.hw5b.core.Data;

/**
 * 
 * Interface describing the implementation for a data plugin in our framework
 * 
 * @author Dhruv Ranjan
 * 
 */
public interface DataPlugin {

    /**
     * Get the name of the data plugin.
     * 
     * @return Returns the name of the data plugin
     */
    String getName();

    /**
     * Sets up the data plugin. This involves steps like accessing the API.
     */
    void setup();

    /**
     * 
     * @param s
     *            The Query string passed to the data plugin
     * @return A list of data objects, where each data objects describes
     *         information from a post pertaining to the query string
     */
    List<Data> getData(String s);
}
