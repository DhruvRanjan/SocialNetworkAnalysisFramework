***********************************************************************************************************
HOW TO IMPLEMENT PLUGINS
************************************************************************************************************

------------------------------------------------------------------------------------------------------------
How to add Plugins
------------------------------------------------------------------------------------------------------------

To add a new Data Plugin, in the main method in the Main.java class, add the line framework.registerDataPlugin(new ****), where **** is the name of your plugin. For example, to register a Twitter Data plugin we could use the line framework.registerDataPlugin(new TwitterDataPlugin()); 

To add a new Analysis plugin, follow the same steps for adding a new Data plugin but use the method framework.registerAnalysisPlugin(new ****) instead. 

------------------------------------------------------------------------------------------------------------
Sentiment Analysis
------------------------------------------------------------------------------------------------------------

One of the main features of our framework is the ability to perform sentiment analysis on posts, using the Stanford NLP core library. This library analyzes the contents of posts and scores them from 0 to 4, with 0 being the most negative sentiment and 4 being the most positive sentiment. 

------------------------------------------------------------------------------------------------------------
Data Plugins
------------------------------------------------------------------------------------------------------------

The Data Plugin Interface contains 3 methods: 

1) public String getName()
For this method, return the name of your Data Plugin. For example, if your Data Plugin pulls data from Twitter using the Twitter API, you could have this method return "Twitter Data Plugin". 

2) public void setup()
This method handles anything you need to do to set up your data plugin. For example, you could define API keys needed to pull data from an API in this method. 

3) public List<Data> getData(String s)
This method retrieves data using an API and parses data into Data Objects (see below) so they can be processed by our framework. 

------------------------------------------------------------------------------------------------------------
Data Objects
------------------------------------------------------------------------------------------------------------

A data object contains: 
1) private String post: The contents of a post represented as a String 
2) private String username: The name of the user who made the post
3) private String location: The location the post was made from. Note that many social media sites do not publish this data, or if they do it is up to the users discretion whether they want it to be public, so many posts may not have this data. If so, this can be left blank, or a descriptive string such as "No Location" can be used instead. 
4) private Date date: The date the post was made on 
5) private int score: A score associated with the post. The way the score is calculated is defined by the data plugin. For example, a Twitter data plugin could define score as the number of followers the user who made the post has, the number of retweets a post has, or any combination of different factors. 

------------------------------------------------------------------------------------------------------------
Analysis Plugins
------------------------------------------------------------------------------------------------------------

The Analysis Plugin Interface contains 3 methods: 

1) public String getName()
For this method, return the name of your Analysis Plugin.

2) public void setup()
This method handles any setup you need to do for your analysis plugin, like creating objects necessary for your plugin. 

3) public void update(List<Data> data, HashMap<Integer, List<Data>> sentiment) 
This method describes what occurs when an update call is made to the analysis plugin. It takes a List of Data objects and a HashMap mapping sentiment values (ranging from 0 to 4, where 0 is the most negative and 4 is the most positive) to Data objects whose posts exhibit that sentiment, and uses this data to perform some type of analysis. This should also update GUI elements relevant to the analysis plugin. 

4) public JPanel getPanel()
Returns a JPanel containing any GUI elements the analysis plugin wants to display. 
