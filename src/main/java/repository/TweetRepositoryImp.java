/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import model.Tweet;
import model.User;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author M
 */
@Stateless
@JPA
public class TweetRepositoryImp implements TweetRepository {

    /**
     * Persisting the context of the entity manager. By doing this methods data
     * can be requested or persisted to the database.
     */
    @PersistenceContext(unitName = "kwetterpu")
    EntityManager em;

    public TweetRepositoryImp() {

    }


    @Override
    public Tweet createTweet(Tweet tweet) {
        em.persist(tweet);
        return tweet;

    }

    @Override
    public Tweet getTweet(int id) {
        return null;
    }

    @Override
    public List<Tweet> getAllTweets(int begin, int max) {
        return null;
    }

    @Override
    public Tweet updateTweet(Tweet tweet) {
        try {
            return em.merge(tweet);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean removeTweet(Tweet tweet) {
        return false;
    }

    @Override
    public List<Tweet> getTweetsOfUser(User user, int begin, int max) {
        return null;
    }

    @Override
    public List<Tweet> getTweetsFollowing(User myAccount, int begin, int max) {
        return null;
    }


}
