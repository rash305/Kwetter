package repository;

import model.Tweet;
import model.User;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by Sjoerd on 8-3-2018.
 */
@Stateless
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

    public List<Tweet> getAllTweets(DateTime startTime) {

        return tweets;
    }

    @Override
    public List<Tweet> getAllTweetsWithTag(DateTime AfterTime, String tag) {
        // Ignore after time because this is only a performance setting
        List<Tweet> returnTweets = new ArrayList<Tweet>();
        for (Tweet t : tweets) {
            if (t.getTags().contains(tag)) {
                returnTweets.add(t);
            }
        }
        return tweets;
    }

    @Override
    public List<String> getTrends() {
        List<String> trends = new ArrayList<>();
        for (Tweet t: tweets ) {
            trends.addAll(t.getTags());
        }
        List<String> result =
                trends.stream()
                        .map(String::toLowerCase)
                        .collect(groupingBy(identity(), counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.<String, Long> comparingByValue(reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
                        .limit(10)
                        .map(Map.Entry::getKey)
                        .collect(toList());

        return result;
    }

    @Override
    public List<Tweet> getAllTweets(int begin, int max, User user) {
        return null;
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


    public List<Tweet> getTweetsOfUser(DateTime afterTime,User user) {

        List<Tweet> returnTweets = new ArrayList<Tweet>();
        for (Tweet t : tweets) {
            if (t.getTweetedBy().getId() == user.getId()) {
                returnTweets.add(t);
            }
        }
        return tweets;
    }

    public List<Tweet> getTweetsMentioned(User user) {

        List<Tweet> returnTweets = new ArrayList<Tweet>();
        for (Tweet t : tweets) {
            if (t.getMentions().contains(user)) {
                returnTweets.add(t);
            }
        }
        return tweets;
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
