package edu.cmu.cs.cs214.hw5b.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.cmu.cs.cs214.hw5b.framework.Framework;
import edu.cmu.cs.cs214.hw5b.plugin.AnalysisPlugin;
import edu.cmu.cs.cs214.hw5b.plugin.DataPlugin;

/**
 * GUI Client for the framework.
 */
public class GUI {
    private static final String FRAMEWORK_TITLE = "Social Network Analytics";
    private static final String SELECT_DATAPLUGIN = "Select Data Plugin:";
    private static final String SELECT_ANALYSISPLUGIN = "Select Analysis Plugin:";
    private static final String DEFAULT_QUERY_TEXT = "Query:";
    private static final String SUBMIT_TEXT = "Search";

    private static final int FILLER_SIZE = 20;

    private Framework framework;

    private JFrame frame;
    private JPanel mainpanel;
    private JComboBox<String> dataDropdown;
    private JComboBox<String> analysisDropdown;
    private JTextField queryText;
    private JLabel queryLabel;

    private JPanel analysisPanel;
    private JPanel lowerPanel;

    /**
     * Initializes the frame.
     */
    public GUI(Framework core) {
        framework = core;

        frame = new JFrame(FRAMEWORK_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 700));

        mainpanel = new JPanel(new BorderLayout());
        createPanel();
        attachListeners();

        frame.add(mainpanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Creates the panels for the GUI display. Upper Panel has two parts: Title
     * Panel - for the project title. Select Panel - for the selection of the
     * plugins and searching for the required data. Lower Panel just gets the
     * JPanel returned from the Analysis Plugin.
     */
    private void createPanel() {

        // Upper panel CONTROLS

        int size = FILLER_SIZE;
        int halfSize = FILLER_SIZE / 2;
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,
                Color.BLACK));

        // Social Network Analysis Label
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(halfSize, size, halfSize, size));
        titlePanel.setBackground(Color.GRAY);
        JLabel title = new JLabel(FRAMEWORK_TITLE);
        title.setForeground(Color.BLACK);
        title.setFont(title.getFont().deriveFont(18.0f));
        titlePanel.add(title);
        upperPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.setBorder(new EmptyBorder(size, size, size, size));
        upperPanel.add(selectPanel, BorderLayout.CENTER);

        // Data Plugin dropdown
        selectPanel.add(new JLabel(SELECT_DATAPLUGIN, JLabel.CENTER));
        dataDropdown = new JComboBox<String>();
        selectPanel.add(dataDropdown);

        // Analysis Plugin dropdown
        selectPanel.add(new JLabel(SELECT_ANALYSISPLUGIN, JLabel.CENTER));
        analysisDropdown = new JComboBox<String>();
        selectPanel.add(analysisDropdown);

        // Query text field
        queryLabel = new JLabel(DEFAULT_QUERY_TEXT, JLabel.CENTER);
        selectPanel.add(queryLabel);
        queryText = new JTextField();
        selectPanel.add(queryText);

        // Search button
        JButton submit = new JButton(SUBMIT_TEXT);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataPlugin dPlugin = getDataPlugin();
                AnalysisPlugin aPlugin = getAnalysisPlugin();
                String query = queryText.getText();
                framework.requestData(dPlugin, query);
                framework.notifyAnalysisPlugin(aPlugin);
                analysisPanel = getAnalysisPlugin().getPanel();
                lowerPanel.removeAll();
                lowerPanel.add(analysisPanel);
                lowerPanel.validate();
                lowerPanel.repaint();
            }
        });
        selectPanel.add(submit);

        mainpanel.add(upperPanel, BorderLayout.NORTH);

        // Lower panel CONTROLS
        lowerPanel = new JPanel(new BorderLayout());
        analysisPanel = new JPanel();
        lowerPanel.add(analysisPanel, BorderLayout.CENTER);

        mainpanel.add(lowerPanel, BorderLayout.CENTER);

    }

    /**
     * Attaching listeners to the dropdowns for selection of plugins.
     */
    private void attachListeners() {
        List<DataPlugin> l = framework.getDataPlugins();
        dataDropdown.removeAllItems();
        for (DataPlugin d : l) {
            dataDropdown.addItem(d.getName());
        }
        List<AnalysisPlugin> l2 = framework.getAnalysisPlugins();
        analysisDropdown.removeAllItems();
        for (AnalysisPlugin d : l2) {
            analysisDropdown.addItem(d.getName());
        }
    }

    /**
     * Returns the selected Data Plugin to send to the framework
     * 
     * @return selected DataPlugin
     */
    private DataPlugin getDataPlugin() {
        String name = (String) dataDropdown.getSelectedItem();
        for (DataPlugin plugin : framework.getDataPlugins()) {
            if (plugin.getName().equals(name)) {
                return plugin;
            }
        }
        return null;
    }

    /**
     * Returns the selected Analysis Plugin to send to the framework
     * 
     * @return selected AnalysisPlugin
     */
    private AnalysisPlugin getAnalysisPlugin() {
        String name = (String) analysisDropdown.getSelectedItem();
        for (AnalysisPlugin plugin : framework.getAnalysisPlugins()) {
            if (plugin.getName().equals(name)) {
                return plugin;
            }
        }
        return null;
    }
}