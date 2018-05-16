package Collectionrepository;

import model.Account;
import model.Tweet;
import model.Group;
import org.junit.Before;
import org.junit.Test;
import repository.TweetCollectionRepository;
import repository.TweetRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 8-3-2018.
 */
public class TweetCollectionRepositoryTest {

    Account account0;
    Account account1;
    TweetRepository tweetRepository;

    Tweet tweet0;

    @Before
    public void setUp() throws Exception {

        Set<Group> roles = new HashSet<Group>();
        roles.add(new Group("standard"));
        account0 = new Account("username", "test@email.com", "encPass", "Eindhoven", "Dit ben ik", "/avatar/username.png", "website.nl",roles );
        tweet0 = new Tweet();

        account1 = new Account();
        tweetRepository = new TweetCollectionRepository();
    }

    @Test
    public void createTweet() throws Exception {
        int tweetId = 99;
        Tweet t = new Tweet("TestTweet", account0);
        t.setId(tweetId);
        Tweet repoTweet = tweetRepository.createTweet(t);
        assertNotEquals(repoTweet.getId(), tweetId);

    }

    @Test
    public void getTweet() throws Exception {
        int tweetId = 99;
        Tweet t = new Tweet("TestTweet", account0);
        t.setId(tweetId);

        Tweet repoTweet = tweetRepository.createTweet(t);
        Tweet getTweet = tweetRepository.getTweet(repoTweet.getId());
        assertEquals(repoTweet,getTweet);
        assertNull(tweetRepository.getTweet(tweetId));
    }

    @Test
    public void updateTweet() throws Exception {
        int tweetId = 99;
        String message = "Test tweet message";
        Tweet t = new Tweet(message, account0);
        t.setId(tweetId);

        Tweet repoTweet = tweetRepository.createTweet(t);
        assertEquals(tweetRepository.getTweet(repoTweet.getId()).getMessage(), message);
        String newTweetMessage = "Nieuwe tweet message";
        repoTweet.setMessage(newTweetMessage);

        tweetRepository.updateTweet(repoTweet);
        assertEquals(tweetRepository.getTweet(repoTweet.getId()).getMessage(), newTweetMessage);

        assertNull(tweetRepository.updateTweet(new Tweet()));
    }

    @Test
    public void removeTweet() throws Exception {

        int tweetId = 99;
        String message = "Test tweet message";
        Tweet t = new Tweet(message, account0);
        t.setId(tweetId);

        Tweet repoTweet = tweetRepository.createTweet(t);
        assertEquals(tweetRepository.getTweet(repoTweet.getId()).getMessage(), message);

        assertTrue(tweetRepository.removeTweet(repoTweet));
        assertFalse(tweetRepository.removeTweet(repoTweet));
    }

    @Test
    public void getTweetsOfUser() throws Exception {
        Account account = new Account();
        account.setId(0);
        account.setUserName("EersteNaam");

        Account account1 = new Account();
        account1.setId(1);
        account1.setUserName("TweedeNaam");


        Tweet tweet1 = new Tweet("Eerste Tweet", account);
        account.addTweet(tweet1);
        tweetRepository.createTweet(tweet1);
        Tweet tweet2 = new Tweet("Tweede Tweet", account1);
        account1.addTweet(tweet2);
        tweetRepository.createTweet(tweet2);

        Tweet tweet3 = new Tweet("Derde Tweet", account);
        account.addTweet(tweet3);
        tweetRepository.createTweet(tweet3);

        Tweet tweet4 = new Tweet("Vierde Tweet", account1);
        account1.addTweet(tweet4);
        tweetRepository.createTweet(tweet4);

        Tweet tweet5 = new Tweet("Vijfde Tweet", account);
        account.addTweet(tweet5);
        tweetRepository.createTweet(tweet5);

        Tweet tweet6 = new Tweet("Zesde Tweet", account1);
        account1.addTweet(tweet6);
        tweetRepository.createTweet(tweet6);

//        List<Tweet> tweetList = tweetRepository.getTweetsOfUser(account1, 0, 200);
       // assertTrue(account1.getTweets().containsAll(tweetList) );
    }

    @Test
    public void getTweetsFollowing() throws Exception {
    }

}