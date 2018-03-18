package repository;

import model.Tweet;
import model.User;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.eclipse.persistence.jpa.jpql.parser.DatetimeExpressionBNF;

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

    List<Tweet> getAllTweets(int begin, int max, User user);

    Tweet updateTweet(Tweet tweet);

    boolean removeTweet(Tweet tweet);

    List<Tweet> getTweetsOfUser(DateTime AfterTime, User user );

    List<Tweet> getTweetsMentioned(User user );

    List<Tweet> getTweetsFollowing(User myAccount, int begin, int max);



}
