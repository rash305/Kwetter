package restfull;

import model.Tweet;
import model.USER_ROLE;
import model.User;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import service.TweetService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * Created by Sjoerd on 10-3-2018.
 */


@Stateless
@Path("tweet/")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class TweetRestlayer {

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    //   @GET
    //  public List<User> getUser(String name) {
///        return userService.findUserByName(name);
    //   }

    //<editor-fold Desc="Region GRUD">
    @POST
    @Path("create")
    public Tweet createTweet(Tweet tweet) {
        try {
            return tweetService.createTweet(tweet);
        } catch (Exception PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("update")
    public Tweet updateTweet(Tweet tweet){
        if(tweet.getId() == 0){
            throw new WebApplicationException("No tweet id is given.",Response.Status.BAD_REQUEST);
        }
        Tweet tweet1 = tweetService.updateTweet(tweet);
        if(tweet1 == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return tweet1;
    }

    @GET
    @Path("{user}")
    public List<Tweet> getTimeline(@PathParam("user") int id) {
        try {
            return tweetService.getTweetsFollowing(id, 0, 99999);
        } catch (Exception ex) {
            throw new WebApplicationException("User can not be found", Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("get/{tweetid}")
    public Tweet getTweet(@PathParam("tweetid") int id) {
        try {
            Tweet tweet = tweetService.getTweet(id);
            if(tweet == null){
                throw new WebApplicationException("Tweet not found.", Response.Status.NOT_FOUND);

            }
            return tweet;
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("by/{user}")
    public List<Tweet> getTweetsOfUser(@PathParam("user") int id, DateTime time) {
        try {
            return tweetService.getTweetsOfUser(time, id);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }


    @GET
    @Consumes(APPLICATION_JSON)
    public List<Tweet> getTweets(DateTime time) {
        return tweetService.getAllTweets(time);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("like/{userid}")
    public Tweet likeTweet(Tweet tweet, @PathParam("userid") int userid) {
        Tweet returnTweet;
        try {
            returnTweet = tweetService.addLike(tweet, userid);
        } catch (PersistenceException PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnTweet;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("dislike/{userid}")
    public Tweet deleteLikeTweet(Tweet tweet, @PathParam("userid") int userid) {
        Tweet returnTweet;
        try {
            returnTweet = tweetService.dislike(tweet, userid);
        } catch (Exception ex) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnTweet;
    }


    @DELETE
    @Path("delete/{id}")
    public void deleteTweet(@PathParam("id") int id) {
        try {
            tweetService.deleteTweet(id);
        } catch (NullPointerException ex) {
            throw new WebApplicationException("Tweet can not be found", 404);
        }
    }


    @GET
    @Path("trends")
    public List<String> getTrends() {

        return tweetService.getTrends();
    }

    @GET
    @Path("tag/{tagName}")
    public List<Tweet> getTagTweets(@PathParam("tagName") String tag, DateTime time) {
        return tweetService.getTweetsWithTag(tag, time);
    }


    @GET
    @Path("mention/{userid}")
    public List<Tweet> getMentionedTweets(@PathParam("userid") int userid) {
        return tweetService.getMentionedTweet(userid);
    }


}

