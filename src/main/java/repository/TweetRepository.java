package repository;

import model.Tweet;
import model.User;

import java.util.List;

/**
 * Created by Sjoerd on 8-3-2018.
 */
public interface TweetRepository {
    Tweet createTweet(Tweet tweet);

    Tweet getTweet(int id);

    Tweet updateTweet(Tweet tweet);

    boolean removeTweet(Tweet tweet);

    List<Tweet> getTweetsOfUser(User user, int begin, int max);

    List<Tweet> getTweetsFollowing(User myAccount, int begin, int max);



}
