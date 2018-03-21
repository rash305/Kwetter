package service;

import model.Tweet;
import model.User;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import repository.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Stateless
public class TweetService {

    @Inject @JPA
    TweetRepository tweetRepository;
    @Inject @JPA
    UserRepository userRepository;


    public void UseCollectionRepository(){
        tweetRepository = new TweetCollectionRepository();
        userRepository = new UserCollectionRepository();
    }

    //Region Methods.

    public Tweet createTweet(Tweet tweet){

        return tweetRepository.createTweet(tweet);
    }

    public Tweet updateTweet(Tweet tweet){
        return tweetRepository.updateTweet(tweet);
    }

    public void deleteTweet(int tweetid){
        Tweet tweet = tweetRepository.getTweet(tweetid);
        if(tweet == null){
            throw new NullPointerException();
        }
        tweetRepository.removeTweet(tweet);
    }

    public Tweet getTweet(int id){
        return tweetRepository.getTweet(id);
    }

    public List<Tweet> getAllTweets(DateTime beginTime) {
        return tweetRepository.getAllTweets(beginTime);
    }

    public List<Tweet> getTweetsOfUser(DateTime afterTime, int userid) {
        User user = userRepository.getUser(userid);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        return tweetRepository.getTweetsOfUser(afterTime, user);
    }

    public Tweet addLike(Tweet tweet, int userid) {
        User loggedin = userRepository.getUser(userid);
        tweet = tweetRepository.getTweet(tweet.getId());
        if(loggedin == null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        if(tweet == null ){
            throw new NotFoundException( String.format("Target user can not be found"));
        }
        tweet.AddLike(loggedin);

        return tweetRepository.updateTweet(tweet);
    }

    public Tweet dislike(Tweet tweet, int userid) {
        User loggedin = userRepository.getUser(userid);
        tweet = tweetRepository.getTweet(tweet.getId());
        if(loggedin == null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        if(tweet == null ){
            throw new NotFoundException( String.format("Target user can not be found"));
        }
        tweet.DeleteLike(loggedin);

        return tweetRepository.updateTweet(tweet);
    }

    //Region JPA will handle this logic
    public Collection<User> getTweetLikes(Tweet tweet) {
        return tweet.getLikes();
    }

    public Collection<User> getLikes(Tweet tweet) {
        return tweet.getLikes();
    }


    public List<Tweet> getTweetsFollowing(int userid, int minIndex, int maxIndex){

        User loggedinUser = userRepository.getUser(userid);
        if(loggedinUser== null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        return tweetRepository.getTweetsFollowing(loggedinUser, minIndex, maxIndex);
    }

    public List<String> getTrends(){
        return tweetRepository.getTrends();
    }

    public List<Tweet> getTweetsWithTag(String tag, DateTime time) {
        return tweetRepository.getAllTweetsWithTag(time, tag);
    }

    public List<Tweet> getMentionedTweet(int userid) {
        User loggedinUser = userRepository.getUser(userid);
        if(loggedinUser== null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        return tweetRepository.getTweetsMentioned(loggedinUser);
    }


    //endregion

}
