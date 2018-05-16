package restfull;

import DTO.AccountProfile;
import DTO.PrivateAccountDetails;
import DTO.TweetDTO;
import model.Account;
import model.Group;
import model.Tweet;
import service.TweetService;
import service.UserService;
import util.DTOMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * Created by Sjoerd on 10-3-2018.
 */


@Stateless
@Path("hateaos/users/")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class UserRestlayerHate {

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    @Context
    SecurityContext securityContext;

    @Context
    UriInfo servletUriInfo;

    //   @GET
    //  public List<Account> getUser(String name) {
///        return userService.findUserByName(name);
    //   }

    //<editor-fold Desc="Region GRUD">
    @POST
    @Consumes(APPLICATION_JSON)
    @Path("create")
    public Response createUser(Account account, @Context UriInfo uriInfo) {
        if (account.getUserName() == null || account.getUserName().isEmpty()) {
            throw new WebApplicationException("Username is required", 400);
        }
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            throw new WebApplicationException("Email is required", 400);
        }

        Link self = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("POST").build();
        return Response.ok(userService.createUser(account))
                .links(self).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createNewuser(PrivateAccountDetails account, @Context UriInfo uriInfo) {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new WebApplicationException("Username is required", 400);
        }
        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            throw new WebApplicationException("Email is required", 400);
        }
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            throw new WebApplicationException("Password is required", 400);
        }

        try {
            account = userService.createUser(account);
        } catch (Exception ex) {
            throw ex;
        }


        Link self = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("GET").build();
        Link tweet = Link.fromUri(uriInfo.getAbsolutePath() + "/tweets").rel("tweets").type("GET").build();
        return Response.ok(account)
                .links(self,tweet).build();
    }

    @GET
    @Produces({APPLICATION_JSON})
    @Consumes(APPLICATION_JSON)
    @Path("{id}")
    public Response findUser(@PathParam("id") int id, @Context UriInfo uriInfo) {
        Account account = null;
        AccountProfile returnAccount = null;
        try {
            account = userService.getUser(id);
            returnAccount = new AccountProfile(account);

        } catch (PersistenceException PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Link self = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("GET").build();
        initLinks(returnAccount);
        Link tweet = Link.fromUri(uriInfo.getAbsolutePath() + "/tweets").rel("tweets").type("GET").build();

        return Response.ok(returnAccount)
                .links(self,tweet).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @Path("username/{username}")
    public Response findUser(@PathParam("username") String username, @Context UriInfo uriInfo) {
        Account account = null;
        AccountProfile returnAccountProfile = null;
        try {
            account = userService.getUser(username);
            returnAccountProfile = new AccountProfile(account);
        } catch (PersistenceException PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Link self = Link.fromUri(uriInfo.getAbsolutePath()).rel("self").type("GET").build();
        initLinks(returnAccountProfile);
        return Response.ok(returnAccountProfile)
                .links(self).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response getUsers(@Context UriInfo uriInfo) {
        List<Account> returnAccount = null;
        try {
            returnAccount = userService.getUsers();
        } catch (PersistenceException PersistEx) {
            //Don't show any database exceptions
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                .rel("self").type("GET").build();
        return Response.ok(returnAccount)
                .links(self).build();
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
    public void updateUser(AccountProfile account) {
        userService.updateUser(account);
    }

    @GET
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("{id}/following")
    public Response getFollowing(@PathParam("id") int id, @Context UriInfo uriInfo) {
        List<Account> following = new ArrayList<>();
        List<AccountProfile> returnfollowing = new ArrayList<>();
        following.addAll(userService.getFollowing(id));
        for (Account a : following) {
            returnfollowing.add(new AccountProfile(a));
        }

        returnfollowing.forEach(p -> initLinks(p));
        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                .rel("self").type("GET").build();
        return Response.ok(returnfollowing)
                .links(self).build();
    }

    private void initLinks(AccountProfile accountProfile) {
        UriBuilder uriBuilder = servletUriInfo.getBaseUriBuilder();
        uriBuilder = uriBuilder.path("hateaos/users/");
        uriBuilder = uriBuilder.path(Integer.toString(accountProfile.getId()));
        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link selfLink = linkBuilder.rel("self").type("GET").build();
        accountProfile.addLink(selfLink);

        uriBuilder = servletUriInfo.getBaseUriBuilder();
        uriBuilder = uriBuilder.path("hateaos/users/");
        uriBuilder = uriBuilder.path(Integer.toString(accountProfile.getId()));
        uriBuilder = uriBuilder.path("/tweets");

        linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link tweetsLink = linkBuilder.rel("tweets").type("GET").build();
        accountProfile.addLink(tweetsLink);

        uriBuilder = servletUriInfo.getBaseUriBuilder();
        uriBuilder = uriBuilder.path("hateaos/users/");
        uriBuilder = uriBuilder.path(Integer.toString(accountProfile.getId()));
        uriBuilder = uriBuilder.path("/followers");
        linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link followersLink = linkBuilder.rel("followers").type("GET").build();
        accountProfile.addLink(followersLink);

        uriBuilder = servletUriInfo.getBaseUriBuilder();
        uriBuilder = uriBuilder.path("hateaos/users/");
        uriBuilder = uriBuilder.path(Integer.toString(accountProfile.getId()));
        uriBuilder = uriBuilder.path("/following");
        linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link followingLink = linkBuilder.rel("following").type("GET").build();
        accountProfile.addLink(followingLink);
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{id}/followers")
    public Response getFollowers(@PathParam("id") int id, @Context UriInfo uriInfo) {
        List<AccountProfile> returnfollowers = new ArrayList<>();
        List<Account> followers = new ArrayList<>();
        followers.addAll(userService.getFollowers(id));
        for (Account a : followers) {
            returnfollowers.add(new AccountProfile(a));
        }
        returnfollowers.forEach(p -> initLinks(p));

        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                .rel("self").build();
        return Response.ok(returnfollowers)
                .links(self).build();
    }

    @POST
    @Path("{id}/followers")
    public boolean addFollower(@PathParam("id") int id, int loggedinId) {
        return userService.addFollower(id, loggedinId);
    }

    @DELETE
    @Path("{id}/followers")
    public boolean remove(@PathParam("id") int id) {
        int loggedInUserId = Integer.parseInt(securityContext.getUserPrincipal().getName());

        return userService.removeFollower(id, loggedInUserId);
    }


    //</editor-fold >


    @GET
    @Path("{user}/timeline")
    public Response getTimeline(@PathParam("user") int id, @Context UriInfo uriInfo) {
        try {
            List<Tweet> tweets = tweetService.getTweetsFollowing(id, 0, 99999);
            List<TweetDTO> tweetDTOS = new ArrayList<>();
            for (Tweet t : tweets) {
                tweetDTOS.add(DTOMapper.getTweetDTO(t));
            }
            Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                    .rel("self").build();
            return Response.ok(tweetDTOS)
                    .links(self).build();
    } catch (Exception ex) {
            throw new WebApplicationException("Account can not be found", Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("{user}/tweets")
    public Response getTweetsOfUser(@PathParam("user") int id, @Context UriInfo uriInfo) {
        try {
            List<Tweet> tweets = tweetService.getTweetsOfUser(null, id);
            List<TweetDTO> tweetDTOS = new ArrayList<>();
            for (Tweet t : tweets) {
                tweetDTOS.add(DTOMapper.getTweetDTO(t));
            }
            Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                    .rel("self").build();
            return Response.ok(tweetDTOS)
                    .links(self).build();
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
        } catch (PersistenceException PersistEx) {
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

