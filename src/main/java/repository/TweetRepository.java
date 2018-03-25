package repository;

import model.Account;
import model.Tweet;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import java.util.List;

/**
 * Created by Sjoerd on 8-3-2018.
 */

public interface TweetRepository {
    Tweet createTweet(Tweet tweet);

    Tweet getTweet(int id);

    List<Tweet> getAllTweets(DateTime AfterTime);
    List<Tweet> getAllTweetsWithTag(DateTime AfterTime, String tag);

    List<String> getTrends();

    List<Tweet> getAllTweets(int begin, int max, Account account);

    Tweet updateTweet(Tweet tweet);

    boolean removeTweet(Tweet tweet);

    List<Tweet> getTweetsOfUser(DateTime AfterTime, Account account);

    List<Tweet> getTweetsMentioned(Account account);

    List<Tweet> getTweetsFollowing(Account myAccount, int begin, int max);



}
