/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import model.Account;
import model.Tweet;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

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
        em.flush();
        em.refresh(tweet);
        return tweet;

    }

    @Override
    public Tweet getTweet(int id) {
        return em.find(Tweet.class, id);
    }

    @Override
    public List<Tweet> getAllTweets(DateTime aftertime) {
        int pagesize = 50;
        if(aftertime == null){
            return em.createQuery("Select a from Tweet a WHERE a.published > CURRENT_TIMESTAMP order by a.published desc", Tweet.class)
                    .getResultList();
        }
        return em.createQuery("Select a from Tweet a WHERE a.published > :time order by a.published desc", Tweet.class)
                .setParameter("time",aftertime).setMaxResults(pagesize)
                .getResultList();
    }

    @Override
    public List<Tweet> getAllTweetsWithTag(DateTime AfterTime, String tag) {
        if(AfterTime == null){
            return em.createQuery("select t From Tweet t where  :tag member t.tags ORDER BY t.published DESC")
                    .setParameter("tag", tag )
                    .getResultList();

        }
        return em.createQuery("select t From Tweet t where  t.published > :time AND :tag member t.tags ORDER BY t.published DESC")
                .setParameter("tag", tag )
                .setParameter("time",AfterTime )
                .getResultList();
    }

    @Override
    public List<Tweet> getAllTweets(int begin, int max, Account account) {
        return null;
    }

    @Override
    public Tweet updateTweet(Tweet tweet) {
        Tweet tweetExists = em.find(Tweet.class, tweet.getId());
        if (tweetExists == null) {
            return null;
        }
        em.merge(tweet);
        em.flush();
        em.refresh(tweetExists);
        return tweetExists;
    }

    @Override
    public boolean removeTweet(Tweet tweet) {
        Tweet findTweet = em.find(Tweet.class, tweet.getId());
        if(findTweet != null) {
            em.remove(tweet);
            return true;
        }
        return false;
    }

    @Override
    public List<Tweet> getTweetsOfUser(DateTime afterTime, Account account) {
        return em.createQuery("select t from Tweet t where t.tweetedBy = :user ORDER BY t.published DESC",Tweet.class )
                .setParameter("user", account)
                .getResultList();
    }

    @Override
    public List<Tweet> getTweetsMentioned(Account account) {
        return em.createQuery("select t From Tweet t where :user member t.mentions ORDER BY t.published DESC")
                .setParameter("user", account)
                .getResultList();    }

    @Override
    public List<String> getTrends(){
        return em.createNamedQuery("Tweet.getCurrentTrends").getResultList();
    }


    @Override
    public List<Tweet> getTweetsFollowing(Account myAccount, int begin, int max) {
        return  em.createNamedQuery("Tweet.getTweetsOfFollowing2").
                setParameter("userid", myAccount.getId())
                .setMaxResults(max)
                .getResultList();
    }
}
