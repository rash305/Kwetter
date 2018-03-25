package Init;

import model.Account;
import model.Tweet;
import model.Group;
import repository.JPA;
import repository.TweetRepository;
import repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
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

    private Group userrole1;
    private Group userrole2;

    private List<String> tags = new ArrayList();
    private List<Account> mentions = new ArrayList();
    private List<Account> noMentions = new ArrayList();

    private Account account1 = null;
    private Account account2 = null;
    private Account account3 = null;
    private Account account4 = null;


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

    private void createRoles() {
        userrole1 = new Group("Standard");
        userrole2 = new Group("Admin");
        Set<Group> roles = new HashSet<>();
        roles.add(userrole1);
        roles.add(userrole2);
        userRepository.createRoles(roles);
    }

    private void createUsers() {


        account1 = new Account("Admin1", "email1@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        account1.setId(1);
        account1.setEncryptedPassword("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08");
        account1.addRole(userrole1);
        account1.addRole(userrole2);
        try {
            Account accountPersisted = userRepository.createUser(account1);
        } catch (EntityExistsException ex) {

        }

        account2 = new Account("user 2", "email2@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        account2.setId(2);
        account2.setEncryptedPassword("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08");
        account2.addRole(userrole1);
        try {
            account2 = userRepository.createUser(account2);
        } catch (EntityExistsException ex) {

        }
        account3 = new Account("user 3", "email3@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        account3.setId(3);
        account3.setEncryptedPassword("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08");

        account3.addRole(userrole1);
        try {
            account3 = userRepository.createUser(account3);
        } catch (EntityExistsException ex) {

        }
        account4 = new Account("user 4", "email4@gmail.com", "password", "Sittard", "This is my bio", "/avatar/default.png", "Website");
        account4.addRole(userrole1);
        account4.setEncryptedPassword("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08");
        account4.addFollower(account1);
        account4.addFollower(account2);
        try {
            account4 = userRepository.createUser(account4);
        } catch (EntityExistsException ex) {

        }
    }

    private void createTweets() {
        tweet1 = new Tweet("Tweet message #doggo #cat 1", account1);
        tweet1.AddTag("doggo");
        tweet1.AddTag("Cat");
        tweetRepository.createTweet(tweet1);

        tweet2 = new Tweet("Tweet message 2", account2);
        tweet2.AddTag("Cat");
        tweetRepository.createTweet(tweet2);
        tweet3 = new Tweet("Tweet message 3", account3);
        tweetRepository.createTweet(tweet3);
    }
}


