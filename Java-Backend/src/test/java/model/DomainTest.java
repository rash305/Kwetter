package model;

import org.junit.*;

import java.util.*;

/**
 * Created by Sjoerd on 26-2-2018.
 */
public class DomainTest {

    Account account0;
    Account account1;
    Account account2;
    Account account3;
    Account account4;
    Account account5;
    Account account6;
    Account account7;
    Account account8;
    Account account9;

    Tweet tweet0;
    Tweet tweet1;
    Tweet tweet2;
    Tweet tweet3;
    Tweet tweet4;
    Tweet tweet5;
    Tweet tweet6;
    Tweet tweet7;
    Tweet tweet8;
    Tweet tweet9;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {

        Set<Group> roles = new HashSet<Group>();
        roles.add(new Group("standard"));
        account0 = new Account("username", "test@email.com", "encPass", "Eindhoven", "Dit ben ik", "/avatar/username.png", "website.nl",roles );

        account1 = new Account();
        tweet1 = new Tweet("Tweet 1", account1);

        account2 = new Account();
        tweet2 = new Tweet("Tweet 2", account2);

        account3 = new Account();
        tweet3 = new Tweet("Tweet 3", account3);

        account4 = new Account();
        tweet4 = new Tweet("Tweet 4", account4);

        account5 = new Account();
        tweet5 = new Tweet("Tweet 5", account5);

        account6 = new Account();
        tweet6 = new Tweet("Tweet 6", account6);

        account7 = new Account();
        tweet7 = new Tweet("Tweet 7", account7);

        account8 = new Account();
        tweet8 = new Tweet("Tweet 8", account8);

        account9 = new Account();
        tweet9 = new Tweet("Tweet 9", account9);

    }

    @After
    public void tearDown() throws Exception {

    }




    @org.junit.Test
    public void testTweet(){
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, account0);

        //First tweet so ID is 0
        Assert.assertEquals(tweet.getId(), 0);
        //Validate tweet message
        Assert.assertEquals(tweet.getMessage(), tweetMessage);

        //Validate auto add tweet to user tweet list
        Assert.assertEquals(account0.getTweets().iterator().next(), tweet );


        //validate tweeted by
        Assert.assertEquals(tweet.getTweetedBy(), account0);

        //Validate add no double tweets
        Assert.assertFalse(account0.addTweet(tweet));

        //validate tweet delete
        Assert.assertTrue(account0.deleteTweet(tweet));
        Assert.assertTrue(account0.getTweets().isEmpty());

        Tweet emptyTweet = new Tweet();

    }

    @org.junit.Test
    public void testTweetDatabaseValues(){
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, account0);
        //Validate database filled value
        Assert.assertNull(tweet.getPublished());
     }

    @org.junit.Test
    public void testTweetMentions() {
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, account0);

        // Test mentions
        Assert.assertTrue(tweet.AddMention(account1));

        Assert.assertEquals(tweet.getMentions().iterator().next(), account1);

        Assert.assertTrue(tweet.DeleteMention(account1));

        Assert.assertTrue(tweet.getMentions().isEmpty());

    }

    @org.junit.Test
    public void testTweetTags() {
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, account0);

        // Test tags
        String tag = "TestTag";
        Assert.assertTrue(tweet.AddTag(tag));

        Assert.assertEquals(tweet.getTags().iterator().next(), tag);

        // Accept double tags
        Assert.assertTrue(tweet.AddTag(tag));
        Iterator<String> iterator =  tweet.getTags().iterator();
        iterator.next();
        Assert.assertEquals(iterator.next(), tag);

        Assert.assertTrue(tweet.DeleteTag(tag));
        Assert.assertTrue(tweet.DeleteTag(tag));

        Assert.assertTrue(tweet.getTags().isEmpty());

        Assert.assertTrue(tweet.AddTag(tag));

        String newTag = "new tag";
        Assert.assertTrue(tweet.AddTag(newTag));

        Assert.assertTrue(tweet.DeleteTag(0));

        Assert.assertEquals(tweet.getTags().iterator().next(), newTag);
    }

    @org.junit.Test
    public void testTweetLikes() {
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, account0);

        // Test likes
        Assert.assertTrue(tweet.AddLike(account1));

        Assert.assertEquals(tweet.getLikes().iterator().next(), account1);

        Assert.assertEquals(tweet.getLikes().iterator().next(), account1);


        // Accept no double likes
        Assert.assertFalse(tweet.AddLike(account1));

        // Test delete like
        Assert.assertTrue(tweet.DeleteLike(account1));

        Assert.assertTrue(tweet.getLikes().isEmpty());
    }



}
