package edu.cmu.cs.cs214.hw5b.core;

import java.util.Date;

/**
 * This is the object used for communication of data. A data object will only
 * have information for a single entity.
 * 
 * It contains the "location" of a post, the actual post, the user who put up
 * the post, the date it went up and an associated score that goes with the
 * post.
 * 
 * @author lxslarry
 * 
 */
public class Data {

    /**
     * Location where the post occurred.
     */
    private String location;

    /**
     * The string of the text in the post.
     */
    private String post;

    /**
     * The date this post was created.
     */
    private Date date;

    /**
     * The name of the user who created it.
     */
    private String username;

    /**
     * A score value used to rank the data.
     */
    private int score;

    /**
     * Sets up the object
     * 
     * @param l
     *            the location
     * @param p
     *            the post
     * @param d
     *            the date
     * @param u
     *            the username
     * @param s
     *            the score
     */
    public Data(String l, String p, Date d, String u, int s) {
        this.location = l;
        this.post = p;
        this.date = d;
        this.username = u;
        this.score = s;
    }

    /**
     * Get the location
     * 
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the post
     * 
     * @return the post
     */
    public String getPost() {
        return post;
    }

    /**
     * Get the date
     * 
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the username
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the score.
     * 
     * @return the score
     */
    public int getScore() {
        return score;
    }

}
