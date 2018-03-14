package Init;

import model.Tweet;
import model.USER_ROLE;
import model.User;
import repository.JPA;
import repository.TweetRepository;
import repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Startup
@Singleton
public class init {

    @Inject
    @JPA
    TweetRepository tweetRepository;
    @Inject
    @JPA
    UserRepository userRepository;

    private USER_ROLE userrole1;
    private USER_ROLE userrole2;

    private List<String> tags = new ArrayList();
    private List<User> mentions = new ArrayList();
    private List<User> noMentions = new ArrayList();

    private User user1 = null;
    private User user2 = null;
    private User user3 = null;
    private User user4 = null;


    private Tweet tweet1 = null;
    private Tweet tweet2 = null;
    private Tweet tweet3 = null;
    private Tweet tweet4 = null;
    private Tweet tweet5 = null;
    private Tweet tweet6 = null;
    private Tweet tweet7 = null;
    private Tweet tweet8 = null;
    private Tweet tweet9 = null;
    private Tweet tweet10 = null;
    private Tweet tweet11 = null;
    private Tweet tweet12 = null;
    private Tweet tweet13 = null;

    @PostConstruct
    public void init() {
        System.out.println("Fill the database with dummydata");
        createRoles();
        createUsers();
        createTweets();
    }

    private void createRoles(){
        userrole1 = new USER_ROLE("Standard");
        userrole2 = new USER_ROLE("Admin");
        Set<USER_ROLE> roles = new HashSet<>();
        roles.add(userrole1);
        roles.add(userrole2);
        userRepository.createRoles(roles);
    }

    private void createUsers(){


        user1 = new User("Admin 1", "email1@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        user1.addRole(userrole1);
        user1.addRole(userrole2);
        User userPersisted = userRepository.createUser(user1);

        user2 = new User("user 2", "email2@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        user2.addRole(userrole1);
        user2 = userRepository.createUser(user2);

        user3 = new User("user 3", "email3@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        user3.addRole(userrole1);
        user3 = userRepository.createUser(user3);

        user4 = new User("user 4", "email4@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        user4.addRole(userrole1);
        user4 = userRepository.createUser(user4);
    }

    private void createTweets(){
        tweet1 = new Tweet("Tweet message 1", user1);
        tweetRepository.createTweet(tweet1);

        tweet2 = new Tweet("Tweet message 2", user2);
        tweetRepository.createTweet(tweet2);
        tweet3 = new Tweet("Tweet message 3", user3);
        tweetRepository.createTweet(tweet3);
    }
}


