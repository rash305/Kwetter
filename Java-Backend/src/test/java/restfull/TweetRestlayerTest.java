package restfull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.*;

public class TweetRestlayerTest {


    private HttpUriRequest request;
    private Gson gson = new Gson();
    private ObjectMapper objectMapper;



    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

   /*   @Test
    public void createTweet() {
      HttpEntityEnclosingRequest request = new HttpPost("http://localhost:8080/kwetter-1.0-SNAPSHOT/api/tweet/create");
            Account user1 = new Account("Admin 1", "email1@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
            user1.setId(1);
            Tweet tweet1 = new Tweet("Tweet message #doggo #cat 1", user1);
            tweet1.AddTag("doggo");
            tweet1.AddTag("Cat");
            HttpEntity entity = new StringEntity(gson.toJson(tweet1), ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            HttpResponse response = HttpClientBuilder.create().build().execute(request);

            Tweet tweet = om.readValue(response.getEntity().getContent(), Tweet.class);

            assertNotNull(tweet.getId());

    }
*/

    @Test
    public void testfindTweetById() {
        try {
            HttpUriRequest request = new HttpGet("http://localhost:8080/kwetter-1.0-SNAPSHOT/api/tweet/get/1");
            HttpResponse response = HttpClientBuilder.create().build().execute(request);

            //Tweet tweet =  objectMapper.readValue(response.getEntity().getContent(), Tweet.class);

        //    assertNotNull(tweet.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}