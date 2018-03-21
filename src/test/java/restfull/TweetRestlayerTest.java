package restfull;

import com.google.gson.Gson;
import model.Tweet;
import model.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class TweetRestlayerTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("KwetterBackendTestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private HttpUriRequest request;
    private Gson gson = new Gson();


    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
 /*       try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            Logger.getLogger(TweetRestlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        om = new ObjectMapper();
*/
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @After
    public void tearDown() {
    }

   /*   @Test
    public void createTweet() {
      HttpEntityEnclosingRequest request = new HttpPost("http://localhost:8080/kwetter-1.0-SNAPSHOT/api/tweet/create");
            User user1 = new User("Admin 1", "email1@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
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
}