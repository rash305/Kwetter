package restfull;

import com.google.gson.Gson;
import model.Tweet;
import model.User;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.*;
import org.mockito.Mock;
import repository.*;
import service.TweetService;
import service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TweetMockitoRestlayerTest {
    @Mock
    private TweetService tweetService = null;
    @Mock
    private UserService userService = null;
    @Mock
    private TweetRepository tweetRepo = null;
    @Mock
    private UserRepository userRepo = null;
    @Mock
    private List<Tweet> tweets = null;
    @Mock
    private List<String> tags = null;
    @Mock
    private List<User> users = null;
    @Mock
    private Tweet tweet1 = null;
    @Mock
    private Tweet tweet2 = null;
    @Mock
    private User user1 = null;
    @Mock
    private User user2 = null;
    @Mock
    private User user3 = null;



    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tweetService = mock(TweetService.class);
        userService = mock(UserService.class);
        tweetRepo = mock(TweetCollectionRepository.class);
        userRepo = mock(UserCollectionRepository.class);

        tweetService.UseCollectionRepository();

        users = mock(ArrayList.class);
        tags = mock(ArrayList.class);
        tags.add("Konijn");
        tags.add("cavia");
        tweets = mock(ArrayList.class);

        user1 = mock(User.class);

        user2 = mock(User.class);
        users.add(user2);
        user3 = mock(User.class);
        users.add(user3);

        tweet1 = mock(Tweet.class);
        tweet2 = mock(Tweet.class);

        user1.setId(1);
        Tweet tweet1 = new Tweet("Tweet message #doggo #cat 1", user1);


        tweetService.createTweet(tweet1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllTweets() {

        when(tweetRepo.getAllTweets(null)).thenReturn(tweets);
        assertEquals(tweets, tweetRepo.getAllTweets(null));
        tweetService.getAllTweets(null);
        verify(tweetRepo).getAllTweets(null);
    }




}