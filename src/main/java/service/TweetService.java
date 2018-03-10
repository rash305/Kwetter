package service;

import model.Tweet;
import model.User;
import repository.JPA;
import repository.TweetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Stateless
public class TweetService {

    @Inject @JPA
    TweetRepository tweetRepository;


    //Region Methods.

    public Tweet createTweet(Tweet tweet){
        return tweetRepository.createTweet(tweet);
    }

    public Tweet updateTweet(Tweet tweet){
        return tweetRepository.updateTweet(tweet);
    }

    public Tweet getTweet(int id){
        return tweetRepository.getTweet(id);
    }

    public List<Tweet> getAllTweets(int startIndex, int endIndex) {
        return tweetRepository.getAllTweets(startIndex, endIndex);
    }

    public List<Tweet> getTweetsOfUser(User user, int startIndex, int endIndex) {
        return tweetRepository.getTweetsOfUser(user, startIndex, endIndex);
    }

    public boolean addLike(Tweet tweet, User user) {
        boolean returnValue = tweet.AddLike(user);
        Tweet t = tweetRepository.updateTweet(tweet);
        return returnValue;
    }

    public void addMentions(Tweet tweet, List<User> users){
        for (User mention:users) {
            tweet.AddMention(mention);
        }
    }
    //Region JPA will handle this logic
    public Collection<User> getTweetLikes(Tweet tweet) {
        return tweet.getLikes();
    }

    public Collection<User> getLikes(Tweet tweet) {
        return tweet.getLikes();
    }

    public List<User> getMentions(Tweet tweet){
        return tweet.getMentions();
    }

    public List<Tweet> getTweetsFollowing(User user, int minIndex, int maxIndex){
        return tweetRepository.getTweetsFollowing(user, minIndex, maxIndex);
    }

    //endregion

}
