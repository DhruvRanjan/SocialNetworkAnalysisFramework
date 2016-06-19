package edu.cmu.cs.cs214.hw5b.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import edu.cmu.cs.cs214.hw5b.core.Data;

/**
 * 
 * An implementation of the DataPlugin interface using Twitter as a data source
 * 
 * @author Dhruv Ranjan
 * 
 */
public class TwitterDataPlugin implements DataPlugin {

    /**
     * Constant for the maximum number of results to return.
     */
    private static final int NUM_MAX_RESULTS = 5000;

    private TwitterFactory twitterFactory;

    /**
     * sets up the Twitter data plugin by providing access keys and creating a
     * Twitter factory
     */
    public void setup() {

        /*
         * API keys are read from the twitter4j.properties file. The properties
         * file is left blank for this submission.
         */
        this.twitterFactory = new TwitterFactory();
    }

    /**
     * @return Returns the name of this data plugin
     */
    public String getName() {
        return "Twitter Data Plugin";
    }

    /*
     * returns the location a post was made from. If no location can be read,
     * instead returns "No Location Found"
     */
    private String getLocation(Status s) {
        if (s.getPlace() != null) {
            return s.getPlace().getFullName();
        } else {
            return "No Location Found";
        }
    }

    /**
     * Gets the post
     */
    private String getPost(Status s) {
        return s.getText();
    }

    /**
     * Gets the user name.
     */
    private String getUsername(Status s) {
        return s.getUser().getName();
    }

    /**
     * Get the number of retweets as the score.
     */
    private int getScore(Status s) {
        return s.getRetweetCount();
    }

    /**
     * Gets the date.
     */
    private Date getDate(Status s) {
        return s.getCreatedAt();
    }

    /**
     * @return Returns a list of Data objects where each data object contains
     *         information about a post which contained the query string s
     */
    public List<Data> getData(String s) {
        Twitter twitter = this.twitterFactory.getInstance();
        Query q = new Query(s);
        q.setCount(NUM_MAX_RESULTS);
        List<Data> dataList = new ArrayList<Data>();
        try {
            QueryResult queryResult = twitter.search(q);
            List<Status> tweetList = queryResult.getTweets();
            for (int i = 0; i < tweetList.size(); i++) {
                Data data = new Data(getLocation(tweetList.get(i)),
                        getPost(tweetList.get(i)), getDate(tweetList.get(i)),
                        getUsername(tweetList.get(i)),
                        getScore(tweetList.get(i)));
                dataList.add(data);
            }
        } catch (TwitterException exception) {
        }
        return dataList;
    }
}
