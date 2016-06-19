package edu.cmu.cs.cs214.hw5b.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import edu.cmu.cs.cs214.hw5b.core.Data;
import edu.cmu.cs.cs214.hw5b.plugin.AnalysisPlugin;
import edu.cmu.cs.cs214.hw5b.plugin.DataPlugin;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * This is an implementation of the Framework interface. Specifically it is a
 * framework that will perform sentiment analysis on the data retrieved by the
 * data plugins using Stanford's NLP Core Library.
 * 
 * Scores for the sentiment are in the range of 0 to 4 where 0 is extremely
 * negative and 4 is extremely positive. The results are are then grouped
 * according to their sentiment value and given to the analysis plugin.
 * 
 * Note that results might take a while to compute due to the amount of work
 * done by the library.
 * 
 * Also note that this can be very cumbersome when run with a DataPlugin that is
 * getting its data from Github.
 * 
 * @author lxslarry
 * 
 */
public class SentimentFramework implements Framework {

    /**
     * List of registered DataPlugins
     */
    private ArrayList<DataPlugin> dataPlugins;

    /**
     * List of registered AnalysisPlugins
     */
    private ArrayList<AnalysisPlugin> analysisPlugins;

    /**
     * StanfordCoreNLP object used to get tokenize the string.
     */
    private StanfordCoreNLP tokenizer;

    /**
     * StanfordCoreNLP object used to get the sentiments.
     */
    private StanfordCoreNLP pipeline;

    /**
     * The last set of data returned by a data plugin.
     */
    private List<Data> lastComputedData;

    /**
     * The last set of grouped data computed by this framework.
     */
    private HashMap<Integer, List<Data>> lastComputedMap;

    /**
     * Initialize the necessary information for this framework.
     */
    public SentimentFramework() {
        dataPlugins = new ArrayList<DataPlugin>();
        analysisPlugins = new ArrayList<AnalysisPlugin>();

        Properties tokenizerProps = new Properties();
        tokenizerProps.setProperty("annotators", "tokenize, ssplit");
        tokenizer = new StanfordCoreNLP(tokenizerProps);

        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(prop);
    }

    /** {@inheritDoc} */
    public void registerDataPlugin(DataPlugin data) {
        dataPlugins.add(data);
        data.setup();
    }

    /** {@inheritDoc} */
    public void registerAnalysisPlugin(AnalysisPlugin analysis) {
        analysisPlugins.add(analysis);
        analysis.setup();
    }

    /**
     * Group the data by sentiment values.
     */
    private HashMap<Integer, List<Data>> getStanfordAnalysis(List<Data> data) {
        HashMap<Integer, List<Data>> collected = new HashMap<Integer, List<Data>>();

        for (Data d : data) {
            int longest = 0;
            int mainSentiment = 0;

            Annotation annotation = tokenizer.process(d.getPost());
            pipeline.annotate(annotation);

            List<CoreMap> sentences = annotation
                    .get(CoreAnnotations.SentencesAnnotation.class);

            if (sentences.size() < 1) {
                continue;
            }

            for (int i = 0; i < 3; i++) {
                int size = sentences.size();
                Random g = new Random();
                CoreMap sentence = annotation.get(
                        CoreAnnotations.SentencesAnnotation.class).get(
                        g.nextInt(size));
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }

            if (collected.containsKey(mainSentiment)) {
                collected.get(mainSentiment).add(d);
            } else {
                ArrayList<Data> newList = new ArrayList<Data>();
                newList.add(d);
                collected.put(mainSentiment, newList);
            }
        }

        return collected;
    }

    /** {@inheritDoc} */
    public void requestData(DataPlugin data, String query) {
        lastComputedData = data.getData(query);
        lastComputedMap = getStanfordAnalysis(lastComputedData);
    }

    /**
     * A call to requestData must be done before this method is called.
     */
    /** {@inheritDoc} */
    public void notifyAnalysisPlugin(AnalysisPlugin analysis) {
        analysis.update(lastComputedData, lastComputedMap);
    }

    /** {@inheritDoc} */
    public ArrayList<DataPlugin> getDataPlugins() {
        return dataPlugins;
    }

    /** {@inheritDoc} */
    public ArrayList<AnalysisPlugin> getAnalysisPlugins() {
        return analysisPlugins;
    }

}
