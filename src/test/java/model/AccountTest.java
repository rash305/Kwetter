package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class AccountTest {

    private Account account;
    private Account account1;
    private Account account2;

    @Before
    public void setUp() throws Exception {
        account = new Account();
        account1 = new Account();
        account2 = new Account();

    }



    @org.junit.Test
    public void testFollowing(){

 /*       Collection<Account> following = account.getFollowing();
        //Validate that the account does not follow account1
        Assert.assertFalse(following.contains(account1));

        //Follow a account
        Assert.assertTrue(account.Follow(account1));
        //Can't follow a account twice
        Assert.assertFalse(account.Follow(account1));
        Collection<Account> newFollowing = account.getFollowing();

        Assert.assertTrue(newFollowing.contains(account1));*/
    }

    @org.junit.Test
    public void testUnfollow(){
/*        Collection<Account> following = account1.getFollowing();
        //Validate that the account does not follow account1
        Assert.assertFalse(following.contains(account2));

        //Follow a account
        Assert.assertTrue(account1.Follow(account2));

        //Validate Following
        Assert.assertTrue( account1.getFollowing().contains(account2));

        //Unfollow
        Assert.assertTrue(account1.unFollow(account2));

        //Validate unfollowing
        Assert.assertEquals(account1.getFollowing(), following);*/
    }

    @org.junit.Test
    public void testFollowers(){

        Collection<Account> followers = account.getFollowers();
        //Validate that the account does not follow account1
        Assert.assertFalse(followers.contains(account1));

        //Follow a account
        Assert.assertTrue(account.addFollower(account1));
        //Can't follow a account twice
        Assert.assertFalse(account.addFollower(account1));
        Collection<Account> newFollowing = account.getFollowers();

        Assert.assertTrue(newFollowing.contains(account1));
    }

    @org.junit.Test
    public void testLoseFollower(){
        Collection<Account> followers = account1.getFollowers();
        //Validate that the account is not followed by account2
        Assert.assertFalse(followers.contains(account2));

        //Follow a account
        Assert.assertTrue(account1.addFollower(account2));

        //Validate Following
        Assert.assertTrue( account1.getFollowers().contains(account2));

        //Unfollow
        Assert.assertTrue(account1.loseFollower(account2));

        //Validate unfollowing
        Assert.assertEquals(account1.getFollowers(), followers);
    }



    @Test
    public void setId() throws Exception {
        account.setId(1);
        assertEquals(account.getId(), 1);
    }

    @Test
    public void setUserName() throws Exception {
        String username = "username";
        account.setUserName(username);
        assertEquals(account.getUserName(), username);
    }

    @Test
    public void setEmail() throws Exception {
        String email = "test@gmail.com";
        account.setEmail(email);
        assertEquals(account.getEmail(), email);
    }

    @Test
    public void setEncryptedPassword() throws Exception {
        String encryptedPass = "nuwbdklawglidghuawhdkbk!2ewe";
        account.setEncryptedPassword(encryptedPass);
        assertEquals(account.getEncryptedPassword(), encryptedPass);
    }

    @Test
    public void setLocation() throws Exception {
        String location = "Netherlands";
        account.setLocation(location);
        assertEquals(account.getLocation(),location);

    }

    @Test
    public void setBio() throws Exception {

        String bio = "mijn omschrijving";
        account.setBio(bio);
        assertEquals(account.getBio(),bio);
    }

    @Test
    public void setAvatarPath() throws Exception {
        String avatarPath = "/test/url/e.png";
        account.setAvatarPath(avatarPath);
        assertEquals(account.getAvatarPath(), avatarPath);
    }

    @Test
    public void setWebsite() throws Exception {
        String website = "www.google.nl";
        account.setWebsite(website);
        assertEquals(account.getWebsite(), website);
    }

    @Test
    public void setTweets() throws Exception {
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet("test tweet", account));
        account.setTweets(tweets);

        assertEquals(account.getTweets(), tweets);
    }

    @Test
    public void setFollowing() throws Exception {
        HashSet<Account> following= new HashSet<>();
        following.add(new Account());
        account.setFollowing(following);
        assertEquals(account.getFollowing(), following);
    }
    @Test
    public void setFollowers() throws Exception {
        HashSet<Account> followers= new HashSet<Account>();
        followers.add(new Account());
        account.setFollowers(followers);
        assertEquals(account.getFollowers(), followers);
    }

    @Test
    public void setRoles() throws Exception {
        Set<Group> roles= new HashSet<Group>();
        roles.add(new Group("Admin"));
        account.setRoles(roles);
        assertEquals(account.getRoleset(), roles);
    }




}