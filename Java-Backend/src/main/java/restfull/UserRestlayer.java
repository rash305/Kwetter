package restfull;

import DTO.AccountProfile;
import DTO.PrivateAccountDetails;
import DTO.TweetDTO;
import model.Group;
import model.Account;

import model.Tweet;
import service.TweetService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
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
@Path("users/")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class UserRestlayer{

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

 //   @GET
  //  public List<Account> getUser(String name) {
///        return userService.findUserByName(name);
 //   }

    //<editor-fold Desc="Region GRUD">
    @POST
    @Consumes(APPLICATION_JSON)
    @Path("create")
    public Account createUser(Account account) {
        if(account.getUserName() == null || account.getUserName().isEmpty()){
            throw new WebApplicationException("Username is required", 400);
        }
        if(account.getEmail() == null || account.getEmail().isEmpty()){
            throw new WebApplicationException("Email is required", 400);
        }

        return userService.createUser(account);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public PrivateAccountDetails createNewuser(PrivateAccountDetails account) {
        if(account.getUsername() == null || account.getUsername().isEmpty()){
            throw new WebApplicationException("Username is required", 400);
        }
        if(account.getEmail() == null || account.getEmail().isEmpty()){
            throw new WebApplicationException("Email is required", 400);
        }
        if(account.getPassword() == null || account.getPassword().isEmpty()){
            throw new WebApplicationException("Password is required", 400);
        }

        try{
       account = userService.createUser(account);}
       catch (Exception ex){
            throw ex;
       }
        return account;
    }

    @GET
    @Produces({APPLICATION_JSON})
    @Consumes(APPLICATION_JSON)
    @Path("{id}")
        public AccountProfile findUser(@PathParam("id") int id) {
        Account account = null;
        AccountProfile returnAccount = null;
        try {
            account = userService.getUser(id);
            returnAccount = new AccountProfile(account);

        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnAccount;
    }

    @GET
    @Produces({APPLICATION_JSON})
    @Consumes(APPLICATION_JSON)
        @Path("username/{username}")
    public AccountProfile findUser(@PathParam("username") String username) {
        Account account = null;
        AccountProfile returnAccountProfile = null;
        try {
            account = userService.getUser(username);
            returnAccountProfile = new AccountProfile(account);
        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnAccountProfile ;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<Account> getUsers() {
        List<Account> returnAccount = null;
        try {
            returnAccount = userService.getUsers();
        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnAccount;
    }

    @DELETE
    @Consumes(APPLICATION_JSON)
    @Path("remove/{id}")
    public boolean removeUser(@PathParam("id") int id) {
        return userService.removeUser(id);
    }
    //</editor-fold>

    //<editor-fold Desc="Account functionality">
    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("update")
    public Account updateUser(Account account) {
        return userService.updateUser(account);
    }

    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("{id}/following")
    public List<AccountProfile> getFollowing(@PathParam("id") int id) {
        List<Account> following = new ArrayList<>();
        List<AccountProfile> returnfollowing = new ArrayList<>();
        following.addAll(userService.getFollowing(id));
        for (Account a :following) {
            returnfollowing.add(new AccountProfile(a) );
        }
        return returnfollowing;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{id}/followers")
    public List<AccountProfile> getFollowers(@PathParam("id") int id) {
        List<AccountProfile> returnfollowers = new ArrayList<>();
        List<Account> followers = new ArrayList<>();
        followers.addAll(userService.getFollowers(id));
        for (Account a : followers) {
            returnfollowers.add(new AccountProfile(a) );
        }
        return returnfollowers;
    }

    @POST
    @Path("{id}/followers")
    public boolean addFollower(@PathParam("id") int id, int loggedinId) {
        return userService.addFollower(id, loggedinId);
    }

    @POST
    @Path("followers/{id}/remove/{myId}")
    public boolean removeFollower(@PathParam("id") int id, @PathParam("myId") int loggedinId) {
        return userService.removeFollower(id, loggedinId);
    }


    //</editor-fold >


    @GET
    @Path("{user}/timeline")
    @JWTTokenNeeded
    public List<TweetDTO> getTimeline(@PathParam("user") int id) {
        try {
            List<Tweet> tweets = tweetService.getTweetsFollowing(id, 0, 99999);
            List<TweetDTO> tweetDTOS = new ArrayList<>();
        for(Tweet t : tweets){
            tweetDTOS.add(new TweetDTO(t));
        }
            return tweetDTOS;
        } catch (Exception ex) {
            throw new WebApplicationException("Account can not be found", Response.Status.NOT_FOUND);
        }
    }
    @GET
    @Path("{user}/tweets")
    @JWTTokenNeeded
    public List<TweetDTO> getTweetsOfUser(@PathParam("user") int id) {
        try {
            List<Tweet> tweets = tweetService.getTweetsOfUser(null , id);
            List<TweetDTO> tweetDTOS = new ArrayList<>();
            for (Tweet t: tweets) {
                tweetDTOS.add(new TweetDTO(t) );
            }
            return tweetDTOS;
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.NOT_FOUND);
        }
    }

    //<editor-fold Desc="Roles">

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("roles")
    public List<Group> getRoles() {
        List<Group> returnRoles = null;
        try {
            returnRoles = userService.getRoles();
        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnRoles;
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Path("{userid}/role")
    public boolean approveUserRole(@PathParam("userid") int userid, Group roleid) {
        //Except when role description is not set. Integer needs to be filled because it is an int value.

        return userService.approveRole(userid, roleid);
    }


    @DELETE
    @Consumes(APPLICATION_JSON)
    @Path("{userid}/role/remove/{roleid}")
    public boolean removeUserRole(@PathParam("userid") int userid, @PathParam("roleid") String roleid) {
        return userService.revokeRole(userid, roleid);
    }
    //</editor-fold>


}

