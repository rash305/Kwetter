package model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class TweetTest {

    Account account0;
    Account account1;


    Tweet tweet0;

    @Before
    public void setUp() throws Exception {

        Set<Group> roles = new HashSet<Group>();
        roles.add(new Group("standard"));
        account0 = new Account("username", "test@email.com", "encPass", "Eindhoven", "Dit ben ik", "/avatar/username.png", "website.nl",roles );
        tweet0 = new Tweet();

        account1 = new Account();

    }

    @Test
    public void getId() throws Exception {
        assertEquals(tweet0.getId(), 0);
    }




    @Test
    public void setId() throws Exception {
        tweet0.setId(1);
        assertEquals(tweet0.getId(), 1);
    }

    @Test
    public void setMessage() throws Exception {
        String testMessage = "test Message tweet";
        tweet0.setMessage(testMessage);
        assertEquals(tweet0.getMessage(), testMessage);
    }

    @Test
    public void setTags() throws Exception {
        List<String> tags = new ArrayList<String>();
        tags.add("test tags");
        tweet0.setTags(tags);

        assertEquals(tweet0.getTags(), tags);
    }

    @Test
    public void setTweetedBy() throws Exception {
        tweet0.setTweetedBy(account0);
        assertEquals(tweet0.getTweetedBy(), account0);
    }

    @Test
    public void setLikes() throws Exception {
        Set<Account> likes = new HashSet<Account>();
        likes.add(account0);
        tweet0.setLikes(likes);
        assertEquals(tweet0.getLikes(), likes);
    }

    @Test
    public void setMentions() throws Exception {
        List<Account> mentions = new ArrayList<Account>();
        mentions.add(account0);
        tweet0.setMentions(mentions);
        assertEquals(tweet0.getMentions(), mentions);
    }

    @Test
    public void setPublished() throws Exception {
        Date d = new Date();
        Timestamp time = new Timestamp(d.getTime());
        tweet0.setPublished(time);
        assertEquals(tweet0.getPublished(), time);

    }

}