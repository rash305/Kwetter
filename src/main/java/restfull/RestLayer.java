package restfull;

import model.Tweet;
import model.User;
import repository.JPA;
import repository.TweetRepository;
import repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * Created by Sjoerd on 10-3-2018.
 */

@Stateless
@Path("users")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class RestLayer {

    @Inject @JPA
    UserRepository userService;

    @Inject @JPA
    TweetRepository tweetService;

    @GET
    public List<User> getUser(String name) {
        return userService.findUserByName(name);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("createuser")
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @GET
    @Path("finduser/{id}")
    public User findUser(@PathParam("id") int id) {
        return userService.getUser(id);
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Path("removetweet")
    public boolean removeTweet(Tweet tweet) {
        return tweetService.removeTweet(tweet);
    }

}

