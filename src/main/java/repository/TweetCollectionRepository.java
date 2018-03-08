package repository;

import model.Tweet;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sjoerd on 8-3-2018.
 */
public class TweetCollectionRepository implements TweetRepository {

    private List<Tweet> tweets;
    private int userid = -1;

    public TweetCollectionRepository() {
        tweets = new ArrayList();

    }

    private int getNewTweetId() {
        return userid++;
    }


    public Tweet createTweet(Tweet tweet) {
        tweet.setId(getNewTweetId());
        tweets.add(tweet);

        return tweet;
    }

    public Tweet getTweet(int tweetid) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == tweetid) {
                return tweet;
            }
        }
        return null;
    }

    public List<Tweet> getAllTweets(int begin, int max) {
        return tweets.subList(begin, max);
    }


    public Tweet updateTweet(Tweet tweet) {
        for (Tweet t : tweets) {
            if (t.getId() == tweet.getId()) {
                tweets.set(tweets.indexOf(tweet), tweet);
                return tweet;
            }
        }
        return null;
    }

    public boolean removeTweet(Tweet tweet) {
        return tweets.remove(tweet);
    }


    public List<Tweet> getTweetsOfUser(User user, int begin, int max) {

        List<Tweet> returnTweets = new ArrayList<Tweet>();
        for (Tweet t : tweets) {
            if (t.getTweetedBy().getId() == user.getId()) {
                returnTweets.add(t);
            }
        }
        return tweets.subList(begin, max);
    }

    public List<Tweet> getTweetsFollowing(User myAccount, int begin, int max) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        for (User followingUser : myAccount.getFollowing()) {
            for (Tweet t : tweets) {
                if (t.getTweetedBy().getId() == followingUser.getId()) {
                    tweets.add(t);
                }
            }
        }
        return tweets.subList(begin, max);
    }
}
