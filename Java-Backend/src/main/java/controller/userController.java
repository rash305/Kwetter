package controller;

import model.Account;
import model.Group;
import model.Tweet;
import service.TweetService;
import service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Init;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named(value = "userControl")
@ViewScoped
public class userController implements Serializable {

    @Inject
    UserService userService;

    @Inject
    TweetService tweetService;

    private List<Account> allUsers;
    private Group adminRole;
    private Group moderatorRole;
    private Group standardRole;
    private Account selectedUser;
    private Tweet selectedTweet;

    public userController() {
    }

    @PostConstruct
    private void init() {
        allUsers = userService.getUsers(0);
        setAllRoles(userService.getRoles());
    }


    public List<Account> getAllUsers() {
        return allUsers;
    }


    private void setAllRoles(List<Group> roles) {

        for (Group group : roles) {
            if (group.getName().equals("Admin")) {
                this.adminRole = group;
            }
            if (group.getName().equals("Moderator")) {
                this.moderatorRole = group;
            }
            if (group.getName().equals("Standard")) {
                this.standardRole = group;
            }

        }
    }

    public void setSelectedUser(Account account) {
        this.selectedUser = (Account) account;
    }

    public Account getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedTweet(Tweet tweet) {
        this.selectedTweet = tweet;
    }

    public Tweet getSelectedTweet() {
        return selectedTweet;
    }


    public void RemoveTweet() {

        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedTweet == null) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No Tweet selected."));


            return;
        }
        context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Tweet deleted."));

        tweetService.deleteTweet(selectedTweet.getId());
        selectedUser.deleteTweet(selectedTweet);
        init();
    }

    public void ToggleModerator() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedUser == null) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No user selected."));
            return;
        }
        
        String loggedin = context.getExternalContext().getRemoteUser();
        if( selectedUser.getUserName().equals(loggedin) )
        {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Can not change own permissions.."));
            return;
        }

        boolean returnvalue = selectedUser.addRole(moderatorRole);
        if (!returnvalue) {
            selectedUser.deleteRole(moderatorRole);
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Successful", "Moderator rights deleted."));
        }
        selectedUser = userService.updateUser(selectedUser);
        init();

        if (returnvalue) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Moderator rights are assigned."));

        }
    }

    public void ToggleAdministrator() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedUser == null) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No user selected."));
            return;
        }
        if( selectedUser.getUserName().equals(context.getExternalContext().getRemoteUser()) )
        {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Can not change own permissions.."));
            return;
        }

        boolean returnvalue = selectedUser.addRole(adminRole);
        if (!returnvalue) {
            selectedUser.deleteRole(adminRole);
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Successful", "Admin rights deleted."));
        }
        selectedUser = userService.updateUser(selectedUser);
        init();

        if (returnvalue) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Admin rights are assigned."));

        }
    }

    public void ToggleStandardRole() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedUser == null) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No user selected."));
            return;
        }

        if( selectedUser.getUserName().equals(context.getExternalContext().getRemoteUser()) )
        {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Can not change own permissions.."));
            return;
        }

        boolean returnvalue = selectedUser.addRole(standardRole);
        if (!returnvalue) {
            selectedUser.deleteRole(standardRole);
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Successful", "Standard rights deleted."));
        }
        selectedUser = userService.updateUser(selectedUser);
        init();

        if (returnvalue) {
            context.addMessage("tweetMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Standard rights are assigned."));

        }
    }


    public void Logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        context.getCurrentInstance().getExternalContext().invalidateSession();
        context.getExternalContext().setResponseStatus(401);

        context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/index.xhtml");

    }

}
