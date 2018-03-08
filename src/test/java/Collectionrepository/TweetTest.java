package Collectionrepository;

import model.Tweet;
import model.USER_ROLE;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class TweetTest {

    User user0;
    User user1;


    Tweet tweet0;

    @Before
    public void setUp() throws Exception {

        Set<USER_ROLE> roles = new HashSet<USER_ROLE>();
        roles.add(new USER_ROLE("standard"));
        user0 = new User("username", "test@email.com", "encPass", "Eindhoven", "Dit ben ik", "/avatar/username.png", "website.nl",roles );
        tweet0 = new Tweet();

        user1 = new User();

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
        tweet0.setTweetedBy(user0);
        assertEquals(tweet0.getTweetedBy(), user0);
    }

    @Test
    public void setLikes() throws Exception {
        Set<User> likes = new HashSet<User>();
        likes.add(user0);
        tweet0.setLikes(likes);
        assertEquals(tweet0.getLikes(), likes);
    }

    @Test
    public void setMentions() throws Exception {
        List<User> mentions = new ArrayList<User>();
        mentions.add(user0);
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