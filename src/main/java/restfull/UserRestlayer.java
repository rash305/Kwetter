package restfull;

import model.User;

import service.TweetService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
public class UserRestlayer{

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

 //   @GET
  //  public List<User> getUser(String name) {
///        return userService.findUserByName(name);
 //   }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("createuser")
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("finduser/{id}")
    public User findUser(@PathParam("id") int id) {
        User returnUser = null;
        try {
            returnUser = userService.getUser(id);
        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnUser;
    }

    @GET
    @Path("{page}")
    public List<User> getUsers(@PathParam("page") int page) {
        List<User> returnUser = null;
        try {
            returnUser = userService.getUsers(page);

        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnUser;
    }
    @GET
    @Path("")
    public List<User> getUsers() {
        List<User> returnUser = null;
        try {
            returnUser = userService.getUsers();
        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnUser;
    }

  /*  @POST
    @Produces(APPLICATION_JSON)
    @Path("removetweet")
    public boolean removeTweet(Tweet tweet) {
        return tweetService.removeTweet(tweet);
    }
*/
}

