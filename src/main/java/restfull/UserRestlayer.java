package restfull;

import model.USER_ROLE;
import model.User;

import service.TweetService;
import service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.management.relation.Role;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
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
  //  public List<User> getUser(String name) {
///        return userService.findUserByName(name);
 //   }

    //<editor-fold Desc="Region GRUD">
    @POST
    @Consumes(APPLICATION_JSON)
    @Path("createuser")
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @GET
    @Produces({APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_JSON})
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
    @Produces(MediaType.APPLICATION_JSON)
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

    @DELETE
    @Consumes(APPLICATION_JSON)
    @Path("remove/{id}")
    public boolean removeUser(@PathParam("id") int id) {
        return userService.removeUser(id);
    }
    //</editor-fold>

    //<editor-fold Desc="User functionality">
    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("update")
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("following/{id}")
    public List<User> getFollowing(@PathParam("id") int id) {
        List<User> following = new ArrayList<>();
        following.addAll(userService.getFollowing(id));
        return following;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("followers/{id}")
    public List<User> getFollowers(@PathParam("id") int id) {
        List<User> following = new ArrayList<>();
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
    public List<USER_ROLE> getRoles() {
        List<USER_ROLE> returnRoles = null;
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
    public boolean approveUserRole(@PathParam("userid") int userid, USER_ROLE roleid) {
        return userService.approveRole(userid, roleid);
    }


    @DELETE
    @Consumes(APPLICATION_JSON)
    @Path("{userid}/role/remove/{roleid}")
    public boolean removeUserRole(@PathParam("userid") int userid, @PathParam("roleid") int roleid) {
        return userService.revokeRole(userid, roleid);
    }
    //</editor-fold>


}

