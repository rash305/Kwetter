package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class UserTest {

    private User user;
    private User user1;
    private User user2;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user1 = new User();
        user2 = new User();

    }



    @org.junit.Test
    public void testFollowing(){

 /*       Collection<User> following = user.getFollowing();
        //Validate that the user does not follow user1
        Assert.assertFalse(following.contains(user1));

        //Follow a user
        Assert.assertTrue(user.Follow(user1));
        //Can't follow a user twice
        Assert.assertFalse(user.Follow(user1));
        Collection<User> newFollowing = user.getFollowing();

        Assert.assertTrue(newFollowing.contains(user1));*/
    }

    @org.junit.Test
    public void testUnfollow(){
/*        Collection<User> following = user1.getFollowing();
        //Validate that the user does not follow user1
        Assert.assertFalse(following.contains(user2));

        //Follow a user
        Assert.assertTrue(user1.Follow(user2));

        //Validate Following
        Assert.assertTrue( user1.getFollowing().contains(user2));

        //Unfollow
        Assert.assertTrue(user1.unFollow(user2));

        //Validate unfollowing
        Assert.assertEquals(user1.getFollowing(), following);*/
    }

    @org.junit.Test
    public void testFollowers(){

        Collection<User> followers = user.getFollowers();
        //Validate that the user does not follow user1
        Assert.assertFalse(followers.contains(user1));

        //Follow a user
        Assert.assertTrue(user.addFollower(user1));
        //Can't follow a user twice
        Assert.assertFalse(user.addFollower(user1));
        Collection<User> newFollowing = user.getFollowers();

        Assert.assertTrue(newFollowing.contains(user1));
    }

    @org.junit.Test
    public void testLoseFollower(){
        Collection<User> followers = user1.getFollowers();
        //Validate that the user is not followed by user2
        Assert.assertFalse(followers.contains(user2));

        //Follow a user
        Assert.assertTrue(user1.addFollower(user2));

        //Validate Following
        Assert.assertTrue( user1.getFollowers().contains(user2));

        //Unfollow
        Assert.assertTrue(user1.loseFollower(user2));

        //Validate unfollowing
        Assert.assertEquals(user1.getFollowers(), followers);
    }



    @Test
    public void setId() throws Exception {
        user.setId(1);
        assertEquals(user.getId(), 1);
    }

    @Test
    public void setUserName() throws Exception {
        String username = "username";
        user.setUserName(username);
        assertEquals(user.getUserName(), username);
    }

    @Test
    public void setEmail() throws Exception {
        String email = "test@gmail.com";
        user.setEmail(email);
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void setEncryptedPassword() throws Exception {
        String encryptedPass = "nuwbdklawglidghuawhdkbk!2ewe";
        user.setEncryptedPassword(encryptedPass);
        assertEquals(user.getEncryptedPassword(), encryptedPass);
    }

    @Test
    public void setLocation() throws Exception {
        String location = "Netherlands";
        user.setLocation(location);
        assertEquals(user.getLocation(),location);

    }

    @Test
    public void setBio() throws Exception {

        String bio = "mijn omschrijving";
        user.setBio(bio);
        assertEquals(user.getBio(),bio);
    }

    @Test
    public void setAvatarPath() throws Exception {
        String avatarPath = "/test/url/e.png";
        user.setAvatarPath(avatarPath);
        assertEquals(user.getAvatarPath(), avatarPath);
    }

    @Test
    public void setWebsite() throws Exception {
        String website = "www.google.nl";
        user.setWebsite(website);
        assertEquals(user.getWebsite(), website);
    }

    @Test
    public void setTweets() throws Exception {
        List<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(new Tweet("test tweet", user));
        user.setTweets(tweets);

        assertEquals(user.getTweets(), tweets);
    }

    @Test
    public void setFollowing() throws Exception {
        HashSet<User> following= new HashSet<>();
        following.add(new User());
        user.setFollowing(following);
        assertEquals(user.getFollowing(), following);
    }
    @Test
    public void setFollowers() throws Exception {
        HashSet<User> followers= new HashSet<User>();
        followers.add(new User());
        user.setFollowers(followers);
        assertEquals(user.getFollowers(), followers);
    }

    @Test
    public void setRoles() throws Exception {
        Set<USER_ROLE> roles= new HashSet<USER_ROLE>();
        roles.add(new USER_ROLE("Admin"));
        user.setRoles(roles);
        assertEquals(user.getRoles(), roles);
    }




}