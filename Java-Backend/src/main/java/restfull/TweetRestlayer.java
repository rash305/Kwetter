package restfull;

import DTO.TweetDTO;
import com.sun.javafx.collections.NonIterableChange;
import model.Tweet;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import service.TweetService;
import service.UserService;
import util.DTOMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * Created by Sjoerd on 10-3-2018.
 */


@Stateless
@Path("tweets/")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class TweetRestlayer {

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;



    @Context
    SecurityContext securityContext;


    //   @GET
    //  public List<Account> getUser(String name) {
///        return userService.findUserByName(name);
    //   }

    //<editor-fold Desc="Region GRUD">
    @POST
    @JWTTokenNeeded
    public TweetDTO createTweet(TweetDTO tweet) {

        int loggedInUserId = Integer.parseInt(securityContext.getUserPrincipal().getName());
        try {
            return DTOMapper.getTweetDTO(tweetService.createTweet(tweet, loggedInUserId));
        } catch (Exception PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
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
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
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
    @Consumes(APPLICATION_JSON)
    public List<Tweet> getTweets(DateTime time) {
        return tweetService.getAllTweets(time);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("{tweetid}/like")
    @JWTTokenNeeded
    public TweetDTO likeTweet(@PathParam("tweetid") int tweetid) {
        int loggedInUserId = Integer.parseInt(securityContext.getUserPrincipal().getName());

        Tweet returnTweet;
        try {
            returnTweet = tweetService.addLike(tweetid, loggedInUserId);
        } catch (PersistenceException PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return DTOMapper.getTweetDTO(returnTweet);
    }

    @DELETE
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("{tweetid}/like")
    @JWTTokenNeeded
    public TweetDTO deleteLikeTweet(@PathParam("tweetid") int tweetid) {
        int loggedInUserId = Integer.parseInt(securityContext.getUserPrincipal().getName());

        Tweet returnTweet;
        try {
            returnTweet = tweetService.dislike(tweetid, loggedInUserId);
        } catch (Exception ex) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return DTOMapper.getTweetDTO(returnTweet);
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

    @POST
    @JWTTokenNeeded
    @Path("tag")
    public List<TweetDTO> getTagTweets(String tag) {
        return DTOMapper.getTweetDTOList(tweetService.getTweetsWithTag(tag));
    }


    @GET
    @Path("mention/{userid}")
    public List<Tweet> getMentionedTweets(@PathParam("userid") int userid) {
        return tweetService.getMentionedTweet(userid);
    }


}

