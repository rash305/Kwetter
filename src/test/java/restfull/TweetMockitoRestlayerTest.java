package restfull;

import model.Tweet;
import model.Account;
import org.junit.*;
import org.mockito.Mock;
import repository.*;
import service.TweetService;
import service.UserService;

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
    private List<Account> accounts = null;
    @Mock
    private Tweet tweet1 = null;
    @Mock
    private Tweet tweet2 = null;
    @Mock
    private Account account1 = null;
    @Mock
    private Account account2 = null;
    @Mock
    private Account account3 = null;



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

        accounts = mock(ArrayList.class);
        tags = mock(ArrayList.class);
        tags.add("Konijn");
        tags.add("cavia");
        tweets = mock(ArrayList.class);

        account1 = mock(Account.class);

        account2 = mock(Account.class);
        accounts.add(account2);
        account3 = mock(Account.class);
        accounts.add(account3);

        tweet1 = mock(Tweet.class);
        tweet2 = mock(Tweet.class);

        account1.setId(1);
        Tweet tweet1 = new Tweet("Tweet message #doggo #cat 1", account1);


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

    @Test
    public void testGetAllTweetsOfUser() {
        Account account2 = new Account();
        account2.setId(99);
        List<Tweet> tweetList = new ArrayList<>();
        Tweet tweet1 = new Tweet("Tweet message 3", account3);
        tweetList.add(tweet1);
        account2.addTweet(tweet1);

        when(userRepo.getUser(99)).thenReturn(account2);

        when(tweetRepo.getTweetsOfUser(null, account1)).thenReturn(tweetList);
        assertTrue(tweetList.containsAll(tweetService.getTweetsOfUser(null, account2.getId())));
        assertTrue(tweetList.containsAll(tweetRepo.getTweetsOfUser(null, account2)));
        verify(tweetRepo).getTweetsOfUser(null, account2);
        verify(tweetService).getTweetsOfUser(null, account2.getId());
    }


}