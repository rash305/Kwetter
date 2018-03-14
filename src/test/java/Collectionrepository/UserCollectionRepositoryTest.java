package Collectionrepository;

import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.UserCollectionRepository;
import repository.UserRepository;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class UserCollectionRepositoryTest {

    private User user;
    private User user1;
    private User user2;
    private UserRepository userRepository;


    @Before
    public void setUp() throws Exception {
        user = new User();
        user1 = new User();
        user2 = new User();
        userRepository = new UserCollectionRepository();


    }

    @Test
    public void testCreateUser() {
        String username = "Usertest";
        int id = 99;
        User user = new User();
        user.setEmail("test@email.nl");
        user.setId(id);
        user.setUserName(username);
        User repoUser = userRepository.createUser(user);
        assertNotEquals(repoUser.getId(), id);
        assertEquals(repoUser.getUserName(), username);
    }


    @Test
    public void testGetUser() {
        String username = "Usertest";
        User user = new User();
        user.setEmail("test@email.nl");
        User repoUser = userRepository.createUser(user);
        User getUser = userRepository.getUser(repoUser.getId());
        assertEquals(repoUser, user);
        assertNull(userRepository.getUser(999));
    }

    @Test
    public void testFollowing() {

        Collection<User> following = user.getFollowing();

        //Follow a user
        user.Follow(user1);

        Collection<User> newFollowing = user.getFollowing();
        User repoUser = userRepository.createUser(user);
        Collection<User> repoFollowing = userRepository.getFollowing(repoUser);
        assertEquals(repoFollowing, newFollowing);

        assertNull(userRepository.getFollowing(new User()));
    }


    @Test
    public void testFollowers() {

        Collection<User> followers = user.getFollowers();
        //Validate that the user does not follow user1
        Assert.assertFalse(followers.contains(user1));

        //Follow a user
        Assert.assertTrue(user.addFollower(user1));
        //Can't follow a user twice
        Collection<User> newFollowers = user.getFollowers();

        User repoUser = userRepository.createUser(user);
        Collection<User> repoFollowers = userRepository.getFollowers(repoUser);
        Assert.assertEquals(repoFollowers, newFollowers);

        assertNull(userRepository.getFollowers(new User()));

    }


    @Test
    public void updateUser() throws Exception {
        String oldUsername = "Test eerste username";
        User user = new User();
        user.setUserName(oldUsername);

        User repoUser = userRepository.createUser(user);
        assertEquals(userRepository.getUser(repoUser.getId()).getUserName(), oldUsername);
        String newUserName = "Nieuwe username";
        repoUser.setUserName(newUserName);

        userRepository.updateUser(repoUser);
        assertEquals(userRepository.getUser(repoUser.getId()).getUserName(), newUserName);

        assertNull(userRepository.updateUser(new User()));
    }

    @Test
    public void removeUser() throws Exception {
        String oldUsername = "Test eerste username";
        User user = new User();
        user.setUserName(oldUsername);

        User repoUser = userRepository.createUser(user);
        assertEquals(userRepository.getUser(repoUser.getId()).getUserName(), oldUsername);

        assertTrue(userRepository.removeUser(repoUser));
        assertFalse(userRepository.removeUser(repoUser));
    }


}