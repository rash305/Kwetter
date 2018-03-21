package model;

import org.junit.*;

import java.util.*;

/**
 * Created by Sjoerd on 26-2-2018.
 */
public class DomainTest {

    User user0;
    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    User user6;
    User user7;
    User user8;
    User user9;

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

        Set<USER_ROLE> roles = new HashSet<USER_ROLE>();
        roles.add(new USER_ROLE("standard"));
        user0 = new User("username", "test@email.com", "encPass", "Eindhoven", "Dit ben ik", "/avatar/username.png", "website.nl",roles );

        user1 = new User();
        tweet1 = new Tweet("Tweet 1", user1);

        user2 = new User();
        tweet2 = new Tweet("Tweet 2", user2);

        user3 = new User();
        tweet3 = new Tweet("Tweet 3", user3);

        user4 = new User();
        tweet4 = new Tweet("Tweet 4", user4);

        user5 = new User();
        tweet5 = new Tweet("Tweet 5", user5);

        user6 = new User();
        tweet6 = new Tweet("Tweet 6", user6);

        user7 = new User();
        tweet7 = new Tweet("Tweet 7", user7);

        user8 = new User();
        tweet8 = new Tweet("Tweet 8", user8);

        user9 = new User();
        tweet9 = new Tweet("Tweet 9", user9);

    }

    @After
    public void tearDown() throws Exception {

    }




    @org.junit.Test
    public void testTweet(){
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, user0);

        //First tweet so ID is 0
        Assert.assertEquals(tweet.getId(), 0);
        //Validate tweet message
        Assert.assertEquals(tweet.getMessage(), tweetMessage);

        //Validate auto add tweet to user tweet list
        Assert.assertEquals(user0.getTweets().iterator().next(), tweet );


        //validate tweeted by
        Assert.assertEquals(tweet.getTweetedBy(), user0);

        //Validate add no double tweets
        Assert.assertFalse(user0.addTweet(tweet));

        //validate tweet delete
        Assert.assertTrue(user0.deleteTweet(tweet));
        Assert.assertTrue(user0.getTweets().isEmpty());

        Tweet emptyTweet = new Tweet();

    }

    @org.junit.Test
    public void testTweetDatabaseValues(){
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, user0);
        //Validate database filled value
        Assert.assertNull(tweet.getPublished());
     }

    @org.junit.Test
    public void testTweetMentions() {
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, user0);

        // Test mentions
        Assert.assertTrue(tweet.AddMention(user1));

        Assert.assertEquals(tweet.getMentions().iterator().next(), user1);

        Assert.assertTrue(tweet.DeleteMention(user1));

        Assert.assertTrue(tweet.getMentions().isEmpty());

    }

    @org.junit.Test
    public void testTweetTags() {
        String tweetMessage = "Test Tweet message";
        Tweet tweet = new Tweet(tweetMessage, user0);

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
        Tweet tweet = new Tweet(tweetMessage, user0);

        // Test likes
        Assert.assertTrue(tweet.AddLike(user1));

        Assert.assertEquals(tweet.getLikes().iterator().next(), user1);

        Assert.assertEquals(tweet.getLikes().iterator().next(), user1);


        // Accept no double likes
        Assert.assertFalse(tweet.AddLike(user1));

        // Test delete like
        Assert.assertTrue(tweet.DeleteLike(user1));

        Assert.assertTrue(tweet.getLikes().isEmpty());
    }



}
