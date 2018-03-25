package restfull;

import model.Group;
import model.Account;

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

    @GET
    @Produces({APPLICATION_JSON})
    @Consumes(APPLICATION_JSON)
    @Path("get/{id}")
    public Account findUser(@PathParam("id") int id) {
        Account returnAccount = null;
        try {
            returnAccount = userService.getUser(id);
        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnAccount;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{page}")
    public List<Account> getUsers(@PathParam("page") int page) {
        List<Account> returnAccount = null;
        try {
            returnAccount = userService.getUsers(page);

        }
        catch (PersistenceException PersistEx){
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return returnAccount;
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
    @Path("following/{id}")
    public List<Account> getFollowing(@PathParam("id") int id) {
        List<Account> following = new ArrayList<>();
        following.addAll(userService.getFollowing(id));
        return following;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("followers/{id}")
    public List<Account> getFollowers(@PathParam("id") int id) {
        List<Account> following = new ArrayList<>();
        following.addAll(userService.getFollowers(id));
        return following;
    }

    @POST
    @Path("followers/{id}/add/{myId}")
    public boolean addFollower(@PathParam("id") int id, @PathParam("myId") int loggedinId) {
        return userService.addFollower(id, loggedinId);
    }

    @POST
    @Path("followers/{id}/remove/{myId}")
    public boolean removeFollower(@PathParam("id") int id, @PathParam("myId") int loggedinId) {
        return userService.removeFollower(id, loggedinId);
    }


    //</editor-fold >


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

